package paradox.representation;

import java.util.Optional;

public abstract class AttributeLike<T> {

    protected final T value;

    public AttributeLike(final T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return extractAttribute(other)
                .map(attr -> value.equals(attr.getValue()))
                .orElse(false);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        final String className = value.getClass().getSimpleName();

        return "Attribute:[value=\"" + value.toString() + "\", type=" + className + "]";
    }

    private Optional<AttributeLike<?>> extractAttribute(final Object other) {
        if (other.getClass().isInstance(this)) {
            return Optional.of((AttributeLike<?>) other);

        } else {
            return Optional.empty();
        }
    }
}
