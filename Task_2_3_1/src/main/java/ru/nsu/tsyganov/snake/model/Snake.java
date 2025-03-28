package ru.nsu.tsyganov.snake.model;

import javafx.geometry.Point2D;
import java.util.LinkedList;

public class Snake {
    private LinkedList<Point2D> body;
    private Direction direction;
    private boolean grow;

    public Snake(Point2D startPosition) {
        body = new LinkedList<>();
        body.add(startPosition);
        direction = Direction.RIGHT;
        grow = false;
    }

    public void move() {
        Point2D head = getHead();
        Point2D newHead = null;

        switch (direction) {
            case UP:
                newHead = new Point2D(head.getX(), head.getY() - 1);
                break;
            case DOWN:
                newHead = new Point2D(head.getX(), head.getY() + 1);
                break;
            case LEFT:
                newHead = new Point2D(head.getX() - 1, head.getY());
                break;
            case RIGHT:
                newHead = new Point2D(head.getX() + 1, head.getY());
                break;
        }

        body.addFirst(newHead);

        if (!grow) {
            body.removeLast();
        } else {
            grow = false;
        }
    }

    public void grow() {
        this.grow = true;
    }

    public boolean collidesWith(Point2D point) {
        return body.contains(point);
    }

    public boolean collidesWithSelf() {
        Point2D head = getHead();
        return body.stream().skip(1).anyMatch(segment -> segment.equals(head));
    }

    public Point2D getHead() {
        return body.getFirst();
    }

    public LinkedList<Point2D> getBody() {
        return body;
    }

    public void setDirection(Direction direction) {
        // Prevent 180-degree turns
        if ((this.direction == Direction.UP && direction != Direction.DOWN) ||
                (this.direction == Direction.DOWN && direction != Direction.UP) ||
                (this.direction == Direction.LEFT && direction != Direction.RIGHT) ||
                (this.direction == Direction.RIGHT && direction != Direction.LEFT)) {
            this.direction = direction;
        }
    }

    public Direction getDirection() {
        return direction;
    }
}
