package model;

import expr.Environment;
import expr.ErrorResult;
import expr.ExprResult;
import expr.ValueResult;

public class ExprEnviroment implements Environment {
    @Override
    public ExprResult value(String name) {
        return new ValueResult(Integer.parseInt(name));
    }
}
