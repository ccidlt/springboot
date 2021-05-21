package com.ds.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


@Slf4j
public class TestRabbitMQ {

    /**
     * 接收并打印消息
     * 可以当队列不存在时自动创建队列，以及自动绑定指定的Exchange(Direct直接连接，Fanout广播，Topic订阅)
     * @param message message
     */
    // @RabbitListener注解用于监听RabbitMQ，bindings可以创建指定的队列及自动绑定Exchange
    /*@RabbitListener(queuesToDeclare = @Queue("myQueue"))
    public void receive1(String message) {
        log.info(message);
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(value = "myExchange"),
            key = "computer"
    ))
    public void receive2(String message) {
        log.info(message);
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(value = "myExchange",type = "fanout")
    ))
    public void receive3(String message) {
        log.info(message);
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(value = "myExchange",type = "topic"),
            //key = {"user.computer","user.fruit"}
            key = {"user.#"}
    ))
    public void receive4(String message) {
        log.info(message);
    }*/
}