package com.huntkey.rx.sceo.mpf.common;

import com.huntkey.rx.sceo.mpf.constant.Constant;
import com.huntkey.rx.sceo.mpf.exception.MsgException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

/**
 * Created by taochangl on 2017/5/12 0012.
 */
public class MsgClientFactory {
    private static Logger log = LogManager.getLogger(MsgClientFactory.class.getName());

    public static MsgClient createClient(Properties properties) throws MsgException {
        return createKafkaClient(properties);
    }

    private static MsgClient createKafkaClient(Properties properties) {
        final String zkServer    = getPropertyString(properties, Constant.KEY_ZOOKEEPER_CONNECTION_SERVER,Constant.VALUE_DEFAULT_ZOOKEEPER_CONNECTION_SERVER);
        final String kafkaBroker = getPropertyString(properties, Constant.KEY_KAFKA_CONNECTION_BROKER,    Constant.VALUE_DEFAULT_KAFKA_CONNECTION_BROKER);
        final String groupId  = getPropertyString(properties, Constant.KEY_GROUP_ID ,                  Constant.VALUE_DEFAULT_GROUP_ID);
        final String autoCommit  = getPropertyString(properties, Constant.KEY_AUTO_COMMIT,                  Constant.VALUE_DEFAULT_AUTO_COMMIT);

        return new MsgClientKafkaImpl(zkServer,kafkaBroker,groupId,autoCommit);
    }



    private static String getPropertyString(final Properties properties, final String key, final String defaultValue)  {

        String value = defaultValue;
        try {
            value = getPropertyString(properties, key);
        }
        catch (MsgException e ) {
            log.warn("The value of property {} is not specified, setting to the default value:{}", key, defaultValue);
        }

        return value;
    }


    private static String getPropertyString(final Properties properties, final String key) throws MsgException {
        final String value = properties.getProperty(key);

        if (value==null) {
            throw new MsgException(String.format("Property %s must not be empty.", key));
        }

        return value;
    }

    private static int getPropertyInteger(final Properties properties, final String key) throws MsgException {
        final String strValue = getPropertyString(properties, key);

        try {
            final Integer value = Integer.parseInt(strValue);
            return value;
        }
        catch (NumberFormatException e ) {
            throw new MsgException (String.format("Property %s must has a integer value, but got %s.", key, strValue));
        }
    }

    private static int getPropertyInteger(final Properties properties, final String key, final int defaultValue)  {

        int value = defaultValue;
        try {
            value = getPropertyInteger(properties, key);
        }
        catch (MsgException e ) {
            log.warn("The value of property {} is not specified, setting to the default value:{}", key, defaultValue);
        }

        return value;
    }
}
