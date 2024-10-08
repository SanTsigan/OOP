package ru.nsu.tsyganov.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
    private Expression expSimple;
    private Expression expAdd;
    private Expression expSub;
    private Expression expMul;
    private Expression expDiv;
    private Expression number;
    private ExpressionParser parser;

    @BeforeEach
    void setup() {
        expSimple = new Add(new Number(3), new Mul(new Number(2), new Variable("x"))); // (3+(2*x))
        expAdd = new Add(new Number(2), new Variable("x")); // (2+x)
        expSub = new Sub(new Variable("x"), new Number(3)); // (x-3)
        expMul = new Mul(new Variable("x"), new Number(4)); // (x*4)
        expDiv = new Div(new Number(5), new Variable("x")); // (5/x)
        number = new Number(10); // 10
        parser = new ExpressionParser();
    }

    @Test
    void printTest() {
        number.print();
        expSimple.print();
        expAdd.print();
        expSub.print();
        expMul.print();
        expDiv.print();
    }

    @Test
    void numberParse() {
        Expression e = parser.parse("10");
        assertEquals(number, e);
    }

    @Test
    void addParse() {
        Expression e = parser.parse("(2+x)");
        assertEquals(expAdd, e);
    }

    @Test
    void subParse() {
        Expression e = parser.parse("(x-3)");
        assertEquals(expSub, e);
    }

    @Test
    void mulParse() {
        Expression e = parser.parse("(x*4)");
        assertEquals(expMul, e);
    }

    @Test
    void divParse() {
        Expression e = parser.parse("(5/x)");
        assertEquals(expDiv, e);
    }
}