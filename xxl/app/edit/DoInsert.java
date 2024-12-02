package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.core.*;
import xxl.core.exception.UnrecognizedEntryException;
import xxl.app.exception.*;

/**
 * Class for inserting data.
 */
class DoInsert extends Command<Spreadsheet> {

  DoInsert(Spreadsheet receiver) {
    super(Label.INSERT, receiver);
    addStringField("address", Message.address());
    addStringField("content", Message.contents());
  }
  

  @Override
  protected final void execute() throws CommandException, UnknownFunctionException {
    try {
      Range range = _receiver.createRange(stringField("address"));
      Parser parser = new Parser(_receiver);
      for (int i = range.getBeginRow(); i <= range.getEndRow(); i++) {
        for (int j = range.getBeginColumn(); j <= range.getEndColumn(); j++) {
          _receiver.insertContent(i, j, parser.parseContent(stringField("content")));
        }
      }
    } catch (UnrecognizedEntryException e) {
      if (e.getMessage().startsWith("Invalid function: ")) {
        throw new UnknownFunctionException(e.getMessage());
      }
    } catch (InvalidCellRangeException e) {
      throw new InvalidCellRangeException(stringField("address"));
    }
  }
}
