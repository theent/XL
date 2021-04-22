package expr;

/**
 * An <code>Expr</code> object represents a real valued expression that may
 * contain variables. The value of a variable is obtained from an
 * <code>Environment</code> object by specifying the name of the variable.
 *
 * @author Lennart Andersson
 * @see Environment
 */
public interface Expr {
  /**
   * The <code>value</code> method returns the value of this expression.
   *
   * @param env is the <code>Environment</code> containing the values of
   *            variables.
   * @return the value of this expression (or an error message if there is a problem).
   */
  ExprResult value(Environment env);
}
