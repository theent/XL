package model;

import expr.Environment;
import expr.ErrorResult;
import expr.ExprParser;
import expr.ExprResult;

import java.io.IOException;

public class ExprCell implements Cell {
     private final String expr;
     private ExprParser parser;

     public ExprCell(String expr) {
          this.expr = expr;
          parser = new ExprParser();
     }

     @Override
     public String expr() {
          return expr;
     }

     public ExprResult evaluate(Environment e) throws IOException {
          return parser.build(expr).value(e);
     }
}
