package model;

import expr.Environment;
import expr.ExprResult;

import java.io.IOException;

public interface Cell {

    /**
     * Return the "Expression text (what you write in the window)
     * @return
     */
    String expr();

    /**
     * Returns the result from the expression
     * Ex: Input 2 + 2 gets the result 4
     * throws errors in all but ExprCell
     * @param e
     * @return
     */
    ExprResult evaluate(Environment e);

    /**
     * Mainly used ny TextCell to return the comment in pure text
     * @return
     */
    String toString();
}
