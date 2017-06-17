package paradox.representation;

import java.util.List;

public class ObjectAttribute extends Attribute<List<Attribute<?>>> {
    public ObjectAttribute(final String identifier, final List<Attribute<?>> value) {
        super(identifier, value);
    }

    @Override
    public void accept(final AttributeVisitor attributeVisitor) {
        attributeVisitor.visit(this);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("Attribute:[id=\"" + identifier + "\" value=\"\n");

        value.forEach(x -> stringBuilder
                .append("\t")
                .append(x.toString())
                .append("\n"));

        stringBuilder.append("\", type=List]");

        return stringBuilder.toString();
    }
}
