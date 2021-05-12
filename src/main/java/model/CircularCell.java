package model;

import expr.Environment;
import expr.ExprResult;

public class CircularCell implements Cell{

    @Override
    public String expr() {
        return "";
    }

    @Override
    public ExprResult evaluate(Environment e){
        throw new Error("Circular Error");
    }

    @Override
    public String toString(){
        return "";
    }
}
