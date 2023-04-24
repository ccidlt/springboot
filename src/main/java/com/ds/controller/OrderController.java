package com.ds.controller;
 
import com.ds.entity.Order;
import com.ds.entity.OrderVO;
import com.ds.entity.OrderDTO;
import com.ds.service.OrderService;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import java.util.*;
 
/**
 * @Author makejava
 * @Desc (Order)表控制层
 * @Date 2023-04-24 08:50:05
 */
@Api(tags = "")
@RestController
@RequestMapping("order")
public class OrderController {
 
    @Resource
    private OrderService orderService;
    
    
    @ApiOperation("分库分表-按条件查询")
    @PostMapping("/queryByParam")
    public R<List<OrderVO>> queryByParam(@RequestBody OrderDTO orderDTO) {
        return orderService.queryByParam(orderDTO);
    }
 
 
    @ApiOperation("分库分表-分页查询")
    @PostMapping("/queryByPage")
    public R<IPage<OrderVO>> queryByPage(@RequestBody OrderDTO orderDTO) {
        return orderService.queryByPage(orderDTO);
    }
    
 
    @ApiOperation("分库分表-根据ID查详情")
    @GetMapping("/queryById/{id}")
    public R<OrderVO> queryById(@PathVariable("id") Long orderId) {
        return orderService.queryById(orderId);
    }
 
 
    @ApiOperation("分库分表-新增数据")
    @PostMapping("/insert")
    public R insert(@RequestBody Order order) {
        return orderService.insert(order);
    }
 
 
    @ApiOperation("分库分表-编辑数据")
    @PostMapping("/update")
    public R update(@RequestBody Order order) {
        return orderService.update(order);
    }
 
 
    @ApiOperation("分库分表-删除数据")
    @GetMapping("/deleteById/{id}")
    public R deleteById(@PathVariable("id") Long orderId) {
        return orderService.deleteById(orderId);
    }
 
}
