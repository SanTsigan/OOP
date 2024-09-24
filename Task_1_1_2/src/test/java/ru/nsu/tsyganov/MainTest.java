package ru.nsu.tsyganov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private Game game;
    private Deck emptyDeck;
    private Deck fullDeck;
    private Player player;
    private Dealer dealer;
    private Hand hand;

    @BeforeEach
    void setUp() {
        //game = new Game();
        emptyDeck = new Deck();
        fullDeck = new Deck(true);
        player = new Player();
        dealer = new Dealer();
        hand = new Hand();
    }

    @Test
    void checkAddCard() {
        Card diamond6 = new Card(Suit.DIAMOND, Rank.SIX);
        Card heartA = new Card(Suit.HEART, Rank.ACE);
        Card spadeQ = new Card(Suit.SPADE, Rank.QUEEN);
        Card clubK = new Card(Suit.CLUB, Rank.KING);
        emptyDeck.addCard(diamond6);
        emptyDeck.addCard(heartA);
        emptyDeck.addCard(spadeQ);
        emptyDeck.addCard(clubK);

        assertEquals("Шестёрка Бубны (6)\nТуз Червы (11)\nПиковая Дама (10)\nТрефовый Король (10)\n", emptyDeck.toString());
    }

    @Test
    void checkShuffle() {
        fullDeck.shuffle();
        Deck testDeck = new Deck(true);
        assertNotEquals(fullDeck, testDeck);
    }

    @Test
    void checkTakeCard() {
        Card testCard = fullDeck.takeCard();
        assertEquals("Туз Трефы (11)", testCard.toString());
    }

    @Test
    void checkHasCards() {
        assertTrue(fullDeck.hasCards());
    }

    @Test
    void checkEmptyDeck() {
        fullDeck.emptyDeck();
        assertEquals(fullDeck.toString(), emptyDeck.toString());
    }

    @Test
    void checkAddCards() {
        Deck testDeck = new Deck();
        testDeck.addCards(fullDeck.getCards());
        assertEquals(fullDeck.toString(), testDeck.toString());
    }

}