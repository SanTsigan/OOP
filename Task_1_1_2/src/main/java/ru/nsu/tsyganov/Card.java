package ru.nsu.tsyganov;

/**
 * Класс карты.
 */
public class Card {

    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public int getValue() {
        return rank.rankValue;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public String toString() {
        if (rank.gender == 0) {
            return (rank.rankName + " " + suit.suitName0 + " (" + this.getValue() + ")");
        } else if (rank.gender == 1){
            return (suit.suitName1 + " " + rank.rankName + " (" + this.getValue() + ")");
        } else {
            return (suit.suitName2 + " " + rank.rankName + " (" + this.getValue() + ")");
        }
    }

    public Card(Card card) {
        this.suit = card.getSuit();
        this.rank = card.getRank();
    }
}
