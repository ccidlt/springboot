package com.ds.controller.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;

@ConditionalOnProperty(prefix = "spring.kafka",value = "enabled", havingValue = "true")
@RestController
@Slf4j
@RequestMapping("/kafka")
public class KafkaController{

    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @RequestMapping("/send/{message}")
    public void send(@PathVariable("message") String message){
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("topic01", 0, "key01", message);
        //同步发送消息
        try {
            SendResult<String, String> sendResult = future.get();
            RecordMetadata recordMetadata = sendResult.getRecordMetadata();
            System.out.println(recordMetadata.topic()+"\t"+recordMetadata.partition()+"\t"+recordMetadata.offset());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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

    //@KafkaListener(groupId = "topicgroupid-"+"#{T(java.util.UUID).randomUUID()}", topics = "#{'${kafka_topics}'.split(',')}",errorHandler = "kafkaListenerException")
    @KafkaListener(id="topicid1",topics = "#{'${kafka_topics}'.split(',')}",errorHandler = "kafkaListenerException")
    public void onMessage(ConsumerRecord<String,String> record){
        System.out.println("消费者收到的消息："+record.topic()+"\t"+record.partition()+"\t"+record.offset()+"\t"+record.key()+"\t"+record.value());
    }


    @RequestMapping("/sendmessage")
    public void send(){
        for(int i=0;i<5;i++){
            ListenableFuture<SendResult<String,String>> send = kafkaTemplate.send("topic-new-1", "key" + i, "消息" + i);
            send.addCallback(result -> {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                System.out.println("消息发送成功："+recordMetadata.topic()+"\t"+recordMetadata.partition()+"\t"+recordMetadata.offset());
            }, error -> {
                System.out.println("消息发送失败："+error.getMessage());
            });
        }
    }

    //@KafkaListener(groupId = "topicgroupid-"+"#{T(java.util.UUID).randomUUID()}", topics = "#{'${kafka_topics}'.split(',')}",errorHandler = "kafkaListenerException")
    @KafkaListener(id="topicid2",topics = "#{'${kafka_topics}'.split(',')}",errorHandler = "kafkaListenerException")
    public void accept(List<ConsumerRecord<String,String>> records, Acknowledgment ack){
        if(records != null && !records.isEmpty()){
            for(ConsumerRecord<String,String> record : records){
                System.out.println(record.value());
            }
        }
        ack.acknowledge();
    }


    //开启监听
    @GetMapping("/start")
    public String start() {
        // 判断监听容器是否启动，未启动则将其启动
        if (!registry.getListenerContainer("topic-new-1-id").isRunning()) {
            registry.getListenerContainer("topic-new-1-id").start();
            return "===> kafka Listener start";
        }
        // 恢复启动
        registry.getListenerContainer("topic-new-1-id").resume();
        return "===> kafka Listener resume";
    }

    //关闭监听
    @GetMapping("/stop")
    public String stop() {
        registry.getListenerContainer("topic-new-1-id").stop();
        return "===> kafka Listener stop";
    }

    //暂停监听
    @GetMapping("/pause")
    public String pause() {
        if(registry.getListenerContainer("topic-new-1-id").isRunning()) {
            registry.getListenerContainer("topic-new-1-id").pause();
        }
        return "===> kafka Listener pause";
    }
}
