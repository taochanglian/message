package com.huntkey.rx.sceo.mpf.common;

import com.huntkey.rx.sceo.mpf.constant.Event;
import com.huntkey.rx.sceo.mpf.constant.Topic;
import com.huntkey.rx.sceo.mpf.constant.User;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.huntkey.rx.sceo.mpf.constant.Constant.logger;
import static java.time.ZoneOffset.MAX;

/**
 * Created by taochangl on 2017/5/12 0012.
 */
public class MsgClientKafkaImpl implements MsgClient{
    private static final Logger log = LogManager.getLogger(MsgClientKafkaImpl.class.getName());

    private static final String KAFKA_CONSUMING_WORKER = "-kafkaConsumingWorker-";

    private String zkServer;
    private String kafkaBroker;
    private String groupId;
    private String autoCommit;
    private int threadsNum;
    private String stopThreadInfoLevel;

    private KafkaProducer<String,String> producer;
    private Properties producerProperties;

    private ConsumerConnector kafkaConsumerConnector;
    private ConsumerConfig consumerConfig;
    //消费者消费的topic信息
    private Map<String, Integer> topicInfo;
    //连接池 每个topic会开启一个线程消费
    private ExecutorService kafkaConsumingThreadPool;
    //消费后的信息 回调接口
    private MsgConsumerInterface kafkaStreamWork;
    //private Map<String,MsgConsumerInterface> msgConsumerInterfaceMap;

    private ConsumerIterator<byte[], byte[]> consumerIterator;

    private boolean readStatus = true;

    protected MsgClientKafkaImpl(final String zkServer,final String kafkaBroker,final String groupId,final String autoCommit) {
        this.zkServer    = zkServer;
        this.kafkaBroker = kafkaBroker;
        this.groupId     = groupId;
        this.autoCommit  = autoCommit;
        this.readStatus  = true;
        this.threadsNum  = 3;

        producerProperties = getProducerProperties();
        consumerConfig = getConsumerProperties();
        this.kafkaConsumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
        producer = new KafkaProducer<String, String>(producerProperties);
    }

    private Properties getProducerProperties() {
        final Properties props = new Properties();
        props.put("bootstrap.servers", kafkaBroker);
        props.put("zookeeper.connect", zkServer);
        //props.put("acks", "0");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("compression.type", "snappy");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");
        props.put("zookeeper.session.timeout.ms", "200000");
        props.put("zookeeper.sync.time.ms", "2000");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //测试发送端消息不丢失加配置信息
        props.put("block.on.buffer.full", "true");
        //props.put("retries", MAX);
        props.put("unclean.leader.election.enable", "false");
        props.put("replication.factor", "3");
        props.put("min.insync.replicas", "2");
        props.put("acks", "all");
        return props;
    }

    private ConsumerConfig getConsumerProperties(){
        final Properties props = new Properties();
        props.put("zookeeper.connect", zkServer);
        props.put("group.id", this.groupId);
        props.put("enable.auto.commit", "true");//autoCommit
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("fetch.min.bytes", "1");
        props.put("heartbeat.interval.ms", "3000");
        props.put("max.partition.fetch.bytes", "1048576");
        props.put("zookeeper.session.timeout.ms", "200000");
        props.put("session.timeout.ms", "10000");

        props.put("rebalance.backoff.ms", "30000");
        props.put("rebalance.max.retries", "10");

        //importance:medium
        props.put("auto.offset.reset", "smallest");//latest
        props.put("connections.max.idle.ms", "540000");
        props.put("exclude.internal.topics", "true");
        props.put("fetch.max.bytes", "52428800");
        props.put("max.poll.interval.ms", "300000");
        props.put("max.poll.records", "500");
        props.put("receive.buffer.bytes", "32768");
        props.put("request.timeout.ms", "8000");
        props.put("send.buffer.bytes", "1024");

        return new ConsumerConfig(props);
    }

