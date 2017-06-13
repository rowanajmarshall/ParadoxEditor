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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@BuildParseTree
class SaveGameTokeniser extends BaseParser<Map<String, Attribute>> {

    private static final String LEGAL_IDENTIFIERS = "abcdefghijklmnopqrstuvwxyz_";

    Rule attributeGroup() {
        return Sequence(
                push(new HashMap<>()),
                ZeroOrMore(valuePair()),
                EOI
        );
    }

    protected Rule valuePair() {
        final Rule identifier = OneOrMore(AnyOf(LEGAL_IDENTIFIERS));

        final Var<String> id = new Var<>();
        final Var<Attribute> valueHolder = new Var<>();

        final Rule value = FirstOf(listValue(valueHolder), innerObjectValue(valueHolder), stringAttribute(valueHolder));

        final Rule valuePair = Sequence(
                identifier,
                id.set(match()),
                '=', value,
                addToMap(id, valueHolder)
        );

        return Sequence(
                valuePair,
                Optional('\n')
        );
    }

    protected Rule stringAttribute(final Var<Attribute> value) {
        final Rule content = Sequence(OneOrMore(TestNot(INDENT, DEDENT), NoneOf("\n\"{}")), value.set(new StringAttribute(match())));
        return Sequence(Optional('\"'), content, Optional('\"'));
    }

    protected Rule innerObjectValue(final Var<Attribute> value) {
        final Rule opening = String("{\n");
        final Rule closing = Ch('}');
        final Rule contents = OneOrMore(valuePair());

        return Sequence(opening,
                push(new HashMap<>()),
                indented(contents),
                value.set(new ObjectAttribute(pop())),
                closing
        );
    }

    protected Rule listValue(final Var<Attribute> value) {
        final Var<List<String>> list = new Var<>(new LinkedList<>());

        final Rule opening = String("{\n");
        final Rule closing = Ch('}');
        final Rule contents = OneOrMore(
                Sequence(OneOrMore(NoneOf("\" {}\n")), addToList(list), Optional(' '))
        );

        return Sequence(opening, indented(Sequence(OneOrMore(contents), '\n')), value.set(new ListAttribute(list.get())), closing);
    }

    @SuppressSubnodes
    protected Rule indented(final Rule rule) {
        return Sequence(INDENT, rule, DEDENT);
    }

    protected Action<Map<String, Attribute>> addToMap(final Var<String> id, final Var<Attribute> value) {
        return context -> {
            final Map<String, Attribute> attributeMap = context.getValueStack().peek();

            attributeMap.put(id.get(), value.get());

            return true;
        };
    }

    protected Action addToList(final Var<List<String>> list) {
        return context -> {
            list.get().add(context.getMatch());
            return true;
        };
    }

}
