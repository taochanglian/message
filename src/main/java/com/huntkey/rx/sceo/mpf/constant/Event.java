package com.huntkey.rx.sceo.mpf.constant;

import org.apache.avro.AvroRuntimeException;
import org.apache.avro.Schema;
import org.apache.avro.data.RecordBuilder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.*;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created by liangh on 2017/5/26.
 */

@SuppressWarnings("all")
@AvroGenerated
public class Event extends SpecificRecordBase implements SpecificRecord {
    private static final long serialVersionUID = 3019453098083125873L;
    public static final Schema SCHEMA$ =
            new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Event\",\"" +
                                    "namespace\":\"com.huntkey.rx.sceo.mpf.constant\",\"" +
                                    "fields\":[{\"name\":\"name\",\"type\":\"string\"}," +
                                              "{\"name\":\"time\",\"type\":\"string\"}," +
                                              "{\"name\":\"address\",\"type\":\"string\"}," +
                                              "{\"name\":\"context\",\"type\":[\"string\",\"null\"]}]}");
    public static Schema getClassSchema() { return SCHEMA$; }
    @Deprecated public CharSequence name;
    @Deprecated public CharSequence time;
    @Deprecated public CharSequence address;
    @Deprecated public CharSequence context;

    public Event() {}

    public Event(CharSequence name, CharSequence time, CharSequence address, CharSequence context) {
        this.name = name;
        this.time = time;
        this.address = address;
        this.context = context;
    }

    public Schema getSchema() { return SCHEMA$; }

    public Object get(int field$) {
        switch (field$) {
            case 0: return name;
            case 1: return time;
            case 2: return address;
            case 3: return context;
            default: throw new AvroRuntimeException("Bad index");
        }
    }

    // Used by DatumReader.  Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int field$, Object value$) {
        switch (field$) {
            case 0: name = (CharSequence)value$; break;
            case 1: time = (CharSequence)value$; break;
            case 2: address = (CharSequence)value$; break;
            case 3: context = (CharSequence)value$; break;
            default: throw new AvroRuntimeException("Bad index");
        }
    }

    public CharSequence getName() {
        return name;
    }

    public void setName(CharSequence value) {
        this.name = value;
    }

    public CharSequence getTime() {
        return time;
    }

    public void setTime(CharSequence value) {
        this.time = value;
    }

    public CharSequence getAddress() {
        return address;
    }

    public void setAddress(CharSequence value) {
        this.address = value;
    }

    public CharSequence getContext() {
        return context;
    }

    public void setContext(CharSequence value) {
        this.context = value;
    }


    public static Event.Builder newBuilder() {
        return new Event.Builder();
    }

    public static Event.Builder newBuilder(Event.Builder other) {
        return new Event.Builder(other);
    }

    public static Event.Builder newBuilder(Event other) {
        return new Event.Builder(other);
    }

    /**
     * RecordBuilder for User instances.
     */
    public static class Builder extends SpecificRecordBuilderBase<Event>
            implements RecordBuilder<Event> {

        private CharSequence name;
        private CharSequence time;
        private CharSequence address;
        private CharSequence context;

        /** Creates a new Builder */
        private Builder() {
            super(SCHEMA$);
        }

        /**
         * Creates a Builder by copying an existing Builder.
         * @param other The existing Builder to copy.
         */
        private Builder(Event.Builder other) {
            super(other);
            if (isValidValue(fields()[0], other.name)) {
                this.name = data().deepCopy(fields()[0].schema(), other.name);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.time)) {
                this.time = data().deepCopy(fields()[1].schema(), other.time);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.address)) {
                this.address = data().deepCopy(fields()[2].schema(), other.address);
                fieldSetFlags()[2] = true;
            }
            if (isValidValue(fields()[3], other.context)) {
                this.context = data().deepCopy(fields()[3].schema(), other.context);
                fieldSetFlags()[3] = true;
            }
        }

        /**
         * Creates a Builder by copying an existing User instance
         * @param other The existing instance to copy.
         */
        private Builder(Event other) {
            super(SCHEMA$);
            if (isValidValue(fields()[0], other.name)) {
                this.name = data().deepCopy(fields()[0].schema(), other.name);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.time)) {
                this.time = data().deepCopy(fields()[1].schema(), other.time);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.address)) {
                this.address = data().deepCopy(fields()[2].schema(), other.address);
                fieldSetFlags()[2] = true;
            }
            if (isValidValue(fields()[3], other.context)) {
                this.context = data().deepCopy(fields()[3].schema(), other.context);
                fieldSetFlags()[3] = true;
            }
        }

        //name
        public CharSequence getName() {
            return name;
        }
        public Event.Builder setName(CharSequence value) {
            validate(fields()[0], value);
            this.name = value;
            fieldSetFlags()[0] = true;
            return this;
        }
        public boolean hasName() {
            return fieldSetFlags()[0];
        }
        public Event.Builder clearName() {
            name = null;
            fieldSetFlags()[0] = false;
            return this;
        }

        //time
        public CharSequence getTime() {
            return time;
        }
        public Event.Builder setTime(CharSequence value) {
            validate(fields()[1], value);
            this.time = value;
            fieldSetFlags()[1] = true;
            return this;
        }
        public boolean hasTime() {
            return fieldSetFlags()[1];
        }
        public Event.Builder clearTime() {
            time = null;
            fieldSetFlags()[1] = false;
            return this;
        }

        //address
        public CharSequence getAddress() {
            return address;
        }
        public Event.Builder setAddress(CharSequence value) {
            validate(fields()[2], value);
            this.address = value;
            fieldSetFlags()[2] = true;
            return this;
        }
        public boolean hasAddress() {
            return fieldSetFlags()[2];
        }
        public Event.Builder clearAddress() {
            address = null;
            fieldSetFlags()[2] = false;
            return this;
        }

        //context
        public CharSequence getContext() {
            return context;
        }
        public Event.Builder setContext(CharSequence value) {
            validate(fields()[3], value);
            this.context = value;
            fieldSetFlags()[3] = true;
            return this;
        }
        public boolean hasContext() {
            return fieldSetFlags()[3];
        }
        public Event.Builder clearContext() {
            context = null;
            fieldSetFlags()[3] = false;
            return this;
        }

        @Override
        public Event build() {
            try {
                Event record = new Event();
                record.name = fieldSetFlags()[0] ? this.name : (CharSequence) defaultValue(fields()[0]);
                record.time = fieldSetFlags()[1] ? this.time : (CharSequence) defaultValue(fields()[1]);
                record.address = fieldSetFlags()[2] ? this.address : (CharSequence) defaultValue(fields()[2]);
                record.context = fieldSetFlags()[3] ? this.context : (CharSequence) defaultValue(fields()[3]);
                return record;
            } catch (Exception e) {
                throw new AvroRuntimeException(e);
            }
        }
    }

    private static final DatumWriter
            WRITER$ = new SpecificDatumWriter(SCHEMA$);

    @Override public void writeExternal(ObjectOutput out)
            throws IOException {
        WRITER$.write(this, SpecificData.getEncoder(out));
    }

    private static final DatumReader
            READER$ = new SpecificDatumReader(SCHEMA$);

    @Override public void readExternal(ObjectInput in)
            throws IOException {
        READER$.read(this, SpecificData.getDecoder(in));
    }

}

