package model;

import expr.*;

import java.io.IOException;

public class ExprCell implements Cell {
     private final String expr;
     private Expr ex;

     public ExprCell(String expr) {
          this.expr = expr;
          try {
               ex = new ExprParser().build(expr);
          } catch (IOException e) {
               ex = new ErrorExpr(e.getMessage());
          }
     }

     @Override
     public String expr() {
          return expr;
     }

     public ExprResult evaluate(Environment env){
          return ex.value(env);
     }
}
