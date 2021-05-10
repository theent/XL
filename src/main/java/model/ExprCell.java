package model;

import expr.Environment;
import expr.ErrorResult;
import expr.ExprParser;
import expr.ExprResult;

import java.io.IOException;

public class ExprCell implements Cell {
     private double value;
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

     @Override
     public Double value() {
          return value;
     }

     @Override
     public void evaluate(Environment e) {
          if (expr.length() == 0){
               throw new EmptyError(expr);
          }
          try{
               ExprResult res = parser.build(expr).value(e);
               if (res.isError()){
                    if (res.error().matches("Circular Error")){
                         throw new CircularError(res.toString());
                    } else{
                         throw new Error(res.toString());
                    }
               } else{
                    this.value = res.value();
               }
          } catch (IOException ex){
               throw new Error(new ErrorResult(ex.getMessage()).toString());
          }
     }

     @Override
     public String toString() {
          return Double.toString(value);
     }
}
