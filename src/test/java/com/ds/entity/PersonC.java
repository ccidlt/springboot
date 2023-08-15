package com.ds.entity;

import java.io.Serializable;
import java.util.List;


public class PersonC implements Serializable {

    private static final long serialVersionUID = -5449634131757608891L;
    String name;
    String tel;
    List<Boy> boyList;

    public PersonC() {}

    public PersonC(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public PersonC(String name, String tel, List<Boy> boyList) {
        this.name = name;
        this.tel = tel;
        this.boyList = boyList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public List<Boy> getBoyList() {
        return boyList;
    }

    public void setBoyList(List<Boy> boyList) {
        this.boyList = boyList;
    }

    @Override
    public String toString() {
        return "PersonC{" +
                "name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", boyList=" + boyList +
                '}';
    }
}
