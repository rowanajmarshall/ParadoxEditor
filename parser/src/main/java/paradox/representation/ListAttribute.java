package paradox.representation;

import java.util.List;

public final class ListAttribute extends Attribute<List<String>> {
    public ListAttribute(final String identifier, final List<String> value) {
        super(identifier, value);
    }

    @Override
    public void accept(final AttributeVisitor attributeVisitor) {
        attributeVisitor.visit(this);
    }

}
