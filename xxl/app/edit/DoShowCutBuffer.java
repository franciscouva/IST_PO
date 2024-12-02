package xxl.app.edit;

import java.util.List;

import pt.tecnico.uilib.menus.Command;
import xxl.core.Cell;
import xxl.core.CutBuffer;
import xxl.core.Spreadsheet;
import xxl.core.exception.UnrecognizedEntryException;

/**
 * Show cut buffer command.
 */
class DoShowCutBuffer extends Command<Spreadsheet> {

  DoShowCutBuffer(Spreadsheet receiver) {
    super(Label.SHOW_CUT_BUFFER, receiver);
  }
  
  @Override
  protected final void execute() {
      CutBuffer cutBuffer = _receiver.getCutBuffer();
      List<Cell> cells = cutBuffer.getCells();
      _display.popup(cells);
  }
}
