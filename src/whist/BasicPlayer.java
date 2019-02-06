package whist;

import cards.*;

public class BasicPlayer implements Player{
    Hand hand;
    int id;
    Strategy strat;

    public BasicPlayer(BasicStrategy strategy, Hand hand, int id){
        this.strat = strategy;
        this.hand = hand;
        this.id = id;
    }
    //player can view the finished trick
    @Override
    public void seeTrick(Trick trick) {
        strat.updateMemory(trick);
    }

    @Override
    public void makeTrump() {
        Trick.trumpSuit = Card.Suit.getRandomSuit();
    }

    @Override
    public void addCardToHand(Card card) {
        hand.addSingleCard(card);
    }

    @Override
    public void removeCardFromHand(Card card) {
        hand.removeSingleCard(card);
    }

    @Override
    public void removeHandFromHand(Hand hand) {
        this.hand.removeAnotherHand(hand);
    }

    @Override
    public void setStrategy(Strategy strategy) {
        strat = strategy;
    }
    //Makes decision using the strategy and current trick in play
    //to play the best card
    @Override
    public Card playCard(Trick trick) {
        Card card = strat.pickCard(this.hand, trick);
        this.hand.removeSingleCard(card);
        System.out.println("Chosen card of player - " + card);
        return card;
    }


    @Override
    public int getId() {
        return id;
    }
}
