package psd.token;

public class Token {

    private final String type;
    private final String index;

    public Token(String type, String index) {
        this.type = type;
        this.index = index;
    }

    public Token(String type) {
        this(type, "");
    }

    public String getType() {
        return type;
    }

    public String getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return type + index;
    }
}
