package model;

public abstract class CellFactory {
    public Cell createCell(String text){
        if (text.length() == 0){
            return new EmptyCell();
        } else if (text.charAt(0) == '#'){
            return new TextCell(text, text.substring(1));
        } else{
            return new ExprCell(text);
        }
    }
}