package paradox.parser;

import org.parboiled.Action;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.annotations.SuppressSubnodes;
import org.parboiled.support.Var;
import paradox.representation.Attribute;
import paradox.representation.ListAttribute;
import paradox.representation.ObjectAttribute;
import paradox.representation.StringAttribute;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@BuildParseTree
class SaveGameTokeniser extends BaseParser<List<Attribute<?>>> {

    private static final String LEGAL_IDENTIFIERS = "abcdefghijklmnopqrstuvwxyz_";

    Rule attributeGroup() {
        return Sequence(
                push(new LinkedList<>()),
                ZeroOrMore(valuePair()),
                EOI
        );
    }

    protected Rule valuePair() {
        final Rule value = FirstOf(listValue(), innerObjectValue(), stringAttribute());

        return Sequence(value, Optional('\n'));
    }

    protected Rule stringAttribute() {

        final Var<String> id = new Var<>();
        final Var<String> value = new Var<>();

        return Sequence(
                identifier(),
                id.set(match()),
                Ch('='),
                Optional('\"'),
                OneOrMore(
                        TestNot(INDENT, DEDENT),
                        NoneOf("\n\"{}")
                ),
                value.set(match()),
                Optional('\"'),
                peek().add(new StringAttribute(id.get(), value.get()))
        );
    }

    protected Rule innerObjectValue() {

        final Var<String> id = new Var<>();
        final Var<List<Attribute<?>>> value = new Var<>();

        final Rule opening = String("{\n");
        final Rule closing = Ch('}');
        final Rule contents = OneOrMore(valuePair());

        return Sequence(
                identifier(),
                id.set(match()),
                '=',
                opening,
                push(new LinkedList<>()),
                indented(contents),
                value.set(pop()),
                closing,
                peek().add(new ObjectAttribute(id.get(), value.get()))
        );
    }

    protected Rule listValue() {
        final Var<String> id = new Var<>();
        final Var<List<String>> value = new Var<>(new LinkedList<>());

        final Rule opening = String("{\n");
        final Rule closing = Ch('}');
        final Rule contents = OneOrMore(
                Sequence(OneOrMore(NoneOf("\" {}\n")), addToList(value), Optional(' '))
        );

        return Sequence(
                identifier(),
                id.set(match()),
                '=',
                opening,
                indented(
                        Sequence(
                                OneOrMore(contents),
                                '\n'
                        )
                ),
                closing,
                peek().add(new ListAttribute(id.get(), value.get())));
    }

    @SuppressSubnodes
    protected Rule indented(final Rule rule) {
        return Sequence(INDENT, rule, DEDENT);
    }

    protected Rule identifier() {
        return OneOrMore(AnyOf(LEGAL_IDENTIFIERS));
    }

    protected Action addToList(final Var<List<String>> list) {
        return context -> {
            list.get().add(context.getMatch());
            return true;
        };
    }
}
