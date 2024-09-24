package ru.nsu.tsyganov;

/**
 * Содержит масти карт и их названия.
 */
public enum Suit {
    CLUB("Трефы", "Трефовая", "Трефовый"),
    DIAMOND("Бубны", "Бубновая", "Бубновый"),
    HEART("Червы", "Червовая", "Червовый"),
    SPADE("Пики", "Пиковая", "Пиковый");

    String suitName0, suitName1, suitName2;

    Suit(String suitName0, String suitName1, String suitName2) {
        this.suitName0 = suitName0;
        this.suitName1 = suitName1;
        this.suitName2 = suitName2;
    }

    public String toString() {
        return suitName0 + suitName1 + suitName2;
    }
}
