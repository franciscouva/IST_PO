package xxl.core;

import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
    private String _name;
    protected List<Spreadsheet> _spreadsheets;

    public User(String name) {
        _name = name;
        _spreadsheets = new ArrayList<Spreadsheet>();
    }
    
    public String getName() {
        return _name;
    }

    public List<Spreadsheet> getSpreadsheets() {
        return _spreadsheets;
    }

    public boolean equals(Object obj) {
        return _name.equals(obj);
    }

    public void add(Spreadsheet spreadsheet) {
        _spreadsheets.add(spreadsheet);
    }
    
    
}