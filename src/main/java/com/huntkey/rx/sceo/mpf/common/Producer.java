package com.huntkey.rx.sceo.mpf.common;

/**
 * Created by liangh on 2017/5/18.
 */

import com.huntkey.rx.sceo.mpf.constant.AvroSerializer;
import com.huntkey.rx.sceo.mpf.constant.Topic;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer<T extends SpecificRecordBase> {

    private KafkaProducer<String, T> producer = new KafkaProducer<>(getProperties());

    public void sendData(Topic topic, T data) {
        producer.send(new ProducerRecord<>(topic.topicName, data),
                (metadata, exception) -> {
                    if (exception == null) {
                        System.out.printf("Sent user: %s \n", data);
                    } else {
                        System.out.println("data sent failed: " + exception.getMessage());
                    }
                });
        try {
            Thread.sleep(2000);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.13.33:6667");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoProducer");
        props.put(ProducerConfig.LINGER_MS_CONFIG, 0);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                AvroSerializer.class.getName());
        return props;
    }
}
