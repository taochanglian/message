package com.huntkey.rx.sceo.mpf.constant;

import org.apache.avro.specific.SpecificRecordBase;

import java.util.EnumSet;

public enum Topic {

    USER("testUser", new User()),
    EVENT("testEvent", new Event());

    public final String topicName;
    public final SpecificRecordBase topicType;

    public String getTopicName() {
        return this.topicName;
    }

    public SpecificRecordBase getTopicType() { return this.topicType; }

    Topic(String topicName, SpecificRecordBase topicType) {
        this.topicName = topicName;
        this.topicType = topicType;
    }

    public static Topic matchFor(String topicName) {
        return EnumSet.allOf(Topic.class).stream()
                .filter(topic -> topic.topicName.equals(topicName))
                .findFirst()
                .orElse(null);
    }
}
