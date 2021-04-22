package expr;

/**
 * Represents the result of an expression which can be either a
 * value (ValueResult) or an error (ErrorResult).
 */
public interface ExprResult {
  double value();

  String error();

  boolean isError();
}
