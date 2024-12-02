package xxl.core;

import java.util.*;

public abstract class BinaryFunction extends Function {
    private Content _arg1;
    private Content _arg2;

    public BinaryFunction(Content arg1, Content arg2, String name, String args) {
        super(name, args);
        _arg1 = arg1;
        _arg2 = arg2;
    }

    public List<Content> getContent() {
        final List<Content> result = new ArrayList<Content>();
        result.add(_arg1);
        result.add(_arg2);
        return result;
    }

    public Content getArg1() {
        return _arg1;
    }

    public Content getArg2() {
        return _arg2;
    }

    public boolean verifyArgs() {
        return (_arg1.value() instanceof LiteralString || _arg1.value() instanceof LiteralString);
    }

    @Override
    public String toString() {
      return "Célula com uma referência ou função - " /*+ Integer.toString(_row) + 
      ";" + Integer.toString(_column) + "|" + this.value().asString() + "=" + this.asString()*/;
    }
}
