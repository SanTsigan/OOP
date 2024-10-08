package ru.nsu.tsyganov.blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс колоды карт.
 */
public class Deck {

    private List<Card> deck;

    /**
     * Конструктор.
     */
    public Deck() {
        deck = new ArrayList<Card>();
    }

    public List<Card> getCards() {
        return deck;
    }

    public void addCard(Card card) {
        deck.add(card);
    }

    /**
     * Приводит к строчному виду.
     */
    public String toString() {
        StringBuilder output = new StringBuilder();

        for (Card card : deck) {
            output.append(card);
            output.append("\n");
        }
        return output.toString();
    }

    /**
     * Для создания полной колоды со всеми 52 картами.
     */
    public Deck(boolean makeDeck) {
        deck = new ArrayList<>();
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
        List<Card> shuffled = new ArrayList<>();

        while (!deck.isEmpty()) {
            int cardToPull = (int) (Math.random() * (deck.size() - 1));
            shuffled.add(deck.get(cardToPull));
            deck.remove(cardToPull);
        }
        deck = shuffled;
    }

    /**
     * Проверка на наличие карт в колоде.
     */
    public boolean hasCards() {
        return !deck.isEmpty();
    }

    /**
     * Взять карту сверху колоды.
     */
    public Card takeCard(Deck discard) {
        if (!hasCards()) {
            reloadDeckFromDiscard(discard);
        }
        Card cardToTake = new Card(deck.get(0));
        deck.remove(0);
        return cardToTake;

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
    public void addCards(List<Card> cards) {
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
