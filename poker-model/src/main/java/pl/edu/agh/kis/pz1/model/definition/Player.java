package pl.edu.agh.kis.pz1.model.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static pl.edu.agh.kis.pz1.common.GameSettings.STARTING_CHIP_VALUE;

/**
 * Class representing a Poker player and his cards in hand
 * <p>
 * * @author Bartosz Kruczek
 */
public class Player {
    private static final Logger logger = Logger.getLogger(Player.class.getName());
    private List<Card> cardsInHand;
    private Integer chipsAccount;
    private String name;
    private boolean folded;

    public Player(String name) {
        cardsInHand = new ArrayList<>();
        this.chipsAccount = STARTING_CHIP_VALUE;
        this.name = name;
    }


    /**
     * prints a player's cards in hand
     */
    public void showHand() {
        cardsInHand.forEach(c -> logger.info(c.toString()));
    }

    public int playerHandSize() {
        return this.cardsInHand.size();
    }

    public void addCardsToHand(List<Card> cardsToAll) {
        this.cardsInHand.addAll(cardsToAll);
    }

    public void addBalance(Integer chips){
        this.chipsAccount+=chips;
    }

    //region getters setters


    public Integer getChipsAccount() {
        return chipsAccount;
    }

    public void setChipsAccount(Integer chipsAccount) {
        this.chipsAccount = chipsAccount;
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public void setCardsInHand(List<Card> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public boolean isFolded() {
        return folded;
    }

    public void setFolded(boolean folded) {
        this.folded = folded;
    }
    //endregion
}
