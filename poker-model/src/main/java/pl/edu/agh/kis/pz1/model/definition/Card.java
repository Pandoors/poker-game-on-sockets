package pl.edu.agh.kis.pz1.model.definition;

import pl.edu.agh.kis.pz1.model.enums.Rank;
import pl.edu.agh.kis.pz1.model.enums.Suit;

import java.util.Objects;


/**
 * Card class implementation. It contains card's rank and suit
 * @author Bartosz Kruczek
 */
public class Card {
    private Rank rank;
    private Suit suit;

    /**
     * Card constructor
     * @param rank new card's rank
     * @param suit new card's suit
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    //region getters setters
    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }


    public void setSuit(Suit suit) {
        this.suit = suit;
    }
    //endregion


    @Override
    public String toString() {
        return rank + " " + suit;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

}
