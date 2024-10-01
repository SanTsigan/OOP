package ru.nsu.tsyganov.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
    private Game game;
    private Deck emptyDeck;
    private Deck fullDeck;
    private Deck discard;
    private Player player;
    private Dealer dealer;
    private Hand hand;

    @BeforeEach
    void setUp() {
        emptyDeck = new Deck();
        fullDeck = new Deck(true);
        discard = new Deck();
        player = new Player();
        dealer = new Dealer();
        hand = new Hand();
        game = new Game();
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

        assertEquals("Шестёрка Бубны (6)", emptyDeck.takeCard(discard).toString());
        assertEquals("Туз Червы (11)", emptyDeck.takeCard(discard).toString());
        assertEquals("Пиковая Дама (10)", emptyDeck.takeCard(discard).toString());
        assertEquals("Трефовый Король (10)", emptyDeck.takeCard(discard).toString());
    }

    @Test
    void checkShuffle() {
        fullDeck.shuffle();
        Deck testDeck = new Deck(true);
        assertNotEquals(fullDeck, testDeck);
    }

    @Test
    void checkTakeCard() {
        Card testCard = fullDeck.takeCard(discard);
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
        dealer.getHand().takeCardFromDeck(fullDeck, discard);
        dealer.getHand().takeCardFromDeck(fullDeck, discard);
        dealer.printFirstHand();
    }

    @Test
    void checkPlayerMakeDecision1()
        throws IOException {
        String inputString = "1";
        InputStream stream = new ByteArrayInputStream(inputString.getBytes());
        player.input = new Scanner(stream);
        int returnValue = player.makeDecision(fullDeck, discard);
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
        player.getHand().takeCardFromDeck(fullDeck, discard);
        player.getHand().takeCardFromDeck(fullDeck, discard);
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

        player.getHand().takeCardFromDeck(emptyDeck, discard);
        player.getHand().takeCardFromDeck(emptyDeck, discard);

        assertTrue(player.hasBlackjack());
    }

    @Test
    void checkPersonGetSet() {
        hand.takeCardFromDeck(fullDeck, discard);
        player.setHand(hand);
        assertEquals("Туз Трефы (11), ", player.getHand().toString());
        assertEquals("Дилер", dealer.getName());
        player.setName("Санёк");
        assertEquals("Санёк", player.getName());
    }

    @Test
    void checkMain()
        throws IOException {
        String inputString = "1\n1\n1\n1\n9\n";
        InputStream stream = new ByteArrayInputStream(inputString.getBytes());
        game.player.input = new Scanner(stream);
        game.startRound();
        assertNotEquals(0, game.losses);
    }
}