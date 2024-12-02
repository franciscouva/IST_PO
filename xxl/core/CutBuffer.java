package xxl.core;

import java.io.Serializable;
import java.util.List;

public class CutBuffer implements Serializable {
    private List<Cell> _cells;
    private boolean _horizontal;

    public List<Cell> getCells() {
        return _cells;
    }

    public boolean isHorizontal() {
        return _horizontal;
    }

    public void setHorizontal(boolean state) {
        _horizontal = state;
    }

    public void setCells(List<Cell> cells) {
        _cells = cells;
    }
}
