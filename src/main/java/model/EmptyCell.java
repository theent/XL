package model;

import expr.Environment;
import expr.ExprResult;

public class EmptyCell implements Cell {

    @Override
    public String inputText() {
        return "";
    }

    @Override
    public ExprResult evaluateExpr(Environment env){
        throw new Error("Missing value");
    }

}
