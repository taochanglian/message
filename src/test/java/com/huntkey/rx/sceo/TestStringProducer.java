package com.huntkey.rx.sceo;

import com.huntkey.rx.sceo.mpf.common.MsgClient;
import com.huntkey.rx.sceo.mpf.common.MsgClientFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by liangh on 2017/5/22.
 */
public class TestStringProducer {

    public static void main(String args[]) throws Exception {

        //测试发送字符串消息到test主题
        String topic = "myTopic";//"mykafka";messageData
        String message = "Hello world " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date());

        Properties prop = new Properties();
        MsgClient client = MsgClientFactory.createClient(prop);
        for(int i=0;i<5000;i++) {
            client.sendMessage(topic,message + " " + i+1);
        }
        client.closeProducer();
    }
}
