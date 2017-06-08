package paradox.parser;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import paradox.representation.Attribute;
import paradox.representation.ListAttribute;
import paradox.representation.ObjectAttribute;
import paradox.representation.StringAttribute;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

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
        // given
        final Map<String, Attribute> expected = expectedMap();

        // when
        final Optional<Map<String, Attribute>> result = paradoxFileParser.parseFile(saveGameFile);

        // then
        assertThat(result, isPresent());
        assertEquals(result.get(), expected);
    }

    private Map<String, Attribute> expectedMap() {
        final Map<String, Attribute> general = new ImmutableMap.Builder<String, Attribute>()
                .put("name", new StringAttribute("Bhenji Patidar"))
                .put("dynasty", new StringAttribute("Patidar"))
                .put("type", new StringAttribute("general"))
                .put("manuever", new StringAttribute("2"))
                .put("shock", new StringAttribute("1"))
                .put("siege", new StringAttribute("1"))
                .put("country", new StringAttribute("REB"))
                .put("activation", new StringAttribute("1444.11.11"))
                .put("id", new ObjectAttribute(ImmutableMap.of(
                        "id", new StringAttribute("1604"),
                        "type", new StringAttribute("49")
                )))
                .build();

        final Map<String, Attribute> rebelFaction = new ImmutableMap.Builder<String, Attribute>()
                .put("id", new ObjectAttribute(ImmutableMap.of(
                        "id", new StringAttribute("571"),
                        "type", new StringAttribute("50")
                )))
                .put("type", new StringAttribute("noble_rebels"))
                .put("name", new StringAttribute("Multani Noble Rebels"))
                .put("heretic", new StringAttribute("Yazidi"))
                .put("country", new StringAttribute("MUL"))
                .put("religion", new StringAttribute("sunni"))
                .put("government", new StringAttribute("feudal_monarchy"))
                .put("province", new StringAttribute("2079"))
                .put("seed", new StringAttribute("981932888"))
                .put("general", new ObjectAttribute(general))
                .put("leader", new ObjectAttribute(ImmutableMap.of(
                        "id", new StringAttribute("1604"),
                        "type", new StringAttribute("49")
                )))
                .put("possible_provinces", new ListAttribute(ImmutableList.of("2079", "2086")))
                .put("active", new StringAttribute("no"))
                .build();

        return ImmutableMap.of("rebel_faction", new ObjectAttribute(rebelFaction));
    }
}
