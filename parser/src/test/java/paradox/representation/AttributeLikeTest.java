package paradox.representation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttributeLikeTest {

    @Test
    public void twoAttributesWithTheSameValueShouldBeEqual() {
        assertEquals(generateStringAttribute(), generateStringAttribute());
        assertEquals(generateListAttribute(), generateListAttribute());
        assertEquals(generateAttributeMap(), generateAttributeMap());
    }

    private ObjectAttribute generateAttributeMap() {
        return new ObjectAttribute(ImmutableMap.of(
                "list", generateListAttribute(),
                "string", generateStringAttribute()
        ));
    }

    private ListAttribute generateListAttribute() {
        return new ListAttribute(ImmutableList.of("a", "b", "c"));
    }

    private StringAttribute generateStringAttribute() {
        return new StringAttribute("value");
    }
}
