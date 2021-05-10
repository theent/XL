package model;

import expr.Environment;

public class CircularCell implements Cell{

    private String content;
    private String expr;

    public CircularCell(){
        this.content = null;
        this.expr = null;
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
    public Double value() {
        throw new Error("Circular Error");
    }

    @Override
    public void evaluate(Environment e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString(){
        return content;
    }
}
