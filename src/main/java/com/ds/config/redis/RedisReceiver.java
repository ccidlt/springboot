package com.ds.config.redis;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RedisReceiver {

    private AtomicInteger counter = new AtomicInteger();

    public int getCount() {
        return counter.get();
    }

    /**
     * 消息队列1
     **/
    public void receiveMessage(String message){

    }

    /**
     * 消息队列2
     **/
    public void receiveMessage2(String message){

    }

    /**
     * 消息队列3
     **/

    public void receiveMessage3(String message){

    }

}
