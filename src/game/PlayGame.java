package game;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.GameInput;
import fileio.Input;

import java.util.ArrayList;

public class PlayGame {
    Decks deck1, deck2;
    ArrayList<Game> games;
    int numberOfGames = 0;
    int numberOfGamesPlayer1 = 0;
    int numberOfGamesPlayer2 = 0;
    Winner winner = new Winner(numberOfGames, numberOfGamesPlayer1, numberOfGamesPlayer2);

    public PlayGame(Input inputData, ArrayNode output) {
        this.deck1 = new Decks(inputData.getPlayerOneDecks());
        this.deck2 = new Decks(inputData.getPlayerTwoDecks());
        games = new ArrayList<>();
        for (GameInput gameInput : inputData.getGames()) {
            this.deck1 = new Decks(inputData.getPlayerOneDecks());
            this.deck2 = new Decks(inputData.getPlayerTwoDecks());
            games.add(new Game(gameInput, deck1, deck2, output, winner));
        }

    }
}
