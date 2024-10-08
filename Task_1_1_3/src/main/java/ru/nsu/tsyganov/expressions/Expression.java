package ru.nsu.tsyganov.expressions;

/**
 * Класс Expression - от него все наследуют(кроме парсера).
 */
public abstract class Expression {
    public abstract double eval(String variables);

    public void print() {
        System.out.println(this.toString());
    }

    public abstract Expression derivative(String var);

}
