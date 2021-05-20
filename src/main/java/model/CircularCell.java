package model;

import expr.Environment;
import expr.ExprResult;

/**
 * Cell used to prevent StackOverflow from circular errors
 */
public class CircularCell implements Cell{

    @Override
    public String inputText() {
        return "";
    }

    @Override
    public ExprResult evaluateExpr(Environment e){
        throw new Error("Circular Error");
    }
}
