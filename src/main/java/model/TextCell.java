package model;

import expr.Environment;

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
    public Double value() {
        throw new Error("Missing value");
    }

    @Override
    public void evaluate(Environment e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return content;
    }
}
