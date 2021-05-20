package model;

import expr.Environment;
import expr.ExprResult;
interface Cell {

    /**
     * Returns the Expresion in text form
     * @return
     */
    String inputText();

    /**
     * Evaluates the value of a expression and returns it as a ExprResult
     * Ex: Input 2 + 2 returns 4
     * All but ExprCell throws Errors
     * @param env
     * @return
     */
    ExprResult evaluateExpr(Environment env);

}
