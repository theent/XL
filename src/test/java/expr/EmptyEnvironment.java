package expr;

/** Mock environment for testing. */
public class EmptyEnvironment implements Environment {
  @Override public ExprResult value(String name) {
    return new ErrorResult("missing value " + name);
  }
}
