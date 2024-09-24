package ru.nsu.tsyganov.blackjack;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


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

        assertEquals("Шестёрка Бубны (6)", emptyDeck.takeCard().toString());
        assertEquals("Туз Червы (11)", emptyDeck.takeCard().toString());
        assertEquals("Пиковая Дама (10)", emptyDeck.takeCard().toString());
        assertEquals("Трефовый Король (10)", emptyDeck.takeCard().toString());
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

    @Test
    void checkDealerPrintFirstHand() {
        dealer.getHand().takeCardFromDeck(fullDeck);
        dealer.getHand().takeCardFromDeck(fullDeck);
        dealer.printFirstHand();
    }

    @Test
    void checkPlayerMakeDecision1()
        throws IOException {
        String inputString = "1";
        InputStream stream = new ByteArrayInputStream(inputString.getBytes());
        player.input = new Scanner(stream);
        int returnValue = player.makeDecision(fullDeck, emptyDeck);
        assertEquals(1, returnValue);
        assertEquals("Туз Трефы (11), ", player.getHand().toString());
    }

    @Test
    void checkPlayerMakeDecision2()
            throws IOException {
        String inputString = "0";
        InputStream stream = new ByteArrayInputStream(inputString.getBytes());
        player.input = new Scanner(stream);
        int returnValue = player.makeDecision(fullDeck, emptyDeck);
        assertEquals(0, returnValue);
    }

    @Test
    void checkPrintHand() {
        player.getHand().takeCardFromDeck(fullDeck);
        player.getHand().takeCardFromDeck(fullDeck);
        player.printHand();
    }

    @Test
    void checkHit() {
        player.hit(fullDeck, emptyDeck);
        assertEquals("Туз Трефы (11), ", player.getHand().toString());
    }

    @Test
    void checkHasBlackjack() {
        Card clubA = new Card(Suit.CLUB, Rank.ACE);
        Card clubK = new Card(Suit.CLUB, Rank.KING);
        emptyDeck.addCard(clubA);
        emptyDeck.addCard(clubK);

        player.getHand().takeCardFromDeck(emptyDeck);
        player.getHand().takeCardFromDeck(emptyDeck);

        assertTrue(player.hasBlackjack());
    }

    @Test
    void checkPersonGetSet() {
        hand.takeCardFromDeck(fullDeck);
        player.setHand(hand);
        assertEquals("Туз Трефы (11), ", player.getHand().toString());
        assertEquals("Дилер", dealer.getName());
        player.setName("Санёк");
        assertEquals("Санёк", player.getName());
    }
}