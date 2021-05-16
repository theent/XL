package model;

import expr.Environment;
import expr.ExprResult;

import java.io.IOException;

public interface Cell {

    String expr();

    ExprResult evaluate(Environment env);

    String toString();
}
