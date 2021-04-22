package expr;

public interface Environment {
  ExprResult value(String name);
}
