package game;

import fileio.StartGameInput;
import java.util.ArrayList;
import java.util.*;

public class DeckForGame{

    ArrayList<Card> deckPlayer1;
    ArrayList<Card> deckPlayer2;

    ArrayList<Card> handPlayer1 = new ArrayList<>();
    ArrayList<Card> handPlayer2 = new ArrayList<>();

    ArrayList<Minion> frontRowP1 = new ArrayList<>();
    ArrayList<Minion> backRowP1 = new ArrayList<>();

    ArrayList<Minion> frontRowP2 = new ArrayList<>();
    ArrayList<Minion> backRowP2 = new ArrayList<>();

    int indexDeckP1, indexDeckP2;
    int seed, starting_player;
    Hero heroPlayer1, heroPlayer2;
    Table table;
    int manaP1 = 0, manaP2 = 0;

    public DeckForGame(StartGameInput game, Decks decks1, Decks decks2, Table table) {
        this.indexDeckP1 = game.getPlayerOneDeckIdx();
        this.indexDeckP2 = game.getPlayerTwoDeckIdx();
        this.seed = game.getShuffleSeed();


        this.heroPlayer1 = new Hero(game.getPlayerOneHero());
        this.heroPlayer2 = new Hero(game.getPlayerTwoHero());

        this.starting_player = game.getStartingPlayer();

        this.deckPlayer1 = new ArrayList<>();
        this.deckPlayer1.addAll(decks1.getDecks_player().get(indexDeckP1));
        this.deckPlayer2 = new ArrayList<>();
        this.deckPlayer2.addAll(decks2.getDecks_player().get(indexDeckP2));

        this.table = table;
    }

    public DeckForGame(DeckForGame currentDeck){
        this.indexDeckP1 = currentDeck.indexDeckP1;
        this.indexDeckP2 = currentDeck.indexDeckP2;
        this.seed = currentDeck.seed;
        this.starting_player = currentDeck.starting_player;

        this.manaP1 = currentDeck.manaP1;
        this.manaP2 = currentDeck.manaP2;

        this.heroPlayer1 = new Hero(currentDeck.heroPlayer1);
        this.heroPlayer2 = new Hero(currentDeck.heroPlayer2);

        this.deckPlayer1 = new ArrayList<>();
        this.deckPlayer1.addAll(currentDeck.deckPlayer1);
        this.deckPlayer2 = new ArrayList<>();
        this.deckPlayer2.addAll(currentDeck.deckPlayer2);

        Collections.shuffle(this.deckPlayer1, new Random(this.seed));
        Collections.shuffle(this.deckPlayer2, new Random(this.seed));

        this.handPlayer1 = new ArrayList<>();
        this.handPlayer2 = new ArrayList<>();

        this.frontRowP1 = new ArrayList<>();
        this.backRowP1 = new ArrayList<>();
        this.frontRowP2 = new ArrayList<>();
        this.backRowP2 = new ArrayList<>();

        this.table = currentDeck.table;
    }


    public DeckForGame(ArrayList<Card> cards) {

    }
}
