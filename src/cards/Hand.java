package cards;

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

    //empty constructor returns empty list
    public Hand(){
        handOfCards = new ArrayList<>();
    }

    //contructor taking in list and adds it to hand
    public Hand(ArrayList<Card> arrayOfCards){
        handOfCards = new ArrayList<>();
        addCardCollection(arrayOfCards);
    }
    //constructor takes in hand and adds it to another
    public Hand(Hand handOfCards){
        this.handOfCards = new ArrayList<>();
        addHand(handOfCards);
    }
    //calculates the hand count
    private void calculateCurrentHandCount(boolean addition){
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
        if(this.currentHandCount.size()==0){
            this.currentHandCount.add(0);
        }
        if(!(aceCount==0)) {
            if(addition){
                this.currentHandCount.add(0);
            }
            for (int i = 0; i <= aceCount; i++) {
                this.currentHandCount.set(i,tempTotalCount + (aceCount - i) + (i * 11));
            }
        }else{
            for(int i=0; i<this.currentHandCount.size(); i++){
                this.currentHandCount.set(i, this.currentHandCount.get(i)+ tempHandCount);
            }
        }
    }
    //is called everytime a card is added to deck
    private void cardAdditionMethod(Card card){
        this.handOfCards.add(card);
        increaseSuitCount(card.getSuit());
        calculateCurrentHandCount(true);
    }
    //adds single single card to deck
    public void addSingleCard(Card card){
        cardAdditionMethod(card);
    }
    //adds entire list to deck
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

    //adds hand to another hand
    private void addHand(Hand handOfCards){
        for (Card card: handOfCards.getHandOfCards()) {
            cardAdditionMethod(card);
        }
    }
    //increments the count of specifc suit
    private void increaseSuitCount(Card.Suit cardSuit){
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
    //called every time card is removed from deck
    private void cardSubtractionMethod(Card card){
        for (int i=0; i<this.handOfCards.size(); i++) {
            if(this.handOfCards.get(i).toString().equals(card.toString())){
                this.handOfCards.remove(i);
            }
        }
        decreaseSuitCount(card.getSuit());
        calculateCurrentHandCount(false);
    }
    //remove single card from deck
    public boolean removeSingleCard(Card card){
        cardSubtractionMethod(card);
        return true;
    }
    //remove another hand from a deck
    public boolean removeAnotherHand(Hand handOfCards){
        //contains and remove wasn't working
        //so had to get the indexes instead of the Card objects themselves
        ArrayList<Integer> indexArray = new ArrayList<>();
        ArrayList<Card> futureRemoval = new ArrayList<>();
        for (int i=0; i<this.handOfCards.size(); i++) {
            for (Card card: handOfCards.getHandOfCards()) {
                if(this.handOfCards.get(i).toString().equals(card.toString())){
                    indexArray.add(i);
                    futureRemoval.add(card);
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
        for(Card card: futureRemoval){
            cardSubtractionMethod(card);
        }
        return true;

    }
    //remove specifc position from deck
    private Card removeSpecificPosition(int cardIndex){
        Card card = handOfCards.get(cardIndex);
        System.out.println(card);
        cardSubtractionMethod(card);
        return card;
    }
    //decrements a suit count
    private void decreaseSuitCount(Card.Suit cardSuit){
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
    //custom iterator
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

    private void sort(){
        handOfCards.sort(Card::compareTo);
    }

    public void sortByRank(){
        handOfCards.sort(Card.CompareRank.rankComparator);
    }
    //counts the number of times suit occurs
    private int countSuit(Card.Suit suit){
        switch (suit){
            case HEARTS: return currentHeartCount;
            case SPADES: return currentSpadeCount;
            case DIAMONDS: return currentDiamondCount;
            case CLUBS: return currentClubCount;
            default: return 0;
        }
    }
    //counts the rank of a card
    private int countRank(Card.Rank rankOfCard){
        int countOfRank = 0;
        for (Card card: handOfCards) {
            if(rankOfCard == card.getRank()){
                countOfRank++;
            }
        }
        return countOfRank;
    }
    //checks if it has a suit
    private boolean hasSuit(Card.Suit cardSuit){
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
    //testing
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
        hand.addSingleCard(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS));
        System.out.println(hand.currentHandCount + " should = [41, 51, 61]");
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
        System.out.println(hand.getCurrentHandCount() + "-hand count");
        System.out.println(hand.removeSingleCard(card1));
        System.out.println(hand.getCurrentHandCount() + "-hand count");
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
        System.out.println(hand.getHandOfCards() + "-hand");
        System.out.println(hand.getCurrentHandCount() + "-hand count");
        System.out.println(hand.getCurrentClubCount() + "-club count");
        System.out.println(hand.getCurrentHeartCount() + "-heart count");
        System.out.println(hand.getCurrentDiamondCount() + "-diamond count");
        System.out.println(hand.getCurrentSpadeCount() + "-spade count");
    }



}
