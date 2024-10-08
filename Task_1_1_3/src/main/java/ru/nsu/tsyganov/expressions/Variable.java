package ru.nsu.tsyganov.expressions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Класс для переменных.
 */
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
    public String toString() {
        return name;
    }

    @Override
    public Expression derivative(String var) {
        if (var.isEmpty()) {
            throw new RuntimeException("No variables");
        }
        return name.equals(var) ? new Number(1) : new Number(0);
    }

    @Override
    public double eval(String variables) {
        Map<String, Double> varMap = parseVariables(variables);
        return varMap.getOrDefault(name, 0.0);
    }

    private Map<String, Double> parseVariables(String variables) {
        if (variables.isEmpty()) {
            throw new RuntimeException("No variables");
        }
        Map<String, Double> varMap = new HashMap<>();
        String[] pairs = variables.split(";");
        for (String pair : pairs) {
            String[] parts = pair.split("=");
            if (parts.length == 2) {
                String varName = parts[0].trim();
                double varValue = Double.parseDouble(parts[1].trim());
                varMap.put(varName, varValue);
            }
        }
        return varMap;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        final Variable other = (Variable) obj;

        return (value != other.value) && (this.name != null) ?
                (other.name == null) : Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 53 * hash + (int) this.value;
        return hash;
    }

}
