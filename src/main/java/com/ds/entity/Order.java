package com.ds.entity;
 
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
 
/**
 * (Order)实体类
 *
 * @author makejava
 * @since 2023-04-24 08:50:10
 */
@Data
@TableName(value = "t_order")
public class Order {
     
    private Long orderId;
     
    private Integer userId;
     
    private String userName;
 
}
