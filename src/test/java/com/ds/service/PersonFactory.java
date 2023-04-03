package com.ds.service;

@FunctionalInterface
public interface PersonFactory<P> {

    P create(String name, String tel);

}
