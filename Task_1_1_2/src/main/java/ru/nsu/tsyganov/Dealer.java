package ru.nsu.tsyganov;

/**
 * Класс дилера, наследующий класс Person.
 */
public class Dealer extends Person {
    public Dealer() {
        super.setName("Дилер");
    }

    public void printFirstHand() {
        System.out.println("Карты Дилера: [" + super.getHand().getCard(0) + ", <закрытая карта>]");
    }
}
