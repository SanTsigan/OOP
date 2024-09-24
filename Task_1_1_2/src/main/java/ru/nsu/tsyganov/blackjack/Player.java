package ru.nsu.tsyganov.blackjack;

import java.util.Scanner;

/**
 * Класс игрока, наследующий класс Person.
 */
public class Player extends Person {

    Scanner input = new Scanner(System.in);

    /**
     * Конструктор.
     */
    public Player() {
        super.setName("Игрок");
    }

    /**
     * Метод обрабатывающий пользовательский ввод.
     */
    public int makeDecision(Deck deck, Deck discard) {

        int decision = 0;
        boolean getNum = true;

        while (getNum) {

            try {
                System.out.print("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться...");
                System.out.println(" (Введи 9 чтобы выйти)");
                decision = input.nextInt();
                getNum = false;
            } catch (Exception e) {
                System.out.println("Неверно.");
                input.next();
            }
        }

        if (decision == 1) {
            this.hit(deck, discard);
            return 1;
        } else if (decision == 9) {
            return -1;
        } else {
            return 0;
        }
    }

}
