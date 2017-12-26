package com.huntkey.rx.sceo.mpf.common;

/**
 * Created by liangh on 2017/5/18.
 */
public interface MsgConsumerInterface {
    /**
     *
     * @param topic   topic 名称
     * @param message 收到的消息信息
     */
    public void consume(String topic, String message);
}
