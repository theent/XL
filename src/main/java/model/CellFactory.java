package model;

public abstract class CellFactory {
    public Cell createCell(String text){
        if (text.length() == 0){
            EmptyCell eC = new EmptyCell();
            return new EmptyCell();
        } else if (text.charAt(0) == '#'){
            TextCell tC = new TextCell(text, text.substring(1));
            return new TextCell(text, text.substring(1));
        } else{
            return new ExprCell(text);
        }
    }
}