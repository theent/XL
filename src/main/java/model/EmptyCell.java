package model;

import expr.Environment;

import java.io.IOException;

public class EmptyCell implements Cell {
    @Override
    public String expr() {
        return "";
    }

    @Override
    public Double value(Environment e) throws IOException {
        throw new EmptyError("Missing value");
    }
}
