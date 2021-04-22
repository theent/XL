package expr;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestExpr {
    @Test public void test1() throws IOException {
        ExprParser parser = new ExprParser();
        Expr expr = parser.build("1+2*3");
        assertEquals(expr.value(new EmptyEnvironment()).toString(), "7.0");
    }

    @Test public void test2() throws IOException {
        Environment env = name -> {
            if (name.equals("A3"))
                return new ValueResult(1);
            if (name.equals("A2"))
                return new ValueResult(2);
            if (name.equals("A1"))
                return new ValueResult(3);
            System.out.println(name + " is undefined");
            return new ValueResult(0);
        };
        ExprParser parser = new ExprParser();
        Expr expr = parser.build("1+2*3");
        assertEquals(expr.value(env).toString(), "7.0");
    }
}
