package model;

import expr.Environment;
import expr.ErrorResult;
import expr.ExprResult;

public class ErrorCell implements Cell{
    private final String error;
    private final String expr;

    public ErrorCell(String expr, String error){
        this.error = error;
        this.expr = expr;
    }

    @Override
    public String expr() {
        return expr;
    }

    @Override
    public ExprResult evaluate(Environment env) {
        return new ErrorResult(error);
    }
}
