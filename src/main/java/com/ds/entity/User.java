package com.ds.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private String name;
    private int age;
    private Integer roleId;
    private Integer deptId;
    private Integer roleScope;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
