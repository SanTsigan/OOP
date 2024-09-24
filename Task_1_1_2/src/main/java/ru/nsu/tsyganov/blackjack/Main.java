package ru.nsu.tsyganov.blackjack;

/**
 * Класс Main чтобы поприветсвовать игрока и запустить саму игру.
 */
public class Main {
    /**
     * main.
     */
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в Блэкджек!");

        Game blackjack = new Game();
        blackjack.startRound() ;
    }
}