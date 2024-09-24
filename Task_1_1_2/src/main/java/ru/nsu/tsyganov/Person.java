package ru.nsu.tsyganov;

/**
 * Класс участник игры. То есть дилера либо живого игрока.
 */
public abstract class Person {
    private Hand hand;
    private String name;

    /**
     * Конструктор Person. Создаёт участнику пустое имя и руку.
     */
    public Person() {
        this.hand = new Hand();
        this.name = "";
    }

    /**
     * Проверяет, есть ли у участника блэкджек.
     */
    public boolean hasBlackjack() {
        return (this.getHand().calculatedValue() == 21);
    }

    public Hand getHand() {
        return this.hand;
    }
    public String getName() {
        return this.name;
    }
    public void setHand(Hand hand) {
        this.hand = hand;
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Печатает содержимое руки участника в форматированном виде.
     */
    public void printHand() {
        System.out.println("Карты " + this.name + "а: [" + this.hand + "] -> " + this.hand.calculatedValue());
    }

    /**
     * Взять карту.
     * @param deck - из какой колоды берем.
     * @param discard - колода сброса если нужно обновить колоду.
     */
    public void hit(Deck deck, Deck discard) {
        if(!deck.hasCards()) {
            deck.reloadDeckFromDiscard(discard);
        }
        this.hand.takeCardFromDeck(deck);
        System.out.println(this.name + " открыл карту " + this.hand.getCard(this.hand.getSize() - 1));
    }
}
