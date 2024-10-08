package ru.nsu.tsyganov.expressions;

public class Number extends Expression {
    private int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public double eval(String variables) {
        if (variables.isEmpty()) {
            throw new RuntimeException("No variables");
        }
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        final Number other = (Number) obj;

        return value == other.value;
    }

    @Override
    public int hashCode() {
        return value * 31;
    }
}
