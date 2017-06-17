package paradox.representation;

public class StringAttribute extends Attribute<String> {
    public StringAttribute(final String identifier, final String value) {
        super(identifier, value);
    }

    @Override
    public void accept(AttributeVisitor attributeVisitor) {
        attributeVisitor.visit(this);
    }

}
