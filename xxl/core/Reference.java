package xxl.core;

import java.util.*;

public class Reference extends Content{
    private int _row;
    private int _column;
    private Spreadsheet _spreadsheet;
    
    public Reference(int row, int column, Spreadsheet spreadsheet){
      _row = row;
      _column = column;
      _spreadsheet = spreadsheet;
    }


    public Literal value() {
        List<Cell> cells = new ArrayList<Cell>();
        cells = _spreadsheet.getCells();
        if (! referenceCheck()) {
          return new LiteralString("#VALUE");
        }
        Iterator<Cell> iterator = cells.iterator();
        while(iterator.hasNext()) {
          Cell cell = iterator.next();
          if (cell.getRow() == _row && cell.getColumn() == _column) {
            if (cell.getContent() == null) {
              return new LiteralString("#VALUE");
            }
            return cell.value();
          }
        }
        return new LiteralString("#VALUE");
    }

    @Override
    public String asString() {
        return "=" + Integer.toString(_row) + ";" + Integer.toString(_column);
    }

    public boolean referenceCheck() {
      int spreadsheetCol, spreadsheetRow;
      spreadsheetCol = _spreadsheet.getColumns();
      spreadsheetRow = _spreadsheet.getRows();
      return (spreadsheetCol >= _column && spreadsheetRow >= _row);
    }



    @Override
    public String toString() {
      return "Célula com uma referência ou função - "/* + Integer.toString(Cell.getRow()) + 
      ";" + Integer.toString(Cell.getColumn()) + "|" + this.value().asString() + "=" + this.asString()*/;
    }
}
