package expr;

class Sub extends BinaryExpr {
  Sub(Expr expr1, Expr expr2) {
    super(expr1, expr2);
  }

  @Override public ExprResult eval(double op1, double op2) {
    return new ValueResult(op1 - op2);
  }

  @Override protected String opString() {
    return "-";
  }
}
