package model;

import expr.Environment;

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
    public Double value() {
        throw new CircularError("Circular Error");
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
