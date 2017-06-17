package paradox.parser;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import paradox.SaveGame;
import paradox.representation.Attribute;
import paradox.representation.ListAttribute;
import paradox.representation.ObjectAttribute;
import paradox.representation.StringAttribute;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.hasValue;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

public class ParadoxParserTest {

    private ParadoxFileParser paradoxFileParser;
    private File saveGameFile;

    @Before
    public void setup() {
        paradoxFileParser = new ParadoxFileParser(true);

        final URL resource = getClass().getClassLoader().getResource("example.eu4");

        assert resource != null;

        saveGameFile = new File(resource.getFile());
    }

    @Test
    public void shouldCorrectlyReadExampleSaveGameFile() throws FileNotFoundException {
        // when
        final Optional<SaveGame> result = paradoxFileParser.parseFile(saveGameFile);

        // then
        assertThat(result, isPresent());
        assertThat(result, hasValue(
                hasProperty("attributes", is(expectedAttributes()))
        ));
    }

    private List<Attribute<?>> expectedAttributes() {
        final List<Attribute<?>> general = ImmutableList.of(
                new StringAttribute("name", "Bhenji Patidar"),
                new StringAttribute("dynasty", "Patidar"),
                new StringAttribute("type", "general"),
                new StringAttribute("manuever", "2"),
                new StringAttribute("shock", "1"),
                new StringAttribute("siege", "1"),
                new StringAttribute("country", "REB"),
                new StringAttribute("activation", "1444.11.11"),
                new ObjectAttribute("id", ImmutableList.of(
                        new StringAttribute("id", "1604"),
                        new StringAttribute("type", "49")
                ))
        );

        return ImmutableList.of(
                new ObjectAttribute("id", ImmutableList.of(
                        new ObjectAttribute("id", ImmutableList.of(
                                new StringAttribute("id", "571"),
                                new StringAttribute("type", "50")
                        )),
                        new StringAttribute("type", "noble_rebels"),
                        new StringAttribute("name", "Multani Noble Rebels"),
                        new StringAttribute("heretic", "Yazidi"),
                        new StringAttribute("country", "MUL"),
                        new StringAttribute("religion", "sunni"),
                        new StringAttribute("government", "feudal_monarchy"),
                        new StringAttribute("province", "2079"),
                        new StringAttribute("seed", "981932888"),
                        new ObjectAttribute("general", general),
                        new ObjectAttribute("leader", ImmutableList.of(
                                new StringAttribute("id", "1604"),
                                new StringAttribute("type", "49")
                        )),
                        new ListAttribute("possible_provinces", ImmutableList.of("2079", "2086")),
                        new StringAttribute("active", "no")
                ))
        );
    }
}
