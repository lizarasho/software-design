package tokens;

import java.util.List;

public interface Tokenizer {
    Token next();
    List<Token> toList();
}
