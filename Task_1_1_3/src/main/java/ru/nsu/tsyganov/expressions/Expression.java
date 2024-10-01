package ru.nsu.tsyganov.expressions;

public abstract class Expression {
    public abstract double eval();

    public abstract String toString();

    public void print() {
        System.out.println(this.toString());
    }
}
