package ru.nsu.tsyganov.expressions;

import java.util.Objects;

public class Add extends Expression {
    public final Expression left;
    public final Expression right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public double eval(String variables) {
        return left.eval(variables) + right.eval(variables);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "+" + right.toString() + ")";
    }

    @Override
    public Expression derivative(String var) {
        return new Add(left.derivative(var), right.derivative(var));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        final Add other = (Add) obj;
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
