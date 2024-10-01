package ru.nsu.tsyganov.expressions;

public class Variable extends Expression {
    public final String name;
    public double value;

    public Variable(String name) {
        this.name = name;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double eval() {
        return 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
