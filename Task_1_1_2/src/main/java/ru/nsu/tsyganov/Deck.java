package ru.nsu.tsyganov;

import java.util.ArrayList;

/**
 * Класс колоды карт
 */
public class Deck {

    private ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<Card>();
    }

    public ArrayList<Card> getCards() {
        return deck;
    }

    public void addCard(Card card) {
        deck.add(card);
    }

    public String toString() {
        String output = "";

        for(Card card: deck){
            output += card;
            output += "\n";
        }
        return output;
    }

    public Deck(boolean makeDeck) {
        deck = new ArrayList<Card>();
        if(makeDeck) {
            for (Suit suit : Suit.values()) {
                for(Rank rank : Rank.values()) {
                    deck.add(new Card(suit, rank));
                }
            }
        }
    }

    public void shuffle() {
        ArrayList<Card> shuffled = new ArrayList<Card>();

        while (!deck.isEmpty()) {
            int cardToPull = (int)(Math.random() * (deck.size() - 1));
            shuffled.add(deck.get(cardToPull));
            deck.remove(cardToPull);
        }
        deck = shuffled;
    }

    public Card takeCard() {
        Card cardToTake = new Card(deck.get(0));
        deck.remove(0);
        return cardToTake;
    }

    public boolean hasCards() {
        return (!deck.isEmpty());
    }

    public void emptyDeck() {
        deck.clear();
    }

    public void addCards(ArrayList<Card> cards) {
        deck.addAll(cards);
    }

    public void reloadDeckFromDiscard(Deck discard) {
        this.addCards(discard.getCards());
        this.shuffle();
        discard.emptyDeck();
    }

    public int cardsLeft() {
        return deck.size();
    }
}
