package ru.nsu.tsyganov.expressions;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

class ExpressionParser {
    private static final Set<String> OPERATORS = new HashSet<>(Arrays.asList("+", "-", "*", "/"));

    public Expression parse(String input) {
        Deque<Expression> values = new ArrayDeque<>();
        Deque<String> operators = new ArrayDeque<>();
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
                if (!token.isEmpty()) {
                    processToken(token.toString(), values);
                    token.setLength(0); // Очищаем токен
                }

                // Обрабатываем оператор или скобки
                if (ch == '(') {
                    operators.addFirst("(");
                } else if (ch == ')') {
                    while (!operators.isEmpty() && !operators.peek().equals("(")) {
                        values.addFirst(applyOperator(operators.removeFirst(),
                                values.removeFirst(),
                                values.removeFirst()));
                    }
                    operators.removeFirst(); // Удаляем '('
                } else if (OPERATORS.contains(String.valueOf(ch))) {
                    while (!operators.isEmpty()
                            && precedence(operators.peek()) >= precedence(String.valueOf(ch))) {
                        values.addFirst(applyOperator(operators.removeFirst(),
                                values.removeFirst(),
                                values.removeFirst()));
                    }
                    operators.addFirst(String.valueOf(ch));
                }
            }
        }

        // Обрабатываем последний токен, если он есть
        if (!token.isEmpty()) {
            processToken(token.toString(), values);
        }

        while (!operators.isEmpty()) {
            values.addFirst(applyOperator(operators.removeFirst(),
                    values.removeFirst(),
                    values.removeFirst()));
        }

        return values.removeFirst();
    }

    private void processToken(String token, Deque<Expression> values) {
        if (isNumeric(token)) {
            values.addFirst(new Number(Integer.parseInt(token)));
        } else if (isVariable(token)) {
            values.addFirst(new Variable(token));
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

