package visitors;


import java.util.List;

import tokens.braces.Brace;
import tokens.numbers.NumberToken;
import tokens.Token;
import tokens.operations.Operation;

public interface TokenVisitor<T> {
    void visitNumber(NumberToken number);

    void visitBrace(Brace brace);

    void visitOperation(Operation operation);

    T visitAll(List<Token> tokens);
}
