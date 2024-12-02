package xxl.core;

public class LiteralInteger extends Literal {
    private int _value;

    @Override
    public String toString() {
        return "\'" + Integer.toString(_value) + "\'";
    }

    @Override
    public String asString() {
        return Integer.toString(_value);
    }

    public int asInt() {
        return _value;
    }

    public LiteralInteger(int value) {
        _value = value;
    }
}
