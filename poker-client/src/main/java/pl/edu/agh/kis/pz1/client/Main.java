package pl.edu.agh.kis.pz1.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import static pl.edu.agh.kis.pz1.common.GameSettings.*;

/**
 * Main client side
 * Connects to a server via port and host located in utility module / GameSettings
 * @author Bartosz Kruczek
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {

        try {
            Socket socket = new Socket(HOST, PORT);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            logger.info("Enter your name:");
            Scanner scan = new Scanner(System.in);
            String name = scan.nextLine();

            outputStream.writeUTF(name);
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            while (true) {
                //showing cards
                logger.info("SERVER: " + inputStream.readUTF());
                logger.info("SERVER: " + inputStream.readUTF());
                logger.info("SERVER: " + inputStream.readUTF());
                for (int i = 0; i < MAX_CARDS_ON_HAND; i++) {
                    logger.info("SERVER: " + (i + 1) + ": " + inputStream.readUTF());
                }

                //betting calling...
                while (true) {
                    logger.info("SERVER " + inputStream.readUTF());
                    logger.info("SERVER: " + inputStream.readUTF());
                    scan = new Scanner(System.in);
                    String action = scan.nextLine();
                    outputStream.writeUTF(action);
                    if (action.contains("9")) {
                        logger.info("Input the amount You'd like to raise the pot");
                        scan = new Scanner(System.in);
                        String raise = scan.nextLine();
                        outputStream.writeUTF(raise);

                    }
                    logger.info("SERVER: " + inputStream.readUTF());

                    String isBettingEnded = inputStream.readUTF();
                    if (!isBettingEnded.equals("")) {
                        break;
                    }
                }

                //swapping cards
                logger.info("Enter how many cards you wish to swap: ");
                scan = new Scanner(System.in);
                String howMany = scan.nextLine();

                StringBuilder stringBuilder = new StringBuilder();
                int howManyNumber = Integer.parseInt(howMany);
                for (int i = 0; i < howManyNumber; i++) {
                    logger.info("Enter index of a card: ");
                    scan = new Scanner(System.in);
                    stringBuilder.append(scan.nextLine().charAt(0));
                }
                String indexesToSwap = stringBuilder.toString();
                outputStream.writeUTF(indexesToSwap);
                //showing swapped cards
                logger.info("SERVER: " + inputStream.readUTF());
                logger.info("SERVER: " + inputStream.readUTF());
                for (int i = 0; i < MAX_CARDS_ON_HAND; i++) {
                    logger.info("SERVER: " + (i + 1) + ": " + inputStream.readUTF());
                }

                //betting calling...
                while (true) {
                    logger.info("SERVER: " + inputStream.readUTF());
                    logger.info("SERVER: " + inputStream.readUTF());
                    scan = new Scanner(System.in);
                    String action = scan.nextLine();
                    outputStream.writeUTF(action);
                    if (action.contains("9")) {
                        logger.info("Input the amount You'd like to raise the pot");
                        scan = new Scanner(System.in);
                        String raise = scan.nextLine();
                        outputStream.writeUTF(raise);

                    }
                    logger.info("SERVER: " + inputStream.readUTF());

                    String isBettingEnded = inputStream.readUTF();
                    if (!isBettingEnded.equals("")) {
                        break;
                    }
                }

                logger.info("---------Time to see the winner of this round, and the winner is: " + inputStream.readUTF() + "---------");


                String key = inputStream.readUTF();
                if (!key.equals("")) {
                    break;
                }

            }
            logger.info("SERVER: " + inputStream.readUTF());

        } catch (Exception e) {

        }

    }
}
