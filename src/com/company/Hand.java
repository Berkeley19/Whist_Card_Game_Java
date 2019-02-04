package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class Hand implements Serializable {
    private static final long serialVersionUID = 300L;

    private ArrayList<Card> handOfCards;
    private int currentDiamondCount;
    private int currentSpadeCount;
    private int currentClubCount;
    private int currentHeartCount;
    private int currentHandCount;


    public Hand(){
        handOfCards = new ArrayList<>();
    }

    public Hand(ArrayList<Card> arrayOfCards){
        handOfCards = new ArrayList<>();
        //add arrayOfCards to hand
    }

    public Hand(Hand handOfCards){
        this.handOfCards = new ArrayList<>();

    }
    public void additionCardMethod(Card card){
        handOfCards.add(card);
        currentHandCount += card.getRank().getRankValue();
    }
    public void addSingleCard(Card card){
        increaseSuitCount(card);
        additionCardMethod(card);
    }

    public void addCardColletion(){

    }

    public void addHand(){

    }

    public void increaseSuitCount(Card card){
        Card.Suit suit = card.getSuit();
        switch(suit){
            case CLUBS: currentClubCount++;
                break;
            case DIAMONDS: currentDiamondCount++;
                break;
            case SPADES: currentSpadeCount++;
                break;
            case HEARTS: currentHeartCount++;
                break;
        }
    }

    public boolean removeSingleCard(){

    }

    public boolean removeAnotherHand(){

    }

    public Card removeSpecificPosition(int index){

    }

}