    public void sendMessage(String topic, String message) {
        ProducerRecord<String,String> producerRecord = new ProducerRecord<String, String>(topic,message);
        producer.send(producerRecord);
        //producer.close();
    }

    public void sendMessage(Topic topic, User user) {
        Producer<User> producer = new Producer<>();
        producer.sendData(topic, user);
    }

    public void sendMessage(Topic topic, Event event) {
        Producer<Event> producer = new Producer<>();
        producer.sendData(topic, event);
    }

    public void sendMessage(Topic topic, String message) {
        if("testUser".equals(topic.getTopicName())) {
            Producer<User> producer = new Producer<>();
            if(message.contains(" ")) {
                String [] s = message.split(" ");
                producer.sendData(topic, new User(s[0], s[1]));
            }
        }
    }

    public void closeProducer() {
        producer.close();
    }

    public void closeConsumer() {
        this.stopThreadInfoLevel = "info";
        kafkaConsumerConnector.shutdown();
    }

    public int getPartitionNumByTopic(String topic) {
        try{
            ZooKeeper zk = new ZooKeeper(zkServer, 10000, new Watcher(){
                @Override
                public void process(WatchedEvent event) {
                    logger.info("ZK event ["+ event.toString() +"]");
                }
            });
            //String partitionInfo = new String(zk.getData("/brokers/topics/test_dynamic_partitions/partitions", false, null));
            List<String> partitionList = zk.getChildren("/brokers/topics/"+topic+"/partitions",false);
            return partitionList.size();
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public ConsumerConnector getConsumerConnector() {
        return this.kafkaConsumerConnector;
    }

    public ConsumerIterator<byte[], byte[]> getConsumerIterator(){
        return this.consumerIterator;
    }

    public boolean getReadStatus() {
        return this.readStatus;
    }

    public void setReadStatus(boolean readStatus) {
        this.readStatus = readStatus;
    }

    public String getStopThreadInfoLevel() {
        return this.stopThreadInfoLevel;
    }

    public void receiveMessage(String topic, MsgConsumerInterface kafkaStreamWork) {
        //this.groupId = groupId;
        //consumerConfig = getConsumerProperties();
        if(getPartitionNumByTopic(topic)!=0) {
            this.threadsNum = getPartitionNumByTopic(topic);
        }else{
            kafkaConsumerConnector.shutdown();
        }

        this.kafkaStreamWork = kafkaStreamWork;

        initTopic(topic);
        try {
            start();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public List<SpecificRecordBase> receiveMessage(String groupId, Topic topic) {
        List<SpecificRecordBase> users = null;
        com.huntkey.rx.sceo.mpf.common.Consumer<SpecificRecordBase> consumer = new com.huntkey.rx.sceo.mpf.common.Consumer<>();

        try{
            users = consumer.receive(groupId, topic);
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println(" deserialize failed!!!! ");
        }

        return users;
    }
    /*public List<User> receiveMessage(String groupId, Topic topic) {
        List<User> users = null;
        com.huntkey.rx.sceo.mpf.common.Consumer<User> consumer = new com.huntkey.rx.sceo.mpf.common.Consumer<>();

        try{
            users = consumer.receive(groupId, topic);
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println(" deserialize failed!!!! ");
        }

        return users;
    }*/

    public List<Event> receiveEventAvroMessage(String groupId, Topic topic) {
        List<Event> events = null;
        com.huntkey.rx.sceo.mpf.common.Consumer<Event> consumer = new com.huntkey.rx.sceo.mpf.common.Consumer<>();
        try{
            events = consumer.receive(groupId, topic);
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println(" deserialize failed!!!! ");
        }
        return events;
    }

    private void initTopic(String topic){
        Set<String> topicSet = new HashSet<String>();
        if(topic==""||"".equals(topic)){//消费主题为空，写出错误信息，返回
            log.error(" topic is null! ");
            return;
        }else if(topic.contains(",")){//消费主题有多个topic组成
            String topics[] = topic.split(",");
            for(int i=0;i<topics.length;i++){
                topicSet.add(topics[i]);
            }
        }else{//消费主题就一个
            topicSet.add(topic);
        }
        log.debug("获取的Topic信息="+topicSet);
        topicInfo = new HashMap<String,Integer>();
        Iterator<String> it = topicSet.iterator();
        while(it.hasNext()){
            topicInfo.put(it.next(),new Integer(threadsNum));
        }
    }

    public void start() throws Exception {
        this.kafkaConsumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);

        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = kafkaConsumerConnector.createMessageStreams(topicInfo);

        kafkaConsumingThreadPool = Executors.newFixedThreadPool(threadsNum);

        for (String topic : topicInfo.keySet()) {
            List<KafkaStream<byte[], byte[]>> kafkaStreamList = messageStreams.get(topic);

            int streamNum = 0;

            for (KafkaStream<byte[], byte[]> kafkaStream : kafkaStreamList) {
                kafkaConsumingThreadPool.submit(new ConsumingWorker(topic, ++streamNum, kafkaStream));
                log.info("={}" + KAFKA_CONSUMING_WORKER + "{} Running !!! topic信息= " +topic+"  流信息="+streamNum);
            }
        }

    }

    public void startAsDeamon() throws Exception {
        start();
        while (!kafkaConsumingThreadPool.isTerminated())
            Thread.sleep(10000);
    }

    public boolean isRunning() {
        return !kafkaConsumingThreadPool.isTerminated();
    }

    public void stop() throws RuntimeException {
        if (kafkaConsumerConnector != null)
            kafkaConsumerConnector.shutdown();
        if (kafkaConsumingThreadPool != null)
            kafkaConsumingThreadPool.shutdown();
        while (!kafkaConsumingThreadPool.isTerminated()) {
        }
    }

    public void setKafkaStreamWork(MsgConsumerInterface kafkaStreamWork) {
        this.kafkaStreamWork = kafkaStreamWork;
    }

    private class ConsumingWorker implements Runnable {

        private String topic;
        private int streamNum;
        private KafkaStream<byte[], byte[]> kafkaStream;
        private String threadName;

        public ConsumingWorker(String topic, int numOfThreadForKafka,
                               KafkaStream<byte[], byte[]> kafkaStream) {
            this.topic = topic;
            this.streamNum = numOfThreadForKafka;
            this.kafkaStream = kafkaStream;
            this.threadName = topic + KAFKA_CONSUMING_WORKER + streamNum;
        }

        @Override
        public void run(){
            try {
                Thread.currentThread().setName(threadName);
                ConsumerIterator<byte[], byte[]> consumerIterator = kafkaStream.iterator();
                while (consumerIterator.hasNext()) {
                    String message = new String( consumerIterator.next().message() );
                    log.debug("consuming topic = {}, streamNum = {}, messageBytes = {}, message = {}"+
                            topic + streamNum + message.length() + message);
                    try {
                        //System.out.println("before consume topic = " + topic + ",message = "+ message);
                        kafkaStreamWork.consume(topic, message);
                        /*for (String mciName : msgConsumerInterfaceMap.keySet()) {
                            msgConsumerInterfaceMap.get(mciName).consume(topic, message);
                        }*/

                    } catch (Exception e) {
                        log.error( "==Exception Occur !!! - consuming topic = " + topic
                                + ", streamNum = " + streamNum + ", messageBytes = " + message.length(), e);
                    }
                    kafkaConsumerConnector.commitOffsets();
                }
                //Thread.currentThread().interrupt();
            } catch (Exception e) {
                log.error("=[" + threadName + "] Exception Occur!!!", e);
            } finally {
                if("info".equals(getStopThreadInfoLevel())) {
                    log.info("=[" + threadName + "] stopped!!!");
                }else {
                    log.error("=[" + threadName + "] stopped!!!");
                }

            }
        }
    }
}
