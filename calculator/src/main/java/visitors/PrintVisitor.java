package visitors;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import tokens.Token;
import tokens.braces.Brace;
import tokens.numbers.NumberToken;
import tokens.operations.Operation;

public class PrintVisitor implements TokenVisitor<Void> {
    private final PrintStream writer;

    public PrintVisitor(PrintStream writer) {
        this.writer = writer;
    }

    @Override
    public void visitNumber(NumberToken number) {
        writer.printf("%d ", number.getValue());
    }

    @Override
    public void visitBrace(Brace brace) {
        writer.printf("%s ", brace.toString());
    }

    @Override
    public void visitOperation(Operation operation) {
        writer.printf("%s ", operation.toString());
    }

    @Override
    public Void visitAll(List<Token> tokens) {
        for (Token token : tokens) {
            token.accept(this);
        }
        writer.flush();
        return null;
    }
}
