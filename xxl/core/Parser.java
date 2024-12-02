package xxl.core;


import java.io.*;

import xxl.app.exception.InvalidCellRangeException;
import xxl.core.exception.UnrecognizedEntryException;

public class Parser {

    private Spreadsheet _spreadsheet;

    public Parser() {
    }

    public Parser(Spreadsheet spreadsheet) {
        _spreadsheet = spreadsheet;
    }

    public Spreadsheet parseFile(String filename) throws IOException, UnrecognizedEntryException, InvalidCellRangeException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            parseDimensions(reader);

            String line;

            while ((line = reader.readLine()) != null)
                parseLine(line);
            
        }

        return _spreadsheet;
    }

    private void parseDimensions(BufferedReader reader) throws IOException, UnrecognizedEntryException {
        int rows = -1;
        int columns = -1;
        int i;

        for (i = 0; i < 2; i++) {
            String[] dimension = reader.readLine().split("=");
            if (dimension[0].equals("linhas"))
                rows = Integer.parseInt(dimension[1]);
            else if (dimension[0].equals("colunas"))
                columns = Integer.parseInt(dimension[1]);
            else
                throw new UnrecognizedEntryException("Unrecognized entry: " + dimension[0]);
        }

        if (rows <= 0 || columns <= 0)
            throw new UnrecognizedEntryException("Invalid dimensions for the spreadsheet");

        _spreadsheet = new Spreadsheet(null, rows, columns);
    }

    private void parseLine(String line) throws UnrecognizedEntryException, InvalidCellRangeException {
        String[] components = line.split("\\|");

        if (components.length == 1) // do nothing
            return;

        if (components.length == 2) {
            String[] address = components[0].split(";");
            Content content = parseContent(components[1]);
            _spreadsheet.insertContent(Integer.parseInt(address[0]), Integer.parseInt(address[1]), content);
        } else
            throw new UnrecognizedEntryException("Wrong format in line: " + line);
    }

    // parse the beginning of an expression
    public Content parseContent(String contentSpecification) throws UnrecognizedEntryException, InvalidCellRangeException {
        char c = contentSpecification.charAt(0);

        if (c == '=')
            return parseContentExpression(contentSpecification.substring(1));
        else
            return parseLiteral(contentSpecification);
    }

    private Literal parseLiteral(String literalExpression) throws UnrecognizedEntryException {
        if (literalExpression.charAt(0) == '\'')
            
            return new LiteralString(literalExpression);
        else {
            try {
                int val = Integer.parseInt(literalExpression);
                return new LiteralInteger(val);
            } catch (NumberFormatException nfe) {
                throw new UnrecognizedEntryException("Invalid number: " + literalExpression);
            }
        }
    }

    // contentSpecification is what comes after '='
    private Content parseContentExpression(String contentSpecification) throws UnrecognizedEntryException{
        try{
            if (contentSpecification.contains("("))
                return parseFunction(contentSpecification);
            // It is a reference
            String[] address = contentSpecification.split(";");
            return new Reference(Integer.parseInt(address[0].trim()), Integer.parseInt(address[1]), _spreadsheet);
        } catch (InvalidCellRangeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Content parseFunction(String functionSpecification) throws UnrecognizedEntryException, InvalidCellRangeException {
        String[] components = functionSpecification.split("[()]");
        if (components[1].contains(","))
            return parseBinaryFunction(components[0], components[1]);

        return parseIntervalFunction(components[0], components[1]);
    }

    private Content parseBinaryFunction(String functionName, String args) throws UnrecognizedEntryException {
        String[] arguments = args.split(",");
        Content arg0 = parseArgumentExpression(arguments[0]);
        Content arg1 = parseArgumentExpression(arguments[1]);

        switch (functionName) {
            case "ADD":
                return new Add(arg0, arg1, functionName, args);
            case "SUB":
                return new Sub(arg0, arg1, functionName, args);
            case "MUL":
                return new Mul(arg0, arg1, functionName, args);
            case "DIV":
                return new Div(arg0, arg1, functionName, args);
            default:
                throw new UnrecognizedEntryException("Invalid function: " + functionName);
        }
    }

    private Content parseArgumentExpression(String argExpression) throws UnrecognizedEntryException {
        if (argExpression.contains(";") && argExpression.charAt(0) != '\'') {
            String[] address = argExpression.split(";");
            return new Reference(Integer.parseInt(address[0].trim()), Integer.parseInt(address[1]), _spreadsheet);
        } else
            return parseLiteral(argExpression);
    }

    private Content parseIntervalFunction(String functionName, String rangeDescription) throws UnrecognizedEntryException, InvalidCellRangeException {
        Range range = _spreadsheet.createRange(rangeDescription);
        switch (functionName) {
            case "CONCAT":
                return new Concat(range, functionName, rangeDescription);
            case "COALESCE":
                return new Coalesce(range, functionName, rangeDescription);
            case "PRODUCT":
                return new Product(range, functionName, rangeDescription);
            case "AVERAGE":
                return new Average(range, functionName, rangeDescription);
            default:
                throw new UnrecognizedEntryException("Invalid function: " + functionName);
        }
     }

    //In the Spreadsheet class, you need to implement the createRange method.
    public Range createRange(String range) throws UnrecognizedEntryException, InvalidCellRangeException {
        String[] rangeCoordinates;
        int firstRow, firstColumn, lastRow, lastColumn;

        if (range.indexOf(':') != -1) {
            rangeCoordinates = range.split("[:;]");
            firstRow = Integer.parseInt(rangeCoordinates[0]);
            firstColumn = Integer.parseInt(rangeCoordinates[1]);
            lastRow = Integer.parseInt(rangeCoordinates[2]);
            lastColumn = Integer.parseInt(rangeCoordinates[3]);
        } else {
            rangeCoordinates = range.split(";");
            firstRow = lastRow = Integer.parseInt(rangeCoordinates[0]);
            firstColumn = lastColumn = Integer.parseInt(rangeCoordinates[1]);
        }

        
        if (firstRow == lastRow || firstColumn == lastColumn) {
            if (firstRow <= lastRow && firstRow > 0 && lastRow <= _spreadsheet.getRows() &&
                firstColumn <= lastColumn && firstColumn > 0 && lastColumn <= _spreadsheet.getColumns()) {
                return new Range(firstRow, firstColumn, lastRow, lastColumn, _spreadsheet);
            }
        }
        throw new InvalidCellRangeException(range);
    }
}
