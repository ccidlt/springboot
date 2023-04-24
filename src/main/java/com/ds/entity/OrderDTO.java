package com.ds.entity;
 
import lombok.Data;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
 
/**
 * (Order)DTO类
 *
 * @author makejava
 * @since 2023-04-24 08:50:11
 */
@Data
public class OrderDTO extends Page {
     
    private Long orderId;
     
    private Integer userId;
     
    private String userName;
 
}
