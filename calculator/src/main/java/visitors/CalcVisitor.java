package visitors;

import java.util.List;
import java.util.Stack;

import tokens.Token;
import tokens.braces.Brace;
import tokens.numbers.NumberToken;
import tokens.operations.DivOperation;
import tokens.operations.MulOperation;
import tokens.operations.Operation;
import tokens.operations.SubOperation;
import tokens.operations.SumOperation;

public class CalcVisitor implements TokenVisitor<Integer> {
    public final Stack<Integer> stack = new Stack<>();

    @Override
    public void visitNumber(NumberToken number) {
        stack.add(number.getValue());
    }

    @Override
    public void visitBrace(Brace brace) {
        // Empty
    }

    @Override
    public void visitOperation(Operation operation) {
        if (stack.size() < 2) {
            throw new IllegalStateException(String.format(
                    "Couldn't apply operation %s, at least two arguments are required.",
                    operation.toString()
            ));
        }
        int right = stack.pop();
        int left = stack.pop();
        int operationResult;
        if (operation instanceof SumOperation) {
            operationResult = left + right;
        } else if (operation instanceof SubOperation) {
            operationResult = left - right;
        } else if (operation instanceof MulOperation) {
            operationResult = left * right;
        } else if (operation instanceof DivOperation) {
            operationResult = left / right;
        } else {
            throw new IllegalArgumentException(String.format(
                    "Unknown operation %s, only \"+\", \"-\", \"*\", \"/\" are possible.",
                    operation.getClass().toString()
            ));
        }
        stack.add(operationResult);
    }

    @Override
    public Integer visitAll(List<Token> tokens) {
        stack.clear();
        for (Token token : tokens) {
            token.accept(this);
        }
        if (stack.size() < 1) {
            throw new IllegalStateException(String.format(
                    "Couldn't calculate the result for tokens %s: there are no numbers.", tokens
            ));
        }
        if (stack.size() > 1) {
            throw new IllegalStateException(String.format(
                    "Couldn't calculate the result for tokens %s: all operations were not processed.", tokens
            ));
        }
        return stack.peek();
    }
}
