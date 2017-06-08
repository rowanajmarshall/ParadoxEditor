package paradox.representation;

import java.util.Map;

public class ObjectAttribute extends AttributeLike<Map<String, Attribute>> implements Attribute {
    public ObjectAttribute(final Map<String, Attribute> value) {
        super(value);
    }
}
