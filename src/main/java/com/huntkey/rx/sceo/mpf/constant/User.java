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
 * Created by liangh on 2017/5/19.
 */

@SuppressWarnings("all")
@AvroGenerated
public class User extends SpecificRecordBase implements SpecificRecord {
    private static final long serialVersionUID = 3019453098083125873L;
    public static final Schema SCHEMA$ =
            new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"User\",\"namespace\":\"com.huntkey.rx.sceo.mpf.constant\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"address\",\"type\":[\"string\",\"null\"]}]}");
    public static Schema getClassSchema() { return SCHEMA$; }
    @Deprecated public CharSequence name;
    @Deprecated public CharSequence address;

    /**
     * Default constructor.  Note that this does not initialize fields
     * to their default values from the schema.  If that is desired then
     * one should use <code>newBuilder()</code>.
     */
    public User() {}

    /**
     * All-args constructor.
     * @param name The new value for name
     * @param address The new value for address
     */
    public User(CharSequence name, CharSequence address) {
        this.name = name;
        this.address = address;
    }

    public Schema getSchema() { return SCHEMA$; }
    // Used by DatumWriter.  Applications should not call.
    public Object get(int field$) {
        switch (field$) {
            case 0: return name;
            case 1: return address;
            default: throw new AvroRuntimeException("Bad index");
        }
    }

    // Used by DatumReader.  Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int field$, Object value$) {
        switch (field$) {
            case 0: name = (CharSequence)value$; break;
            case 1: address = (CharSequence)value$; break;
            default: throw new AvroRuntimeException("Bad index");
        }
    }

    /**
     * Gets the value of the 'name' field.
     * @return The value of the 'name' field.
     */
    public CharSequence getName() {
        return name;
    }

    /**
     * Sets the value of the 'name' field.
     * @param value the value to set.
     */
    public void setName(CharSequence value) {
        this.name = value;
    }

    /**
     * Gets the value of the 'address' field.
     * @return The value of the 'address' field.
     */
    public CharSequence getAddress() {
        return address;
    }

    /**
     * Sets the value of the 'address' field.
     * @param value the value to set.
     */
    public void setAddress(CharSequence value) {
        this.address = value;
    }

    /**
     * Creates a new User RecordBuilder.
     * @return A new User RecordBuilder
     */
    public static User.Builder newBuilder() {
        return new User.Builder();
    }

    /**
     * Creates a new User RecordBuilder by copying an existing Builder.
     * @param other The existing builder to copy.
     * @return A new User RecordBuilder
     */
    public static User.Builder newBuilder(User.Builder other) {
        return new User.Builder(other);
    }

    /**
     * Creates a new User RecordBuilder by copying an existing User instance.
     * @param other The existing instance to copy.
     * @return A new User RecordBuilder
     */
    public static User.Builder newBuilder(User other) {
        return new User.Builder(other);
    }

    /**
     * RecordBuilder for User instances.
     */
    public static class Builder extends SpecificRecordBuilderBase<User>
            implements RecordBuilder<User> {

        private CharSequence name;
        private CharSequence address;

        /** Creates a new Builder */
        private Builder() {
            super(SCHEMA$);
        }

        /**
         * Creates a Builder by copying an existing Builder.
         * @param other The existing Builder to copy.
         */
        private Builder(User.Builder other) {
            super(other);
            if (isValidValue(fields()[0], other.name)) {
                this.name = data().deepCopy(fields()[0].schema(), other.name);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.address)) {
                this.address = data().deepCopy(fields()[1].schema(), other.address);
                fieldSetFlags()[1] = true;
            }
        }

        /**
         * Creates a Builder by copying an existing User instance
         * @param other The existing instance to copy.
         */
        private Builder(User other) {
            super(SCHEMA$);
            if (isValidValue(fields()[0], other.name)) {
                this.name = data().deepCopy(fields()[0].schema(), other.name);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.address)) {
                this.address = data().deepCopy(fields()[1].schema(), other.address);
                fieldSetFlags()[1] = true;
            }
        }

        /**
         * Gets the value of the 'name' field.
         * @return The value.
         */
        public CharSequence getName() {
            return name;
        }

        /**
         * Sets the value of the 'name' field.
         * @param value The value of 'name'.
         * @return This builder.
         */
        public User.Builder setName(CharSequence value) {
            validate(fields()[0], value);
            this.name = value;
            fieldSetFlags()[0] = true;
            return this;
        }

        /**
         * Checks whether the 'name' field has been set.
         * @return True if the 'name' field has been set, false otherwise.
         */
        public boolean hasName() {
            return fieldSetFlags()[0];
        }


        /**
         * Clears the value of the 'name' field.
         * @return This builder.
         */
        public User.Builder clearName() {
            name = null;
            fieldSetFlags()[0] = false;
            return this;
        }

        /**
         * Gets the value of the 'address' field.
         * @return The value.
         */
        public CharSequence getAddress() {
            return address;
        }

        /**
         * Sets the value of the 'address' field.
         * @param value The value of 'address'.
         * @return This builder.
         */
        public User.Builder setAddress(CharSequence value) {
            validate(fields()[1], value);
            this.address = value;
            fieldSetFlags()[1] = true;
            return this;
        }

        /**
         * Checks whether the 'address' field has been set.
         * @return True if the 'address' field has been set, false otherwise.
         */
        public boolean hasAddress() {
            return fieldSetFlags()[1];
        }


        /**
         * Clears the value of the 'address' field.
         * @return This builder.
         */
        public User.Builder clearAddress() {
            address = null;
            fieldSetFlags()[1] = false;
            return this;
        }

        @Override
        public User build() {
            try {
                User record = new User();
                record.name = fieldSetFlags()[0] ? this.name : (CharSequence) defaultValue(fields()[0]);
                record.address = fieldSetFlags()[1] ? this.address : (CharSequence) defaultValue(fields()[1]);
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

