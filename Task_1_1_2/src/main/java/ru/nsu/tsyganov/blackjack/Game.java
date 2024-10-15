package ru.nsu.tsyganov.blackjack;

/**
 * Класс Game, который ведёт игру.
 */
public class Game {

    private Deck deck;
    private Deck discarded;

    private final Dealer dealer;
    public Player player;
    private int wins;
    public int losses;
    private int rounds;
    private int turn;
    private int playerAction;

    /**
     * Конструктор game, создаёт переменные и начинает игру.
     */
    public Game() {

        wins = 0;
        losses = 0;
        rounds = 0;
        //создаём колоду
        deck = new Deck(true);
        discarded = new Deck();

        dealer = new Dealer();
        player = new Player();

        deck.shuffle();
    }

    /**
     * Печатает форматированный счёт.
     */
    private void printScore() {
        System.out.print("Счёт " + wins + ":" + losses);
        if (wins > losses) {
            System.out.println(" в вашу пользу.");
        } else if (wins < losses) {
            System.out.println(" в пользу Дилера.");
        } else {
            System.out.println(".");
        }
    }

    /**
     * Печатает содержимое рук игрока и дилера.
     */
    private void printHands() {
        player.printHand();
        if (turn == 0) {
            dealer.printFirstHand();
        } else {
            dealer.printHand();
        }
        System.out.println();
    }

    /**
     * Полный раунд игры.
     */
    public void startRound() {
        rounds++;

        System.out.println("\nРаунд " + rounds);
        System.out.println("Дилер раздал карты.");

        turn = 0;

        if (rounds > 1) {
            System.out.println();
            dealer.getHand().discardHandToDeck(discarded);
            player.getHand().discardHandToDeck(discarded);
        }

        if (deck.cardsLeft() < 4) {
            deck.reloadDeckFromDiscard(discarded);
        }

        dealer.getHand().takeCardFromDeck(deck, discarded);
        dealer.getHand().takeCardFromDeck(deck, discarded);

        player.getHand().takeCardFromDeck(deck, discarded);
        player.getHand().takeCardFromDeck(deck, discarded);

        player.printHand();
        dealer.printFirstHand();

        if (dealer.hasBlackjack()) {
            dealer.printHand();

            if (player.hasBlackjack()) {
                System.out.println("У вас обоих 21 - Ничья.");
                printScore();
                startRound();
            } else {
                losses++;
                System.out.println("У крупье блэкджек. Вы проиграли.");
                printScore();
                startRound();
            }
        }

        if (player.hasBlackjack()) {
            wins++;
            System.out.println("У вас блэкджек! Вы выиграли!");
            printScore();
            startRound();
        }

        System.out.println("\nВаш ход\n-------");
        while (player.getHand().calculatedValue() <= 20) {
            playerAction = player.makeDecision(deck, discarded);
            if (playerAction == 0) {
                break;
            } else if (playerAction == -1) {
                System.out.print("Итоговый ");
                printScore();
                break;
            }
            printHands();
        }

        if (playerAction != -1) {
            if (player.getHand().calculatedValue() > 21) {
                losses++;
                System.out.println("У вас больше 21.");
                System.out.print("Вы проиграли этот раунд. ");
                printScore();
                startRound();
            }

            if (player.getHand().calculatedValue() == 21) {
                wins++;
                System.out.print("Вы выиграли этот раунд. ");
                printScore();
                startRound();
            }

            turn = 1;

            System.out.println("Ход дилера\n-------");
            System.out.println("Дилер открывает закрытую карту " + dealer.getHand().getCard(1));
            printHands();
            while (dealer.getHand().calculatedValue() < 17) {
                System.out.println();
                dealer.hit(deck, discarded);
                printHands();
            }

            if (dealer.getHand().calculatedValue() > 21) {
                wins++;
                System.out.print("Вы выиграли этот раунд. ");
                printScore();
            } else if (player.getHand().calculatedValue() > dealer.getHand().calculatedValue()) {
                wins++;
                System.out.print("Вы выиграли этот раунд. ");
                printScore();
            } else if (dealer.getHand().calculatedValue() > player.getHand().calculatedValue()) {
                losses++;
                System.out.print("Вы проиграли этот раунд. ");
                printScore();
            } else {
                System.out.print("Ничья. ");
                printScore();
            }

            startRound();

        } else {
            return; // выход из рекурсии.
        }
    }
}
