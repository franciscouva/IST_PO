package xxl.core;


import java.util.*;

import xxl.app.main.*;

public class Product extends IntervalFunction{

    public Product(Range arg, String functionName, String args) {
        super(arg, functionName, args);
    }
    
    public Literal compute() {
        int result = 1;
        List<Cell> cells = new ArrayList<>();
        cells = getRange().getCells();
        
        Iterator<Cell> iterator = cells.iterator();
        while(iterator.hasNext()) {
            Cell cell = iterator.next();
            if (cell.getContent() == null) {
                continue;
            }
            result *= cell.getContent().value().asInt();
        }
        
        return new LiteralInteger(result);
    }
}
