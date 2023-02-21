package com.ds.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ds.dao.BoyDao;
import com.ds.entity.Boy;
import com.ds.entity.Result;
import com.ds.service.BoyFeignService;
import com.ds.service.BoyService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service("boyService")
@CacheConfig(cacheNames = "boy")
//@DS("slave")
public class BoyServiceImpl extends ServiceImpl<BoyDao, Boy> implements BoyService {

    @Resource
    private BoyDao boyDao;

    //@Transactional
    //@Cacheable(key = "#boy.all")
    @Cacheable(keyGenerator = "keyGenerator")
    //@CacheEvict(allEntries = true)//删除所有的boy缓存
    @SentinelResource(value = "getBoys")
    public List<Boy> getBoys() {
        List<Boy> result = new ArrayList<>();
        List<Boy> boyList = boyDao.getBoys();
        boyList.stream().collect(Collectors.groupingBy(boy -> boy.getId() + "-" + boy.getName())).forEach((x, y) -> {
            y.stream().reduce((a, b) -> {
                return new Boy(a.getId(), a.getName(), a.getGirls() + "," + b.getGirls());
            }).ifPresent(c -> result.add(c));
        });
        return result.stream().sorted((d, e) -> d.getId() - e.getId()).collect(Collectors.toList());
    }


    //查询
    @Cacheable(keyGenerator = "keyGenerator")
    public List<Boy> queryBoy() {
        List<Boy> boys = boyDao.selectList(null);
        return boys;
    }

    @Cacheable(key = "#id")
    public Boy queryBoy(int id) {
        Boy boy = boyDao.selectById(id);
        return boy;
    }

    @Cacheable(keyGenerator = "keyGenerator")
    public List<Boy> queryBoy(List<Integer> ids) {
        List<Boy> boys = boyDao.selectBatchIds(ids);
        return boys;
    }

    @Cacheable(key = "#map.get('id')+','+#map.get('name')")
    public List<Boy> queryBoy(Map<String, Object> map) {
        List<Boy> boys = boyDao.selectByMap(map);
        return boys;
    }

    @Cacheable(keyGenerator = "keyGenerator")
    public List<Boy> queryBoy(Boy boy) {
        QueryWrapper<Boy> objectQueryWrapper = new QueryWrapper<>();
        if (boy.getId() != 0) {
            objectQueryWrapper.eq("id", boy.getId());
        }
        if (boy.getName() != null) {
            objectQueryWrapper.like("name", boy.getName()).orderByDesc("id");
        }
        List<Boy> boys = boyDao.selectList(objectQueryWrapper);
        return boys;
    }

    //新增
    //beforeInvocation为true，意思是说当执行这个方法之前执行清除缓存的操作，这样不管这个方法执行成功与否，该缓存都将不存在
    //allEntries为true时，意思是说这个清除缓存是清除当前value值空间下的所有缓存数据
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#boy.id+'_add_'+#boy.name", beforeInvocation = true, allEntries = true)
    public int addBoy(Boy boy) {
        return boyDao.insert(boy);
    }

    @Transactional
    @CacheEvict(keyGenerator = "keyGenerator", allEntries = true)
    public Boy addBoy2(Boy boy) {
        boyDao.insertBoy(boy);
        return boy;
    }

    //修改
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#boy.id+'_update_'+#boy.name", beforeInvocation = true, allEntries = true)
    public int updateBoy(Boy boy) {
        return boyDao.updateById(boy);
    }

    //删除
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(beforeInvocation = true, allEntries = true)
    public int deleteBoy(int id) {
        return boyDao.deleteById(id);
    }

    //分页
    @Cacheable(keyGenerator = "keyGenerator")
    public Page<Boy> queryBoy(int pagenum, int pagesize) {
        /*PageHelper.startPage(pagenum,pagesize);
        List<Boy> boys = boyDao.getBoyDataPage();
        PageInfo<Boy> boyPageInfo = new PageInfo<>(boys);
        return boyPageInfo.getList();*/
        Page<Boy> boyPage = new Page<>(pagenum, pagesize);
        boyDao.selectPage(boyPage, null);
        return boyPage;
    }

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private BoyFeignService boyFeignService;
    @Override
    @GlobalTransactional
    @Transactional
    public Boy saveBoy(Boy boy) {
        System.out.println("saveBoy AService XID: "+ RootContext.getXID());
        Map<String, String> map = new HashMap<>();
        map.put("name","韦小宝");
//        Result r1 = restTemplate.postForObject("http://springboot/globalTransactional", map, Result.class);
        Result r2 = boyFeignService.globalTransactional();
        boyDao.insert(boy);
        int i = 1/0;
        return boyDao.selectById(boy.getId());
    }

    @Override
    @Transactional
    public void async() {
        System.out.println("threadName:"+Thread.currentThread().getName());
        Boy boy = new Boy();
        boy.setName("袁承志");
        boyDao.insert(boy);
        BoyServiceImpl boyService = (BoyServiceImpl) AopContext.currentProxy();
        boyService.asyncMethod();
    }

    @Resource(name="tptExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Override
    public void atomic() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        List<CompletableFuture> completableFutureList = new ArrayList<>();
        for(int i=1;i<=5;i++){
            int num = i;
            CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "---num:" + num + "---atomic:" + atomicInteger.incrementAndGet());
            },threadPoolTaskExecutor);
            completableFutureList.add(completableFuture);
        }
        //同时返回
        CompletableFuture<Void> future = CompletableFuture.allOf(completableFutureList.stream().toArray(CompletableFuture[]::new));
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public void asyncMethod() {
        System.out.println("threadName:"+Thread.currentThread().getName());
        Boy boy = new Boy();
        boy.setName("乔峰");
        boyDao.insert(boy);
        int i = 1/0;
    }
}
