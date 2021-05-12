package model;

import expr.Environment;
import expr.ErrorResult;
import expr.ExprResult;

import java.io.IOException;

public class CircularCell implements Cell{

    private String expr;
    private String content;

    public CircularCell(){
        this.expr = null;
        this.content = null;
    }

    public CircularCell(String expr, String content){
        this.expr = expr;
        this.content = content;
    }

    @Override
    public String expr() {
        return expr;
    }

    @Override
    public ExprResult evaluate(Environment e) throws IOException {
        throw new IOException("Circular Error");
    }

    @Override
    public String toString(){
        return content;
    }
}
