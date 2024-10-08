package ru.nsu.tsyganov.expressions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

class ExpressionParser {
    private static final Set<String> OPERATORS = new HashSet<>(Arrays.asList("+", "-", "*", "/"));

    public Expression parse(String input) {
        Stack<Expression> values = new Stack<>();
        Stack<String> operators = new Stack<>();
        StringBuilder token = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            // Игнорируем пробелы
            if (Character.isWhitespace(ch)) {
                continue;
            }

            // Если символ - цифра или буква, собираем токен
            if (Character.isDigit(ch) || Character.isLetter(ch)) {
                token.append(ch);
            } else {
                // Если собран токен, обрабатываем его
                if (token.length() > 0) {
                    processToken(token.toString(), values);
                    token.setLength(0); // Очищаем токен
                }

                // Обрабатываем оператор или скобки
                if (ch == '(') {
                    operators.push("(");
                } else if (ch == ')') {
                    while (!operators.isEmpty() && !operators.peek().equals("(")) {
                        values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                    }
                    operators.pop(); // Удаляем '('
                } else if (OPERATORS.contains(String.valueOf(ch))) {
                    while (!operators.isEmpty()
                            && precedence(operators.peek()) >= precedence(String.valueOf(ch))) {
                        values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                    }
                    operators.push(String.valueOf(ch));
                }
            }
        }

        // Обрабатываем последний токен, если он есть
        if (!token.isEmpty()) {
            processToken(token.toString(), values);
        }

        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private void processToken(String token, Stack<Expression> values) {
        if (isNumeric(token)) {
            values.push(new Number(Integer.parseInt(token)));
        } else if (isVariable(token)) {
            values.push(new Variable(token));
        }
    }

    private boolean isNumeric(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isVariable(String token) {
        return token.matches("[a-zA-Z]+"); // Переменные состоят только из букв
    }

    private int precedence(String operator) {
        return switch (operator) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> 0;
        };
    }

    private Expression applyOperator(String operator, Expression right, Expression left) {
        return switch (operator) {
            case "+" -> new Add(left, right);
            case "-" -> new Sub(left, right);
            case "*" -> new Mul(left, right);
            case "/" -> new Div(left, right);
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }
}

