package ru.nsu.tsyganov.expressions;

public class Main {
    public static void main(String[] args) {
        //Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x"))); // (3+(2*x))

        ExpressionParser parser = new ExpressionParser();

        Expression e = parser.parse("(3+(2*x))");

        e.print();
    }
}