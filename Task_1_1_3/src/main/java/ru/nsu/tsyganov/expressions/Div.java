package ru.nsu.tsyganov.expressions;

/**
 * Класс деления.
 */
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        final Div other = (Div) obj;
        return (this.left.equals(other.left)) && (this.right.equals(other.right));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.left != null ? this.left.hashCode() : 0);
        hash = 53 * hash + (this.right != null ? this.right.hashCode() : 0);
        return hash;
    }
}
