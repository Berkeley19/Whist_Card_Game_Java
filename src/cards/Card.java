package cards;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Card implements Serializable, Comparable<Card> {

    private static final long serialVersionUID = 100L;
    private Rank rank;
    private Suit suit;

    //Ranks of cards
    public enum Rank {

        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
        NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        private int rankValue;
        private static final Rank[] rankList = Rank.values();


        Rank(int value) {
            this.rankValue = value;
        }

        public int getRankValue() {
            return this.rankValue;
        }

        public Rank getNext() {

            return rankList[(ordinal()+1)%rankList.length];
        }

        public int getValue() {
            return this.getRankValue();
        }

    }
    //Suits of Cards
    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;

        private static final Random random = new Random();
        private static final Suit[] suitList = Suit.values();

        public static Suit getRandomSuit() {
            return suitList[random.nextInt(suitList.length)];
        }
    }

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return this.rank;
    }

    public Suit getSuit() {
        return this.suit;
    }

    @Override
    public String toString() {
        return this.rank + " of " + this.suit;
    }

    //compares two card values
    @Override
    public int compareTo(Card card) {

        return compareGreaterCard(this, card);

    }
    //checks if they have the same ranks e.g ace=ace
    private static boolean isRankEqual(Card card1, Card card2){
        return card1.getRank().ordinal() == (card2).getRank().ordinal();
    }
    //returns highest card value in list of cards
    private static Card max(List<Card> listOfCards) {
        Iterator<Card> itr = listOfCards.iterator();
        Card maxCard = listOfCards.get(0);

        while (itr.hasNext()) {
            Card card = itr.next();
            if (card.compareTo(maxCard) > 0) {
                maxCard = card;
            }
        }
        return maxCard;
    }
    //list in descending order of values but suits keep order
    public static class CompareDescending {
        static Comparator<Card> compareDescending = (Card card1, Card card2) -> {
            if (card1.getRank().ordinal() > card2.getRank().ordinal()) {
                return -1;
            } else if (card1.getRank().ordinal() == card2.getRank().ordinal()) {
                return compareSuits(card1, card2 );
            } else {
                return 1;
            }
        };
    }
    //checks indexes of suits in enum to create value
    private static int compareSuits(Card card1, Card card2) {
        if (card1.getSuit().ordinal() > card2.getSuit().ordinal()) {
            return 1;
        } else if (card1.getSuit().ordinal() < card2.getSuit().ordinal()) {
            return -1;
            //never happen as no duplicate cards
        } else {
            return 0;
        }
    }
    //just rank values are ordered suits are ignored
    static class CompareRank {
        static Comparator<Card> rankComparator = (Card card1, Card card2) -> {
            if (card1.getRank().ordinal() > card2.getRank().ordinal()) {
                return 1;
            } else if (card1.getRank().ordinal() < card2.getRank().ordinal()) {
                return -1;
            } else {
                return 0;
            }
        };
    }
    //check if one card is greater than another
    private static int compareGreaterCard(Card card1, Card card2){
        if (card1.getRank().getRankValue() > card2.getRank().getRankValue()) {
            return 1;
        } else if (isRankEqual(card1,card2)) {
            return compareSuits(card1, card2);
        } else {
            return -1;
        }
    }
    //testing comparators
    private static void selectTest(){
            List<Card> cardList = new ArrayList<>();
            cardList.add(new Card(Rank.FOUR, Suit.DIAMONDS));
            cardList.add(new Card(Rank.FOUR, Suit.CLUBS));
            cardList.add(new Card(Rank.FOUR, Suit.SPADES));
            cardList.add(new Card(Rank.TEN, Suit.DIAMONDS));
            cardList.add(new Card(Rank.TEN, Suit.CLUBS));
            cardList.add(new Card(Rank.TWO, Suit.SPADES));
            cardList.add(new Card(Rank.SEVEN, Suit.SPADES));
            Card card =  new Card(Rank.FOUR, Suit.HEARTS);
            Comparator<Card> cardComparatorGreater = (Card c1, Card c2) -> compareGreaterCard(c1, c2);
            System.out.println(chooseGreater(cardList, CompareDescending.compareDescending, card));
            System.out.println(chooseGreater(cardList, CompareRank.rankComparator, card));
            System.out.println(chooseGreater(cardList, cardComparatorGreater, card));
    }
    //descending returns opposite values of compareTo so need to check for them
    private static ArrayList<Card> chooseGreater(List<Card> cardList, Comparator<Card> cardComparator, Card card){
        ArrayList<Card> cardArrayList = new ArrayList<>();
        for (Card cardElement: cardList) {
            if(cardComparator.equals(CompareDescending.compareDescending)){
                if(isRankEqual(cardElement,card) && cardComparator.compare(cardElement, card)> 0){
                    cardArrayList.add(cardElement);
                }else if(!isRankEqual(cardElement,card) && cardComparator.compare(cardElement, card)<0) {
                    cardArrayList.add(cardElement);
                }
            }else{
                if(cardComparator.compare(cardElement,card)>0) {
                    cardArrayList.add(cardElement);
                }
            }
        }
        return cardArrayList;
    }

    //Testing
    public static void main(String[] args) {
        ArrayList<Card> cardList = new ArrayList<>();
        System.out.println(Rank.SEVEN.getNext().getValue());
        cardList.add(new Card(Rank.FOUR, Suit.DIAMONDS));
        cardList.add(new Card(Rank.FOUR, Suit.CLUBS));
        cardList.add(new Card(Rank.FIVE, Suit.DIAMONDS));
        cardList.add(new Card(Rank.TWO, Suit.CLUBS));
        cardList.add(new Card(Rank.EIGHT, Suit.CLUBS));
        cardList.add(new Card(Rank.KING, Suit.DIAMONDS));
        cardList.add(new Card(Rank.ACE, Suit.CLUBS));
        System.out.println(cardList.get(0).compareTo(cardList.get(1)));
        cardList.sort(CompareDescending.compareDescending);
        System.out.println(cardList);
        selectTest();
        System.out.println(max(cardList));

    }
}
