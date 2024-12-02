package xxl.core;

import java.io.Serializable;

public class Cell implements Serializable {
    private int _row;
    private int _column;
    private Content _content;
    private Literal _contentValue;

    public Cell (int row, int column) {
        _row = row;
        _column = column;
        _content = null;
    }

    public int getRow() {
        return _row;
    }

    public int getColumn() {
        return _column;
    }

    @Override
    public String toString() {
        if (_content == null) {
            return Integer.toString(_row) + ";" + Integer.toString(_column) + "|";
        }
        if (_content instanceof Literal) {
            return Integer.toString(_row) + ";" + Integer.toString(_column) + "|" + _content.value().asString();
        }
        if (_content instanceof Reference || _content instanceof Function) {
            return Integer.toString(_row) + ";" +  Integer.toString(_column) + "|" + _content.value().asString() + _content.asString();
        }
        return null;
    }

    public void setContent(Content c) {
        _content = c;
        if (c == null) {
            _contentValue = null;
        } else {
            _contentValue = _content.value();
        }
    }

    public Content getContent() {
        return _content;
    }

    public Literal value() {
        return _contentValue;
    }
}
