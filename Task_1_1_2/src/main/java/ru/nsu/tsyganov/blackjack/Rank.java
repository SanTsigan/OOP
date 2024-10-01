package ru.nsu.tsyganov.blackjack;

/**
 * Содержит достоинства и названия карт, а также кол-во очков за каждую из них.
 */
public enum Rank {
    ACE("Туз", 11, Gender.NONE),
    TWO("Двойка", 2, Gender.NONE),
    THREE("Тройка", 3, Gender.NONE),
    FOUR("Четвёрка", 4, Gender.NONE),
    FIVE("Пятёрка", 5, Gender.NONE),
    SIX("Шестёрка", 6, Gender.NONE),
    SEVEN("Семёрка", 7, Gender.NONE),
    EIGHT("Восьмёрка", 8, Gender.NONE),
    NINE("Девятка", 9, Gender.NONE),
    TEN("Десятка", 10, Gender.NONE),
    JACK("Валет", 10, Gender.MALE),
    QUEEN("Дама", 10, Gender.FEMALE),
    KING("Король", 10, Gender.MALE);

    public final String rankName;
    public final int rankValue;
    final Gender gender;

    Rank(String rankName, int rankValue, Gender gender) {
        this.rankName = rankName;
        this.rankValue = rankValue;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return rankName;
    }
}
