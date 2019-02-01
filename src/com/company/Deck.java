package com.company;

import java.util.*;

public class Deck {
    private ArrayList<Card> deckOfCards;

    public Deck(){
        deckOfCards = new ArrayList<>();
        initialiseDeck();
        Collections.shuffle(deckOfCards);
    }

    private void initialiseDeck(){
        for (Card.Rank rank: Card.Rank.values()) {
            for (Card.Suit suit: Card.Suit.values()) {
                deckOfCards.add(new Card(rank, suit));
            }
        }
    }

    public int size(){
        return deckOfCards.size();
    }

    public void newDeck(){
        deckOfCards = new ArrayList<>();
        initialiseDeck();
        Collections.shuffle(deckOfCards);
    }

    public static void main(String[] args){
        Deck deck = new Deck();
        System.out.println(deck.deckOfCards.size());
        deck.newDeck();
        System.out.println(deck.size());
    }




}
