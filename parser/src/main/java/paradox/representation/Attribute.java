package paradox.representation;

/**
 * Simple representation of an attribute.
 *
 * @param <T> the type of the attribute's value.
 */
public abstract class Attribute<T> {

    protected final String identifier;
    protected final T value;

    public Attribute(final String identifier, final T value) {
        this.identifier = identifier;
        this.value = value;
    }

    public abstract void accept(final AttributeVisitor attributeVisitor);

    public String getIdentifier() {
        return identifier;
    }

    public T getValue() {
        return value;
    }

    @SuppressWarnings("unchecked")
    public Class<T> getValueType() {
        return (Class<T>) value.getClass();
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass().isInstance(this)) {
            final Attribute<?> attr = (Attribute<?>) other;
            return value.equals(attr.getValue());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        final String className = value.getClass().getSimpleName();

        return "Attribute:[id=\"" + identifier + "\" value=\"" + value.toString() + "\", type=" + className + "]";
    }
}
