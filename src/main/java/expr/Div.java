package expr;

class Div extends BinaryExpr {
  Div(Expr expr1, Expr expr2) {
    super(expr1, expr2);
  }

  @Override public ExprResult eval(double op1, double op2) {
    if (op2 != 0) {
      return new ValueResult(op1 / op2);
    } else {
      return new ErrorResult("division by zero");
    }
  }

  @Override protected String opString() {
    return "/";
  }
}
