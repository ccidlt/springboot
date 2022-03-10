package com.ds.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ds.dao.UserDao;
import com.ds.entity.Boy;
import com.ds.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("userService")
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    //@Transactional
    //@Cacheable(key = "#user.all")
    @Cacheable(keyGenerator = "keyGenerator")
    //@CacheEvict(allEntries = true)//删除所有的user缓存
    public List<Boy> getBoys() {
        List<Boy> result = new ArrayList<>();
                List<Boy> boyList = userDao.getBoys();
        boyList.stream().collect(Collectors.groupingBy(boy -> boy.getId()+"-"+boy.getName())).forEach((x,y) -> {
            y.stream().reduce((a,b) -> {
               return new Boy(a.getId(),a.getName(),a.getGirls()+ ","+b.getGirls());
            }).ifPresent(c -> result.add(c));
        });
        return result.stream().sorted((d,e) -> d.getId()-e.getId()).collect(Collectors.toList());
    }


    //查询
    @Cacheable(keyGenerator = "keyGenerator")
    public List<Boy> queryBoy(){
        List<Boy> boys = userDao.selectList(null);
        return boys;
    }
    @Cacheable(key = "#id")
    public Boy queryBoy(int id){
        Boy boy = userDao.selectById(id);
        return boy;
    }
    @Cacheable(keyGenerator = "keyGenerator")
    public List<Boy> queryBoy(List<Integer> ids){
        List<Boy> boys = userDao.selectBatchIds(ids);
        return boys;
    }
    @Cacheable(key = "#map.get('id')+','+#map.get('name')")
    public List<Boy> queryBoy(Map<String,Object> map){
        List<Boy> boys = userDao.selectByMap(map);
        return boys;
    }
    @Cacheable(keyGenerator = "keyGenerator")
    public List<Boy> queryBoy(Boy boy){
        QueryWrapper<Boy> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.like("name", boy.getName()).orderByDesc("id");
        List<Boy> boys = userDao.selectList(objectQueryWrapper);
        return boys;
    }

    //新增
    //beforeInvocation为true，意思是说当执行这个方法之前执行清除缓存的操作，这样不管这个方法执行成功与否，该缓存都将不存在
    //allEntries为true时，意思是说这个清除缓存是清除当前value值空间下的所有缓存数据
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#boy.id+'_add_'+#boy.name", beforeInvocation = true, allEntries = true)
    public int addBoy(Boy boy){
        return userDao.insert(boy);
    }

    //修改
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#boy.id+'_update_'+#boy.name", beforeInvocation = true, allEntries = true)
    public int updateBoy(Boy boy){
        return userDao.updateById(boy);
    }

    //删除
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(beforeInvocation = true, allEntries = true)
    public int deleteBoy(int id){
        return userDao.deleteById(id);
    }

    //分页
    @Cacheable(keyGenerator = "keyGenerator")
    public Page<Boy> queryBoy(int pagenum, int pagesize){
        Page<Boy> boyPage = new Page<>(pagenum, pagesize);
        userDao.selectPage(boyPage, null);
        return boyPage;
    }
}
