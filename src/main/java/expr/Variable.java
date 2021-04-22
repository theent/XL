package expr;

class Variable implements Expr {
  private String name;

  Variable(String name) {
    this.name = name;
  }

  @Override public String toString() {
    return name;
  }

  public ExprResult value(Environment env) {
    return env.value(name);
  }
}
