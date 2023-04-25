package com.ds.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TreeData<T> implements Serializable {
    private static final long serialVersionUID = -7896496388368740077L;
    private Integer id;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private Integer pid;
    @TableField(exist = false)
    private Integer sort;
    @TableField(exist = false)
    private List<T> children;
    @TableField(exist = false)
    private List<Integer> parentIds;
    @TableField(exist = false)
    private List<String> parentNames;
}
