package model;

import expr.*;
import java.io.IOException;

public class ExprCell implements Cell {
     private final String expr;
     private Expr ex;

     /**
      * Creates a Cell that contains a expression
      * @param expr
      */
     public ExprCell(String expr) {
          this.expr = expr;

          try {
               ex = new ExprParser().build(expr);
          } catch (IOException e) {
               ex = new ErrorExpr(e.getMessage());
          }
     }

     @Override
     public String inputText() {
          return expr;
     }

     public ExprResult evaluateExpr(Environment env){
          return ex.value(env);
     }

}
