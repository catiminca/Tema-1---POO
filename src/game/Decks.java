package game;

import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;

public class Decks {
    int nrCardsInDeck;
    int nrDecks;
    ArrayList<ArrayList<Card>> decks_player = new ArrayList<>();
    public Decks(DecksInput decksInput){
        nrDecks = decksInput.getNrDecks();
        nrCardsInDeck = decksInput.getNrCardsInDeck();
        for(ArrayList<CardInput> decks : decksInput.getDecks()) {
            ArrayList<Card> cards = new ArrayList<>();
            for (CardInput cardInput : decks) {
                if(cardInput.getName().equals("Firestorm") || cardInput.getName().equals("Winterfell") ||
                        cardInput.getName().equals("Heart Hound")) {
                    cards.add(new Environment(cardInput));
                } else {
                    cards.add(new Minion(cardInput));
                }
            }
            decks_player.add(cards);
        }

    }
    public String toString() {
        return "InfoInput{"
                + "nr_cards_in_deck="
                + nrCardsInDeck
                +  ", nr_decks="
                + nrDecks
                + ", decks="
                + decks_player
                + '}';
    }

    public Decks(Decks deck){
        nrDecks = deck.nrDecks;
        nrCardsInDeck = deck.nrCardsInDeck;
        decks_player = new ArrayList<>();
//        for(int i = 0; i < nrDecks; i++) {
//            decks_player.add(new DeckForGame(deck.decks_player.get(i)));
//        }
        decks_player.addAll(deck.decks_player);

    }
    public ArrayList<ArrayList<Card>> getDecks_player() {
        return decks_player;
    }
}
