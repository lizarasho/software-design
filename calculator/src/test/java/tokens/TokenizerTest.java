package tokens;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static utils.TestingUtils.toTokens;

public class TokenizerTest {
    @DataProvider(name = "positiveParams")
    public Object[][] positiveParams() {
        return new Object[][]{
                {
                        "",
                        List.of()
                },
                {
                        "123 - 26 * 4 + 1",
                        toTokens(List.of("123", "-", "26", "*", "4", "+", "1"))
                },
                {
                        "123 - 26 * (4 + 1)",
                        toTokens(List.of("123", "-", "26", "*", "(", "4", "+", "1", ")"))
                },
                {
                        "123 + (26     / 4 - 1)  ",
                        toTokens(List.of("123", "+", "(", "26", "/", "4", "-", "1", ")"))
                },
                {
                        "(     123 + 26) / (4 -     1)",
                        toTokens(List.of("(", "123", "+", "26", ")", "/", "(", "4", "-", "1", ")"))
                }
        };
    }

    @Test(dataProvider = "positiveParams")
    void positiveTest(String inputText, List<Token> expectedTokens) {
        List<Token> actualTokens = getStateTokenizer(inputText).toList();
        Assert.assertEquals(actualTokens, expectedTokens);
    }

    @Test
    void negativeTest() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            getStateTokenizer("1 - x").toList();
        });
    }

    private StateTokenizer getStateTokenizer(String inputText) {
        InputStream inputStream = new ByteArrayInputStream(inputText.getBytes());
        return new StateTokenizer(inputStream);
    }
}