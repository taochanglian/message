package com.huntkey.rx.sceo.mpf.common;

import com.huntkey.rx.sceo.mpf.constant.Event;
import com.huntkey.rx.sceo.mpf.constant.Topic;
import com.huntkey.rx.sceo.mpf.constant.User;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.avro.specific.SpecificRecordBase;

import java.util.List;

/**
 * Created by taochangl on 2017/5/12 0012.
 */
public interface MsgClient {

    /*
     * 指定topic，发送字符串消息
     */
    public void sendMessage(String topic, String message);

    /*
     * 指定topic，发送Avro序列化后的User对象消息
     */
    public void sendMessage(Topic topic, User User);

    /*
     * 指定topic，发送Avro序列化后的Event对象消息
     */
    public void sendMessage(Topic topic, Event event);

    /*
     * 指定topic，根据topic指定的序列化对象进行String类型消息转Avro消息发送
     */
    public void sendMessage(Topic topic, String message);

    /*
     * 指定topic，接收字符串消息，kafkaStreamWork为回调函数
     */
    public void receiveMessage(String topic, MsgConsumerInterface kafkaStreamWork);

    /*
     * 指定groupId,topic，接收Avro序列化后的SpecificRecordBase基类对象消息
     */
    public List<SpecificRecordBase> receiveMessage(String groupId, Topic topic);

    public List<Event> receiveEventAvroMessage(String groupId, Topic topic);

    public ConsumerConnector getConsumerConnector();

    public boolean getReadStatus();

    public void setReadStatus(boolean readStatus);

    public void closeProducer();

    public void closeConsumer();

    public int getPartitionNumByTopic(String topic);

}
