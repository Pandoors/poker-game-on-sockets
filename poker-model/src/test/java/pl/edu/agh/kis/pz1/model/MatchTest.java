package pl.edu.agh.kis.pz1.model;

import junit.framework.TestCase;
import pl.edu.agh.kis.pz1.model.definition.Card;
import pl.edu.agh.kis.pz1.model.definition.Deck;
import pl.edu.agh.kis.pz1.model.definition.Player;
import pl.edu.agh.kis.pz1.model.exceptions.LackOfPlayersException;
import pl.edu.agh.kis.pz1.model.exceptions.TooManyPlayersException;
import pl.edu.agh.kis.pz1.model.exceptions.UnproperCardsToExchangeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchTest extends TestCase {

    public void testNewPlayer() {
    }

    public void testNewMatch() {
    }

    public void testDetermineRoundWinner() throws LackOfPlayersException, TooManyPlayersException {
        Match match = new Match();
        Player p = new Player("odyn");
        match.newPlayer(p);
        match.newPlayer(new Player("abu"));
        match.newPlayer(new Player("jojas"));
        match.newMatch();
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player("one"));
        playerList.add(new Player("two"));

        List<Player> winners = match.determineRoundWinner(100);

    }

    public void testWinnersToString() throws LackOfPlayersException, TooManyPlayersException {
        Match match = new Match();
        Player p = new Player("odyn");
        match.newPlayer(p);
        match.newPlayer(new Player("abu"));
        match.newPlayer(new Player("jojas"));
        match.newMatch();
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player("one"));
        playerList.add(new Player("two"));
        assertNotNull(match);
        String winners = match.winnersToString(playerList);
    }

    public void testExchangePlayerCards() throws TooManyPlayersException, LackOfPlayersException, UnproperCardsToExchangeException {
        Match match = new Match();
        Player p = new Player("odyn");
        match.newPlayer(p);
        match.newPlayer(new Player("abu"));
        match.newPlayer(new Player("jojas"));
        match.newMatch();
        List<Card> cards = Collections.emptyList();
        match.exchangePlayerCards(p, cards);
    }

    public void testIsBettingEnded() throws TooManyPlayersException {
        Match match = new Match();
        match.newPlayer(new Player("odyn"));
        match.newPlayer(new Player("abu"));
        match.newPlayer(new Player("jojas"));

        match.isBettingEnded();
    }

    public void testClearAfterBet() throws TooManyPlayersException {
        Match match = new Match();
        match.newPlayer(new Player("odyn"));
        match.newPlayer(new Player("abu"));
        match.newPlayer(new Player("jojas"));

        match.clearAfterBet();
        for (Player player : match.getPlayers()) {
            assertFalse(player.isFolded());
        }
    }

    public void testCheckIfEndOfGame() throws TooManyPlayersException {
        Match match = new Match();
        match.newPlayer(new Player("odyn"));
        match.newPlayer(new Player("abu"));
        match.newPlayer(new Player("jojas"));

        boolean p = match.checkIfEndOfGame();

    }


    public void testGetPlayers() {
        Match match = new Match();
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player("one"));
        playerList.add(new Player("two"));
        match.setPlayers(playerList);
        List<Player> playerList1 = match.getPlayers();
    }

    public void testSetPlayers() {
        Match match = new Match();
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player("one"));
        playerList.add(new Player("two"));
        match.setPlayers(playerList);
    }

    public void testGetWinner() {
        Match match = new Match();
        Player p = new Player("winner");
        match.setWinner(p);
        Player p2 = match.getWinner();
    }

    public void testSetWinner() {
        Match match = new Match();
        Player p = new Player("winner");
        match.setWinner(p);
    }

    public void testGetGameDeck() {
        Deck deck = new Deck();
        deck.generateDeck();
        Match match = new Match();
        match.setGameDeck(deck);
        Deck d = match.getGameDeck();
    }

    public void testSetGameDeck() {
        Deck deck = new Deck();
        deck.generateDeck();
        Match match = new Match();
        match.setGameDeck(deck);
    }

    public void testGetCurrentPlayer() {
        Match match = new Match();
        Player p1 = new Player("Bart");
        match.setCurrentPlayer(p1);
        Player p2 = match.getCurrentPlayer();
    }

    public void testSetCurrentPlayer() {
        Match match = new Match();
        Player p1 = new Player("Bart");
        match.setCurrentPlayer(p1);
    }

    public void testIsGameStarted() {
        Match match = new Match();
        match.setGameStarted(false);
        boolean is = match.isGameStarted();
    }

    public void testSetGameStarted() {
        Match match = new Match();
        match.setGameStarted(false);

    }

    public void testGetPot() {
        Match match = new Match();
        match.setPot(12);
        Integer pot = match.getPot();
    }

    public void testSetPot() {
        Match match = new Match();
        match.setPot(12);
    }

    public void testIsBetWasMade() {
        Match match = new Match();
        match.setBetWasMade(false);
        boolean bet = match.isBetWasMade();
    }

    public void testSetBetWasMade() {
        Match match = new Match();
        match.setBetWasMade(false);
    }

    public void testGetCurrentBet() {
        Match match = new Match();
        match.setCurrentBet(12);
        Integer bet = match.getCurrentBet();
    }

    public void testSetCurrentBet() {
        Match match = new Match();
        match.setCurrentBet(12);
    }
}