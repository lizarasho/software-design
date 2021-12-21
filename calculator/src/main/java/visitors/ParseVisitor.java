package visitors;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import tokens.Token;
import tokens.Tokenizer;
import tokens.braces.Brace;
import tokens.braces.CloseBrace;
import tokens.braces.OpenBrace;
import tokens.numbers.NumberToken;
import tokens.operations.Operation;

public class ParseVisitor implements TokenVisitor<List<Token>> {
    private final List<Token> result = new ArrayList<>();
    private final Stack<Token> stack = new Stack<>();

    @Override
    public void visitNumber(NumberToken number) {
        result.add(number);
    }

    @Override
    public void visitBrace(Brace brace) {
        if (brace instanceof OpenBrace) {
            stack.add(brace);
        } else if (brace instanceof CloseBrace) {
            if (stack.empty()) {
                throw new IllegalStateException("Unexpected closing brace: opening brace was missed.");
            }
            Token currentToken = stack.pop();
            while (!(currentToken instanceof OpenBrace)) {
                result.add(currentToken);
                if (stack.isEmpty()) {
                    throw new IllegalStateException("Unexpected closing brace: opening brace was missed.");
                }
                currentToken = stack.pop();
            }
        }
    }

    @Override
    public void visitOperation(Operation operation) {
        if (!stack.empty()) {
            Token currentToken = stack.peek();
            while (currentToken instanceof Operation && ((Operation) currentToken).getPriority() >= operation.getPriority()) {
                result.add(stack.pop());
                if (stack.empty()) {
                    break;
                }
                currentToken = stack.peek();
            }
        }
        stack.add(operation);
    }

    @Override
    public List<Token> visitAll(List<Token> tokens) {
        result.clear();
        for (Token token : tokens) {
            token.accept(this);
        }
        while (!stack.empty()) {
            result.add(stack.pop());
        }
        return result;
    }
}
