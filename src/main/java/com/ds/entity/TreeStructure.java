package com.ds.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 树结构类
 * @Author lt
 * @Date 2023/5/4 9:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeStructure {

    private Long id;

    private Long pid;

    private String name;

    private Integer sort;

    private List<TreeStructure> children = new ArrayList<>();

    public TreeStructure(Long id, Long pid, String name, Integer sort) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.sort = sort;
    }
}
