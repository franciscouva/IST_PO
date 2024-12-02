package xxl.core;

public abstract class IntervalFunction extends Function {
    private Range _range;

    public IntervalFunction(Range arg, String name, String args) {
        super(name, args);
        _range = arg;
    }

    public Range getRange() {
        return _range;
    }

    @Override
    public String toString() {
        return "Fixme";
    }
}
