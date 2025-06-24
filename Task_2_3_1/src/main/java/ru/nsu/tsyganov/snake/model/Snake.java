package ru.nsu.tsyganov.snake.model;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.LinkedList;

public class Snake implements Actor {
    private final LinkedList<Point2D> body = new LinkedList<>();
    private Direction direction = Direction.RIGHT;
    private boolean grow;
    private volatile boolean alive = true;

    public Snake(Point2D startPosition) {
        body.add(startPosition);
    }

    @Override
    public synchronized void update(GameModel model) {
        if (!alive) return;

        move();
        checkCollisions(model);
    }

    private void move() {
        Point2D head = getHead();
        Point2D newHead = switch (direction) {
            case UP -> new Point2D(head.getX(), head.getY() - 1);
            case DOWN -> new Point2D(head.getX(), head.getY() + 1);
            case LEFT -> new Point2D(head.getX() - 1, head.getY());
            case RIGHT -> new Point2D(head.getX() + 1, head.getY());
        };

        body.addFirst(newHead);

        // Удаляем хвост только если не растем
        if (!grow) {
            body.removeLast();
        } else {
            grow = false; // Сбрасываем флаг после роста
        }
    }

    private void checkCollisions(GameModel model) {
        Point2D head = getHead();

        // Стены
        if (head.getX() < 0 || head.getX() > model.getWidth() ||
                head.getY() < 0 || head.getY() > model.getHeight()) {
            alive = false;
        }

        // Самопересечение
        if (body.stream().skip(1).anyMatch(segment -> segment.equals(head))) {
            alive = false;
        }
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public boolean isThreadSafe() {
        return true;
    }

    public synchronized ArrayList<Point2D> getBody() {
        return new ArrayList<>(body);
    }

    public synchronized Point2D getHead() {
        return body.getFirst();
    }

    public synchronized void setDirection(Direction newDirection) {
        // Запрещаем разворот на 180°
        if ((direction == Direction.UP && newDirection != Direction.DOWN) ||
                (direction == Direction.DOWN && newDirection != Direction.UP) ||
                (direction == Direction.LEFT && newDirection != Direction.RIGHT) ||
                (direction == Direction.RIGHT && newDirection != Direction.LEFT)) {
            this.direction = newDirection;
            System.out.println("Direction changed to: " + newDirection); // Отладка
        }
    }

    public synchronized void grow() {
        this.grow = true;
    }

    public synchronized int getLength() {
        return body.size();
    }
}