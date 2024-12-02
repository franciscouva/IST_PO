package xxl.core;

import java.io.*;
import java.util.*;
import java.util.zip.InflaterInputStream;

import xxl.core.exception.*;
import pt.tecnico.uilib.forms.Form;
import xxl.app.exception.FileOpenFailedException;
import xxl.app.exception.InvalidCellRangeException;
import xxl.app.main.Message;

/**
 * Class representing a spreadsheet application.
 */
public class Calculator {
  private static Spreadsheet _currentSpreadsheet;
  private User _activeUser = new User("root");
  private List<User> _users = new ArrayList<User>();
  private String _fileName = "";
  
  /**public Calculator(Spreadsheet currentSpreadsheet) {
    _currentSpreadsheet = currentSpreadsheet;
    _activeUser = new User("root");
    _users.add(_activeUser);
    _fileName = null;
  }**/

  /**
   * Return the current spreadsheet.
   *
   * @returns the current spreadsheet of this application. This reference can be null.
   */
  public final Spreadsheet getSpreadsheet() {
    return _currentSpreadsheet;
  }

  public String getFileName(){
    return _fileName;
  }

  public void setFileName(String fileName){
    _fileName = fileName;
  }

  public boolean changed(){
    return _currentSpreadsheet.getChanged();
  }
  /**
   * Saves the serialized application's state into the file associated to the current network.
   *
   * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
   * @throws MissingFileAssociationException if the current network does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   */
  public void save() throws MissingFileAssociationException, IOException {
    if (_fileName == "" || _fileName == null) {
      throw new MissingFileAssociationException("No current file.");
    }
    
    //if (_currentSpreadsheet.getChanged()) {
      try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_fileName)))) {
        out.writeObject(_currentSpreadsheet);
        _currentSpreadsheet.setChanged(false);
      } catch (IOException e) {
        e.printStackTrace();
      }
    //}
    
  }
  
  /**
   * Saves the serialized application's state into the specified file. The current network is
   * associated to this file.
   *
   * @param filename the name of the file.
   * @throws FileNotFoundException if for some reason the file cannot be created or opened.
   * @throws MissingFileAssociationException if the current network does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   */
  public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
    /*boolean fileDoesNotExist = true;
    Iterator<Spreadsheet> iterator = (_activeUser.getSpreadsheets()).iterator();
    while(iterator.hasNext()) {
      Spreadsheet spreadsheetIterator = iterator.next();
      if ((spreadsheetIterator.getFileName()).equals(filename)) {
        fileDoesNotExist = false;
        break;
      }
    }

    if (_currentSpreadsheet.getChanged() && fileDoesNotExist) {
      _currentSpreadsheet.setFileName(filename);
      if (_currentSpreadsheet == null) {
        throw new MissingFileAssociationException("No current file");
      }

    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
        out.writeObject(_currentSpreadsheet);
        out.close();
      }
    }*/
    _fileName = filename;
    save();
  }
  
  /**
   * @param filename name of the file containing the serialized application's state
   *        to load.
   * @throws UnavailableFileException if the specified file does not exist or there is
   *         an error while processing this file.
   * @throws IOException
   * @throws MissingFileAssociationException
   * @throws FileNotFoundException
   */
  public void load(String filename) throws MissingFileAssociationException, IOException, UnavailableFileException {
    _fileName = filename;
    try 
      (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))){
      
      _currentSpreadsheet = (Spreadsheet)ois.readObject();
      if (_currentSpreadsheet == null) {
        throw new UnavailableFileException(filename);
      }
    } catch (IOException | ClassNotFoundException e) {
      throw new UnavailableFileException(filename);
    }
  }

  
  /**
   * Read text input file and create domain entities.
   *
   * @param filename name of the text input file
   * @throws ImportFileException
   */
  public void importFile(String filename) throws ImportFileException {
    try {
      Parser parser = new Parser(_currentSpreadsheet);
      _currentSpreadsheet = parser.parseFile(filename);
    }
    catch (UnrecognizedEntryException| IOException | InvalidCellRangeException e) {
      throw new ImportFileException(filename, new IOException());
    }
  } 

  public void createNewSpreadsheet(int rows, int columns) throws UnrecognizedEntryException{
    
    _currentSpreadsheet = new Spreadsheet(null, rows, columns);
  }

  public boolean createUser(String name) {
    Iterator<User> iterator = _users.iterator();
    while(iterator.hasNext()) {
      User user = iterator.next();
      if (user.getName() == name) return false;
    }
    User newUser = new User(name);
    _users.add(newUser);
    return true;
  }

  public boolean setActiveUser(User user) {
    Iterator<User> iterator = _users.iterator();
    while(iterator.hasNext()) {
      User userIterator = iterator.next();
      if (userIterator.equals(user)) {
        _activeUser = user;
        return true;
      }
    }
    return false;
  }

  public static Spreadsheet getCurrentSpreadsheet() {
    return _currentSpreadsheet;
  }

  public User getActiveUser() {
    return _activeUser;
  }

  public List<User> getUsers() {
    return _users;
  }
}
