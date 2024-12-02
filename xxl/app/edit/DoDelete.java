package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.app.exception.InvalidCellRangeException;
import xxl.app.exception.UnknownFunctionException;
import xxl.core.*;
import xxl.core.exception.UnrecognizedEntryException;

/**
 * Delete command.
 */
class DoDelete extends Command<Spreadsheet> {

  DoDelete(Spreadsheet receiver) {
    super(Label.DELETE, receiver);
    addStringField("address", Message.address());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      Range range = _receiver.createRange(stringField("address"));
      for (int i = range.getBeginRow(); i <= range.getEndRow(); i++) {
        for (int j = range.getBeginColumn(); j <= range.getEndColumn(); j++) {
          _receiver.insertContent(i, j, null);
        }
      }
    } catch (UnrecognizedEntryException | InvalidCellRangeException e) {
      throw new InvalidCellRangeException(stringField("address"));
    }
  }
}
