package com.huntkey.rx.sceo.mpf.common;

/**
 * Created by liangh on 2017/5/19.
 */

import com.huntkey.rx.sceo.mpf.constant.AvroDeserializer;
import com.huntkey.rx.sceo.mpf.constant.Topic;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Consumer<T extends SpecificRecordBase> {

    private KafkaConsumer<String, T> consumer = null;

    public List<T> receive(String groupId, Topic topic) {

        Properties props = getProperties();
        props.put("group.id", groupId);
        consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList(topic.topicName));

        ConsumerRecords<String, T> records = consumer.poll(5000);

        consumer.commitSync();

        return StreamSupport.stream(records.spliterator(), false)
                .map(ConsumerRecord::value).collect(Collectors.toList());
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.13.33:6667");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvroDeserializer.class.getName());
        //props.put("zookeeper.connect", "huntkey.sceo.messageplatform.zkserver3.com:2181");
        //props.put("group.id", "myGroup");
        props.put("fetch.min.bytes", "1");
        props.put("heartbeat.interval.ms", "3000");
        props.put("max.partition.fetch.bytes", "1048576");
        props.put("session.timeout.ms", "10000");
        //importance:medium
        props.put("connections.max.idle.ms", "540000");
        props.put("enable.auto.commit", "true");
        props.put("exclude.internal.topics", "true");
        props.put("fetch.max.bytes", "52428800");
        props.put("max.poll.interval.ms", "300000");
        props.put("max.poll.records", "500");
        props.put("receive.buffer.bytes", "32768");
        props.put("request.timeout.ms", "800000");
        props.put("send.buffer.bytes", "1024");
        props.put("auto.offset.reset", "earliest");
        /*props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.13.33:6667");
        props.put("zookeeper.connect","huntkey.sceo.messageplatform.zkserver2.com:2181");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumer");*/
        //String zkServer = "huntkey.sceo.messageplatform.zkserver1.com:2181,
        // huntkey.sceo.messageplatform.zkserver2.com:2181,huntkey.sceo.messageplatform.zkserver3.com:2181";
        return props;
    }
}

