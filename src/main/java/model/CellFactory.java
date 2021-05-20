package model;

public class CellFactory {


    public Cell cellMaker (String cellKind, String text) {
        if (cellKind.equals("T")){
            return new TextCell(text, text.substring(1));
        } else if (cellKind.equals("C")){
            return new CircularCell();
        } else if(cellKind.equals("E")) {
            return new ExprCell(text);
        } else {
            return new EmptyCell();
        }
    }

}
