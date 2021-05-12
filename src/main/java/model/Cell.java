package model;

import expr.Environment;

import java.io.IOException;

public interface Cell {

    String expr();

    Double value(Environment e) throws IOException;

    String toString();
}
