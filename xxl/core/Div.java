package xxl.core;


public class Div extends BinaryFunction {

    public Div(Content arg0, Content arg1, String functionName, String args) {
        super(arg0, arg1, functionName, args);
    }

    public Literal compute() {
        if (verifyArgs())
            return new LiteralString("#VALUE");
        int result = (getArg1()).asInt() / (getArg2()).asInt();
        return new LiteralInteger(result);
    }
}