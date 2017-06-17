package paradox;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import paradox.representation.Attribute;
import paradox.representation.ListAttribute;
import paradox.representation.StringAttribute;

import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.hasValue;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.junit.Assert.assertThat;

public class SaveGameTest {

    private static final StringAttribute VALID_STRING_ATTRIBUTE = new StringAttribute("string", "value");
    private static final ListAttribute VALID_LIST_ATTRIBUTE = new ListAttribute("list", ImmutableList.of("1234", "2345"));

    @Test
    public void shouldBeAbleToLocateAttributesById() {
        // given
        final SaveGame saveGame = new SaveGame(ImmutableList.of(
                VALID_STRING_ATTRIBUTE, VALID_LIST_ATTRIBUTE
        ));

        // when
        final Optional<Attribute<?>> attribute = saveGame
                .filter(attr -> attr
                        .getIdentifier()
                        .equals(VALID_STRING_ATTRIBUTE.getIdentifier()))
                .findFirst();

        // then
        assertThat(attribute, isPresent());
        assertThat(attribute, hasValue(VALID_STRING_ATTRIBUTE));
    }

    @Test
    public void shouldBeAbleToLocateAttributesByIdAndType() {
        // given
        final SaveGame saveGame = new SaveGame(ImmutableList.of(
                VALID_STRING_ATTRIBUTE, VALID_LIST_ATTRIBUTE
        ));

        // when
        final Optional<Attribute<?>> attribute = saveGame
                .filter(attr -> attr instanceof ListAttribute)
                .filter(attr -> attr.getIdentifier().equals(VALID_LIST_ATTRIBUTE.getIdentifier()))
                .findFirst();

        // then
        assertThat(attribute, isPresent());
        assertThat(attribute, hasValue(VALID_LIST_ATTRIBUTE));
    }
}
