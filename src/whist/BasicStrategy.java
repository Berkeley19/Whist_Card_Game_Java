package whist;

import cards.Card;
import cards.Hand;

public class BasicStrategy implements Strategy {
    @Override
    public Card pickCard(Hand hand, Trick trick) {
        return null;
    }

    @Override
    public void updateMemory(Trick mem) {

    }
}
