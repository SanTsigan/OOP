package ru.nsu.tsyganov.expressions;

public class Div extends Expression {
    private final Expression left;
    private final Expression right;

    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public double eval(String variables) {
        double result = 0;
        try {
            result = left.eval(variables) / right.eval(variables);
        } catch (ArithmeticException e) {
            System.out.println("Division by zero");
        }
        return result;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "/" + right.toString() + ")";
    }

    @Override
    public Expression derivative(String var) {
        return new Div(new Sub(new Mul(left.derivative(var), right),
                               new Mul(left, right.derivative(var))),
                       new Mul(left, left));
    }
}
