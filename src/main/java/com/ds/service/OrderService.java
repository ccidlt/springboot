package com.ds.service;
 
import com.ds.entity.Order;
import com.ds.entity.OrderDTO;
import com.ds.entity.OrderVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.*;
 
/**
 * @Author makejava
 * @Desc (Order)表服务接口
 * @Date 2023-04-24 08:50:08
 */
public interface OrderService extends IService<Order> {
 
    /**
     * 按条件查询
     */
    R<List<OrderVO>> queryByParam(OrderDTO orderDTO);
 
    /**
     * 分页查询
     */
    R<IPage<OrderVO>> queryByPage(OrderDTO orderDTO);
 
    /**
     * 根据ID查详情
     */
    R queryById(Long orderId);
    
    /**
     * 新增数据
     */
    R insert(Order order);
 
    /**
     * 修改数据
     */
    R update(Order order);
 
    /**
     * 通过主键删除数据
     */
    R deleteById(Long orderId);
 
}
