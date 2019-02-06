package whist;

import cards.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Trick {
    protected static Card.Suit trumpSuit;
    private Card firstCard;
    private Card[] cardsOfTrick;
    private int firstPlayer;
    private Card highestCard;


    public Trick(int playerId){
        this.firstPlayer = playerId;
        cardsOfTrick = new Card[4];
    }

    public void makeTrump() {
        trumpSuit = Card.Suit.getRandomSuit();
    }

    public Card[] getCardsOfTrick(){
        return cardsOfTrick;
    }


    public int getFirstPlayer(){
        return firstPlayer;
    }


    public Card getHighestCard(){
        return highestCard;
    }

    public Card getCardByIndex(int index){
        return cardsOfTrick[index];
    }

    public Card.Suit getSuitOfFirstCard(){
        return firstCard.getSuit();
    }
    private Card sortWinningTrick(ArrayList<Card> cardToSort){
        int sizeOfList = cardToSort.size();
        cardToSort.sort(Card::compareTo);
        return cardToSort.get(sizeOfList-1);
    }

    //finds playerId of final trick winner
    private int calculateWinningTrickCard(){
        ArrayList<Card> nonTrumpCards = new ArrayList<>();
        ArrayList<Card> trumpCards = new ArrayList<>();
        boolean equalsTrump = false;
        Card[] winningTrickCard = new Card[1];

        for (Card card: cardsOfTrick) {
            try {
                if(getSuitOfFirstCard().equals(card.getSuit())){
                    nonTrumpCards.add(card);
                }
                if(trumpSuit.equals(card.getSuit())){
                    nonTrumpCards.add(card);
                    equalsTrump=true;
                }
            } catch(NullPointerException e) {
                System.out.println(e);
            }
        }

        if(!equalsTrump){
            winningTrickCard[0] = sortWinningTrick(nonTrumpCards);
        }else{
            for (Card card: nonTrumpCards) {
                if(trumpSuit.equals(card.getSuit())){
                    trumpCards.add(card);
                }
            }
            winningTrickCard[0] = sortWinningTrick(trumpCards);
        }
        List<Card> cardsOfTrickList = new ArrayList<>(Arrays.asList(cardsOfTrick));
        return cardsOfTrickList.indexOf(winningTrickCard[0]);
    }

    public Card getPlayerCard(Player player){
        return cardsOfTrick[player.getId()];
    }
    //Saves newly placed card from player into trick
    public void recordCard(Card card, Player player){
        getPlayerCard(player);
        if(firstPlayer==player.getId()){
            firstCard = card;
        }
        highestCard = cardsOfTrick[calculateWinningTrickCard()];
    }

    public void emptyTrick(){
        cardsOfTrick = new Card[4];
    }

    @Override
    public String toString() {
        return Arrays.toString(cardsOfTrick) + "-cards of trick" + "\n" +
                trumpSuit + "-Trump Suit" + "\n" +
                firstPlayer + "-First Player" + "\n" +
                firstCard + "-First Card" + "\n" +
                highestCard + "-Highest Card in Trick";
    }

    public static void main(String[] args) {
        Trick trick = new Trick(1);
        Card[] cards = new Card[4];

    }
}
