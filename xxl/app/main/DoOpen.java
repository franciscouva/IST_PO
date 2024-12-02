package xxl.app.main;

import java.io.IOException;

import pt.tecnico.uilib.forms.*;
import pt.tecnico.uilib.menus.*;
import xxl.core.Calculator;
import xxl.core.exception.*;
import xxl.app.main.*;


/**
 * Open existing file.
 */
class DoOpen extends Command<Calculator> {

  DoOpen(Calculator receiver) {
    super(Label.OPEN, receiver);
    addStringField("file", Message.openFile());
  }
  
@Override
protected final void execute() throws CommandException {
    try {
        String filename = stringField("file");
        _receiver.load(filename);
        _receiver.setFileName(filename);        
    } catch (MissingFileAssociationException | IOException | UnavailableFileException e) {
      e.printStackTrace();
    }
  }
}
