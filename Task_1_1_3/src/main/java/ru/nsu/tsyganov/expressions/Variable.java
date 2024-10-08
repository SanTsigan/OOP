package ru.nsu.tsyganov.expressions;

import java.util.HashMap;
import java.util.Map;

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
        return name.equals(var) ? new Number(1) : new Number(0);
    }

    @Override
    public double eval(String variables) {
        Map<String, Double> varMap = parseVariables(variables);
        return varMap.getOrDefault(name, 0.0);
    }

    private Map<String, Double> parseVariables(String variables) {
        Map<String, Double> varMap = new HashMap<>();
        String[] pairs = variables.split(";");
        for(String pair : pairs) {
            String[] parts = pair.split("=");
            if(parts.length == 2) {
                String varName = parts[0].trim();
                double varValue = Double.parseDouble(parts[1].trim());
                varMap.put(varName, varValue);
            }
        }
        return varMap;
    }

}
