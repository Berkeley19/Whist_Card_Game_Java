package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;


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

        public Enum getNext() {
            if (this.ordinal() == rankList.length - 1) {
                return Rank.TWO;
            } else {
                return rankList[this.ordinal() + 1];
            }
        }

        public int getValue() {
            return this.getRankValue();
        }


    }

    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;

        private static final Random random = new Random();
        private static final Suit[] suitList = Suit.values();

        public static Enum getRandomSuit() {
            return suitList[random.nextInt(suitList.length)];
        }
    }

    private static final long serialVersionUID = 100L;
    Rank rank;
    Suit suit;

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

    public static Card max(ArrayList<Card> listOfCards) {
        Iterator<Card> itr = listOfCards.iterator();
        Card highestCardValue = listOfCards.get(0);

        while (itr.hasNext()) {
            Card card = itr.next();
            if (card.compareTo(highestCardValue) > 0) {
                highestCardValue = card;
            }
        }
        return highestCardValue;
    }



    //Testing
    public static void main(String[] args) {
        //System.out.println(Rank.SEVEN.getNext());
        //System.out.println(Rank.THREE.getValue());
        Card card = new Card(Rank.FOUR, Suit.DIAMONDS);
        Card card1 = new Card(Rank.FOUR, Suit.CLUBS);
        Card card2 = new Card(Rank.FOUR, Suit.SPADES);
        //System.out.println(card.compareTo(card1));
        ArrayList<Card> cardList = new ArrayList<Card>();
        cardList.add(card);
        cardList.add(card1);
        cardList.add(card2);
        System.out.println(max(cardList));
    }
}
