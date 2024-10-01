package ru.nsu.tsyganov.blackjack;

/**
 * Содержит масти карт и их названия.
 */
public enum Suit {
    CLUB("Трефы", "Трефовая", "Трефовый"),
    DIAMOND("Бубны", "Бубновая", "Бубновый"),
    HEART("Червы", "Червовая", "Червовый"),
    SPADE("Пики", "Пиковая", "Пиковый");

    private final String suitNameNone;
    private final String suitNameFemale;
    private final String suitNameMale;

    Suit(String suitNameNone, String suitNameFemale, String suitNameMale) {
        this.suitNameNone = suitNameNone;
        this.suitNameFemale = suitNameFemale;
        this.suitNameMale = suitNameMale;
    }

    public String correctGender(Gender gender) {
        switch (gender) {
            case MALE:
                return suitNameMale;
            case FEMALE:
                return suitNameFemale;
            default:
                return suitNameNone;
        }
    }
}
