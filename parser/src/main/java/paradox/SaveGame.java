package paradox;

import paradox.representation.Attribute;
import paradox.representation.AttributeVisitor;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Top level class for the paradox save-game.
 */
public final class SaveGame {

    private List<Attribute<?>> attributes;

    /**
     * Constructor which uses basic map of identifiers to attributes
     *
     * @param attributes a list of attributes
     * @apiNote Attributes also contain a copy of their id
     */
    public SaveGame(final List<Attribute<?>> attributes) {
        this.attributes = attributes;
    }

    /**
     * Visit each attribute with the given {@link AttributeVisitor}.
     *
     * @param attributeVisitor a class with methods for each possible type
     */
    public void visitAttributes(final AttributeVisitor attributeVisitor) {
        attributes.forEach(attr -> attr.accept(attributeVisitor));
    }

    public Stream<Attribute<?>> filter(final Predicate<Attribute<?>> predicate) {
        return attributes.stream().filter(predicate);
    }

    public List<Attribute<?>> getAttributes() {
        return attributes;
    }
}
