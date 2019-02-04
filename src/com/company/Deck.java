package com.company;

import java.io.Serializable;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deck implements Iterable<Card>, Serializable {
    private ArrayList<Card> deckOfCards;
    private static final long serialVersionUID = 49;

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

        TraverseDeckIterator(Deck deckOfCards){
            this.deckOfCards = deckOfCards;
            currentPosition = 0;
        }

        @Override
        public boolean hasNext() {
            return currentPosition < deckOfCards.getDeckOfCards().size()
                && deckOfCards.getDeckOfCards().get(currentPosition) != null;
        }

        @Override
        public Card next() {
            if(currentPosition >= deckOfCards.getDeckOfCards().size()){
                throw new NoSuchElementException( "Deck doesn't have anymore cards" );
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
        if(!itr.hasNext()){
            throw new NoSuchElementException("Deck is empty, create new one");
        }
        Card topCard = itr.next();
        deckOfCards.remove(topCard);
        return topCard;
    }

    public static class SpadeIterator implements Iterator<Card> {
        private Deck deckOfCards;
        private int currentPosition;

        public SpadeIterator(Deck deckOfCards){
            Deck spadeDeck = new Deck();
            spadeDeck.getDeckOfCards().clear();
            for(Card card: deckOfCards){
                if(card.getSuit().equals(Card.Suit.SPADES)){
                    spadeDeck.getDeckOfCards().add(card);
                }
            }
            this.deckOfCards = spadeDeck;
            currentPosition = 0;
        }

        @Override
        public boolean hasNext() {
            return currentPosition < deckOfCards.getDeckOfCards().size()
                    && deckOfCards.getDeckOfCards().get(currentPosition) != null;
        }

        @Override
        public Card next() {
            if(currentPosition >= deckOfCards.getDeckOfCards().size()){
                throw new NoSuchElementException( "Deck doesn't have anymore cards" );
            }
            return deckOfCards.getDeckOfCards().get(currentPosition++);
        }

        @Override
        public void remove() {
            deckOfCards.getDeckOfCards().remove(currentPosition);
        }
    }

    @Override
    public String toString() {
        return "Deck of card->" + deckOfCards;
    }

    public static void main(String[] args){
        Deck deck = new Deck();
        System.out.println(deck.getDeckOfCards() + " " + deck.getDeckOfCards().size());
        deck.newDeck();
        System.out.println(deck.getDeckOfCards() + " " + deck.getDeckOfCards().size());
        //deal hand removes 10 cards from deck of cards to finish with 42 cards
        for(int i=0; i<10; i++){
            System.out.println(deck.deal() + " card dealt");
            System.out.println(deck.getDeckOfCards().size());
        }

        /*int y=0;
        TraverseDeckIterator itr = new TraverseDeckIterator(deck);
        while(itr.hasNext()){
            Card nextCard = itr.next();
            y++;
            System.out.println(nextCard + "all of dem");
        }
        System.out.println(y);
        */
        /*SpadeIterator itr2 = new SpadeIterator(deck);
        System.out.println(itr2.deckOfCards.getDeckOfCards());
        while(itr2.hasNext()){
            Card nextCard = itr2.next();
            System.out.println(nextCard + "just spades");
        }*/
        Iterator<Card> itr3 = deck.iterator();
        int check = 0;
        while(itr3.hasNext()){
            Card nextCard = itr3.next();
            System.out.println(nextCard + "default iterator");
            check++;
        }
        System.out.println(check);
    }
}
