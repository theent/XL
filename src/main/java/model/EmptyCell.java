package model;

import expr.Environment;
import expr.ExprResult;

public class EmptyCell implements Cell {

    @Override
    public String expr() {
        return "";
    }

    @Override
    public ExprResult evaluate(Environment env){
        throw new Error("Missing value");
    }

    @Override
    public String toString(){
        return "";
    }
}
