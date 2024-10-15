package ru.nsu.tsyganov.expressions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private Expression expSimple;
    private Expression expAdd;
    private Expression expSub;
    private Expression expMul;
    private Expression expDiv;
    private Expression number;
    private ExpressionParser parser;
    private Variable variable;

    @BeforeEach
    void setup() {
        expSimple = new Add(new Number(3), new Mul(new Number(2), new Variable("x"))); // (3+(2*x))
        expAdd = new Add(new Number(2), new Variable("x")); // (2+x)
        expSub = new Sub(new Variable("x"), new Number(3)); // (x-3)
        expMul = new Mul(new Variable("x"), new Number(4)); // (x*4)
        expDiv = new Div(new Number(5), new Variable("x")); // (5/x)
        number = new Number(10); // 10
        parser = new ExpressionParser();
        variable = new Variable("x");
    }

    @Test
    void printTest() {
        number.print();
        expSimple.print();
        expAdd.print();
        expSub.print();
        expMul.print();
        expDiv.print();
        variable.print();
    }

    @Test
    void numberParse() {
        Number e = (Number) parser.parse("10");
        assertEquals(e.equals(number), e.hashCode() == number.hashCode());
    }

    @Test
    void varParse() {
        Variable e = (Variable) parser.parse("x");
        assertEquals(e.equals(variable), e.hashCode() == variable.hashCode());
    }

    @Test
    void addParse() {
        Add e = (Add) parser.parse("(2+x)");
        assertEquals(e.equals(expAdd), e.hashCode() == expAdd.hashCode());
    }

    @Test
    void subParse() {
        Sub e = (Sub) parser.parse("(x-3)");
        assertEquals(e.equals(expSub), e.hashCode() == expSub.hashCode());
    }

    @Test
    void mulParse() {
        Mul e = (Mul) parser.parse("(x*4)");
        assertEquals(e.equals(expMul), e.hashCode() == expMul.hashCode());
    }

    @Test
    void divParse() {
        Div e = (Div) parser.parse("(5/x)");
        assertEquals(e.equals(expDiv), e.hashCode() == expDiv.hashCode());
    }

    @Test
    void checkDeriv() {
        Expression de = expSimple.derivative("x");
        Expression e = parser.parse("(0+((0*x)+(2*1)))");
        assertEquals(de, e);
    }

    @Test
    void evalNumber() {
        assertEquals(10, number.eval("x = 1"));
    }

    @Test
    void evalVar() {
        assertEquals(10, variable.eval("x = 10"));
    }

    @Test
    void evalAdd() {
        assertEquals(10, expAdd.eval("x = 8"));
    }

    @Test
    void evalSub() {
        assertEquals(10, expSub.eval("x = 13"));
    }

    @Test
    void evalMul() {
        assertEquals(20, expMul.eval("x = 5"));
    }

    @Test
    void evalDiv() {
        assertEquals(1, expDiv.eval("x = 5"));
    }

    @Test
    void evalExp() {
        assertEquals(23, expSimple.eval("x = 10; y = 13"));
    }

    @Test
    void testLongExp() {
        Expression longExp = parser.parse("(((56*x)+(y/2)+(z*9))/((67-x)-(27*y)))");
        assertEquals(202, longExp.eval("x = 10; y = 2; z = 5"));
    }
}