package com.company;

import java.awt.image.AreaAveragingScaleFilter;
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

    private void cardAdditionMethod(Card card){
        handOfCards.add(card);
        increaseSuitCount(card.getSuit());
        if(card.getRank().equals(Card.Rank.ACE)){
            currentHandCount.add(currentHandCount.get(currentHandCount.size()));
        }else{
            for (int handValue: currentHandCount) {
                handValue += card.getRank().getRankValue();
            }

        }

    }

    public void addSingleCard(Card card){
        increaseSuitCount(card.getSuit());
        cardAdditionMethod(card);
    }

    public void addCardColletion(List<Card> listOfCards){
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
            increaseSuitCount(card.getSuit());
            this.handOfCards.add(card);
            //currentHandCount += card.getRank().getRankValue();
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
        handOfCards.remove(card);
        decreaseSuitCount(card.getSuit());
        //currentHandCount -= card.getRank().getRankValue();
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
                decreaseSuitCount(card.getSuit());
                //currentHandCount -= card.getRank().getRankValue();
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

    public int countSuit(){
        return 0;
    }
}
