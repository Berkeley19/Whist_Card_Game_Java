package com.company;

import java.util.ArrayList;

public class Hand {
    private static final long serialVersionUID = 300L;

    ArrayList<Card> handOfCards;
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

    public void addSingleCard(){

    }

    public void addCardColletion(){

    }

    public void addHand(){

    }


}
