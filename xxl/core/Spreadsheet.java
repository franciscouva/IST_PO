package xxl.core;

import java.io.*;
import java.util.*;

import xxl.app.exception.InvalidCellRangeException;
import xxl.core.exception.UnrecognizedEntryException;

/**
 * Class representing a spreadsheet.
 */
public class Spreadsheet implements Serializable {
  @Serial
  private static final long serialVersionUID = 202308312359L;
  private String _fileName;
  private int _rows;
  private int _columns;
  private static boolean _changed;
  private List<User> _userList;
  private static List<Cell> _cells;
  private CutBuffer _cutBuffer;

  public Spreadsheet(String fileName, int rows, int columns) {
    _fileName = fileName;
    _rows = rows;
    _columns = columns;
    _changed = false;
    _userList = new ArrayList<User>();
    _userList.add(new User("root"));
    _cells = new ArrayList<Cell>();
    for (int i = 1; i <= rows; i++) {
      for (int j = 1; j <=columns; j++) {
        Cell newCell = new Cell(i, j);
        _cells.add(newCell);
      }
    }
  }

  public void setFileName(String fileName) {
    _fileName = fileName;
  }

  public String getFileName() {
    return _fileName;
  }

  public List<Cell> getCells() {
    return _cells;
  }

  public int getColumns() {
    return _columns;
  }

  public boolean getChanged() {
    return _changed;
  }

  public int getRows() {
    return _rows;
  }
  
  public List<User> getUserList() {
    return _userList;
  }

  public void addUser(User utilizador) {
    _userList.add(utilizador);
  }
  
  public CutBuffer getCutBuffer() {
    return _cutBuffer;
  } 

  public void setChanged(boolean state) {
    _changed = state;
  }

  public Cell getCell(int row, int column) {
    Iterator<Cell> iterator = _cells.iterator();
    while(iterator.hasNext()) {
      Cell cell = iterator.next();
      if (cell.getRow() == row && cell.getColumn() == column) {
        return cell;
      }
    }
    return null;
  }  


  /**
   * Insert specified content in specified address.
   *
   * @param row the row of the cell to change 
   * @param column the column of the cell to change
   * @param contentSpecification the specification in a string format of the content to put
   *        in the specified cell.
   */
  public void insertContent(int row, int column, Content content) throws UnrecognizedEntryException {
    Iterator<Cell> iterator = _cells.iterator();
    while(iterator.hasNext()) {
      Cell cell = iterator.next();
      if (cell.getRow() == row && cell.getColumn() == column) {
        cell.setContent(content);
        break;
      }
    }
  }

  public void copy(String range) throws UnrecognizedEntryException, InvalidCellRangeException{
    try {
      if (_cutBuffer == null) {
        _cutBuffer = new CutBuffer();
      }
      
      Range rangeParsed = createRange(range);
      _cutBuffer.setHorizontal(rangeParsed.getBeginRow() == rangeParsed.getEndRow());
      _cutBuffer.setCells(rangeParsed.getCells());
    }
    catch (UnrecognizedEntryException e){
      e.printStackTrace();
    }
  }

  public void cut(String range) throws UnrecognizedEntryException, InvalidCellRangeException {
    try {
      copy(range);
      List<Cell> cells = new ArrayList<>();
      cells = _cutBuffer.getCells();

      Iterator<Cell> iterator = cells.iterator();
      while(iterator.hasNext()) {
        Cell cell = iterator.next();
        cell.setContent(null);
      }
    } catch (UnrecognizedEntryException | InvalidCellRangeException e) {
      throw new InvalidCellRangeException(range);
    }
  }

  public void paste(String range) throws UnrecognizedEntryException, InvalidCellRangeException {
    try {
      if (_cutBuffer.getCells() == null) {
        return;
      }
      Range rangeParsed = createRange(range);
      if (rangeParsed.getBeginColumn() == rangeParsed.getEndColumn() && rangeParsed.getBeginRow() == rangeParsed.getEndRow()) {
        List<Cell> cells = new ArrayList<>();
        int i = 0;
        Iterator<Cell> iterator = cells.iterator();
        if (_cutBuffer.isHorizontal() && rangeParsed.getBeginColumn()+i <= _columns) {
          while(iterator.hasNext()) {
            Cell cell = iterator.next();
            this.insertContent(rangeParsed.getBeginRow(), rangeParsed.getBeginColumn()+i, cell.getContent());
            i++;
          }
        } else {
          while(iterator.hasNext() && rangeParsed.getBeginRow()+i <= _rows) {
            Cell cell = iterator.next();
            this.insertContent(rangeParsed.getBeginRow()+i, rangeParsed.getBeginColumn(), cell.getContent());
            i++;
          }
        }
      } else if (_cutBuffer.getCells().size() != rangeParsed.getCells().size()) {
        return;
      }
    } catch (UnrecognizedEntryException | InvalidCellRangeException e) {
      throw new InvalidCellRangeException(range);
    }
  }

  public Range createRange(String range) throws UnrecognizedEntryException, InvalidCellRangeException {
    try{
      Parser parser = new Parser(this);
      Range rangeParsed = parser.createRange(range);
      return rangeParsed;
    } catch(UnrecognizedEntryException | InvalidCellRangeException e) {
      throw new InvalidCellRangeException(range);
    }
    
    
  }
}