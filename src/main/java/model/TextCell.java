package model;

import expr.Environment;

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
    public Double value(Environment e) throws IOException {
        throw new Error("Missing value");
    }

    @Override
    public String toString() {
        return content;
    }
}
