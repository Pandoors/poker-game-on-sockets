package pl.edu.agh.kis.pz1.model.definition;

import pl.edu.agh.kis.pz1.common.GameSettings;
import pl.edu.agh.kis.pz1.model.enums.Rank;
import pl.edu.agh.kis.pz1.model.enums.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pl.edu.agh.kis.pz1.common.GameSettings.MAX_CARDS_ON_HAND;

/**
 * card's deck implementation
 *
 * @author Bartosz Kruczek
 */
public class Deck {
    private List<Card> cards;

    //region constructors


    /**
     * constructor which builds a deck from scratch
     */
    public Deck() {
        cards = new ArrayList<>();
        this.generateDeck();
    }
    //endregion

    //region helper methods

    /**
     * if deck contains any cards, they are being shuffled
     */
    public void shuffle() {
        if (this.cards != null && !this.cards.isEmpty()) {
            Collections.shuffle(this.cards);
        }
    }

    /**
     * fills a deck with cards in sorted order.
     * if a deck contains any cards it is being built from a scratch
     */
    public void generateDeck() {
        if (!this.cards.isEmpty()) {
            this.cards.clear();
        }
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                this.cards.add(new Card(rank, suit));
            }
        }
    }

    public void fillPlayerHand(Player p) {
        List<Card> cardsToGive = new ArrayList<>();
        int numberOfCardsToGive = MAX_CARDS_ON_HAND - p.playerHandSize();
        if (numberOfCardsToGive == 0) {
            return;
        }
        if (cards.size() - numberOfCardsToGive < 0) {
            cardsToGive.addAll(this.cards);
            numberOfCardsToGive = numberOfCardsToGive - cards.size();
            this.generateDeck();
            this.shuffle();
            cardsToGive.addAll(cards.subList(0, numberOfCardsToGive));
            cards.subList(0, numberOfCardsToGive).clear();
        } else if (cards.size() - numberOfCardsToGive == 0) {
            this.generateDeck();
            this.shuffle();
            cardsToGive.addAll(cards.subList(0, numberOfCardsToGive));
            cards.subList(0, numberOfCardsToGive).clear();
        } else {
            cardsToGive.addAll(cards.subList(0, numberOfCardsToGive));
            cards.subList(0, numberOfCardsToGive).clear();
        }
        p.addCardsToHand(cardsToGive);
    }

    //endregion


    //region getters setters
    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
    //endregion

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Card c : this.cards) {
            stringBuilder.append(c.toString());
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

}


