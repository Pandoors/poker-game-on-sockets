package pl.edu.agh.kis.pz1.model.definition;

import pl.edu.agh.kis.pz1.model.enums.Rank;

import java.util.*;
import java.util.stream.Collectors;
/**
 * Class representing all possible hand compositions and checking who has the strongest hand.
 * @author Bartosz Kruczek
 */
public class HandComposition {
    private static final List<Rank> royalFlushRanks = Arrays.asList(Rank.ACE, Rank.KING, Rank.QUEEN, Rank.JACK);


    public static List<Player> royalFlush(List<Player> players) {
        List<Player> matchedPlayers = new ArrayList<>();
        boolean matchingPattern;
        for (Player player : players) {
            List<Card> playerHand = player.getCardsInHand();
            matchingPattern = playerHand.stream().allMatch(c -> c.getSuit().equals(playerHand.get(0).getSuit()));
            if (!matchingPattern) {
                continue;
            }

            List<Rank> ranks = playerHand.stream().map(Card::getRank).collect(Collectors.toList());
            matchingPattern = ranks.containsAll(royalFlushRanks);
            if (matchingPattern) {
                matchedPlayers.add(player);
            }
        }
        return matchedPlayers;
    }

    public static List<Player> streightFlush(List<Player> players) {
        Map<Player, Integer> highestPlayerCards = new HashMap<>();
        boolean matchingPattern;
        for (Player player : players) {
            List<Card> playerHand = player.getCardsInHand();
            matchingPattern = playerHand.stream().allMatch(c -> c.getSuit().equals(playerHand.get(0).getSuit()));
            if (!matchingPattern) {
                continue;
            }

            List<Rank> ranks = playerHand.stream().map(Card::getRank).collect(Collectors.toList());

            ranks = ranks.stream().sorted(Comparator.comparing((Rank::getOrder)).reversed()).collect(Collectors.toList());
            matchingPattern = true;
            for (int i = 1; i < ranks.size(); i++) {
                if (ranks.get(i - 1) != ranks.get(i)) {
                    matchingPattern = false;
                    break;
                }
            }
            if (matchingPattern) {
                highestPlayerCards.put(player, ranks.get(0).getOrder());
            }
        }
        return handleManyWinners(highestPlayerCards);

    }


    public static List<Player> fourOfAKind(List<Player> players) {
        boolean matchingPattern;
        Map<Player, Integer> highestFifthPlayersCard = new HashMap<>();
        for (Player player : players) {
            List<Card> playerHand = player.getCardsInHand();

            List<Rank> ranks = playerHand.stream()
                    .map(Card::getRank)
                    .sorted(Comparator.comparing((Rank::getOrder)).reversed())
                    .collect(Collectors.toList());

            matchingPattern = ranks.subList(0, ranks.size() - 1).stream().allMatch(r -> r.equals(ranks.get(0))) ||
                    ranks.subList(1, ranks.size()).stream().allMatch(r -> r.equals(ranks.get(1)));
            if (matchingPattern) {
                if (ranks.get(0) != ranks.get(1)) {
                    highestFifthPlayersCard.put(player, ranks.get(0).getOrder());
                } else if (ranks.get(ranks.size() - 1) != ranks.get(ranks.size() - 2)) {
                    highestFifthPlayersCard.put(player, ranks.get(ranks.size() - 1).getOrder());
                }
            }
        }

        return handleManyWinners(highestFifthPlayersCard);

    }

    public static List<Player> fullHouse(List<Player> players) {
        boolean matchingPattern;
        Map<Player, Integer> highestCard = new HashMap<>();
        for (Player player : players) {
            List<Card> playerHand = player.getCardsInHand();
            List<Rank> ranks = playerHand.stream()
                    .map(Card::getRank)
                    .sorted(Comparator.comparing((Rank::getOrder)).reversed())
                    .collect(Collectors.toList());

            matchingPattern = ranks.subList(0, 3).stream().allMatch(r -> r.equals(ranks.get(0))) ||
                    ranks.subList(3, ranks.size()).stream().allMatch(r -> r.equals(ranks.get(1)));

            if (matchingPattern) {
                if (ranks.get(2) != ranks.get(3)) {
                    highestCard.put(player, ranks.get(3).getOrder());
                } else if (ranks.get(1) != ranks.get(2)) {
                    highestCard.put(player, ranks.get(2).getOrder());
                }
            }

        }
        return handleManyWinners(highestCard);

    }

