package xxl.core;


public class Mul extends BinaryFunction {

    public Mul(Content arg0, Content arg1, String functionName, String args) {
        super(arg0, arg1, functionName, args);
    }

    public Literal compute() {
        if (verifyArgs())
            return new LiteralString("#VALUE");
        return new LiteralInteger(getArg1().asInt() * getArg2().asInt());
    }
}