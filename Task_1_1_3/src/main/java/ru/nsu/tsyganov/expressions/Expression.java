package ru.nsu.tsyganov.expressions;

public abstract class Expression {
    public abstract double eval(String variables);

    public abstract String toString();

    public void print() {
        System.out.println(this.toString());
    }

    public abstract Expression derivative(String var);
}
