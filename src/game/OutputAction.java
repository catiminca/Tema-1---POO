package game;

import fileio.Coordinates;

import java.util.ArrayList;

public class OutputAction {

}

class GetPlayerDeck {

    private final String command;
    private final int playerIdx;
    private final ArrayList<Card> output;

    public GetPlayerDeck(ArrayList<Card> output, String command, int playerIdx) {
        this.output = output;
        this.command = command;
        this.playerIdx = playerIdx;
    }

    public ArrayList<Card> getOutput() {
        return output;
    }

    public String getCommand() {
        return command;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }


}

class GetPlayerTurn {

    private final String command;
    private final int output;

    public GetPlayerTurn(int output, String command) {
        this.output = output;
        this.command = command;
    }

    public int getOutput() {
        return output;
    }

    public String getCommand() {
        return command;
    }
}

class GetPlayerHero {
    private final String command;
    private final int playerIdx;
    private final Hero output;

    public GetPlayerHero(Hero output, String command, int playerIdx) {
        this.output = output;
        this.command = command;
        this.playerIdx = playerIdx;
    }

    public String getCommand() {
        return command;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public Hero getOutput() {
        return output;
    }
}

class PlaceCard {
    private final String command;
    private final int handIdx;
    private final String error;

    public PlaceCard(String error, String command, int handIndx) {
        this.error = error;
        this.command = command;
        this.handIdx = handIndx;
    }

    public String getCommand() {
        return command;
    }

    public int getHandIdx() {
        return handIdx;
    }

    public String getError() {
        return error;
    }
}

class GetCardsOnTable {
    private final String command;
    private final ArrayList<ArrayList<Minion>> output;

    public GetCardsOnTable(ArrayList<ArrayList<Minion>> output, String command) {
        this.output = output;
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public ArrayList<ArrayList<Minion>> getOutput() {
        return output;
    }
}

class GetPlayerMana {
    private final String command;
    private final int playerIdx;
    private final int output;

    public GetPlayerMana(int output, String command, int playerIndx) {
        this.output = output;
        this.command = command;
        this.playerIdx = playerIndx;
    }

    public String getCommand() {
        return command;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public int getOutput() {
        return output;
    }
}

class GetCardsInHand{
    private final String command;
    private final int PlayerIdx;
    private final ArrayList<Card> output;


    public GetCardsInHand(ArrayList<Card> output, String command, int playerIndx) {
        this.output = output;
        this.command = command;
        this.PlayerIdx = playerIndx;
    }

    public String getCommand() {
        return command;
    }

    public int getPlayerIdx() {
        return PlayerIdx;
    }

    public ArrayList<Card> getOutput() {
        return output;
    }
}

class GetEnvironmentCardsInHand {
    private final String command;
    private final int playerIdx;
    private final ArrayList<Environment> output;

    public GetEnvironmentCardsInHand(String command, int playerIdx, ArrayList<Environment> environment) {
        this.output = environment;
        this.command = command;
        this.playerIdx = playerIdx;
    }

    public String getCommand() {
        return command;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public ArrayList<Environment> getOutput() {
        return output;
    }
}

class UseEnvironment {
    private final String command;
    private final int handIdx;
    private final int affectedRow;
    private final String error;
    public UseEnvironment(String command, int handIdx, int affectedRow, String error) {
        this.command = command;
        this.handIdx = handIdx;
        this.affectedRow = affectedRow;
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public int getHandIdx() {
        return handIdx;
    }

    public int getAffectedRow() {
        return affectedRow;
    }

    public String getError() {
        return error;
    }
}

class GetCardAtPosition {
    private final String command;
    private final int x;
    private final int y;
    private final Object output;
    //private String output;
    //output sa fie pt string si pt carte

    public GetCardAtPosition(String command, int x, int y, Object output) {
        this.command = command;
        this.x = x;
        this.y = y;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Object getOutput() {
        return output;
    }
}

class GetFrozenCardsOnTable {
    private final String command;
    private final ArrayList<Minion> output;

    public GetFrozenCardsOnTable(String command, ArrayList<Minion> output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public ArrayList<Minion> getOutput() {
        return output;
    }
}

class CardUsesAttack {
    private final String command;
    private final Coordinates cardAttacker;
    private final Coordinates cardAttacked;
    private final String error;

    public CardUsesAttack(String command, Coordinates cardAttacker, Coordinates cardAttacked, String error) {
        this.command = command;
        this.cardAttacker = cardAttacker;
        this.cardAttacker.setX(cardAttacker.getX());
        this.cardAttacker.setY(cardAttacker.getY());
        this.cardAttacked = cardAttacked;
        this.cardAttacked.setX(cardAttacked.getX());
        this.cardAttacked.setY(cardAttacked.getY());
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    public Coordinates getCardAttacked() {
        return cardAttacked;
    }

    public String getError() {
        return error;
    }

}

class CardUsesAbility {
    private final String command;
    private final Coordinates cardAttacker;
    private final Coordinates cardAttacked;
    private final String error;

    public CardUsesAbility(String command, Coordinates cardAttacker, Coordinates cardAttacked, String error) {
        this.command = command;
        this.cardAttacker = cardAttacker;
        this.cardAttacker.setX(cardAttacker.getX());
        this.cardAttacker.setY(cardAttacker.getY());
        this.cardAttacked = cardAttacked;
        this.cardAttacked.setX(cardAttacked.getX());
        this.cardAttacked.setY(cardAttacked.getY());
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    public Coordinates getCardAttacked() {
        return cardAttacked;
    }

    public String getError() {
        return error;
    }
}

class UseAttackHero {
    private final String command;
    private final Coordinates cardAttacker;
    private final String error;

    public UseAttackHero(String command, Coordinates cardAttacker, String error) {
        this.command = command;
        this.cardAttacker = cardAttacker;
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    public String getError() {
        return error;
    }
}
class GameOver {
    private final String gameEnded;
    public GameOver(String command) {
        this.gameEnded = command;
    }

    public String getGameEnded() {
        return gameEnded;
    }
}

class UseHeroAbility {
    private final String command;
    private final int affectedRow;
    private final String error;

    public UseHeroAbility(String command, int affectedRow, String error) {
        this.command = command;
        this.affectedRow = affectedRow;
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public int getAffectedRow() {
        return affectedRow;
    }

    public String getError() {
        return error;
    }
}

class GetTotalGamesPlayed {
    private final String command;
    private final int output;

    public GetTotalGamesPlayed(String command, int output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public int getOutput() {
        return output;
    }
}

class GetPlayerOneWins {
    private final String command;
    private final int output;

    public GetPlayerOneWins(String command, int output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public int getOutput() {
        return output;
    }
}

class GetPlayerTwoWins {
    private final String command;
    private final int output;

    public GetPlayerTwoWins(String command, int output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public int getOutput() {
        return output;
    }
}