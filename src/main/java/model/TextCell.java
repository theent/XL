package model;

import expr.Environment;
import expr.ErrorResult;
import expr.ExprResult;

import java.io.IOException;

public class TextCell implements Cell {
    private final String content;
    private final String expr;

    public TextCell(String expr, String content) {
        this.expr = expr;
        this.content = content;
    }

    @Override
    public String expr() {
        return expr;
    }

    @Override
    public ExprResult evaluate(Environment e){
        throw new Error("Missing value");
    }

    @Override
    public String toString() {
        return content;
    }
}
