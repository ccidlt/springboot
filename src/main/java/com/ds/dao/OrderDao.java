package com.ds.dao;
 
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ds.entity.Order;
import com.ds.entity.OrderDTO;
import com.ds.entity.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author makejava  
 * @Desc (Order)表数据库访问层
 * @Date 2023-04-24 08:50:09
 */
@Mapper
public interface OrderDao extends BaseMapper<Order> {

    /**
     * 按条件查询
     */
    List<OrderVO> queryByParam(OrderDTO orderDTO);
 
    /**
     * 分页查询
     */
    IPage<OrderVO> queryByPage(Page page, @Param("param") OrderDTO orderDTO);
 
 
    /**
     * 根据ID查详情
     */
    OrderVO queryById(Long orderId);
 
 
    /**
     * 新增数据
     */
    int insert(Order order);
 
 
    /**
     * 修改数据
     */
    int update(Order order);
 
    /**
     * 通过主键删除数据
     */
    int deleteById(Long orderId);
 
}
