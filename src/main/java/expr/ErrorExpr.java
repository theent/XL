package expr;

/**
 * Represents an illegal expression value.
 */
public class ErrorExpr implements Expr {
  private final String error;

  public ErrorExpr(String error) {
    this.error = error;
  }

  @Override public String toString() {
    return "##ERROR (" + error + ")";
  }

  @Override public ExprResult value(Environment env) {
    return new ErrorResult(error);
  }
}
