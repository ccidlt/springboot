package com.ds.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/kafka")
public class KafkaController {

    /*@Resource
    private KafkaTemplate<String,String> kafkaTemplate;

    @RequestMapping("/send/{message}")
    public void send(@PathVariable("message") String message){
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("topic01", 0, "key01", message);
        //同步发送消息
        *//*try {
            SendResult<String, String> sendResult = future.get();
            RecordMetadata recordMetadata = sendResult.getRecordMetadata();
            System.out.println(recordMetadata.topic()+"\t"+recordMetadata.partition()+"\t"+recordMetadata.offset());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*//*
        //异步发送消息
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("消息发送失败："+throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> sendResult) {
                RecordMetadata recordMetadata = sendResult.getRecordMetadata();
                System.out.println("消息发送成功："+recordMetadata.topic()+"\t"+recordMetadata.partition()+"\t"+recordMetadata.offset());
            }
        });
    }

    @KafkaListener(topics = "topic01")
    public void onMessage(ConsumerRecord<String,String> record){
        System.out.println("消费者收到的消息："+record.topic()+"\t"+record.partition()+"\t"+record.offset()+"\t"+record.key()+"\t"+record.value());
    }*/
}
