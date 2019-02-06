package whist;

import cards.Card;
import cards.Hand;

public interface Player {
    //add card to player hand
    void addCardToHand(Card card);

    //removes card from player's hand
    void removeCardFromHand(Card card);

    //removes hand from player's hand
    void removeHandFromHand(Hand hand);

    //Changes strategy of play
    void setStrategy(Strategy strategy);

    //Based on the strategy and trick given, which card is played (returned)
    //Game play is controlled by this method playCard
    Card playCard(Trick trick);

    //Player sent completed trick
    void seeTrick(Trick trick);

    //Make the trump card
    void makeTrump();

    //return player id
    int getId();

}
