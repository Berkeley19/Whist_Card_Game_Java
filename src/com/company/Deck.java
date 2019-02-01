package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Deck implements Iterable<Card>{
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


    @Override
    public Iterator<Card> iterator() {
        return new TraverseDeckIterator();
    }

    public static class TraverseDeckIterator implements Iterator<Card> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Card next() {
            return null;
        }
    }

    public static void main(String[] args){
        Deck deck = new Deck();
        System.out.println(deck.deckOfCards.size());
        deck.newDeck();
        System.out.println(deck.size());
    }
}
