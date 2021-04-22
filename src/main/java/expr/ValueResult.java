package expr;

public class ValueResult implements ExprResult {
  private final double value;

  public ValueResult(double value) {
    this.value = value;
  }

  @Override public boolean isError() {
    return false;
  }

  @Override public double value() {
    return value;
  }

  @Override public String error() {
    throw new Error("Unexpected value");
  }

  @Override public String toString() {
    return Double.toString(value);
  }
}
