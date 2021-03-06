package model;

public abstract class CellFactory {
    public Cell createCell(String text){
        if (text.length() == 0){
            return new EmptyCell();
        } else if (text.charAt(0) == '#'){
            return new TextCell(text);
        } else if (text.equals("Circular")){
            return new CircularCell();
        } else{
            return new ExprCell(text);
        }
    }
}