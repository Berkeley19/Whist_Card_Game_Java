package cards;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deck implements Iterable<Card>, Serializable {
    private static final long serialVersionUID = 49L;
    private ArrayList<Card> deckOfCards;
    //Deck constructor
    public Deck(){
        deckOfCards = new ArrayList<>();
        initialiseDeck();
        Collections.shuffle(deckOfCards);
    }
    //add all cards to deck
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
    //creates new deck
    private void newDeck(){
        deckOfCards = new ArrayList<>();
        initialiseDeck();
        Collections.shuffle(deckOfCards);
    }

    private ArrayList<Card> getDeckOfCards(){
        return deckOfCards;
    }

    @Override
    public Iterator<Card> iterator() {
        return new TraverseDeckIterator(this);
    }
    //iterates over entire deck
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
    //deals first card from top of deck
    public Card deal(){
        TraverseDeckIterator itr = new TraverseDeckIterator(this);
        if(!itr.hasNext()){
            throw new NoSuchElementException("Deck is empty, create new one");
        }
        Card topCard = itr.next();
        deckOfCards.remove(topCard);
        return topCard;
    }
    //iterates with only spade cards from deck
    public static class SpadeIterator implements Iterator<Card> {
        private Deck deckOfCards;
        private int currentPosition;

        SpadeIterator(Deck deckOfCards){
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
    //testing
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
        int y=0;
        TraverseDeckIterator itr = new TraverseDeckIterator(deck);
        while(itr.hasNext()){
            Card nextCard = itr.next();
            y++;
            System.out.println(nextCard + "all of dem");
        }
        System.out.println(y);
        //Serialization testing to file bytecode and back with spade iteartor
        String filename = "spadeDeck.ser";
        Deck deck1 = new Deck();
        SpadeIterator itr2 = new SpadeIterator(deck1);
        deck1.getDeckOfCards().clear();
        System.out.println(itr2.deckOfCards.getDeckOfCards());
        while(itr2.hasNext()){
            Card nextCard = itr2.next();
            deck1.getDeckOfCards().add(nextCard);
            System.out.println(nextCard + " just spades");
        }
        System.out.println("Spade deck-" + deck1);
        try{
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(deck1);
            out.close();
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fis);
            Deck deck2 = (Deck)in.readObject();
            System.out.println("Read in from file-" + deck2);
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //default iterator
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
