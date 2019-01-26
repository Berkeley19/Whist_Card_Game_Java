package com.company;

import java.io.Serializable;
import java.util.*;


public class Card implements Serializable, Comparable<Card> {




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

    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;

        private static final Random random = new Random();
        private static final Suit[] suitList = Suit.values();

        public static Suit getRandomSuit() {
            return suitList[random.nextInt(suitList.length)];
        }
    }

    private static final long serialVersionUID = 100L;
    private Rank rank;
    private Suit suit;

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

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        return "Card{" + "rank=" + this.rank + ", suit=" + this.suit + '}';
    }

    @Override
    public int compareTo(Card card) {
        if (card.getRank().getRankValue() > this.getRank().getRankValue()) {
            return 1;
        } else if (card.getRank().getRankValue() == this.getRank().getRankValue()) {
            if (card.getSuit().ordinal() < this.getSuit().ordinal()) {
                return 1;
            } else if (card.getSuit().ordinal() > this.getSuit().ordinal()) {
                return -1;
                //never happen as no duplicate cards
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    public static Card max(List<Card> listOfCards) {
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

    public static class CompareDescending {
        public static Comparator<Card> cardComparator = (Card card1, Card card2) -> {
            if (card1.getRank().getRankValue() > card2.getRank().getRankValue()) {
                return -1;
            } else if (card1.getRank().getRankValue() == card2.getRank().getRankValue()) {
                if (card1.getSuit().ordinal() > card2.getSuit().ordinal()) {
                    return -1;
                } else if (card1.getSuit().ordinal() < card2.getSuit().ordinal()) {
                    return 1;
                    //never happen as no duplicate cards
                } else {
                    return 0;
                }
            } else {
                return 1;
            }
        };
    }

    public static class CompareRank {
        public static Comparator<Card> cardComparator = (Card card1, Card card2) -> {
            if (card1.getRank().ordinal() > card2.getRank().ordinal()) {
                return 1;
            } else if (card1.getRank().ordinal() < card2.getRank().ordinal()) {
                return -1;
            } else {
                return 0;
            }
        };
    }
    public static void selectTest(){
        List<Card> cardList = new ArrayList<>();
        cardList.add(new Card(Rank.FOUR, Suit.DIAMONDS));
        cardList.add(new Card(Rank.FOUR, Suit.CLUBS));
        cardList.add(new Card(Rank.FOUR, Suit.SPADES));
        cardList.add(new Card(Rank.TEN, Suit.DIAMONDS));
        cardList.add(new Card(Rank.TEN, Suit.CLUBS));
        cardList.add(new Card(Rank.TWO, Suit.SPADES));
        cardList.sort(CompareDescending.cardComparator);
        

    }
    public static ArrayList<Card> chooseGreater(List<Card> cardList, Comparator cardComparator, Card card){
        ArrayList<Card> cardArrayList = new ArrayList<>();


        return cardArrayList;
    }

    //Testing
    public static void main(String[] args) {
        System.out.println(Rank.SEVEN.getNext().getValue());
        //System.out.println(card.compareTo(card1));
        List<Card> cardList = new ArrayList<>();
        /*cardList.add(new Card(Rank.FOUR, Suit.DIAMONDS));
        cardList.add(new Card(Rank.FOUR, Suit.CLUBS));
        cardList.add(new Card(Rank.FOUR, Suit.SPADES));
        cardList.add(new Card(Rank.TEN, Suit.DIAMONDS));
        cardList.add(new Card(Rank.TEN, Suit.CLUBS));
        cardList.add(new Card(Rank.TWO, Suit.SPADES));*/
        System.out.println(cardList);

    }
}
