package com.ds.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FatherAndSon implements Serializable {

    private int id;

    private String name;

    private int pid;

    private List<FatherAndSon> fass;

    public FatherAndSon(int id, String name, int pid) {
        this.id = id;
        this.name = name;
        this.pid = pid;
    }
}
