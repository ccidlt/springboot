package com.ds.entity;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = -5449634131757608891L;
    String name;
    String tel;

    public Person() {}

    public Person(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
