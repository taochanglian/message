package com.huntkey.rx.sceo;

import com.huntkey.rx.sceo.mpf.common.MsgClient;
import com.huntkey.rx.sceo.mpf.common.MsgClientFactory;
import com.huntkey.rx.sceo.mpf.common.MsgConsumerInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static java.lang.Thread.sleep;

/**
 * Created by liangh on 2017/5/22.
 */
public class TestStringConsumer {

    public static void main(String args[]) throws Exception{

        //测试接收test主题的字符串消息,groupId为MP_Client
        Properties prop = new Properties();
        prop.put("group.id","myGroup12331");
        MsgClient client = MsgClientFactory.createClient(prop);

        client.receiveMessage("myTopic", new MsgConsumerInterface() {
            int totalNum = 0;
            @Override
            public void consume(String topic, String message) {
                /*if("123".equals(message)) {
                    client.closeConsumer();
                }*/
                try{
                    if(!"".equals(message)) {
                        //System.out.println("已经收到从Kafka订阅的消息, Topic = " + topic + ",message = " + message);
                        //sleep(10000);
                        totalNum = totalNum+1;
                        System.out.println("totalNum="+totalNum);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                //String timeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date());
                //System.out .println("timeStr="+timeStr);

            }

        });


        /*for(int i=0;i<1000;i++) {
            if(i==999){
                sleep(10000);
                //client.closeConsumer();
                client.closeMsgConsumer();
            }
        }*/

    }
}
