package model;

import expr.Environment;

public class CircularCell implements Cell{

    @Override
    public String expr() {
        return "";
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
        return "";
    }
}
