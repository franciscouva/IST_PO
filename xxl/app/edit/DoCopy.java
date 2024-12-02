package xxl.app.edit;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.*;
import xxl.core.*;
import xxl.core.exception.*;

/**
 * Copy command.
 */
class DoCopy extends Command<Spreadsheet> {

  DoCopy(Spreadsheet receiver) {
    super(Label.COPY, receiver);
    addStringField("address", Message.address());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.copy(stringField("address"));
    } catch(UnrecognizedEntryException e) {
      e.printStackTrace();
    }
  }
}
