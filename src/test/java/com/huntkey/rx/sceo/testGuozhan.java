package com.huntkey.rx.sceo;

import com.huntkey.rx.sceo.mpf.common.MsgClient;
import com.huntkey.rx.sceo.mpf.common.MsgClientFactory;
import com.huntkey.rx.sceo.mpf.exception.MsgException;

import java.util.Properties;

/**
 * Created by liangh on 2017/10/9 0009.
 */
public class testGuozhan {

    public static void main(String args[]) throws MsgException {
        String topic = "kafkatest";//须是kafka已存在的主题，如无需找kafka负责人创建
        String message = "new message"; //消息内容
        Properties prop = new Properties();
        MsgClient client = MsgClientFactory.createClient(prop);//建立连接
        client.sendMessage(topic,message);//发送消息
    }

}
