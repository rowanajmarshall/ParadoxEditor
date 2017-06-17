package paradox.representation;

public interface AttributeVisitor {

    void visit(final StringAttribute strAttr);
    void visit(final ObjectAttribute strAttr);
    void visit(final ListAttribute strAttr);
}
