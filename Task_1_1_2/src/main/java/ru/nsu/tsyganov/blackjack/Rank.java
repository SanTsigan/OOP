package ru.nsu.tsyganov.blackjack;

/**
 * Содержит достоинства и названия карт, а также кол-во очков за каждую из них.
 */
public enum Rank {
    ACE("Туз", 11, 0),
    TWO("Двойка", 2, 0),
    THREE("Тройка", 3, 0),
    FOUR("Четвёрка", 4, 0),
    FIVE("Пятёрка", 5, 0),
    SIX("Шестёрка", 6, 0),
    SEVEN("Семёрка", 7, 0),
    EIGHT("Восьмёрка", 8, 0),
    NINE("Девятка", 9, 0),
    TEN("Десятка", 10, 0),
    JACK("Валет", 10, 2),
    QUEEN("Дама", 10, 1),
    KING("Король", 10, 2);

    String rankName;
    int rankValue;
    int gender; //1 - женский, 2 - мужсуой, 0 - никакой

    Rank(String rankName, int rankValue, int gender) {
        this.rankName = rankName;
        this.rankValue = rankValue;
        this.gender = gender;
    }

    public String toString() {
        return rankName;
    }
}
