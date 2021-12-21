package utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import tokens.Token;
import tokens.braces.CloseBrace;
import tokens.braces.OpenBrace;
import tokens.numbers.NumberToken;
import tokens.operations.DivOperation;
import tokens.operations.MulOperation;
import tokens.operations.SubOperation;
import tokens.operations.SumOperation;

public class TestingUtils {
    public static List<Token> toTokens(List<String> strings) {
        return strings.stream().map(string -> {
            if (Objects.equals(string, "(")) {
                return new OpenBrace();
            } if (Objects.equals(string, ")")) {
                return new CloseBrace();
            } else if (Objects.equals(string, "+")) {
                return new SumOperation();
            } else if (Objects.equals(string, "-")) {
                return new SubOperation();
            } else if (Objects.equals(string, "*")) {
                return new MulOperation();
            } else if (Objects.equals(string, "/")) {
                return new DivOperation();
            } else {
                return new NumberToken(Integer.parseInt(string));
            }
        }).collect(Collectors.toList());
    }
}
