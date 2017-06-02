package representation;

public class Attribute<T> {

    private final String identifier;
    private final T value;

    public Attribute(final String identifier, final T value) {
        this.identifier = identifier;
        this.value = value;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Object getValue() {
        return value;
    }
}
