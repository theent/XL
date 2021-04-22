package expr;

abstract class BinaryExpr implements Expr {
  private final Expr expr1;
  private final Expr expr2;

  protected BinaryExpr(Expr expr1, Expr expr2) {
    this.expr1 = expr1;
    this.expr2 = expr2;
  }

  abstract protected ExprResult eval(double op1, double op2);

  abstract protected String opString();

  @Override public String toString() {
    return String.format("(%s %s %s)", expr1, opString(), expr2);
  }

  public ExprResult value(Environment env) {
    ExprResult v1 = expr1.value(env);
    if (v1.isError()) {
      return v1;
    }
    ExprResult v2 = expr2.value(env);
    if (v2.isError()) {
      return v2;
    }
    return eval(v1.value(), v2.value());
  }
}
