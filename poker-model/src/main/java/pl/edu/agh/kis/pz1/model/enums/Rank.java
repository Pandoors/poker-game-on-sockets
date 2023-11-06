package pl.edu.agh.kis.pz1.model.enums;

import pl.edu.agh.kis.pz1.model.definition.Card;

public enum Rank {

    ACE(400),
    KING(300),
    QUEEN(200),
    JACK(100),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10);

    private final Integer order;

    private Rank(Integer order){
        this.order = order;
    }

    public Integer getOrder() {
        return order;
    }


}
