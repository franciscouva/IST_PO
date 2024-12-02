package xxl.core;


import java.util.*;


public class Average extends IntervalFunction{

    public Average(Range arg, String functionName, String args) {
        super(arg, functionName, args);
    }
    
    public Literal compute() {
        int result = 0;
        int count = 0;
        List<Cell> cells = new ArrayList<>();
        cells = getRange().getCells();
        
        Iterator<Cell> iterator = cells.iterator();
        while(iterator.hasNext()) {
            Cell cell = iterator.next();
            if (cell.getContent() == null) {
                continue;
            }
            result += cell.value().asInt();
            count++;
        }
        
        return new LiteralInteger(result/count);
    }
}
