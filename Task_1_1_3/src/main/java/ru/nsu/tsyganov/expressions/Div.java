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
        return left.eval(variables) / right.eval(variables);
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
