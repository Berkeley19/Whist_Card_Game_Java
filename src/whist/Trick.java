package whist;

import cards.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Trick {
    protected static Card.Suit trumpSuit;
    private Card firstCard;
    private Card[] cardsOfTrick;
    private int firstPlayer;
    private Card highestCard;


    public Trick(int playerId){
        this.firstPlayer = playerId;
        cardsOfTrick = new Card[4];
    }

    public void makeTrump() {
        trumpSuit = Card.Suit.getRandomSuit();
    }

    public Card[] getCardsOfTrick(){
        return cardsOfTrick;
    }


    public int getFirstPlayer(){
        return firstPlayer;
    }


    public Card getHighestCard(){
        return highestCard;
    }

    public Card getCardByIndex(int index){
        return cardsOfTrick[index];
    }

    public Card sortWinningTrick(ArrayList<Card> cardToSort){
        int sizeOfList = cardToSort.size();
        cardToSort.sort(Card::compareTo);
        return cardToSort.get(sizeOfList);
    }

    public int calculateWinningTrickCard(){
        ArrayList<Card> nonTrumpCards = new ArrayList<>();
        ArrayList<Card> trumpCards = new ArrayList<>();
        boolean equalsTrump = false;
        Card[] winningTrickCard = new Card[1];

        for (Card card: cardsOfTrick) {
            try {
                if(firstCard.getSuit().equals(card.getSuit())){
                    nonTrumpCards.add(card);
                }
                if(trumpSuit.equals(card.getSuit())){
                    nonTrumpCards.add(card);
                    equalsTrump=true;
                }
            } catch(NullPointerException e) {
                System.out.println(e);
            }
        }

        if(!equalsTrump){
            winningTrickCard[0] = sortWinningTrick(nonTrumpCards);
        }else{
            for (Card card: nonTrumpCards) {
                if(trumpSuit.equals(card.getSuit())){
                    trumpCards.add(card);
                }
            }
            winningTrickCard[0] = sortWinningTrick(trumpCards);
        }
        List<Card> cardsOfTrickList = new ArrayList<Card>(Arrays.asList(cardsOfTrick));
        return cardsOfTrickList.indexOf(winningTrickCard[0]);
    }



}
