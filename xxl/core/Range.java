package xxl.core;

import java.util.*;

import xxl.core.exception.UnrecognizedEntryException;

public class Range {
    private int _beginRow;
    private int _beginColumn;
    private int _endRow;
    private int _endColumn;
    private Spreadsheet _spreadsheet;

    public Range(int beginRow, int beginColumn, int endRow, int endColumn, Spreadsheet spreadsheet) throws UnrecognizedEntryException {
        _beginRow = beginRow;
        _beginColumn = beginColumn;
        _endRow = endRow;
        _endColumn = endColumn;
        _spreadsheet = spreadsheet;
    }

    public int getBeginRow() {
        return _beginRow;
    }

    public int getEndRow() {
        return _endRow;
    }

    public int getBeginColumn() {
        return _beginColumn;
    }

    public int getEndColumn() {
        return _endColumn;
    }

    public Spreadsheet getSpreadsheet() {
        return _spreadsheet;
    }

    public List<Cell> getCells() {
        List<Cell> cellsSpreadsheet = new ArrayList<Cell>();
        cellsSpreadsheet = _spreadsheet.getCells();
        List<Cell> cells = new ArrayList<Cell>();

        Iterator<Cell> iterator = cellsSpreadsheet.iterator();
        while(iterator.hasNext()) {
            Cell cell = iterator.next();
            if (_beginRow <= cell.getRow() && cell.getRow() <= _endRow && 
            _beginColumn <= cell.getColumn() && cell.getColumn() <= _endColumn) {
                cells.add(cell);
            }
        }
        return cells;
    }
}
