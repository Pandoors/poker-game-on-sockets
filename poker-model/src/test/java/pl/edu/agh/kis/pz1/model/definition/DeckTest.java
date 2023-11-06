package pl.edu.agh.kis.pz1.model.definition;

import junit.framework.TestCase;

public class DeckTest extends TestCase {

    public void testShuffle() {
        Deck deck = new Deck();
        deck.shuffle();
        System.out.println(deck.getCards().size());


    }

    public void testGenerateDeck() {
        Deck deck = new Deck();
        deck.generateDeck();
    }

    public void testGetCards() {
    }

    public void testFillPlayerHand() {
        Deck deck = new Deck();
        deck.generateDeck();
        Player p1 = new Player("Michal");
        deck.fillPlayerHand(p1);

    }

    public void testSetCards() {
    }

    public void testToString() {
        Deck deck = new Deck();
        deck.shuffle();
        String s = deck.toString();
    }


}