package expr;

class Num implements Expr {
  private final double value;

  Num(double value) {
    this.value = value;
  }

  @Override public String toString() {
    return "" + value;
  }

  @Override public ExprResult value(Environment env) {
    return new ValueResult(value);
  }
}
