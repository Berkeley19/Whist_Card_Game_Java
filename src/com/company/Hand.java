package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Hand implements Serializable, Iterable<Card> {
    private static final long serialVersionUID = 300L;

    private ArrayList<Card> handOfCards;
    private int currentDiamondCount;
    private int currentSpadeCount;
    private int currentClubCount;
    private int currentHeartCount;
    private ArrayList<Integer> currentHandCount;


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


    private void calculateCurrentHandCount(){
        int tempHandCount = 0;
        int aceCount = 0;
        for (Card c: handOfCards) {
            if(c.getRank()!=Card.Rank.ACE){
                tempHandCount += c.getRank().getRankValue();
            }else{
                aceCount++;
            }
        }
        for(int i=0; i<aceCount; i++){
            currentHandCount.add(tempHandCount + (aceCount) + (i*11));
        }
    }

    private void cardAdditionMethod(Card card){
        this.handOfCards.add(card);
        increaseSuitCount(card.getSuit());
        calculateCurrentHandCount();
    }

    public void addSingleCard(Card card){
        cardAdditionMethod(card);
    }

    public void addCardCollection(List<Card> listOfCards){
        for (Card card: listOfCards) {
            cardAdditionMethod(card);
        }
    }

    public ArrayList<Card> getHandOfCards(){
        return handOfCards;
    }

    public int getCurrentClubCount(){
        return currentDiamondCount;
    }

    public int getCurrentDiamondCount(){
        return currentDiamondCount;
    }

    public int getCurrentHeartCount(){
        return currentDiamondCount;
    }

    public int getCurrentSpadeCount(){
        return currentDiamondCount;
    }


    public void addHand(Hand handOfCards){
        for (Card card: handOfCards.getHandOfCards()) {
            cardAdditionMethod(card);
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

    private void cardSubtractionMethod(Card card){
        this.handOfCards.remove(card);
        decreaseSuitCount(card.getSuit());
        calculateCurrentHandCount();
    }

    public boolean removeSingleCard(Card card){
        boolean containsCard = handOfCards.contains(card);
        if(containsCard){
            cardSubtractionMethod(card);
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
                cardSubtractionMethod(card);
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

    @Override
    public Iterator<Card> iterator() {
        return new TraverseHandIterator(this);
    }

    public static class TraverseHandIterator implements Iterator<Card> {
        private Hand handOfCards;
        private int currentPosition;

        TraverseHandIterator(Hand handOfCards){
            this.handOfCards = handOfCards;
            currentPosition = 0;
        }

        @Override
        public boolean hasNext() {
            return currentPosition < handOfCards.getHandOfCards().size()
                    && handOfCards.getHandOfCards().get(currentPosition) != null;
        }

        @Override
        public Card next() {
            if(currentPosition >= handOfCards.getHandOfCards().size()){
                throw new NoSuchElementException( "Hand doesn't have anymore cards" );
            }
            return handOfCards.getHandOfCards().get(currentPosition++);
        }

        @Override
        public void remove() {
            handOfCards.getHandOfCards().remove(currentPosition);
        }
    }

    public void sort(){
        handOfCards.sort(Card::compareTo);
    }

    public void sortByRank(){
        handOfCards.sort(Card.CompareRank.rankComparator);
    }

    public int countSuit(Card.Suit suit){
        switch (suit){
            case HEARTS: return currentHeartCount;
            case SPADES: return currentSpadeCount;
            case DIAMONDS: return currentDiamondCount;
            case CLUBS: return currentClubCount;
            default: return 0;
        }
    }

    public int countRank(Card.Rank rankOfCard){
        int countOfRank = 0;
        for (Card card: handOfCards) {
            if(rankOfCard == card.getRank()){
                countOfRank++;
            }
        }
        return countOfRank;
    }

    public boolean hasSuit(Card.Suit cardSuit){
        for(Card card: handOfCards){
            if(cardSuit == card.getSuit()){
                return true;
            }
        }
        return false;
    }

    

    public static void main(String[] args){
        Hand hand = new Hand();

    }



}
