package xxl.core;

import java.io.Serializable;

public abstract class Content implements Serializable {

    // Método abstrato para retornar uma representação em string
    public abstract String toString();

    // Método abstrato para obter o valor literal
    protected abstract Literal value();

    // Método para converter o valor em uma String
    public abstract String asString();

    // Método para converter o valor em um inteiro
    public int asInt() {
        Literal literal = value();
        if (literal instanceof LiteralInteger) {
            return literal.asInt();
        } else {
            throw new IllegalStateException("Cannot convert to int: unsupported literal type");
        }
    }
}
