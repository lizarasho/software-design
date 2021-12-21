package visitors;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tokens.Token;

import static utils.TestingUtils.toTokens;

public class CalcVisitorTest {
    private TokenVisitor<Integer> tokenVisitor;

    TokenVisitor<Integer> createVisitor() {
        return new CalcVisitor();
    }

    @BeforeMethod
    void beforeTest() {
        tokenVisitor = createVisitor();
    }

    @DataProvider(name = "positiveParams")
    public Object[][] positiveParams() {
        return new Object[][]{
                {toTokens(List.of("3", "2", "+")), 5},
                {toTokens(List.of("3", "2", "32", "*", "+", "22", "11", "/", "-")), 65},
                {toTokens(List.of("3", "2", "+", "33", "22", "-", "*", "11", "/")), 5},
                {toTokens(List.of("10", "9", "-", "8", "7", "-", "-")), 0},
        };
    }

    @Test(dataProvider = "positiveParams")
    void positiveTest(List<Token> inputTokens, int expected) {
        int actual = tokenVisitor.visitAll(inputTokens);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "negativeParams")
    public Object[][] negativeParams() {
        return new Object[][]{
                {toTokens(List.of("3", "+")), new IllegalStateException()},
                {toTokens(List.of("+", "3", "2")), new IllegalStateException()},
                {toTokens(List.of("3", "2")), new IllegalStateException()},
        };
    }

    @Test(dataProvider = "negativeParams")
    void negativeTest(List<Token> tokens, Exception expected) {
        Assert.assertThrows(expected.getClass(), () -> tokenVisitor.visitAll(tokens));
    }
}
