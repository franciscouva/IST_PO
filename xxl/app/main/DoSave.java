package xxl.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import xxl.core.exception.*;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import xxl.core.Calculator;
// FIXME import classes
import xxl.core.Spreadsheet;
import xxl.core.exception.MissingFileAssociationException;

/**
 * Save to file under current name (if unnamed, query for name).
 */
class DoSave extends Command<Calculator> {

  DoSave(Calculator receiver) {
    super(Label.SAVE, receiver, xxl -> xxl.getSpreadsheet() != null);
  }
  
  @Override
  protected final void execute() {
    try {
      _receiver.save();
    } catch (MissingFileAssociationException e) {
      try {
        _receiver.saveAs(Form.requestString(Message.newSaveAs()));
      } catch (MissingFileAssociationException exc) {
        exc.printStackTrace();
      } catch (FileNotFoundException fnfe) {
        fnfe.printStackTrace();
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    } catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
