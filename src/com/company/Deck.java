package com.company;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

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

    public ArrayList<Card> getDeckOfCards(){
        return deckOfCards;
    }

    @Override
    public Iterator<Card> iterator() {

        return new TraverseDeckIterator(this);
    }

    public static class TraverseDeckIterator implements Iterator<Card> {
        private Deck deckOfCards;
        private int currentPosition;

        public TraverseDeckIterator(Deck deckOfCards){
            this.deckOfCards = deckOfCards;
            currentPosition = 0;
        }

        @Override
        public boolean hasNext() {
            return currentPosition < deckOfCards.getDeckOfCards().size();
        }

        @Override
        public Card next() {
            if(currentPosition == deckOfCards.getDeckOfCards().size()){
                throw new NoSuchElementException( "Reached end of deck" );
            }
            return deckOfCards.getDeckOfCards().get(currentPosition++);
        }

        @Override
        public void remove() {
            deckOfCards.getDeckOfCards().remove(currentPosition);
        }

    }

    public Card deal(){
        TraverseDeckIterator itr = new TraverseDeckIterator(this);

    }


    @Override
    public String toString() {
        return "Deck " + deckOfCards;
    }

    public static void main(String[] args){
        Deck deck = new Deck();
        System.out.println(deck.getDeckOfCards().size());
        deck.newDeck();
        System.out.println(deck.size());
    }
}
