package whist;

import cards.Card;

public class BasicPlayer implements Player{


    @Override
    public void addCardToHand(Card card) {

    }

    @Override
    public void removeCardFromHand(Card card) {

    }

    @Override
    public void removeHandFromHand(Card card) {

    }

    @Override
    public void setStrategy(Strategy strategy) {

    }

    @Override
    public Card playCard(Trick trick) {
        return null;
    }

    @Override
    public void viewTrick(Trick trick) {

    }

    @Override
    public void makeTrump() {
        Trick.trumpSuit = Card.Suit.getRandomSuit();
    }

    @Override
    public int getId() {
        return 0;
    }
}
