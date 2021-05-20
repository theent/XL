package model;

import expr.Environment;
import expr.ExprResult;

public class TextCell implements Cell {
    private final String content;
    private final String expr;

    /**
     * Creates a new Cell containing a Comment
     * @param expr
     * @param content
     */
    public TextCell(String expr, String content) {
        this.expr = expr;
        this.content = content;
    }

    @Override
    public String inputText() {
        return expr;
    }

    @Override
    public ExprResult evaluateExpr(Environment env){
        throw new Error("Missing value");
    }

    @Override
    public String toString() {
        return content;
    }
}
