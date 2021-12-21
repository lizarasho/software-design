package tokens;

import java.util.Objects;

public abstract class BaseToken implements Token {
    private final String string;

    public BaseToken(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseToken baseToken = (BaseToken) o;
        return Objects.equals(string, baseToken.string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string);
    }
}
