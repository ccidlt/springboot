package com.ds.entity.table;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.command.BaseModel;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "test")
@TableName("test")
public class TestActable extends BaseModel {

    @TableId(type = IdType.AUTO)//mybatis-plus主键注解
    @IsAutoIncrement   //自增
    @IsKey             //actable主键注解
    @Column(name = "id", type = MySqlTypeConstant.BIGINT, isNull = false, isKey = true, isAutoIncrement=true, comment = "id")
    private Long id;

    @TableField(value = "name")
    @Column(name = "name", type = MySqlTypeConstant.VARCHAR, length = 64, comment = "名称")
    private String name;

    @TableField(value = "create_time")
    @Column(name = "create_time", type = MySqlTypeConstant.DATETIME, comment = "创建时间")
    private Date creatTime;

}