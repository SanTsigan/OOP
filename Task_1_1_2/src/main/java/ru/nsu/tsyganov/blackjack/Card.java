package ru.nsu.tsyganov.blackjack;

/**
 * Класс карты.
 */
public class Card {

    private final Suit suit;
    private final Rank rank;

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

    private String correctGender() {
        if(rank.gender == Gender.NONE) {
            return rank.rankName + " " + suit.correctGender(rank.gender);
        } else {
            return suit.correctGender(rank.gender) + " " + rank.rankName;
        }
    }
    /**
     * Представляет класс Card в строчном виде.
     */
    @Override
    public String toString() {
        return correctGender() + " (" + this.getValue() + ")";
    }

    /**
     * Сделать карту из карты.
     */
    public Card(Card card) {
        this.suit = card.getSuit();
        this.rank = card.getRank();
    }
}
