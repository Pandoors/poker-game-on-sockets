package pl.edu.agh.kis.pz1.common;


public final class GameSettings {

    private GameSettings() {

    }

    public static final int DECK_SIZE = 52;
    public static final int MAX_PLAYERS_NUMBER = 4;
    public static final int MIN_PLAYERS_NUMBER = 2;
    public static final int MAX_CARDS_ON_HAND = 5;
    public static final int MAX_CARDS_TO_EXCHANGE = 4;
    public static final Integer STARTING_CHIP_VALUE = 2000;
    public static int MATCH_SIZE_PLAYERS = 2;
    public static String START_MATCH = "startMatch";
    public static String CONTINUE = "continue";
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 4040;
    public static final int ANTE = 25;

}
