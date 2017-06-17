package paradox.representation;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttributeTest {

    @Test
    public void twoAttributesWithTheSameValueShouldBeEqual() {
        assertEquals(generateStringAttribute(), generateStringAttribute());
        assertEquals(generateListAttribute(), generateListAttribute());
        assertEquals(generateAttributeMap(), generateAttributeMap());
    }

    private ObjectAttribute generateAttributeMap() {
        return new ObjectAttribute("id", ImmutableList.of(
                generateListAttribute(),
                generateStringAttribute()
        ));
    }

    private ListAttribute generateListAttribute() {
        return new ListAttribute("list", ImmutableList.of("a", "b", "c"));
    }

    private StringAttribute generateStringAttribute() {
        return new StringAttribute("id", "value");
    }
}
