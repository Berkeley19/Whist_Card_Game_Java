package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public void cardAdditionMethod(Card card){
        handOfCards.add(card);
        currentHandCount += card.getRank().getRankValue();
    }

    public void addSingleCard(Card card){
        increaseSuitCount(card.getSuit());
        cardAdditionMethod(card);
    }

    public void addCardColletion(List<Card> listOfCards){
        for (Card card: listOfCards) {
            increaseSuitCount(card.getSuit());
            cardAdditionMethod(card);
        }
    }

    public ArrayList<Card> getHandOfCards(){
        return handOfCards;
    }

    public void addHand(Hand handOfCards){
        for (Card card: handOfCards.getHandOfCards()) {
            increaseSuitCount(card.getSuit());
            this.handOfCards.add(card);
            currentHandCount += card.getRank().getRankValue();
        }
    }

    public void increaseSuitCount(Card.Suit cardSuit){
        switch(cardSuit){
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

    public void cardSubtractionMethod(Card card){
        decreaseSuitCount(card.getSuit());
        currentHandCount -= card.getRank().getRankValue();
    }

    public boolean removeSingleCard(Card card){
        boolean containsCard = handOfCards.contains(card);
        if(containsCard){
            cardSubtractionMethod(card);
            handOfCards.remove(card);
        }
        return containsCard;
    }

    public boolean removeAnotherHand(Hand handOfCards){
        boolean containsHand = false;
        for (Card card: handOfCards.getHandOfCards()) {
            if(this.handOfCards.contains(card)){
                containsHand = true;
            }else{
                return false;
            }
        }
        if(containsHand){
            for (Card card:handOfCards.getHandOfCards()) {
                decreaseSuitCount(card.getSuit());
                currentHandCount -= card.getRank().getRankValue();
                this.handOfCards.remove(card);
            }
        }
        return true;
    }

    public Card removeSpecificPosition(int cardIndex){
        Card card = handOfCards.get(cardIndex);
        cardSubtractionMethod(card);
        return handOfCards.remove(cardIndex);
    }

    public void decreaseSuitCount(Card.Suit cardSuit){
        switch(cardSuit){
            case CLUBS: currentClubCount--;
                break;
            case DIAMONDS: currentDiamondCount--;
                break;
            case SPADES: currentSpadeCount--;
                break;
            case HEARTS: currentHeartCount--;
                break;
        }
    }

}
