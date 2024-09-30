package ru.nsu.tsyganov.expressions;

public class Number extends Expression {
    private final double value;

    public Number(double value) {
        this.value = value;
    }

    @Override
    public double eval() {
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
