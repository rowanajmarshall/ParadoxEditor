package com.kandm.save.game;

public class Attribute {

    private final String identifier;
    private final Object value;
    private final Class<?> valueType;

    public Attribute(final String identifier, final Object value) {
        this.identifier = identifier;
        this.value = value;
        this.valueType = value.getClass();
    }

    public Attribute(final String identifier, final Object value, final Class<?> valueType) {
        this.identifier = identifier;
        this.value = value;
        this.valueType = valueType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getValueType() {
        return valueType;
    }
}
