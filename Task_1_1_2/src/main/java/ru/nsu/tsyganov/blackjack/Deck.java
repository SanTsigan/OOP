package ru.nsu.tsyganov.blackjack;

import java.util.ArrayList;

/**
 * Класс колоды карт.
 */
public class Deck {

    private ArrayList<Card> deck;

    /**
     * Конструктор.
     */
    public Deck() {
        deck = new ArrayList<Card>();
    }

    public ArrayList<Card> getCards() {
        return deck;
    }

    public void addCard(Card card) {
        deck.add(card);
    }

    /**
     * Приводит к строчному виду.
     */
    public String toString() {
        String output = "";

        for (Card card : deck) {
            output += card;
            output += "\n";
        }
        return output;
    }

    /**
     * Для создания полной колоды со всеми 52 картами.
     */
    public Deck(boolean makeDeck) {
        deck = new ArrayList<Card>();
        if (makeDeck) {
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    deck.add(new Card(suit, rank));
                }
            }
        }
    }

    /**
     * Перетасовать колоду.
     */
    public void shuffle() {
        ArrayList<Card> shuffled = new ArrayList<Card>();

        while (!deck.isEmpty()) {
            int cardToPull = (int) (Math.random() * (deck.size() - 1));
            shuffled.add(deck.get(cardToPull));
            deck.remove(cardToPull);
        }
        deck = shuffled;
    }

    /**
     * Взять карту сверху колоды.
     */
    public Card takeCard() {
        Card cardToTake = new Card(deck.get(0));
        deck.remove(0);
        return cardToTake;
    }

    /**
     * Проверка на наличие карт в колоде.
     */
    public boolean hasCards() {
        return (!deck.isEmpty());
    }

    /**
     * Отчистить колоду.
     */
    public void emptyDeck() {
        deck.clear();
    }

    /**
     * Добавить в колоду ArrayList карт.
     */
    public void addCards(ArrayList<Card> cards) {
        deck.addAll(cards);
    }

    /**
     * Обновить колоду.
     */
    public void reloadDeckFromDiscard(Deck discard) {
        this.addCards(discard.getCards());
        this.shuffle();
        discard.emptyDeck();
    }

    /**
     * Сколько карт осталось.
     */
    public int cardsLeft() {
        return deck.size();
    }
}
