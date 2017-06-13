package paradox.representation;

import java.util.List;

public class ListAttribute extends AttributeLike<List<String>> implements Attribute {
    public ListAttribute(final List<String> value) {
        super(value);
    }
}
