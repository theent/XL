package model;

import expr.Environment;
import expr.ExprResult;

import java.io.IOException;

interface Cell {

    /**
     * Returns the Expresion in text form
     * @return
     */
    String expr();

    /**
     * Evaluates the value of a expression and returns it as a ExprResult
     * Ex: Input 2 + 2 returns 4
     * All but ExprCell returns Errors
     * @param env
     * @return
     */
    ExprResult evaluate(Environment env);

    /**
     * Mainly used by TextCell to return the comment in "pure" text
     * @return
     */
    String toString();
}
