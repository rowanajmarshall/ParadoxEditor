package parser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.annotations.SuppressSubnodes;
import org.parboiled.support.Var;
import representation.Attribute;

import java.util.LinkedList;
import java.util.List;

@BuildParseTree
public class AttributeGroupParser extends BaseParser<List<Attribute<?>>> {

    private static final String LEGAL_IDENTIFIERS = "abcdefghijklmnopqrstuvwxyz_";

    Rule attributeGroup() {
        return Sequence(ZeroOrMore(
                push(new LinkedList<>()),
                valuePair()), EOI);
    }

    protected Rule valuePair() {
        final Rule identifier = OneOrMore(AnyOf(LEGAL_IDENTIFIERS));

        final Var<String> id = new Var<>();
        final Var<Object> value = new Var<>();

        final Rule valuePair = Sequence(
                identifier,
                id.set(match()),
                '=', value(),
                value.set(match())
        );

        return Sequence(
                valuePair,
                peek().add(new Attribute<>(id.get(), value.get())),
                Optional('\n')
        );
    }

    protected Rule value() {
        return FirstOf(
                stringValue(),
                dateValue(),
                integerValue(),
                innerObjectValue(),
                unidentifiedValue()
        );
    }

    protected Rule innerObjectValue() {
        final Rule opening = String("{\n");
        final Rule closing = Ch('}');
        final Rule contents = OneOrMore(valuePair());

        return Sequence(opening, indented(contents), closing);
    }

    protected Rule dateValue() {
        final Rule year = NTimes(4, digitValue());
        final Rule month = NTimes(2, digitValue());
        final Rule day = NTimes(2, digitValue());

        return Sequence(year, '.', month, '.', day);
    }

    protected Rule unidentifiedValue() {
        return OneOrMore(ANY);
    }

    protected Rule stringValue() {
        return Sequence('\"', OneOrMore(TestNot('\"'), ANY), '\"');
    }

    protected Rule indented(final Rule rule) {
        return Sequence(INDENT, rule, DEDENT);
    }

    protected Rule digitValue() {
        return AnyOf("0123456789");
    }

    @SuppressSubnodes
    protected Rule integerValue() {
        return OneOrMore(digitValue());
    }
}
