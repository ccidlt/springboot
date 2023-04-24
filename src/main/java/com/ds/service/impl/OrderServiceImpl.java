package com.ds.service.impl;
 
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ds.dao.OrderDao;
import com.ds.entity.Order;
import com.ds.entity.OrderDTO;
import com.ds.entity.OrderVO;
import com.ds.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
 
/**
 * @Author makejava
 * @Desc (Order)表服务实现类
 * @Date 2023-04-24 08:50:09
 */
@Service("orderService")
@DS("sharding")
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {
 
    @Resource
    private OrderDao orderDao;
 
    @Override
    public R<List<OrderVO>> queryByParam(OrderDTO orderDTO) {
        return R.ok(orderDao.queryByParam(orderDTO));
    }
 
    @Override
    public R<IPage<OrderVO>> queryByPage(OrderDTO orderDTO) {
        return R.ok(orderDao.queryByPage(orderDTO, orderDTO));
    }
 
 
    @Override
    public R queryById(Long orderId) {
        return R.ok(orderDao.queryById(orderId));
    }
 
 
    @Override
    public R insert(Order order) {
        int num = orderDao.insert(order);
        return R.ok(num);
    }
 
 
    @Override
    public R update(Order order) {
        int num = orderDao.update(order);
        return R.ok(num);
    }
 
 
    @Override
    public R deleteById(Long orderId) {
        int num = orderDao.deleteById(orderId);
        return R.ok(num);
    }
    
}