    public static List<Player> flush(List<Player> players) {
        boolean matchingPattern;
        Map<Player, Integer> highestCard = new HashMap<>();
        for (Player player : players) {
            List<Card> playerHand = player.getCardsInHand();
            matchingPattern = playerHand.stream().allMatch(c -> c.getSuit().equals(playerHand.get(0).getSuit()));
            if (matchingPattern) {
                highestCard.put(player, highestCard(player).getRank().getOrder());
            }
        }
        return handleManyWinners(highestCard);

    }

    public static List<Player> streight(List<Player> players) {
        Map<Player, Integer> highestPlayerCards = new HashMap<>();
        boolean matchingPattern;
        for (Player player : players) {
            List<Card> playerHand = player.getCardsInHand();
            List<Rank> ranks = playerHand.stream().map(Card::getRank).collect(Collectors.toList());

            ranks = ranks.stream().sorted(Comparator.comparing((Rank::getOrder)).reversed()).collect(Collectors.toList());
            matchingPattern = true;
            for (int i = 1; i < ranks.size(); i++) {
                if (ranks.get(i - 1) != ranks.get(i)) {
                    matchingPattern = false;
                    break;
                }
            }
            if (matchingPattern) {
                highestPlayerCards.put(player, ranks.get(0).getOrder());
            }
        }

        return handleManyWinners(highestPlayerCards);

    }

    public static List<Player> threeOfKind(List<Player> players) {
        Map<Player, Integer> highestCard = new HashMap<>();
        for (Player player : players) {
            List<Card> playerHand = player.getCardsInHand();
            List<Rank> ranks = playerHand.stream()
                    .map(Card::getRank)
                    .sorted(Comparator.comparing((Rank::getOrder)).reversed())
                    .collect(Collectors.toList());


            if (ranks.subList(0, 3).stream().allMatch(r -> r.equals(ranks.get(0)))) {
                highestCard.put(player, ranks.get(0).getOrder());
            } else if (ranks.subList(1, 4).stream().allMatch(r -> r.equals(ranks.get(1)))) {
                highestCard.put(player, ranks.get(1).getOrder());
            } else if (ranks.subList(2, 5).stream().allMatch(r -> r.equals(ranks.get(2)))) {
                highestCard.put(player, ranks.get(2).getOrder());
            }


        }
        return handleManyWinners(highestCard);

    }


    public static List<Player> twoPairs(List<Player> players) {
        Map<Player, Integer> highestCard = new HashMap<>();
        for (Player player : players) {
            List<Card> playerHand = player.getCardsInHand();
            List<Rank> ranks = playerHand.stream()
                    .map(Card::getRank)
                    .sorted(Comparator.comparing((Rank::getOrder)).reversed())
                    .distinct().collect(Collectors.toList());

            if (ranks.size() == 3) {
                highestCard.put(player, ranks.get(0).getOrder());
            }


        }
        return handleManyWinners(highestCard);

    }

    public static List<Player> onePair(List<Player> players) {
        Map<Player, Integer> highestCard = new HashMap<>();
        for (Player player : players) {
            List<Card> playerHand = player.getCardsInHand();
            List<Rank> ranks = playerHand.stream()
                    .map(Card::getRank)
                    .sorted(Comparator.comparing((Rank::getOrder)).reversed())
                    .distinct().collect(Collectors.toList());

            if (ranks.size() == 4) {
                highestCard.put(player, ranks.get(0).getOrder());
            }


        }
        return handleManyWinners(highestCard);

    }

    public static List<Player> highestCard(List<Player> players) {
        Map<Player, Integer> highestCard = new HashMap<>();
        for (Player player : players) {
            highestCard.put(player, highestCard(player).getRank().getOrder());
        }
        return handleManyWinners(highestCard);
    }


    public static Card highestCard(Player player) {
        List<Card> playerHand = player.getCardsInHand();
        return Collections.max(playerHand, Comparator.comparing(c -> c.getRank().getOrder()));
    }

    public static List<Player> handleManyWinners(Map<Player, Integer> highestCard) {
        List<Player> matchedPlayers = new ArrayList<>();

        if (highestCard.size() > 1) {
            int max = Collections.max(highestCard.values());
            matchedPlayers = highestCard.entrySet().stream()
                    .filter(e -> e.getValue() == max)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }

        return matchedPlayers;
    }


}
