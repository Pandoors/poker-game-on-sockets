package pl.edu.agh.kis.pz1.model;

import pl.edu.agh.kis.pz1.model.definition.Card;
import pl.edu.agh.kis.pz1.model.definition.Deck;
import pl.edu.agh.kis.pz1.model.definition.HandComposition;
import pl.edu.agh.kis.pz1.model.definition.Player;
import pl.edu.agh.kis.pz1.model.exceptions.LackOfPlayersException;
import pl.edu.agh.kis.pz1.model.exceptions.TooManyPlayersException;
import pl.edu.agh.kis.pz1.model.exceptions.UnproperCardsToExchangeException;

import java.util.*;
import java.util.logging.Logger;

import static pl.edu.agh.kis.pz1.common.GameSettings.*;
/**
 * Match class implementation. Consists of players, winning player, pot and everything that is crutial to match performance
 * @author Bartosz Kruczek
 */
public class Match {

    private static final Logger logger = Logger.getLogger(Match.class.getName());

    private List<Player> players;
    private Player winner = null;
    private Deck gameDeck;
    private Player currentPlayer;
    private boolean gameStarted = false;
    private Integer pot;
    private Integer currentBet;
    private boolean betWasMade = false;

    public Match() {
        players = new ArrayList<>();
        pot = 0;
    }


    //region helper match methods
    public void newPlayer(Player p) throws TooManyPlayersException {
        if (players.size() < MAX_PLAYERS_NUMBER) {
            players.add(p);
        } else {
            throw new TooManyPlayersException();
        }
    }

    public void newMatch() throws LackOfPlayersException {
        currentBet = ANTE;

        if (players == null || players.isEmpty() || players.size() > MAX_PLAYERS_NUMBER || players.size() < MIN_PLAYERS_NUMBER) {
            throw new LackOfPlayersException();
        } else {
            prepareNewMatch();

            logger.info("Starting new match");
        }
    }

    private void prepareNewMatch() {
        Deck deck = new Deck();
        deck.shuffle();
        this.gameDeck = deck;
        for (Player player : players) {
            deck.fillPlayerHand(player);
        }
        logger.info("Match is ready to begin");
        this.gameStarted = true;
    }

    public List<Player> determineRoundWinner(Integer pot) {
        List<Player> winners;

        winners = HandComposition.royalFlush(players);
        if (winners.size() > 0) {
            handleWinners(winners, pot);
            return winners;
        }

        winners = HandComposition.streightFlush(players);
        if (winners.size() > 0) {
            handleWinners(winners, pot);
            return winners;
        }
        winners = HandComposition.fourOfAKind(players);
        if (winners.size() > 0) {
            handleWinners(winners, pot);
            return winners;
        }
        winners = HandComposition.fullHouse(players);
        if (winners.size() > 0) {
            handleWinners(winners, pot);
            return winners;
        }
        winners = HandComposition.flush(players);
        if (winners.size() > 0) {
            handleWinners(winners, pot);
            return winners;
        }
        winners = HandComposition.streight(players);
        if (winners.size() > 0) {
            handleWinners(winners, pot);
            return winners;
        }
        winners = HandComposition.threeOfKind(players);
        if (winners.size() > 0) {
            handleWinners(winners, pot);
            return winners;
        }
        winners = HandComposition.twoPairs(players);
        if (winners.size() > 0) {
            handleWinners(winners, pot);
            return winners;
        }
        winners = HandComposition.onePair(players);
        if (winners.size() > 0) {
            handleWinners(winners, pot);
            return winners;
        }
        winners = HandComposition.highestCard(players);
        if (winners.size() > 0) {
            handleWinners(winners, pot);
            return winners;
        }

        //although this will never happen
        return Collections.emptyList();
    }

    public String winnersToString(List<Player> winners) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Player player : winners) {
            stringBuilder.append(player.getName()).append(" ");
        }
        return stringBuilder.toString();
    }

    //endregion

    //region rounds and phases helper methods

    public void exchangePlayerCards(Player p, List<Card> cardsToExchange) throws UnproperCardsToExchangeException {
        List<Card> cardsInHand = p.getCardsInHand();
        boolean isProperCards = cardsInHand.containsAll(cardsToExchange);
        if (cardsToExchange.size() <= MAX_CARDS_TO_EXCHANGE && isProperCards) {
            cardsInHand.removeAll(cardsToExchange);
            p.setCardsInHand(cardsInHand);
            gameDeck.fillPlayerHand(p);
        } else {
            throw new UnproperCardsToExchangeException();
        }
    }

    private void handleWinners(List<Player> players, Integer pot) {
        Integer toDevide = pot / players.size();
        for (Player player : players) {
            player.addBalance(toDevide);
            logger.info("the winner is " + player.getName());
        }

    }

    public boolean isBettingEnded() {
        int counter = 0;
        for (Player player : players) {
            if (player.isFolded()) {
                counter++;
            }
        }
        if (counter >= players.size() - 1) {
            return true;
        }
        return false;
    }

    public void clearAfterBet() {
        for (Player player : players) {
            player.setFolded(false);
        }
        this.currentBet = ANTE;
        this.betWasMade = false;

    }

    public boolean checkIfEndOfGame() {
        int counter = 0;
        Player wining = null;
        for (Player player : players) {
            if (player.getChipsAccount() > 0) {
                counter++;
                wining = player;
            }
        }
        if (counter == 1) {
            this.setWinner(wining);
            return true;
        }
        return false;
    }

    //endregion

    //region getters setters

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Deck getGameDeck() {
        return gameDeck;
    }

    public void setGameDeck(Deck gameDeck) {
        this.gameDeck = gameDeck;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public Integer getPot() {
        return pot;
    }

    public void setPot(Integer pot) {
        this.pot = pot;
    }

    public boolean isBetWasMade() {
        return betWasMade;
    }

    public void setBetWasMade(boolean betWasMade) {
        this.betWasMade = betWasMade;
    }

    public Integer getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(Integer currentBet) {
        this.currentBet = currentBet;
    }
    //endregion
}



