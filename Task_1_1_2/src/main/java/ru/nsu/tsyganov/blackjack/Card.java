package ru.nsu.tsyganov.blackjack;

/**
 * Класс карты.
 */
public class Card {

    private Suit suit;
    private Rank rank;

    /**
     * Конструктор.
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Кол-во очков за катру.
     */
    public int getValue() {
        return rank.rankValue;
    }

    /**
     * Масть карты.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Номинал карты.
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Представляет класс Card в строчном виде.
     */
    public String toString() {
        if (rank.gender == 0) {
            return (rank.rankName + " " + suit.suitName0 + " (" + this.getValue() + ")");
        } else if (rank.gender == 1) {
            return (suit.suitName1 + " " + rank.rankName + " (" + this.getValue() + ")");
        } else {
            return (suit.suitName2 + " " + rank.rankName + " (" + this.getValue() + ")");
        }
    }

    /**
     * Сделать карту из карты.
     */
    public Card(Card card) {
        this.suit = card.getSuit();
        this.rank = card.getRank();
    }
}
