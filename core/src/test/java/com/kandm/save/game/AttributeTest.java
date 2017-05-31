package com.kandm.save.game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttributeTest {

    @Test
    public void shouldCorrectlySetTypeWhenTypeNotSpecified() {
        // given
        final Attribute attr = new Attribute("test", 42);

        // when
        final Class<?> valueType = attr.getValueType();

        // then
        assertEquals(valueType, Integer.class);
    }
}
