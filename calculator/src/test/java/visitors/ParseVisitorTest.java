package visitors;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tokens.Token;

import static utils.TestingUtils.toTokens;

public class ParseVisitorTest {
    private TokenVisitor<List<Token>> tokenVisitor;

    TokenVisitor<List<Token>> createVisitor() {
        return new ParseVisitor();
    }

    @BeforeMethod
    void beforeTest() {
        tokenVisitor = createVisitor();
    }

    @DataProvider(name = "positiveParams")
    public Object[][] positiveParams() {
        return new Object[][]{
                {
                        toTokens(List.of("3", "+", "2")),
                        toTokens(List.of("3", "2", "+"))
                },
                {
                        toTokens(List.of("3", "+", "2", "*", "89", "-", "90", "/", "11")),
                        toTokens(List.of("3", "2", "89", "*", "+", "90", "11", "/", "-"))
                },
                {
                        toTokens(List.of("(", "3", "+", "2", ")", "*", "(", "89", "-", "90", ")", "/", "11")),
                        toTokens(List.of("3", "2", "+", "89", "90", "-", "*", "11", "/"))
                }
        };
    }

    @Test(dataProvider = "positiveParams")
    void positiveTest(List<Token> inputTokens, List<Token> expected) {
        List<Token> actual = tokenVisitor.visitAll(inputTokens);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "negativeParams")
    public Object[][] negativeParams() {
        return new Object[][]{
                {toTokens(List.of("3", ")")), new IllegalStateException()},
        };
    }

    @Test(dataProvider = "negativeParams")
    void negativeTest(List<Token> tokens, Exception expected) {
        Assert.assertThrows(expected.getClass(), () -> tokenVisitor.visitAll(tokens));
    }
}
