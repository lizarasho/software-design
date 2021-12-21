import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import tokens.StateTokenizer;
import tokens.Token;
import tokens.Tokenizer;
import visitors.CalcVisitor;
import visitors.ParseVisitor;
import visitors.PrintVisitor;
import visitors.TokenVisitor;

public class App {
    public static void main(String[] args) {
        TokenVisitor<List<Token>> parseVisitor = new ParseVisitor();
        TokenVisitor<Integer> calcVisitor = new CalcVisitor();
        TokenVisitor<Void> printVisitor = new PrintVisitor(System.out);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String inputText = reader.readLine();
            InputStream inputStream = new ByteArrayInputStream(inputText.getBytes());

            Tokenizer tokenizer = new StateTokenizer(inputStream);

            List<Token> inputTokens = tokenizer.toList();
            List<Token> parsedTokens = parseVisitor.visitAll(inputTokens);
            int result = calcVisitor.visitAll(parsedTokens);

            System.out.println("reverse polish notation: ");
            printVisitor.visitAll(parsedTokens);
            System.out.println();

            System.out.format("result: %d", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
