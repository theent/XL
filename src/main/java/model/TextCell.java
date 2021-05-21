package model;

import expr.Environment;
import expr.ExprResult;

public class TextCell implements Cell {
    private final String expr;

    /**
     * Creates a new Cell containing a Comment
     * @param expr
     */
    public TextCell(String expr) {
        this.expr = expr;
    }

    @Override
    public String inputText() {
        return expr;
    }

    @Override
    public ExprResult evaluateExpr(Environment env){
        throw new Error("Missing value");
    }
}
