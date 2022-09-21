package com.ds.config.kafka;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    /*@Bean
    public NewTopic topic1(){
        return new NewTopic("nptc-01", 3, (short)1);
    }

    @Bean
    public NewTopic topic2(){
        return new NewTopic("nptc-02", 5, (short)1);
    }

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServers;

    *//**
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
     *//*
    @Bean
    public AdminClient adminClient() {
        Map<String, Object> props = new HashMap<>();
        //配置Kafka实例的连接地址
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
        return AdminClient.create(props);
    }

    @Autowired
    private AdminClient adminClient;

    public void adminClientTest(){
        DescribeTopicsResult result = adminClient.describeTopics(Arrays.asList("nptc-01","nptc-02"));
        try {
            result.all().get().forEach((k,v)->System.out.println("k: "+k+" ,v: "+v.toString()+"\n"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }*/

}
