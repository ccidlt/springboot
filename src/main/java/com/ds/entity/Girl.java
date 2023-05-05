package com.ds.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
//有参构造函数
@AllArgsConstructor
//无参构造函数
@NoArgsConstructor
public class Girl implements Serializable {

    private int id;

    private String name;

    private int boyId;

    private String createUser;

    //自动回填创建时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    //自动回填修改时间
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //乐观锁
    @Version
    private Integer version;

    //逻辑删除
    @TableLogic
    private Integer isdelete;

    public Girl(int id, String name, int boyId) {
        this.id = id;
        this.name = name;
        this.boyId = boyId;
    }
}
