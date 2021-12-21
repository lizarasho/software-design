package tokens.numbers;

import tokens.BaseToken;
import visitors.TokenVisitor;

public class NumberToken extends BaseToken {
    private final int value;

    public NumberToken(int value) {
        super(Integer.toString(value));
        this.value = value;
    }

    @Override
    public void accept(TokenVisitor<?> visitor) {
        visitor.visitNumber(this);
    }

    public int getValue() {
        return value;
    }
}
