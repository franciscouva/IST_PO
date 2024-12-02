package xxl.core;

import java.util.*;

public class Concat extends IntervalFunction{
    
    public Concat(Range arg, String functionName, String args) {
        super(arg, functionName, args);
    }

    public Literal compute() {
        String result = "";

        List<Cell> cells = new ArrayList<>();
        cells = getRange().getCells();
        
        Iterator<Cell> iterator = cells.iterator();
        while(iterator.hasNext()) {
            Cell cell = iterator.next();
            if (cell.getContent() == null) {
                continue;
            }
            Literal cellValue = cell.getContent().value();
            if (cellValue instanceof LiteralString) {
                result += cellValue.asString();
            }
        }
        
        return new LiteralString(result);
    }
}
