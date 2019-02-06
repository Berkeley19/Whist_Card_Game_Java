package com.Whist;

import java.io.Serializable;
import java.util.*;

public class Hand implements Serializable, Iterable<Card> {
    private static final long serialVersionUID = 300L;

    private ArrayList<Card> handOfCards;
    private int currentDiamondCount;
    private int currentSpadeCount;
    private int currentClubCount;
    private int currentHeartCount;
    private ArrayList<Integer> currentHandCount = new ArrayList<>();


    public Hand(){
        handOfCards = new ArrayList<>();
    }


    public Hand(ArrayList<Card> arrayOfCards){
        handOfCards = new ArrayList<>();
        addCardCollection(arrayOfCards);
    }

    public Hand(Hand handOfCards){
        this.handOfCards = new ArrayList<>();
        addHand(handOfCards);
    }

    private void calculateCurrentHandCount(){
        int tempHandCount = 0;
        int aceCount = 0;
        int tempTotalCount = 0;
        for (Card card: this.handOfCards) {
            if(card.getRank()!=Card.Rank.ACE){
                tempHandCount = card.getRank().getRankValue();
                tempTotalCount += card.getRank().getRankValue();
            }else{
                aceCount++;
            }
        }
        if(!(aceCount==0)) {
            currentHandCount.add(0);
            for (int i = 0; i <= aceCount; i++) {
                int newValue = tempTotalCount + (aceCount - i) + (i * 11);
                currentHandCount.set(i, newValue);
            }
        }else{
            if(currentHandCount.size()==0){
                currentHandCount.add(0);
            }
            for(int i=0; i<currentHandCount.size(); i++){
                int oldValue = currentHandCount.get(i);
                int newValue = oldValue + tempHandCount;
                currentHandCount.set(i, newValue);
            }
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

    public ArrayList<Integer> getCurrentHandCount(){return currentHandCount;}

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
        System.out.println(this.handOfCards);
        for (int i=0; i<this.handOfCards.size(); i++) {
            if(this.handOfCards.get(i).toString().equals(card.toString())){
                this.handOfCards.remove(i);
            }
        }
        decreaseSuitCount(card.getSuit());
        calculateCurrentHandCount();
    }

    public boolean removeSingleCard(Card card){
        cardSubtractionMethod(card);
        return true;
    }

    public boolean removeAnotherHand(Hand handOfCards){
        //contains and remove wasn't working
        //so had to get the indexes instead of the Card objects themselves
        ArrayList<Integer> indexArray = new ArrayList<>();
        for (int i=0; i<this.handOfCards.size(); i++) {
            for (Card card: handOfCards.getHandOfCards()) {
                if(this.handOfCards.get(i).toString().equals(card.toString())){
                    indexArray.add(i);
                }
            }
        }
        if(!(handOfCards.getHandOfCards().size()==indexArray.size())){
            return false;
        }
        Comparator<Integer> cmp = Collections.reverseOrder();
        indexArray.sort(cmp);
        for (int index: indexArray) {
            this.handOfCards.remove(this.handOfCards.get(index));
        }
        return true;

    }

    public Card removeSpecificPosition(int cardIndex){
        Card card = handOfCards.get(cardIndex);
        System.out.println(card);
        cardSubtractionMethod(card);
        return card;
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

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Hand||");
        for (Card card: handOfCards) {
            string.append(card.toString()).append(" - ");
        }
        string.append("END OF HAND||");
        return string.toString();
    }

    public static void main(String[] args){
        Hand hand = new Hand();
        hand.addSingleCard(new Card(Card.Rank.SEVEN, Card.Suit.CLUBS));
        System.out.println(hand);
        ArrayList<Card> cardList = new ArrayList<>();
        Card card1 = new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS);
        cardList.add(new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS));
        cardList.add(new Card(Card.Rank.FOUR, Card.Suit.CLUBS));
        cardList.add(new Card(Card.Rank.FOUR, Card.Suit.SPADES));
        cardList.add(new Card(Card.Rank.TEN, Card.Suit.DIAMONDS));
        cardList.add(new Card(Card.Rank.TEN, Card.Suit.CLUBS));
        System.out.println(cardList);
        hand.addCardCollection(cardList);
        System.out.println(hand.currentDiamondCount + " should = 2");
        System.out.println(hand.currentHandCount + " should = 39");
        hand.addSingleCard(new Card(Card.Rank.ACE, Card.Suit.SPADES));
        System.out.println(hand.currentHandCount + " should = [40, 50]");
        Hand hand1 = new Hand(cardList);
        System.out.println(hand1 + "  -same as cardList-  " + cardList.toString());
        System.out.println(hand);
        Hand hand2= new Hand(hand);
        System.out.println(hand2 + "  -same as hand-  " + hand.toString());
        System.out.println(hand);
        System.out.println(hand.countRank(Card.Rank.ACE ) + " should = 0");
        System.out.println(hand.countRank(Card.Rank.FOUR ) + " should = 3");
        hand.sort();
        System.out.println(hand);
        hand.sortByRank();
        System.out.println(hand);
        System.out.println(hand.hasSuit(Card.Suit.HEARTS) + " should = false");
        System.out.println(hand.hasSuit(Card.Suit.CLUBS) + " should = true");
        System.out.println(hand.countSuit(Card.Suit.CLUBS) +  " should = 3");
        System.out.println(hand);
        System.out.println(hand.removeSpecificPosition(3) + " Seven of Clubs removed");
        System.out.println(hand + " Four of diamonds should be here");
        System.out.println(hand.removeSingleCard(card1));
        System.out.println(hand + " Four of diamond should be gone");
        Hand anotherHand = new Hand();
        anotherHand.addSingleCard(new Card(Card.Rank.TEN, Card.Suit.DIAMONDS));
        anotherHand.addSingleCard(new Card(Card.Rank.TEN, Card.Suit.CLUBS));
        System.out.println(hand);
        System.out.println(hand.removeAnotherHand(anotherHand) + " should = true");
        System.out.println(hand);
        TraverseHandIterator itr = new TraverseHandIterator(hand);
        int index = 0;
        while(itr.hasNext()){
            System.out.println(itr.next() + "- hand - " + index);
            index++;
        }
        System.out.println(hand.removeSpecificPosition(0));
        System.out.println(hand);



    }



}
