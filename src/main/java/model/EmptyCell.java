package model;

import expr.Environment;
import expr.ErrorResult;
import expr.ExprResult;

import java.io.IOException;

public class EmptyCell implements Cell {
    @Override
    public String expr() {
        return "";
    }

    @Override
    public ExprResult evaluate(Environment e){
        throw new Error("Missing value");
    }

    @Override
    public String toString(){
        return "";
    }
}
