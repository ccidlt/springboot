package com.ds.config.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@ConditionalOnProperty(prefix = "spring.kafka", name = "enabled", havingValue = "true")
@Configuration
public class KafkaConfig {

//    @Bean("initialTopic1")
//    public NewTopic initialTopic1(){
//        return new NewTopic("topic01",8,(short)1);//分区和副本
//    }

//    @Bean("initialTopic2")
//    public NewTopic initialTopic2(){
//        return new NewTopic("topic-new-1",8,(short)1);//分区和副本
//    }

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServers;

    /**
     *
     * 创建 Topic：createTopics(Collection<NewTopic> newTopics)
     * 删除 Topic：deleteTopics(Collection<String> topics)
     * 罗列所有 Topic：listTopics()
     * 查询 Topic：describeTopics(Collection<String> topicNames)
     * 查询集群信息：describeCluster()
     * 查询 ACL 信息：describeAcls(AclBindingFilter filter)
     * 创建 ACL 信息：createAcls(Collection<AclBinding> acls)
     * 删除 ACL 信息：deleteAcls(Collection<AclBindingFilter> filters)
     * 查询配置信息：describeConfigs(Collection<ConfigResource> resources)
     * 修改配置信息：alterConfigs(Map<ConfigResource, Config> configs)
     * 修改副本的日志目录：alterReplicaLogDirs(Map<TopicPartitionReplica, String> replicaAssignment)
     * 查询节点的日志目录信息：describeLogDirs(Collection<Integer> brokers)
     * 查询副本的日志目录信息：describeReplicaLogDirs(Collection<TopicPartitionReplica> replicas)
     * 增加分区：createPartitions(Map<String, NewPartitions> newPartitions)
     *
     * @return
     */
    @Bean
    public AdminClient adminClient() {
        Map<String, Object> props = new HashMap<>();
        //配置Kafka实例的连接地址
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
        return AdminClient.create(props);
    }

//    @Bean
//    public KafkaConsumer<String, String> getConsumer() {
//        Properties props = new Properties();
//        // kafka集群所需的broker地址
//        props.put("bootstrap.servers", kafkaServers);
//        // kafka消费者群组名称
//        props.put("group.id", "group_demo");
//        // 消费者从broker端获取的消息格式都是byte[]数组类型，key和value需要进行反序列化。
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        // 创建消费者
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
//        return consumer;
//    }

    @Autowired
    private AdminClient adminClient;

    public void adminClientTest(){
        DescribeTopicsResult result = adminClient.describeTopics(Arrays.asList("topic01","topic-new-1"));
        try {
            result.all().get().forEach((k,v)->System.out.println("k: "+k+" ,v: "+v.toString()+"\n"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
