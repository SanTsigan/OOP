package ru.nsu.tsyganov.expressions;

public class Number extends Expression {
    private final int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public double eval(String variables) {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public Expression derivative(String var) {
        if (var.isEmpty()) {
            throw new RuntimeException("No variables");
        }
        return new Number(0);
    }
}
