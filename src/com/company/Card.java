package com.company;
import java.io.*;
import java.util.EnumSet;
import java.util.Random;


public class Card implements Serializable{

    private static final long serialVersionUID = 100L;

    enum Rank{

        TWO(2), THREE(3),FOUR(4),FIVE(5),SIX(6),SEVEN(7),EIGHT(8),
        NINE(9),TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        private int rankValue;
        private static final Rank[] rankList = Rank.values();


        Rank(int value) {
            this.rankValue = value;
        }

        public int getRankValue(){
            return rankValue;
        }

        public Enum getNext(){
            if(this.ordinal() == rankList.length-1){
                return Rank.TWO;
            }else{
                return rankList[this.ordinal() + 1];
            }
        }

        public int getValue(){
            return this.getRankValue();
        }


    }
    public enum Suit{
        CLUBS,DIAMONDS,HEARTS,SPADES;

        private static final Random random = new Random();
        private static final Suit[] suitList = Suit.values();

        public static Enum getRandomSuit(){
            return suitList[random.nextInt(suitList.length)];
        }
    }

    //Testing
    public static void main(String[] args){
        //System.out.println(Rank.SEVEN.getNext());
        //System.out.println(Rank.THREE.getValue());
        System.out.println(Suit.getRandomSuit());

    }
}
