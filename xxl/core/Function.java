package xxl.core;


public abstract class Function extends Content{
    protected String _name;
    protected String _args;

    public Function(String name, String args) {
        _name = name;
        _args = args;
    }

    protected abstract Literal compute();

    @Override
    public Literal value() {
        return compute();
    }

    public String getName() {
        return _name;
    }

    @Override
    public String asString() {
        return "=" + _name + "(" + _args + ")";
    }

    @Override
    public int asInt() {
        return value().asInt();
    }

    
}
