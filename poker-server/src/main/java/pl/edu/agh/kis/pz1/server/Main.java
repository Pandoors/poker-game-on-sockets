package pl.edu.agh.kis.pz1.server;

import pl.edu.agh.kis.pz1.model.Match;
import pl.edu.agh.kis.pz1.model.definition.Card;
import pl.edu.agh.kis.pz1.model.definition.Player;
import pl.edu.agh.kis.pz1.model.exceptions.LackOfPlayersException;
import pl.edu.agh.kis.pz1.model.exceptions.TooManyPlayersException;
import pl.edu.agh.kis.pz1.model.exceptions.UnproperCardsToExchangeException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static pl.edu.agh.kis.pz1.common.GameSettings.*;

/**
 * Main server class
 * playerSocketMap - map consists players and sockets.
 * usage of streams
 *
 * @author Bartosz Kruczek
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static int playerCount = 0;
    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;
    private static Map<Player, Socket> playerSocketMap = new HashMap<>();

    public static void main(String[] args) {
        DataInputStream inputStream;
        DataOutputStream outputStream;
        try {

            ServerSocket serverSocket = new ServerSocket(PORT);
            logger.info("Server started...");
            logger.info("Waiting for players to join...");
            List<Socket> socketList = new ArrayList<>();
            Match match = new Match();
            while (true) {
                if (playerCount <= MAX_PLAYERS_NUMBER) {
                    Socket clientSocket = serverSocket.accept();
                    playerCount++;
                    logger.info("Current number of connected Players: " + playerCount);
                    socketList.add(clientSocket);
                }
                if (playerCount <= MAX_PLAYERS_NUMBER && playerCount >= 2) {
                    logger.info("Already enough players to start the game.");
                    logger.info("Type in \"startMatch\" if You are satisfied with the number of players, or \"continue\" if You're not");
                    Scanner scan = new Scanner(System.in);
                    String serverInput = scan.nextLine();
                    if (serverInput.contains(START_MATCH)) {
                        logger.info("Match instance has been created... Now it's time to set up some necessary things!");
                        break;
                    }
                }
            }
            for (int numb = 0; numb < playerCount; numb++) {
                try {
                    inputStream = new DataInputStream(socketList.get(numb).getInputStream());
                    String nickName = inputStream.readUTF();
                    Player player = new Player(nickName);
                    match.newPlayer(player);
                    playerSocketMap.put(player, socketList.get(numb));
                    logger.info("added new player with name: " + match.getPlayers().get(match.getPlayers().size() - 1).getName());
                } catch (IOException | TooManyPlayersException e) {
                    e.printStackTrace();
                }
            }
            try {
                match.newMatch();
                logger.info("Starting the game! Time to prepare the deck of cards and give everyone cards");
            } catch (LackOfPlayersException e) {
                e.printStackTrace();
            }

            //beginning the looping game sequences
            while (match.getWinner() == null) {
                fullRound(match);
                match.setPot(0);

                if (match.getWinner() == null) {
                    playerSocketMap.values().forEach(v ->
                    {
                        try {
                            dataOutputStream = new DataOutputStream(v.getOutputStream());
                            dataOutputStream.writeUTF("");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    playerSocketMap.values().forEach(v ->
                    {
                        try {
                            dataOutputStream = new DataOutputStream(v.getOutputStream());
                            dataOutputStream.writeUTF("end");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

            }

            logger.info("Winner of the game is: " + match.getWinner().getName());
            playerSocketMap.values().forEach(v ->
            {
                try {
                    dataOutputStream = new DataOutputStream(v.getOutputStream());
                    dataOutputStream.writeUTF("Winner of the game is: " + match.getWinner().getName());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void fullRound(Match match) {
        playerSocketMap.forEach((key, value) -> {
            try {
                key.setChipsAccount(key.getChipsAccount() - ANTE);
                dataOutputStream = new DataOutputStream(value.getOutputStream());
                dataOutputStream.writeUTF("You have given an ante. Your current balance is: " + key.getChipsAccount());
                dataOutputStream.writeUTF("Match has begun and the cards are given to You. Decide if you'll like to change up to 4 of them");
                dataOutputStream.writeUTF("---------------YOUR CARDS---------------");
                key.getCardsInHand().forEach(c ->
                        {
                            try {
                                dataOutputStream.writeUTF(c.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        while (true) {
            playerSocketMap.forEach((key, value) -> {
                try {

                    key.setChipsAccount(key.getChipsAccount() - ANTE);


                    dataOutputStream = new DataOutputStream(value.getOutputStream());
                    dataOutputStream.writeUTF("Time to bet fold or check");

                    if (!match.isBetWasMade()) {
                        dataOutputStream.writeUTF("You can bet(1) fold(2) or check(3). Pick one and write the number of action You have chosen which is given in brackets");
                        dataInputStream = new DataInputStream(value.getInputStream());
                        String action = dataInputStream.readUTF();
                        int actionNumber = action.charAt(0) - 48;

                        switch (actionNumber) {
                            case 1:
                                if (!key.isFolded()) {
                                    match.setBetWasMade(Boolean.TRUE);
                                    key.setChipsAccount(key.getChipsAccount() - match.getCurrentBet());
                                    match.setPot(match.getPot() + match.getCurrentBet());
                                    dataOutputStream.writeUTF("Succesfull bet. Your balance is: " + key.getChipsAccount());
                                } else {
                                    dataOutputStream.writeUTF("cant bet because You folded");
                                }
                                break;
                            case 2:
                                key.setFolded(true);
                                dataOutputStream.writeUTF("Succesfull fold. Your balance is: " + key.getChipsAccount());
                                break;
                            case 3:
                                break;
                            default:
                                break;
                        }

                    } else {
                        dataOutputStream.writeUTF("You can call(1) raise(9) fold(3). Pick one and write the number of action You have chosen which is given in brackets");
                        dataInputStream = new DataInputStream(value.getInputStream());
                        String action = dataInputStream.readUTF();
                        int actionNumber = action.charAt(0) - 48;

                        switch (actionNumber) {
                            case 1:
                                if (!key.isFolded()) {
                                    match.setBetWasMade(Boolean.TRUE);
                                    key.setChipsAccount(key.getChipsAccount() - match.getCurrentBet());
                                    match.setPot(match.getPot() + match.getCurrentBet());
                                    dataOutputStream.writeUTF("Succesfull call. Your balance is: " + key.getChipsAccount());
                                } else {
                                    dataOutputStream.writeUTF("cant call because You folded");
                                }
                                break;
                            case 9:
                                if (!key.isFolded()) {
                                    String raise = dataInputStream.readUTF();
                                    Integer raiseNumber = Integer.valueOf(raise);
                                    match.setCurrentBet(match.getCurrentBet() + raiseNumber);
                                    key.setChipsAccount(key.getChipsAccount() - match.getCurrentBet());
                                    match.setPot(match.getPot() + match.getCurrentBet());
                                    dataOutputStream.writeUTF("Succesfull raise. Your balance is: " + key.getChipsAccount());
                                } else {
                                    dataOutputStream.writeUTF("cant raise because You folded");
                                }
                                break;
                            case 3:
                                key.setFolded(true);
                                dataOutputStream.writeUTF("Succesfull fold. Your balance is: " + key.getChipsAccount());
                            default:
                                break;
                        }

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            logger.info("Current pot is: " + match.getPot());


            if (match.isBettingEnded()) {
                match.clearAfterBet();
                playerSocketMap.values().forEach(v ->
                {
                    try {
                        dataOutputStream = new DataOutputStream(v.getOutputStream());
                        dataOutputStream.writeUTF("stop");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            } else {
                playerSocketMap.values().forEach(v ->
                {
                    try {
                        dataOutputStream = new DataOutputStream(v.getOutputStream());
                        dataOutputStream.writeUTF("");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        //read cards to swap and swap them
        playerSocketMap.forEach((key, value) -> {
            try {
                dataInputStream = new DataInputStream(value.getInputStream());
                String cardsToSwap = dataInputStream.readUTF();
                List<Card> cardsToSwapList = new ArrayList<>();
                for (int i = 0; i < cardsToSwap.length(); i++) {
                    int index = cardsToSwap.charAt(i) - 48;
                    index -= 1;
                    cardsToSwapList.add(key.getCardsInHand().get(index));
                }
                try {
                    match.exchangePlayerCards(key, cardsToSwapList);
                } catch (UnproperCardsToExchangeException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        });

        playerSocketMap.forEach((key, value) -> {
            try {
                dataOutputStream = new DataOutputStream(value.getOutputStream());
                dataOutputStream.writeUTF("Printing your new post cards swap hand");
                dataOutputStream.writeUTF("---------------YOUR CARDS---------------");
                key.getCardsInHand().forEach(c ->
                        {
                            try {
                                dataOutputStream.writeUTF(c.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        while (true) {
            playerSocketMap.forEach((key, value) -> {
                try {

                    key.setChipsAccount(key.getChipsAccount() - ANTE);


                    dataOutputStream = new DataOutputStream(value.getOutputStream());
                    dataOutputStream.writeUTF("Time to bet fold or check");

                    if (!match.isBetWasMade()) {
                        dataOutputStream.writeUTF("You can bet(1) fold(2) or check(3). Pick one and write the number of action You have chosen which is given in brackets");
                        dataInputStream = new DataInputStream(value.getInputStream());
                        String action = dataInputStream.readUTF();
                        int actionNumber = action.charAt(0) - 48;

                        switch (actionNumber) {
                            case 1:
                                if (!key.isFolded()) {
                                    match.setBetWasMade(Boolean.TRUE);
                                    key.setChipsAccount(key.getChipsAccount() - match.getCurrentBet());
                                    match.setPot(match.getPot() + match.getCurrentBet());
                                    dataOutputStream.writeUTF("Succesfull bet. Your balance is: " + key.getChipsAccount());
                                } else {
                                    dataOutputStream.writeUTF("cant bet because You folded");
                                }
                                break;
                            case 2:
                                key.setFolded(true);
                                dataOutputStream.writeUTF("Succesfull fold. Your balance is: " + key.getChipsAccount());
                                break;
                            case 3:
                                break;
                            default:
                                break;
                        }

                    } else {
                        dataOutputStream.writeUTF("You can call(1) raise(9) fold(3). Pick one and write the number of action You have chosen which is given in brackets");
                        dataInputStream = new DataInputStream(value.getInputStream());
                        String action = dataInputStream.readUTF();
                        int actionNumber = action.charAt(0) - 48;

                        switch (actionNumber) {
                            case 1:
                                if (!key.isFolded()) {
                                    match.setBetWasMade(Boolean.TRUE);
                                    key.setChipsAccount(key.getChipsAccount() - match.getCurrentBet());
                                    match.setPot(match.getPot() + match.getCurrentBet());
                                    dataOutputStream.writeUTF("Succesfull call. Your balance is: " + key.getChipsAccount());
                                } else {
                                    dataOutputStream.writeUTF("cant call because You folded");
                                }
                                break;
                            case 9:
                                if (!key.isFolded()) {
                                    String raise = dataInputStream.readUTF();
                                    Integer raiseNumber = Integer.valueOf(raise);
                                    match.setCurrentBet(match.getCurrentBet() + raiseNumber);
                                    key.setChipsAccount(key.getChipsAccount() - match.getCurrentBet());
                                    match.setPot(match.getPot() + match.getCurrentBet());
                                    dataOutputStream.writeUTF("Succesfull raise. Your balance is: " + key.getChipsAccount());
                                } else {
                                    dataOutputStream.writeUTF("cant raise because You folded");
                                }
                                break;
                            case 3:
                                key.setFolded(true);
                                dataOutputStream.writeUTF("Succesfull fold. Your balance is: " + key.getChipsAccount());
                            default:
                                break;
                        }

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            logger.info("Current pot is: " + match.getPot());


            if (match.isBettingEnded()) {
                match.clearAfterBet();
                playerSocketMap.values().forEach(v ->
                {
                    try {
                        dataOutputStream = new DataOutputStream(v.getOutputStream());
                        dataOutputStream.writeUTF("stop");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            } else {
                playerSocketMap.values().forEach(v ->
                {
                    try {
                        dataOutputStream = new DataOutputStream(v.getOutputStream());
                        dataOutputStream.writeUTF("");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        String winners = match.winnersToString(match.determineRoundWinner(match.getPot()));
        playerSocketMap.values().forEach(v ->
        {
            try {
                dataOutputStream = new DataOutputStream(v.getOutputStream());
                dataOutputStream.writeUTF(winners);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }
}
