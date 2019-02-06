package whist;

import cards.Card;
import cards.Deck;
import cards.Hand;

import java.util.Arrays;
import java.util.Objects;

public class BasicStrategy implements Strategy {
    Trick trick;
    int id;
    Hand trumpCards = new Hand();
    Hand firstCards = new Hand();
    Hand neitherTrumpOrFirst = new Hand();

    public BasicStrategy(int id){
        this.id = id;
    }
    @Override
    public Card pickCard(Hand hand, Trick trick) {
        this.trick=trick;
        Card[] cardsFromTrick = trick.getCardsOfTrick();
        //Check if this player is first to play
        boolean playerFirstToPlay;
        boolean playerYetToPlay = true;
        boolean playerCurrentlyWinning = false;
        boolean highestTrumpCard = false;
        int partnerId = 0;

        //remove all cards from previous player's play
        trumpCards.getHandOfCards().clear();
        firstCards.getHandOfCards().clear();
        neitherTrumpOrFirst.getHandOfCards().clear();
        //check if current player is first to play
        playerFirstToPlay = Arrays.stream(cardsFromTrick).allMatch(Objects::isNull);

        //if first then play its highest card
        if(playerFirstToPlay){
            hand.sortByRank();
            return hand.getHandOfCards().get(hand.getHandOfCards().size()-1);
        }

        //check if trump or first card
        for(Card card: hand.getHandOfCards()){
            if(card.getSuit() == Trick.trumpSuit){
                firstCards.addSingleCard(card);
            }else if(card.getSuit() == trick.getSuitOfFirstCard()){
                trumpCards.addSingleCard(card);
            }else
                neitherTrumpOrFirst.addSingleCard(card);
        }

        //Get the partnerId by adding 2 to position
        switch(id){
            case 0: partnerId = 2;
                break;
            case 1: partnerId = 3;
                break;
            case 2: partnerId = 0;
                break;
            case 3: partnerId = 1;
                break;
        }

        //check if partner has played, if he hasn't make yet to play true
        if(cardsFromTrick[partnerId] != null){
            playerYetToPlay = false;
        }

        //check if highest card is trump card as well
        if(!playerYetToPlay){
            if(cardsFromTrick[partnerId].equals(trick.getHighestCard())){
                playerCurrentlyWinning = true;
            }
        }
        //Check if highest hard is a trump card
        if(trick.getHighestCard().getSuit() == Trick.trumpSuit
                && Trick.trumpSuit != trick.getSuitOfFirstCard()){
            highestTrumpCard = true;
        }

        //remove lowest card if winning
        if(playerCurrentlyWinning){
            return removeLowestCard();
        } else {
            //if not winning then this player should play best cards
            if (!firstCards.getHandOfCards().isEmpty()) {//play lead suit
                firstCards.sortByRank();
                //player highest lead > trick top card & trick top card
                //isn't trump, play highest lead
                if (firstCards.getHandOfCards().get(firstCards.getHandOfCards().size()-1).getRank().getRankValue()
                        > trick.getHighestCard().getRank().getRankValue()
                        && !highestTrumpCard) {
                    return trumpCards.getHandOfCards().get(trumpCards.getHandOfCards().size()-1);
                }
            }else if(!trumpCards.getHandOfCards().isEmpty()){//play trump
                trumpCards.sortByRank();
                //play highest trump if trick top card isn't trump
                if(!highestTrumpCard){
                    return trumpCards.getHandOfCards().get(trumpCards.getHandOfCards().size()-1);

                    //if highest card is trump, play trump is greater value than highest valued card
                }else if(highestTrumpCard){
                    if(trumpCards.getHandOfCards().get(trumpCards.getHandOfCards().size()-1).getRank().getRankValue()
                            > trick.getHighestCard().getRank().getRankValue()){
                        return trumpCards.getHandOfCards().get(trumpCards.getHandOfCards().size()-1);
                    }
                }
            }
        }

        return removeLowestCard();
    }

    //remove lowest card in current players hand
    public Card removeLowestCard() {
        if(!neitherTrumpOrFirst.getHandOfCards().isEmpty()){
            neitherTrumpOrFirst.sortByRank();
            return neitherTrumpOrFirst.getHandOfCards().get(0);
        }else if(!firstCards.getHandOfCards().isEmpty()){
            firstCards.sortByRank();
            return firstCards.getHandOfCards().get(0);
        }else{
            trumpCards.sortByRank();
            return trumpCards.getHandOfCards().get(0);
        }
    }

    @Override
    public void updateMemory(Trick mem) {

    }

    public static void main(String[] args) {
        BasicStrategy basicStrategy = new BasicStrategy(0);
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();
        Hand hand3 = new Hand();
        Hand hand4 = new Hand();
        Deck deck = new Deck();
        Trick trick = new Trick(0);

        for(int i = 0; i<13;i++){
            hand1.addSingleCard(deck.deal());
            hand2.addSingleCard(deck.deal());
            hand3.addSingleCard(deck.deal());
            hand4.addSingleCard(deck.deal());
        }

        BasicPlayer player1 = new BasicPlayer(basicStrategy, hand1, 0);
        BasicPlayer player2 = new BasicPlayer(basicStrategy, hand2, 1);
        BasicPlayer player3 = new BasicPlayer(basicStrategy, hand3, 2);
        BasicPlayer player4 = new BasicPlayer(basicStrategy, hand4, 3);

        System.out.println("P1");
        trick.recordCard(player1.playCard(trick), player1);
        System.out.println("P2");
        trick.recordCard(player2.playCard(trick), player2);
        System.out.println("P3");
        trick.recordCard(player3.playCard(trick), player3);
        System.out.println("P4");
        trick.recordCard(player4.playCard(trick), player4);

        System.out.println(hand1.toString() + "-Hand 1");
        System.out.println(hand2.toString() + "-Hand 2");
        System.out.println(hand3.toString() + "-Hand 3");
        System.out.println(hand4.toString() + "-Hand 3");

    }
}
