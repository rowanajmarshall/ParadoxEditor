package parser;

import org.parboiled.Action;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.support.Var;
import representation.SaveGameTree;

@BuildParseTree
class ParadoxParser extends BaseParser<SaveGameTree> {

    Rule SaveGame() {
        Var<Integer> indentationLevel = new Var<>();
        indentationLevel.set(0);
        return Sequence(ZeroOrMore(ValuePair(indentationLevel)), EOI);
    }

    protected Rule ValuePair(Var<Integer> indentationLevel) {
        return Sequence(
                NTimes(indentationLevel.get(), INDENT),
                Identifier(),
                Ch('='),
                Value(indentationLevel),
                Optional('\n'));
    }

    protected Rule Identifier() {
        return OneOrMore(AnyOf("abcdefghijklmnopqrstuvwxyz_"));
    }

    protected Rule Value(Var<Integer> indentationLevel) {
        return FirstOf(
                StringValue(),
                DigitValue(),
                RecursiveObjectValue(indentationLevel)
        );
    }

    protected Rule StringValue() {
        return Sequence('\"', OneOrMore(NoneOf("\"")), '\"');
    }

    protected Rule DigitValue() {
        return OneOrMore(AnyOf("0123456789."));
    }

    protected Rule RecursiveObjectValue(Var<Integer> indentationLevel) {
        return Sequence(
                OneOrMore(INDENT),
                "{\n",
                indentationLevel.set(getContext().getMatchLength() + 1),
                OneOrMore(ValuePair(indentationLevel)),
                "}"
        );
    }
}
