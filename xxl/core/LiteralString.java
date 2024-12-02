package xxl.core;

public class LiteralString extends Literal {
    private String _value;

    public LiteralString(String value) {
        _value = value;
    }
    
    @Override
    public String toString() {
        if (_value == null) {
            return null;
        }
        return _value;
    }

    @Override
    public String asString() {
        return _value;
    }

    public int asInt() {
        return Integer.parseInt(_value);
    }
}
