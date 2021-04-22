package expr;

public class ErrorResult implements ExprResult {
  private final String error;

  public ErrorResult(String error) {
    this.error = error;
  }

  @Override public boolean isError() {
    return true;
  }

  @Override public double value() {
    throw new Error("Missing value");
  }

  @Override public String error() {
    return error;
  }

  @Override public String toString() {
    return "##ERROR (" + error + ")";
  }
}
