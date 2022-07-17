package com.ds.controller.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {
    /**
     * 交换机exchangee可通过路由键key绑定队列queue，消息放在队列上
     * direct直连（路由键精确匹配消息，某个消费者消费完该消息，该消息移除队列）
     * fanout广播（无路由键，多个消费者都可拿到消息）
     * topic订阅（多个消费者携带路由键订阅该消息）
     */

    /*@Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/send1")
    public void send1 (){
        for (int i=1;i<=10;i++){
            rabbitTemplate.convertAndSend("myQueue","message..."+i);
        }
    }
    @RequestMapping("/send2")
    public void send2 (){
        rabbitTemplate.convertAndSend("myExchange","computer","message...direct发送");
    }
    @RequestMapping("/send3")
    public void send3 (){
        rabbitTemplate.convertAndSend("myExchange","","message...fanout发送");
    }
    @RequestMapping("/send4")
    public void send4 (){
        rabbitTemplate.convertAndSend("myExchange","user.fruit","message...topic发送");
    }
    @RequestMapping("/send5")
    public void send5 (){
        rabbitTemplate.convertAndSend("myExchange","user.computer","message...topic发送");
    }*/

    /**
     * 接收并打印消息
     * 可以当队列不存在时自动创建队列，以及自动绑定指定的Exchange(Direct直接连接，Fanout广播，Topic订阅)
     * @param message message
     */
    /*// @RabbitListener注解用于监听RabbitMQ，bindings可以创建指定的队列及自动绑定Exchange
    @RabbitListener(queuesToDeclare = @Queue("myQueue"))
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