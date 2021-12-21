package tokens.braces;

import tokens.BaseToken;
import visitors.TokenVisitor;

public abstract class Brace extends BaseToken {
    public Brace(String string) {
        super(string);
    }

    @Override
    public void accept(TokenVisitor<?> visitor) {
        visitor.visitBrace(this);
    }
}
