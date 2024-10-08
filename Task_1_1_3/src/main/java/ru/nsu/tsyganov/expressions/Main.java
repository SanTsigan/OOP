package ru.nsu.tsyganov.expressions;

public class Main {
    public static void main(String[] args) {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x"))); // (3+(2*x))

        ExpressionParser parser = new ExpressionParser();

        Expression se = parser.parse("(3+(2*x))");

        Expression de = se.derivative("x");

        int result = (int) e.eval("x = 10; y = 13");

        de.print();

        System.out.println(result);
    }
}