package com.huntkey.rx.sceo.mpf.constant;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by taochangl on 2017/5/12 0012.
 */
public interface Constant {
    public static final Logger logger = LogManager.getLogger(Constant.class.getName());

    public static final String KEY_ZOOKEEPER_CONNECTION_SERVER = "zookeeper.connection.server";
    public static final String KEY_KAFKA_CONNECTION_BROKER     = "kafka.connection.broker";
    public static final String KEY_GROUP_ID                    = "group.id";
    public static final String KEY_AUTO_COMMIT                    = "enable.auto.commit";

    public static final String VALUE_DEFAULT_ZOOKEEPER_CONNECTION_SERVER = "huntkey.sceo.messageplatform.zkserver1.com:2181,huntkey.sceo.messageplatform.zkserver2.com:2181,huntkey.sceo.messageplatform.zkserver3.com:2181";
    public static final String VALUE_DEFAULT_KAFKA_CONNECTION_BROKER     = "huntkey.sceo.messageplatform.kafkaserver1.com:6667,huntkey.sceo.messageplatform.kafkaserver2.com:6667,huntkey.sceo.messageplatform.kafkaserver3.com:6667";
    public static final String VALUE_DEFAULT_GROUP_ID                    = "MP_Client";
    public static final String VALUE_DEFAULT_AUTO_COMMIT                    = "true";

}
