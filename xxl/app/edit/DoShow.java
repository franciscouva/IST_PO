package xxl.app.edit;

import java.text.Normalizer.Form;
import java.util.*;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.*;
import pt.tecnico.uilib.Display;
import xxl.core.Spreadsheet;
import xxl.core.exception.UnrecognizedEntryException;
import xxl.core.Cell;
import xxl.core.Range;
import xxl.app.exception.*;

/**
 * Class for searching functions.
 */
class DoShow extends Command<Spreadsheet> {

  DoShow(Spreadsheet receiver) {
    super(Label.SHOW, receiver);
    addStringField("address", Message.address());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try{
      Range range = _receiver.createRange(stringField("address"));
      List<Cell> cells = range.getCells();
      _display.popup(cells);
    } catch (InvalidCellRangeException e){
      throw new InvalidCellRangeException(e.getInvalidRange());
    } catch (UnrecognizedEntryException uee) {
      uee.printStackTrace();
    }
  }
}
