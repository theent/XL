package model;

import expr.Environment;

public interface Cell {

    String expr();

    Double value();

    void evaluate(Environment e);

    String toString();
}
