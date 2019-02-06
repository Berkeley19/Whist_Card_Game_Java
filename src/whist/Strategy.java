package whist;

import cards.Card;
import cards.Hand;

public interface Strategy {

    //pick a hand to play in the trick
    Card pickCard(Hand hand, Trick trick);

    //Update the data
    void updateMemory(Trick mem);
}
