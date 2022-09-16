package com.ds.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    private Integer id;
    private Integer pid;
    private String menu;
    private String desc;
    private String url;
    private Integer sort;
    private String perms;
    private String visible;
    private String type;

}
