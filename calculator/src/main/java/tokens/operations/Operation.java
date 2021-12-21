package tokens.operations;

import tokens.BaseToken;
import visitors.TokenVisitor;

public abstract class Operation extends BaseToken {
    private final int priority;

    public Operation(String string, int priority) {
        super(string);
        this.priority = priority;
    }

    @Override
    public void accept(TokenVisitor<?> visitor) {
        visitor.visitOperation(this);
    }

    public int getPriority() {
        return priority;
    }
}
