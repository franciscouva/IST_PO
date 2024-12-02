package xxl.app.main;

import java.io.IOException;

import pt.tecnico.uilib.forms.*;
import pt.tecnico.uilib.menus.*;
import xxl.core.Calculator;
import xxl.core.exception.MissingFileAssociationException;
import xxl.core.exception.UnrecognizedEntryException;
import xxl.app.main.Message;

/**
 * Open a new file.
 */
class DoNew extends Command<Calculator> {

  DoNew(Calculator receiver) {
    super(Label.NEW, receiver);
  }
  
@Override
protected final void execute() throws CommandException {

  try {
    if(_receiver.getCurrentSpreadsheet() != null){
        if(Form.confirm(Message.saveBeforeExit())){
          if(_receiver.getFileName() == null){
            _receiver.save();
          } else 
            _receiver.saveAs(Form.requestString(Message.newSaveAs()));
        }
    }
  } catch(IOException | MissingFileAssociationException e){
      e.printStackTrace();
  }

  try{
  _receiver.createNewSpreadsheet(Form.requestInteger(Message.lines()),Form.requestInteger(Message.columns()));
  }  
  catch(UnrecognizedEntryException e){}
}
}
