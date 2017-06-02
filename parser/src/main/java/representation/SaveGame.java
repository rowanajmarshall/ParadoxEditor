package representation;

import java.util.List;

public class SaveGame {
    private final List<Attribute> attributes;

    public SaveGame(final List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }
}
