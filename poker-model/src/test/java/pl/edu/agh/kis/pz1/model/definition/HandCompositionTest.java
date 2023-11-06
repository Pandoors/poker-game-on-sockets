package pl.edu.agh.kis.pz1.model.definition;

import junit.framework.TestCase;
import pl.edu.agh.kis.pz1.model.Match;
import pl.edu.agh.kis.pz1.model.exceptions.LackOfPlayersException;
import pl.edu.agh.kis.pz1.model.exceptions.TooManyPlayersException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandCompositionTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testRoyalFlush() throws TooManyPlayersException, LackOfPlayersException {
        Match match = new Match();
        Player p1 = new Player("Tom");
        Player p2 = new Player("Andrzej");
        match.newPlayer(p1);
        match.newPlayer(p2);
        match.newMatch();

        List<Player> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        HandComposition.royalFlush(list);
        assertNotNull(HandComposition.royalFlush(list));
    }

    public void testStreightFlush() throws TooManyPlayersException, LackOfPlayersException {
        Match match = new Match();
        Player p1 = new Player("Tom");
        Player p2 = new Player("Andrzej");
        match.newPlayer(p1);
        match.newPlayer(p2);
        match.newMatch();

        List<Player> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        HandComposition.streightFlush(list);
        assertNotNull(HandComposition.streightFlush(list));
    }

    public void testFourOfAKind() throws LackOfPlayersException, TooManyPlayersException {
        Match match = new Match();
        Player p1 = new Player("Tom");
        Player p2 = new Player("Andrzej");
        match.newPlayer(p1);
        match.newPlayer(p2);
        match.newMatch();

        List<Player> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        HandComposition.fourOfAKind(list);
        assertNotNull(HandComposition.fourOfAKind(list));
    }

    public void testFullHouse() throws LackOfPlayersException, TooManyPlayersException {
        Match match = new Match();
        Player p1 = new Player("Tom");
        Player p2 = new Player("Andrzej");
        match.newPlayer(p1);
        match.newPlayer(p2);
        match.newMatch();

        List<Player> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        HandComposition.fullHouse(list);
        assertNotNull(HandComposition.fullHouse(list));
    }

    public void testFlush() throws TooManyPlayersException, LackOfPlayersException {
        Match match = new Match();
        Player p1 = new Player("Tom");
        Player p2 = new Player("Andrzej");
        match.newPlayer(p1);
        match.newPlayer(p2);
        match.newMatch();

        List<Player> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        HandComposition.flush(list);
        assertNotNull(HandComposition.flush(list));
    }

    public void testStreight() throws TooManyPlayersException, LackOfPlayersException {
        Match match = new Match();
        Player p1 = new Player("Tom");
        Player p2 = new Player("Andrzej");
        match.newPlayer(p1);
        match.newPlayer(p2);
        match.newMatch();

        List<Player> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        HandComposition.streight(list);
        assertNotNull(HandComposition.streight(list));
    }

    public void testThreeOfKind() throws TooManyPlayersException, LackOfPlayersException {
        Match match = new Match();
        Player p1 = new Player("Tom");
        Player p2 = new Player("Andrzej");
        match.newPlayer(p1);
        match.newPlayer(p2);
        match.newMatch();

        List<Player> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        HandComposition.threeOfKind(list);
        assertNotNull(HandComposition.threeOfKind(list));
    }

    public void testTwoPairs() throws TooManyPlayersException, LackOfPlayersException {
        Match match = new Match();
        Player p1 = new Player("Tom");
        Player p2 = new Player("Andrzej");
        match.newPlayer(p1);
        match.newPlayer(p2);
        match.newMatch();

        List<Player> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        HandComposition.twoPairs(list);
        assertNotNull(HandComposition.twoPairs(list));
    }

    public void testOnePair() throws TooManyPlayersException, LackOfPlayersException {
        Match match = new Match();
        Player p1 = new Player("Tom");
        Player p2 = new Player("Andrzej");
        match.newPlayer(p1);
        match.newPlayer(p2);
        match.newMatch();

        List<Player> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        HandComposition.onePair(list);
        assertNotNull(HandComposition.onePair(list));

    }

    public void testHighestCard() throws LackOfPlayersException, TooManyPlayersException {
        Match match = new Match();
        Player p1 = new Player("Tom");
        Player p2 = new Player("Tomek");
        match.newPlayer(p1);
        match.newPlayer(p2);
        match.newMatch();

        HandComposition.highestCard(p1);
        assertNotNull(HandComposition.highestCard(p1));

    }

    public void testTestHighestCard() throws TooManyPlayersException, LackOfPlayersException {
        Match match = new Match();
        Player p1 = new Player("Tom");
        Player p2 = new Player("Andrzej");
        match.newPlayer(p1);
        match.newPlayer(p2);
        match.newMatch();

        List<Player> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        HandComposition.highestCard(list);
        assertNotNull(HandComposition.highestCard(list));

    }

    public void testHandleManyWinners() {
        Map<Player, Integer> playerIntegerMap = new HashMap<>();
        Player player = new Player("Bartosz");
        Player player2 = new Player("Tomasz");
        Player player3 = new Player("Greta");

        playerIntegerMap.put(player, 8);
        playerIntegerMap.put(player2, 8);
        playerIntegerMap.put(player3, 9);

        HandComposition.handleManyWinners(playerIntegerMap);
        assertNotNull(HandComposition.handleManyWinners(playerIntegerMap));
    }
}