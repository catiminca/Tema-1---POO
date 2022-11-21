package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.StartGameInput;

import java.util.ArrayList;

public class Game {

    //StartGameInput startGame;
    ArrayList<Actions> actions;
    int turn;
    int currentP = 0;
    DeckForGame deckForGameCopy;
    DeckForGame deckForGame;
    Table table = new Table();
    int round, mana;
    int winnerOfGame;
    int numberOfGames = 0;
    int numberOfGamesPlayer1 = 0;
    int numberOfGamesPlayer2 = 0;

    public Game(GameInput game, Decks deck1, Decks deck2, ArrayNode output, Winner winner) {

        actions = new ArrayList<>();
        StartGameInput startGame = game.getStartGame();
        this.deckForGame = new DeckForGame(startGame, deck1, deck2, table);
        for (ActionsInput actionsInputs : game.getActions()) {
            Actions action = new Actions(actionsInputs);
            actions.add(action);
        }
        this.deckForGameCopy = new DeckForGame(deckForGame);
        deckForGameCopy.manaP1 += 1;
        deckForGameCopy.manaP2 += 1;
        this.turn = 0;
        deckForGameCopy.handPlayer1.add(deckForGameCopy.deckPlayer1.get(0));
        deckForGameCopy.handPlayer2.add(deckForGameCopy.deckPlayer2.get(0));

        deckForGameCopy.deckPlayer1.remove(0);
        deckForGameCopy.deckPlayer2.remove(0);
        getCommands(output, winner);
        this.numberOfGames = winner.getNumberOfGames();
        this.numberOfGamesPlayer1 = winner.getWinPlayer1();
        this.numberOfGamesPlayer2 = winner.getWinPlayer2();

    }

    void getCommands(ArrayNode output, Winner winner) {
        this.round = 1;
        this.mana = 1;
        if (deckForGameCopy.starting_player == 1)
            this.currentP = 1;
        else if (deckForGameCopy.starting_player == 2) {
            this.currentP = 2;
        }
        for (int i = 0; i < actions.size(); i++) {
            Actions action = new Actions(actions.get(i));
            if (actions.get(i).command.equals("endPlayerTurn")) {
                this.turn++;

                if (this.turn == 2) {
                    //round++ => creste mana + tura

                    if (mana < 10)
                        mana++;
                    deckForGameCopy.manaP1 += mana;
                    deckForGameCopy.manaP2 += mana;
                    this.turn = 0;


                    if (!deckForGameCopy.deckPlayer1.isEmpty()) {
                        deckForGameCopy.handPlayer1.add(deckForGameCopy.deckPlayer1.get(0));
                        deckForGameCopy.deckPlayer1.remove(0);
                    }
                    if (!deckForGameCopy.deckPlayer2.isEmpty()) {
                        deckForGameCopy.handPlayer2.add(deckForGameCopy.deckPlayer2.get(0));
                        deckForGameCopy.deckPlayer2.remove(0);
                    }
                    if (this.currentP == 1) {
                        deckForGameCopy.heroPlayer1.attacked = false;
                        for (int j = 0; j < table.tablePlayer1.get(0).size(); j++) {
                            table.tablePlayer1.get(0).get(j).frozen = false;
                            table.tablePlayer1.get(0).get(j).attacked = false;
                        }
                        for (int j = 0; j < table.tablePlayer1.get(1).size(); j++) {
                            table.tablePlayer1.get(1).get(j).attacked = false;
                            table.tablePlayer1.get(1).get(j).frozen = false;
                        }
                    } else {
                        deckForGameCopy.heroPlayer2.attacked = false;
                        for (int j = 0; j < table.tablePlayer2.get(0).size(); j++) {
                            table.tablePlayer2.get(0).get(j).attacked = false;
                            table.tablePlayer2.get(0).get(j).frozen = false;
                        }

                        for (int j = 0; j < table.tablePlayer2.get(1).size(); j++) {
                            table.tablePlayer2.get(1).get(j).attacked = false;
                            table.tablePlayer2.get(1).get(j).frozen = false;
                        }
                    }
                    this.currentP = deckForGameCopy.starting_player;
                    round++;
                } else if (this.turn == 1) {
                    if (this.currentP == 1) {
                        this.currentP = 2;
                        deckForGameCopy.heroPlayer1.attacked = false;
                        for (int j = 0; j < table.tablePlayer1.get(0).size(); j++) {
                            table.tablePlayer1.get(0).get(j).attacked = false;
                            table.tablePlayer1.get(0).get(j).frozen = false;
                        }


                        for (int j = 0; j < table.tablePlayer1.get(1).size(); j++) {
                            table.tablePlayer1.get(1).get(j).attacked = false;
                            table.tablePlayer1.get(1).get(j).frozen = false;
                        }

                    } else if (currentP == 2) {
                        this.currentP = 1;
                        deckForGameCopy.heroPlayer2.attacked = false;
                        for (int j = 0; j < table.tablePlayer2.get(0).size(); j++) {
                            table.tablePlayer2.get(0).get(j).attacked = false;
                            table.tablePlayer2.get(0).get(j).frozen = false;
                        }

                        for (int j = 0; j < table.tablePlayer2.get(1).size(); j++) {
                            table.tablePlayer2.get(1).get(j).attacked = false;
                            table.tablePlayer2.get(1).get(j).frozen = false;
                        }
                    }
                }

            }
            if (actions.get(i).command.equals("getCardsInHand")) {
                action.command = actions.get(i).command;
                action.playerIdx = actions.get(i).playerIdx;
                getCardsInHand(action.playerIdx, output);

            }
            if (actions.get(i).command.equals("getPlayerDeck")) {
                action.command = actions.get(i).command;
                action.playerIdx = actions.get(i).playerIdx;
                getPlayerDeck(action.playerIdx, output);

            }
            if (actions.get(i).command.equals("getCardsOnTable")) {
                action.command = actions.get(i).command;
                getCardsOnTable(deckForGameCopy.table, output);
            }
            if (actions.get(i).command.equals("getCardAtPosition")) {
                GetCardAtPosition getCardAtPosition = null;
                int indexPlayer = 0;
                action.command = actions.get(i).command;
                action.x = actions.get(i).x;
                action.y = actions.get(i).y;
                Card card;
                if (action.x == 0 || action.x == 1) {
                    indexPlayer = 2;
                    if (action.x == 0) {
                        if (!table.tablePlayer2.get(0).isEmpty()) {
                            if (action.y > deckForGameCopy.backRowP2.size() - 1) {
                                getCardAtPosition = new GetCardAtPosition("getCardAtPosition", action.x, action.y, "No card available at that position.");
                                output.addPOJO(getCardAtPosition);
                            } else {
                                getCardAtPos(table, indexPlayer, action.x, action.y, output);
                            }
                        } else {
                            getCardAtPosition = new GetCardAtPosition("getCardAtPosition", action.x, action.y, "No card available at that position.");
                            output.addPOJO(getCardAtPosition);
                        }
                    } else {
                        if (!table.tablePlayer2.get(1).isEmpty()) {
                            if (action.y > deckForGameCopy.frontRowP2.size() - 1) {
                                getCardAtPosition = new GetCardAtPosition("getCardAtPosition", action.x, action.y, "No card available at that position.");
                                output.addPOJO(getCardAtPosition);
                            } else {
                                getCardAtPos(table, indexPlayer, action.x, action.y, output);
                            }
                        }
                    }
                } else if (action.x == 2 || action.x == 3) {
                    indexPlayer = 1;
                    if (action.x == 2) {
                        if (!table.tablePlayer1.get(0).isEmpty()) {
                            if (action.y > deckForGameCopy.frontRowP1.size() - 1) {
                                getCardAtPosition = new GetCardAtPosition("getCardAtPosition", action.x, action.y, "No card available at that position.");
                                output.addPOJO(getCardAtPosition);
                            } else {
                                getCardAtPos(table, indexPlayer, action.x - 2, action.y, output);
                            }
                        } else {
                            getCardAtPosition = new GetCardAtPosition("getCardAtPosition", action.x, action.y, "No card available at that position.");
                            output.addPOJO(getCardAtPosition);
                        }
                    } else {
                        if (!table.tablePlayer1.get(1).isEmpty()) {
                            if (action.y > deckForGameCopy.backRowP1.size() - 1) {
                                getCardAtPosition = new GetCardAtPosition("getCardAtPosition", action.x, action.y, "No card available at that position.");
                                output.addPOJO(getCardAtPosition);
                            } else {
                                getCardAtPos(table, indexPlayer, action.x - 2, action.y, output);
                            }
                        } else {
                            getCardAtPosition = new GetCardAtPosition("getCardAtPosition", action.x, action.y, "No card available at that position.");
                            output.addPOJO(getCardAtPosition);
                        }
                    }
                } else if (action.x > 3 || action.y > 5) {
                    getCardAtPosition = new GetCardAtPosition("getCardAtPosition", action.x, action.y, "No card available at that position.");
                    output.addPOJO(getCardAtPosition);
                }
            }
            if (actions.get(i).command.equals("getPlayerHero")) {
                action.playerIdx = actions.get(i).playerIdx;
                heroPlayer(action.playerIdx, output);
            }
            if (actions.get(i).command.equals("getPlayerMana")) {
                action.command = actions.get(i).command;
                action.playerIdx = actions.get(i).playerIdx;
                getPlayerMana(action.playerIdx, output);

            }
            if (actions.get(i).command.equals("getEnvironmentCardsInHand")) {
                action.command = actions.get(i).command;
                action.playerIdx = actions.get(i).playerIdx;
                getEnvironmentCardsInHand(action.playerIdx, output);

            }
            if (actions.get(i).command.equals("placeCard")) {

                action.command = actions.get(i).command;
                action.handIdx = actions.get(i).handIdx;
                if (this.turn == 0 && this.currentP == 0)
                    firstRound();
                this.currentP = currentPlayer(this.turn);
                addCardOnTable(this.currentP, action.handIdx, output);

            }
            if (actions.get(i).command.equals("cardUsesAttack")) {
                action.command = actions.get(i).command;
                action.cardAttacker = actions.get(i).cardAttacker;
                action.cardAttacked = actions.get(i).cardAttacked;
                currentP = currentPlayer(turn);
                cardUsesAttack(action.cardAttacker, action.cardAttacked, currentP, output);
            }
            if (actions.get(i).command.equals("cardUsesAbility")) {
                action.command = actions.get(i).command;
                action.cardAttacker = actions.get(i).cardAttacker;
                action.cardAttacked = actions.get(i).cardAttacked;
                cardUsesAbility(action.cardAttacker, action.cardAttacked, currentP, output);
            }
            if (actions.get(i).command.equals("useAttackHero")) {
                action.command = actions.get(i).command;
                action.cardAttacker = actions.get(i).cardAttacker;
                useAttackHero(output, action.cardAttacker, winner);

            }
            if (actions.get(i).command.equals("useHeroAbility")) {
                action.command = actions.get(i).command;
                action.affectedRow = actions.get(i).affectedRow;
                useHeroAbility(output, action.affectedRow, currentP);
            }
            if (actions.get(i).command.equals("useEnvironmentCard")) {
                action.command = actions.get(i).command;
                action.handIdx = actions.get(i).handIdx;
                action.affectedRow = actions.get(i).affectedRow;
                useEnvironmentCard(action.handIdx, action.affectedRow, currentP, output);
            }
            if (actions.get(i).command.equals("getFrozenCardsOnTable")) {
                action.command = actions.get(i).command;
                getFrozenCardsOnTable(deckForGameCopy.table, output);
            }
            if (actions.get(i).command.equals("getPlayerTurn")) {
                getPlayerTurn(currentP, output);
            }
            if(actions.get(i).command.equals("getTotalGamesPlayed")) {
                action.command = actions.get(i).command;
                GetTotalGamesPlayed getTotalGamesPlayed = new GetTotalGamesPlayed("getTotalGamesPlayed", winner.getNumberOfGames());
                output.addPOJO(getTotalGamesPlayed);
            }
            if(actions.get(i).command.equals("getPlayerOneWins")) {
                action.command = actions.get(i).command;
                GetPlayerOneWins getPlayerOneWins = new GetPlayerOneWins("getPlayerOneWins", winner.getWinPlayer1());
                output.addPOJO(getPlayerOneWins);
            }
            if(actions.get(i).command.equals("getPlayerTwoWins")) {
                action.command = actions.get(i).command;
                GetPlayerTwoWins getPlayerTwoWins = new GetPlayerTwoWins("getPlayerTwoWins", winner.getWinPlayer2());
                output.addPOJO(getPlayerTwoWins);
            }

        }
    }

    void firstRound() {
        this.turn++;
        this.currentP = deckForGameCopy.starting_player;
    }

    int currentPlayer(int turn) {
        int current = 0;
        if (turn == 1) {
            if (deckForGameCopy.starting_player == 1)
                current = 2;
            else if (deckForGameCopy.starting_player == 2) {
                current = 1;
            }
        } else if (turn == 0 || turn == 2) {
            current = deckForGameCopy.starting_player;
        }
        return current;
    }

    void getCardsInHand(int PlayerIndex, ArrayNode output) {
        ArrayList<Card> hand = new ArrayList<>();
        if (PlayerIndex == 1) {
            for (int i = 0; i < deckForGameCopy.handPlayer1.size(); i++) {
                if (deckForGameCopy.handPlayer1.get(i) instanceof Minion)
                    hand.add(new Minion((Minion) deckForGameCopy.handPlayer1.get(i)));
                else {
                    hand.add(new Environment(deckForGameCopy.handPlayer1.get(i)));
                }
            }
        } else if (PlayerIndex == 2) {
            for (int i = 0; i < deckForGameCopy.handPlayer2.size(); i++) {
                if (deckForGameCopy.handPlayer2.get(i) instanceof Minion)
                    hand.add(new Minion((Minion) deckForGameCopy.handPlayer2.get(i)));
                else {
                    hand.add(new Environment(deckForGameCopy.handPlayer2.get(i)));
                }
            }
        }
        GetCardsInHand getCardsInHand = new GetCardsInHand(hand, "getCardsInHand", PlayerIndex);
        output.addPOJO(getCardsInHand);
    }

    void getPlayerDeck(int PlayerIndex, ArrayNode output) {
        GetPlayerDeck getPlayerDeck = null;
        if (PlayerIndex == 1) {
            getPlayerDeck = new GetPlayerDeck(deckForGameCopy.deckPlayer1, "getPlayerDeck", PlayerIndex);
        } else if (PlayerIndex == 2) {
            getPlayerDeck = new GetPlayerDeck(deckForGameCopy.deckPlayer2, "getPlayerDeck", PlayerIndex);
        }

        output.addPOJO(getPlayerDeck);
    }

    void heroPlayer(int PlayerIndex, ArrayNode output) {
        GetPlayerHero getPlayerHero = null;
        //ObjectNode node = JsonNodeFactory.instance.objectNode();
        if (PlayerIndex == 1) {
            Hero herocopy = new Hero(deckForGameCopy.heroPlayer1);
            getPlayerHero = new GetPlayerHero(herocopy, "getPlayerHero", PlayerIndex);

        } else {
            Hero herocopy = new Hero(deckForGameCopy.heroPlayer2);
            getPlayerHero = new GetPlayerHero(herocopy, "getPlayerHero", PlayerIndex);

        }

        output.addPOJO(getPlayerHero);
    }

    void getPlayerTurn(int playerTurn, ArrayNode output) {
        GetPlayerTurn getPlayerTurn = new GetPlayerTurn(playerTurn, "getPlayerTurn");

        output.addPOJO(getPlayerTurn);
    }

    void getCardsOnTable(Table table, ArrayNode output) {
        ArrayList<ArrayList<Minion>> list = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            list.add(table.copyRow(i));
        }
        GetCardsOnTable cardsOnTable = new GetCardsOnTable(list, "getCardsOnTable");
        output.addPOJO(cardsOnTable);
    }

    void getCardAtPos(Table table, int indexPlayer, int row, int column, ArrayNode output) {
        GetCardAtPosition getCardAtPosition = null;
        Minion card = null;
        if (indexPlayer == 1) {
            card = new Minion(table.tablePlayer1.get(row).get(column));
            getCardAtPosition = new GetCardAtPosition("getCardAtPosition", row + 2, column, card);
        } else if (indexPlayer == 2) {
            card = new Minion(table.tablePlayer2.get(row).get(column));
            getCardAtPosition = new GetCardAtPosition("getCardAtPosition", row, column, card);
        }

        output.addPOJO(getCardAtPosition);
    }

    void getPlayerMana(int indexPlayer, ArrayNode output) {
        GetPlayerMana getPlayerMana = null;

        if (indexPlayer == 1) {
            getPlayerMana = new GetPlayerMana(deckForGameCopy.manaP1, "getPlayerMana", indexPlayer);

        } else if (indexPlayer == 2) {
            getPlayerMana = new GetPlayerMana(deckForGameCopy.manaP2, "getPlayerMana", indexPlayer);
        }
        output.addPOJO(getPlayerMana);
    }

    void getEnvironmentCardsInHand(int indexPlayer, ArrayNode output) {

        ArrayList<Environment> cardsEnv = new ArrayList<>();
        if (indexPlayer == 1) {
            for (int i = 0; i < deckForGameCopy.handPlayer1.size(); i++) {
                if (deckForGameCopy.handPlayer1.get(i).getName().equals("Firestorm") ||
                        deckForGameCopy.handPlayer1.get(i).getName().equals("Winterfell") ||
                        deckForGameCopy.handPlayer1.get(i).getName().equals("Heart Hound"))
                    cardsEnv.add((Environment) deckForGameCopy.handPlayer1.get(i));
            }
        } else if (indexPlayer == 2) {
            for (int i = 0; i < deckForGameCopy.handPlayer2.size(); i++) {
                if (deckForGameCopy.handPlayer2.get(i).getName().equals("Firestorm") ||
                        deckForGameCopy.handPlayer2.get(i).getName().equals("Winterfell") ||
                        deckForGameCopy.handPlayer2.get(i).getName().equals("Heart Hound"))
                    cardsEnv.add((Environment) deckForGameCopy.handPlayer2.get(i));
            }
        }
        GetEnvironmentCardsInHand getEnvironmentCardsInHand = new GetEnvironmentCardsInHand("getEnvironmentCardsInHand", indexPlayer, cardsEnv);
        output.addPOJO(getEnvironmentCardsInHand);
    }

    void getFrozenCardsOnTable(Table table, ArrayNode output) {
        ArrayList<Minion> frozenCards = new ArrayList<>();
        GetFrozenCardsOnTable getFrozenCardsOnTable = null;
        if (!table.tablePlayer2.get(0).isEmpty()) {
            for (int j = 0; j < table.tablePlayer2.get(0).size(); j++) {
                if (table.tablePlayer2.get(0).get(j).frozen) {
                    frozenCards.add(table.tablePlayer2.get(0).get(j));
                }
            }
        }
        if (!table.tablePlayer2.get(1).isEmpty()) {
            for (int j = 0; j < table.tablePlayer2.get(1).size(); j++) {
                if (table.tablePlayer2.get(1).get(j).frozen) {
                    frozenCards.add(table.tablePlayer2.get(1).get(j));
                }
            }
        }
        if (!table.tablePlayer1.get(0).isEmpty()) {
            for (int j = 0; j < table.tablePlayer1.get(0).size(); j++) {
                if (table.tablePlayer1.get(0).get(j).frozen) {
                    frozenCards.add(table.tablePlayer1.get(0).get(j));
                }
            }
        }
        if (!table.tablePlayer1.get(1).isEmpty()) {
            for (int j = 0; j < table.tablePlayer1.get(1).size(); j++) {
                if (table.tablePlayer1.get(1).get(j).frozen)
                    frozenCards.add(table.tablePlayer1.get(1).get(j));
            }
        }
        getFrozenCardsOnTable = new GetFrozenCardsOnTable("getFrozenCardsOnTable", frozenCards);
        output.addPOJO(getFrozenCardsOnTable);
    }

    void addCardOnTable(int currentPlayer, int indexCard, ArrayNode output) {
        PlaceCard placeCardError = null;
        if (currentPlayer == 1) {

            if (deckForGameCopy.handPlayer1.get(indexCard).getName().equals("Firestorm") ||
                    deckForGameCopy.handPlayer1.get(indexCard).getName().equals("Winterfell") ||
                    deckForGameCopy.handPlayer1.get(indexCard).getName().equals("Heart Hound")) {
                placeCardError = new PlaceCard("Cannot place environment card on table.", "placeCard", indexCard);
                output.addPOJO(placeCardError);
                return;
            }
            if (deckForGameCopy.manaP1 < deckForGameCopy.handPlayer1.get(indexCard).getMana()) {
                placeCardError = new PlaceCard("Not enough mana to place card on table.", "placeCard", indexCard);
                output.addPOJO(placeCardError);
                return;
            }
            if (((Minion) deckForGameCopy.handPlayer1.get(indexCard)).type.equals("front")) {
                if (table.tablePlayer1.get(0).size() >= 5) {
                    placeCardError = new PlaceCard("Cannot place card on table since row is full.", "placeCard", indexCard);
                    output.addPOJO(placeCardError);
                    return;
                }
                deckForGameCopy.frontRowP1.add((Minion) deckForGameCopy.handPlayer1.get(indexCard));
                table.addCardForPlayer(((Minion) deckForGameCopy.handPlayer1.get(indexCard)), 1, 0);
                deckForGameCopy.manaP1 -= deckForGameCopy.handPlayer1.get(indexCard).getMana();
                deckForGameCopy.handPlayer1.remove(indexCard);

                return;
            }
            if (table.tablePlayer1.get(1).size() >= 5) {
                placeCardError = new PlaceCard("Cannot place card on table since row is full.", "placeCard", indexCard);
                output.addPOJO(placeCardError);
                return;
            }
            deckForGameCopy.backRowP1.add((Minion) deckForGameCopy.handPlayer1.get(indexCard));
            table.addCardForPlayer(((Minion) deckForGameCopy.handPlayer1.get(indexCard)), 1, 1);
            deckForGameCopy.manaP1 -= deckForGameCopy.handPlayer1.get(indexCard).getMana();
            deckForGameCopy.handPlayer1.remove(indexCard);
        } else if (currentPlayer == 2) {
            if (deckForGameCopy.handPlayer2.get(indexCard).getName().equals("Firestorm") ||
                    deckForGameCopy.handPlayer2.get(indexCard).getName().equals("Winterfell") ||
                    deckForGameCopy.handPlayer2.get(indexCard).getName().equals("Heart Hound")) {
                placeCardError = new PlaceCard("Cannot place environment card on table.", "placeCard", indexCard);
                output.addPOJO(placeCardError);
                return;
            }
            if (deckForGameCopy.manaP2 < deckForGameCopy.handPlayer2.get(indexCard).getMana()) {
                placeCardError = new PlaceCard("Not enough mana to place card on table.", "placeCard", indexCard);
                output.addPOJO(placeCardError);
                return;
            }
            if (((Minion) deckForGameCopy.handPlayer2.get(indexCard)).type.equals("front")) {
                if (deckForGameCopy.frontRowP2.size() >= 5) {
                    placeCardError = new PlaceCard("Cannot place card on table since row is full.", "placeCard", indexCard);
                    output.addPOJO(placeCardError);
                    return;
                }
                deckForGameCopy.frontRowP2.add((Minion) deckForGameCopy.handPlayer2.get(indexCard));
                table.addCardForPlayer(((Minion) deckForGameCopy.handPlayer2.get(indexCard)), 2, 1);
                deckForGameCopy.manaP2 -= deckForGameCopy.handPlayer2.get(indexCard).getMana();
                deckForGameCopy.handPlayer2.remove(indexCard);
                return;
            }
            if (deckForGameCopy.backRowP2.size() >= 5) {
                placeCardError = new PlaceCard("Cannot place card on table since row is full.", "placeCard", indexCard);
                output.addPOJO(placeCardError);
                return;
            }
            deckForGameCopy.backRowP2.add((Minion) deckForGameCopy.handPlayer2.get(indexCard));
            table.addCardForPlayer(((Minion) deckForGameCopy.handPlayer2.get(indexCard)), 2, 0);
            deckForGameCopy.manaP2 -= deckForGameCopy.handPlayer2.get(indexCard).getMana();
            deckForGameCopy.handPlayer2.remove(indexCard);
        }
    }

    void useEnvironmentCard(int handIdx, int affectedRow, int currentP, ArrayNode output) {
        UseEnvironment useEnvironmentError = null;
        if (currentP == 1) {
            if (deckForGameCopy.handPlayer1.get(handIdx) instanceof Minion) {
                useEnvironmentError = new UseEnvironment("useEnvironmentCard", handIdx, affectedRow, "Chosen card is not of type environment.");
                output.addPOJO(useEnvironmentError);
                return;
            }
            if (deckForGameCopy.manaP1 < deckForGameCopy.handPlayer1.get(handIdx).getMana()) {
                useEnvironmentError = new UseEnvironment("useEnvironmentCard", handIdx, affectedRow, "Not enough mana to use environment card.");
                output.addPOJO(useEnvironmentError);
                return;
            }
            if (affectedRow == 2 || affectedRow == 3) {
                useEnvironmentError = new UseEnvironment("useEnvironmentCard", handIdx, affectedRow, "Chosen row does not belong to the enemy.");
                output.addPOJO(useEnvironmentError);
                return;
            }
            if (deckForGameCopy.handPlayer1.get(handIdx).getName().equals("Winterfell")) {
                if (affectedRow == 0) { //backrow2
                    for (int i = 0; i < table.tablePlayer2.get(0).size(); i++) {
                        table.tablePlayer2.get(0).get(i).frozen = true;
                    }
                } else {
                    for (int i = 0; i < table.tablePlayer2.get(1).size(); i++) {
                        table.tablePlayer2.get(1).get(i).frozen = true;
                    }
                }
            } else if (deckForGameCopy.handPlayer1.get(handIdx).getName().equals("Firestorm")) {
                if (affectedRow == 0) { //backrow2
                    for (int i = 0; i < table.tablePlayer2.get(0).size(); i++) {
                        table.tablePlayer2.get(0).get(i).health -= 1;
                    }
                    for (int i = 0; i < table.tablePlayer2.get(0).size(); i++) {
                        if (table.tablePlayer2.get(0).get(i).health <= 0) {
                            table.tablePlayer2.get(0).remove(i);
                            i--;
                        }
                    }
                } else {//frontrow2
                    for (int i = 0; i < table.tablePlayer2.get(1).size(); i++) {
                        table.tablePlayer2.get(1).get(i).health -= 1;
                    }
                    for (int i = 0; i < table.tablePlayer2.get(1).size(); i++) {
                        if (table.tablePlayer2.get(1).get(i).health <= 0) {
                            table.tablePlayer2.get(1).remove(i);
                            i--;
                        }
                    }
                }
            } else if (deckForGameCopy.handPlayer1.get(handIdx).getName().equals("Heart Hound")) {
                if (affectedRow == 0) {//backrowP2
                    if (table.tablePlayer1.get(1).size() >= 5) {
                        useEnvironmentError = new UseEnvironment("useEnvironmentCard", handIdx, affectedRow, "Cannot steal enemy card since the player's row is full.");
                        output.addPOJO(useEnvironmentError);
                        return;
                    }
                    int max = -1;
                    int indexMax = -1;
                    Minion auxAttacked;
                    for (int i = 0; i < table.tablePlayer2.get(0).size(); i++) {
                        if (table.tablePlayer2.get(0).get(i).health > max) {
                            max = table.tablePlayer2.get(0).get(i).health;
                            indexMax = i;
                        }
                    }
                    auxAttacked = new Minion(table.tablePlayer2.get(0).get(indexMax));
                    table.tablePlayer1.get(1).add(auxAttacked);
                    table.tablePlayer2.get(0).remove(indexMax);
                    return;
                } else {
                    if (table.tablePlayer1.get(0).size() >= 5) {
                        useEnvironmentError = new UseEnvironment("useEnvironmentCard", handIdx, affectedRow, "Cannot steal enemy card since the player's row is full.");
                        output.addPOJO(useEnvironmentError);
                        return;
                    }
                    int max = -1;
                    int indexMax = -1;
                    Minion auxAttacked;
                    for (int i = 0; i < table.tablePlayer2.get(1).size(); i++) {
                        if (table.tablePlayer2.get(1).get(i).health > max) {
                            max = table.tablePlayer2.get(1).get(i).health;
                            indexMax = i;
                        }
                    }
                    auxAttacked = new Minion(table.tablePlayer2.get(1).get(indexMax));
                    table.tablePlayer1.get(0).add(auxAttacked);
                    table.tablePlayer2.get(1).remove(auxAttacked);
                    return;
                }
            }
            deckForGameCopy.manaP1 -= deckForGameCopy.handPlayer1.get(handIdx).getMana();
            deckForGameCopy.handPlayer1.remove(handIdx);
        } else {
            if (deckForGameCopy.handPlayer2.get(handIdx) instanceof Minion) {
                useEnvironmentError = new UseEnvironment("useEnvironmentCard", handIdx, affectedRow, "Chosen card is not of type environment.");
                output.addPOJO(useEnvironmentError);
                return;
            }
            if (deckForGameCopy.manaP2 < deckForGameCopy.handPlayer2.get(handIdx).getMana()) {
                useEnvironmentError = new UseEnvironment("useEnvironmentCard", handIdx, affectedRow, "Not enough mana to use environment card.");
                output.addPOJO(useEnvironmentError);
                return;
            }
            if (affectedRow == 0 || affectedRow == 1) {
                useEnvironmentError = new UseEnvironment("useEnvironmentCard", handIdx, affectedRow, "Chosen row does not belong to the enemy.");
                output.addPOJO(useEnvironmentError);
                return;
            }
            if (deckForGameCopy.handPlayer2.get(handIdx).getName().equals("Winterfell")) {
                if (affectedRow == 2) { //frontrow1
                    for (int i = 0; i < table.tablePlayer1.get(0).size(); i++) {
                        table.tablePlayer1.get(0).get(i).frozen = true;
                    }
                } else {//backrow1
                    for (int i = 0; i < table.tablePlayer1.get(1).size(); i++) {
                        table.tablePlayer1.get(1).get(i).frozen = true;
                    }
                }
            } else if (deckForGameCopy.handPlayer2.get(handIdx).getName().equals("Firestorm")) {
                if (affectedRow == 2) { //frontrow1
                    for (int i = 0; i < table.tablePlayer1.get(0).size(); i++) {
                        table.tablePlayer1.get(0).get(i).health -= 1;
                    }
                    for (int i = 0; i < table.tablePlayer1.get(0).size(); i++) {
                        if (table.tablePlayer1.get(0).get(i).health <= 0) {
                            table.tablePlayer1.get(0).remove(i);
                            i--;
                        }
                    }
                } else {//backrow1
                    for (int i = 0; i < table.tablePlayer1.get(1).size(); i++) {
                        table.tablePlayer1.get(1).get(i).health -= 1;
                    }
                    for (int i = 0; i < table.tablePlayer1.get(1).size(); i++) {
                        if (table.tablePlayer1.get(1).get(i).health <= 0) {
                            table.tablePlayer1.get(1).remove(i);
                            i--;
                        }
                    }
                }
            } else if (deckForGameCopy.handPlayer2.get(handIdx).getName().equals("Heart Hound")) {
                if (affectedRow == 2) {//frontplayer1
                    if (table.tablePlayer2.get(1).size() >= 5) {
                        useEnvironmentError = new UseEnvironment("useEnvironmentCard", handIdx, affectedRow, "Cannot steal enemy card since the player's row is full.");
                        output.addPOJO(useEnvironmentError);
                        return;
                    }
                    int max = -1;
                    int indexMax = -1;
                    Minion auxAttacked;
                    for (int i = 0; i < table.tablePlayer1.get(0).size(); i++) {
                        if (table.tablePlayer1.get(0).get(i).health > max) {
                            max = table.tablePlayer1.get(0).get(i).health;
                            indexMax = i;
                        }
                    }
                    auxAttacked = new Minion(table.tablePlayer1.get(0).get(indexMax));
                    table.tablePlayer2.get(1).add(auxAttacked);
                    table.tablePlayer1.get(0).remove(indexMax);
                    return;
                } else { //backrow1
                    if (table.tablePlayer2.get(0).size() >= 5) {
                        useEnvironmentError = new UseEnvironment("useEnvironmentCard", handIdx, affectedRow, "Cannot steal enemy card since the player's row is full.");
                        output.addPOJO(useEnvironmentError);
                        return;
                    }
                    int max = -1;
                    int indexMax = -1;
                    Minion auxAttacked;
                    for (int i = 0; i < table.tablePlayer1.get(1).size(); i++) {
                        if (table.tablePlayer1.get(1).get(i).health > max) {
                            max = table.tablePlayer1.get(1).get(i).health;
                            indexMax = i;
                        }
                    }
                    auxAttacked = new Minion(table.tablePlayer1.get(1).get(indexMax));
                    table.tablePlayer2.get(0).add(auxAttacked);
                    table.tablePlayer1.get(1).remove(auxAttacked);
                    return;
                }
            }
            deckForGameCopy.manaP2 -= deckForGameCopy.handPlayer2.get(handIdx).getMana();
            deckForGameCopy.handPlayer2.remove(handIdx);
        }
    }

    void cardUsesAttack(AttackCard cardAttacker, AttackCard cardAttacked, int currentPlayer, ArrayNode output) {
        CardUsesAttack cardUserAttack = null;
        if (currentPlayer == 1) {
            int xAttacker = cardAttacker.getX() - 2;

            if (cardAttacked.getX() != 0 && cardAttacked.getX() != 1) {
                cardUserAttack = new CardUsesAttack("cardUsesAttack", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacked card does not belong to the enemy.");
                output.addPOJO(cardUserAttack);
                return;
            }
            if (cardAttacked.getY() > table.tablePlayer2.get(cardAttacked.getX()).size()) {
                cardUserAttack = new CardUsesAttack("cardUsesAttack", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacked card does not belong to the enemy.");
                output.addPOJO(cardUserAttack);
                return;
            }
            if (table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).attacked) {
                cardUserAttack = new CardUsesAttack("cardUsesAttack", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacker card has already attacked this turn.");
                output.addPOJO(cardUserAttack);
                return;
            }
            if (table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).frozen) {
                cardUserAttack = new CardUsesAttack("cardUsesAttack", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacker card is frozen.");
                output.addPOJO(cardUserAttack);
                return;
            }
            if (table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).getName().equals("Goliath") ||
                    table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).getName().equals("Warden")) {
                table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).health -=
                        table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).attackDamage;
                table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).attacked = true;
                if (table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).health <= 0) {
                    table.tablePlayer2.get(cardAttacked.getX()).remove(cardAttacked.getY());
                    deckForGameCopy.frontRowP2.remove(cardAttacked.getY());
                }
                return;
            }

            for (int i = 0; i < table.tablePlayer2.get(1).size(); i++) {
                if (table.tablePlayer2.get(1).get(i).getName().equals("Goliath") ||
                        table.tablePlayer2.get(1).get(i).getName().equals("Warden")) {
                    cardUserAttack = new CardUsesAttack("cardUsesAttack", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacked card is not of type 'Tank'.");
                    output.addPOJO(cardUserAttack);
                    return;
                }
            }

            table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).health -=
                    table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).attackDamage;
            table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).attacked = true;
            if (table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).health <= 0) {
                if (table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).type == "front") {
                    deckForGameCopy.frontRowP2.remove(cardAttacked.getY());
                } else {
                    deckForGameCopy.backRowP2.remove(cardAttacked.getY());
                }
                table.tablePlayer2.get(cardAttacked.getX()).remove(cardAttacked.getY());
            }
        } else {
            int xAttacked = cardAttacked.getX() - 2;
            if (cardAttacked.getX() != 2 && cardAttacked.getX() != 3) {
                cardUserAttack = new CardUsesAttack("cardUsesAttack", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacked card does not belong to the enemy.");
                output.addPOJO(cardUserAttack);
                return;
            }
            if (cardAttacked.getY() > table.tablePlayer1.get(xAttacked).size()) {
                cardUserAttack = new CardUsesAttack("cardUsesAttack", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacked card does not belong to the enemy.");
                output.addPOJO(cardUserAttack);
                return;
            }
            if (table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).attacked) {
                cardUserAttack = new CardUsesAttack("cardUsesAttack", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacker card has already attacked this turn.");
                output.addPOJO(cardUserAttack);
                return;
            }
            if (table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).frozen) {

                cardUserAttack = new CardUsesAttack("cardUsesAttack", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacker card is frozen.");
                output.addPOJO(cardUserAttack);
                return;
            }

            if (table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).getName().equals("Goliath") ||
                    table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).getName().equals("Warden")) {
                table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).health -=
                        table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).attackDamage;
                table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).attacked = true;
                if (table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).health <= 0) {
                    table.tablePlayer1.get(xAttacked).remove(cardAttacked.getY());
                    deckForGameCopy.frontRowP1.remove(cardAttacked.getY());
                }
                return;
            }

            for (int i = 0; i < table.tablePlayer1.get(0).size(); i++) {
                if (table.tablePlayer1.get(0).get(i).getName().equals("Goliath") ||
                        table.tablePlayer1.get(0).get(i).getName().equals("Warden")) {
                    cardUserAttack = new CardUsesAttack("cardUsesAttack", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacked card is not of type 'Tank'.");
                    output.addPOJO(cardUserAttack);
                    return;
                }
            }

            table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).health -=
                    table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).attackDamage;
            table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).attacked = true;
            if (table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).health <= 0) {
                if (table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).type == "front") {
                    deckForGameCopy.frontRowP1.remove(cardAttacked.getY());
                } else {
                    deckForGameCopy.backRowP1.remove(cardAttacked.getY());
                }
                table.tablePlayer1.get(xAttacked).remove(cardAttacked.getY());
            }
        }
    }

    boolean checkAttackDamage(int attack) {
        if (attack < 0) {
            return true;
        }
        return false;
    }

    void cardUsesAbility(AttackCard cardAttacker, AttackCard cardAttacked, int currentPlayer, ArrayNode output) {
        CardUsesAbility cardUsesAbility = null;
        if (currentPlayer == 1) {
            int xAttacker = cardAttacker.getX() - 2;
            int xAttacked = cardAttacked.getX() - 2;

            if (table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).frozen) {
                cardUsesAbility = new CardUsesAbility("cardUsesAbility", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacker card is frozen.");
                output.addPOJO(cardUsesAbility);
                return;
            }
            if (table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).attacked) {
                cardUsesAbility = new CardUsesAbility("cardUsesAbility", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacker card has already attacked this turn.");
                output.addPOJO(cardUsesAbility);
                return;
            }

            if (table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).getName().equals("Disciple")) {
                if (cardAttacked.getX() == 2 || cardAttacked.getX() == 3) {
                    if (table.tablePlayer1.get(xAttacked).isEmpty() || table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()) == null) {
                        cardUsesAbility = new CardUsesAbility("cardUsesAbility", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacked card does not belong to the current player.");
                        output.addPOJO(cardUsesAbility);
                        return;
                    } else {//Disciple
                        table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).health += 2;
                        table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).attacked = true;
                        return;
                    }
                } else {
                    cardUsesAbility = new CardUsesAbility("cardUsesAbility", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacked card does not belong to the current player.");
                    output.addPOJO(cardUsesAbility);
                    return;
                }
            }
            if (cardAttacked.getX() != 0 && cardAttacked.getX() != 1) {
                cardUsesAbility = new CardUsesAbility("cardUsesAbility", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacked card does not belong to the enemy.");
                output.addPOJO(cardUsesAbility);
                return;
            }
            if (cardAttacked.getY() > table.tablePlayer2.get(cardAttacked.getX()).size()) {
                cardUsesAbility = new CardUsesAbility("cardUsesAbility", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacked card does not belong to the enemy.");
                output.addPOJO(cardUsesAbility);
                return;
            }

            if (table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).getName().equals("Goliath") ||
                    table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).getName().equals("Warden")) {
                if (abilityPlayer1(cardAttacker, cardAttacked, xAttacker)) return;
            }
            for (int i = 0; i < table.tablePlayer2.get(1).size(); i++) {
                if (table.tablePlayer2.get(1).get(i).getName().equals("Goliath") ||
                        table.tablePlayer2.get(1).get(i).getName().equals("Warden")) {
                    cardUsesAbility = new CardUsesAbility("cardUsesAbility", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacked card is not of type 'Tank'.");
                    output.addPOJO(cardUsesAbility);
                    return;
                }
            }
            if (abilityPlayer1(cardAttacker, cardAttacked, xAttacker)) return;
        } else {
            int xAttacked = cardAttacked.getX() - 2;
            if (table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).frozen) {
                cardUsesAbility = new CardUsesAbility("cardUsesAbility", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacker card is frozen.");
                output.addPOJO(cardUsesAbility);
                return;
            }
            if (table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).attacked) {
                cardUsesAbility = new CardUsesAbility("cardUsesAbility", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacker card has already attacked this turn.");
                output.addPOJO(cardUsesAbility);
                return;
            }
            if (table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).getName().equals("Disciple")) {
                if (cardAttacked.getX() == 0 || cardAttacked.getX() == 1) {
                    if (table.tablePlayer2.get(cardAttacked.getX()).isEmpty() || table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()) == null) {
                        cardUsesAbility = new CardUsesAbility("cardUsesAbility", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacked card does not belong to the current player.");
                        output.addPOJO(cardUsesAbility);
                        return;
                    } else {//Disciple
                        table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).health += 2;
                        table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).attacked = true;
                        return;
                    }
                } else {
                    cardUsesAbility = new CardUsesAbility("cardUsesAbility", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacked card does not belong to the current player.");
                    output.addPOJO(cardUsesAbility);
                    return;
                }
            }
            if (cardAttacked.getX() != 2 && cardAttacked.getX() != 3) {
                cardUsesAbility = new CardUsesAbility("cardUsesAbility", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacked card does not belong to the enemy.");
                output.addPOJO(cardUsesAbility);
                return;
            }
            if (cardAttacked.getY() > table.tablePlayer1.get(xAttacked).size()) {
                cardUsesAbility = new CardUsesAbility("cardUsesAbility", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacked card does not belong to the enemy.");
                output.addPOJO(cardUsesAbility);
                return;
            }

            if (table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).getName().equals("Goliath") ||
                    table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).getName().equals("Warden")) {
                if (abilityPlayer2(cardAttacker, cardAttacked, xAttacked)) return;
            }
            for (int i = 0; i < table.tablePlayer1.get(0).size(); i++) {
                if (table.tablePlayer1.get(0).get(i).getName().equals("Goliath") ||
                        table.tablePlayer1.get(0).get(i).getName().equals("Warden")) {
                    cardUsesAbility = new CardUsesAbility("cardUsesAbility", cardAttacker.getCardattacker(), cardAttacked.getCardattacked(), "Attacked card is not of type 'Tank'.");
                    output.addPOJO(cardUsesAbility);
                    return;
                }
            }
            if (abilityPlayer2(cardAttacker, cardAttacked, xAttacked)) return;
        }
    }

    private boolean abilityPlayer1(AttackCard cardAttacker, AttackCard cardAttacked, int xAttacker) {
        if (table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).getName().equals("The Ripper")) {
            table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).attackDamage -= 2;
            if (checkAttackDamage(table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).attackDamage)) {
                table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).attackDamage = 0;
            }
            table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).attacked = true;
            return true;
        }

        if (table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).getName().equals("Miraj")) {
            int aux = table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).health;
            table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).health =
                    table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).health;
            table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).health = aux;
            table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).attacked = true;
            return true;
        }

        if (table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).getName().equals("The Cursed One")) {
            int aux = table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).health;
            table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).health =
                    table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).attackDamage;
            table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).attackDamage = aux;
            if (table.tablePlayer2.get(cardAttacked.getX()).get(cardAttacked.getY()).health <= 0) {
                table.tablePlayer2.get(cardAttacked.getX()).remove(cardAttacked.getY());
                if (cardAttacked.getX() == 0) {
                    deckForGameCopy.backRowP2.remove(cardAttacked.getY());
                } else {
                    deckForGameCopy.frontRowP2.remove(cardAttacked.getY());
                }
            }
            table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).attacked = true;
            return true;
        }
        return false;
    }

    private boolean abilityPlayer2(AttackCard cardAttacker, AttackCard cardAttacked, int xAttacked) {
        if (table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).getName().equals("The Ripper")) {
            table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).attackDamage -= 2;
            if (checkAttackDamage(table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).attackDamage)) {
                table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).attackDamage = 0;
            }
            table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).attacked = true;
            return true;
        }

        if (table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).getName().equals("Miraj")) {
            int aux = table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).health;
            table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).health =
                    table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).health;
            table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).health = aux;
            table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).attacked = true;
            return true;
        }

        if (table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).getName().equals("The Cursed One")) {
            int aux = table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).health;
            table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).health =
                    table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).attackDamage;
            table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).attackDamage = aux;
            if (table.tablePlayer1.get(xAttacked).get(cardAttacked.getY()).health <= 0) {
                table.tablePlayer1.get(xAttacked).remove(cardAttacked.getY());
                if (cardAttacked.getX() == 2) {
                    deckForGameCopy.frontRowP1.remove(cardAttacked.getY());
                } else {
                    deckForGameCopy.backRowP1.remove(cardAttacked.getY());
                }
            }
            table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).attacked = true;
            return true;
        }
        return false;
    }

    void useAttackHero(ArrayNode output, AttackCard cardAttacker, Winner winner) {
        UseAttackHero useAttackHero = null;
        int attackerPlayer;
        if (cardAttacker.getX() == 0 || cardAttacker.getX() == 1)
            attackerPlayer = 2;
        else
            attackerPlayer = 1;
        if (attackerPlayer == 1) {
            int xAttacker = cardAttacker.getX() - 2;
            if (table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).frozen) {
                useAttackHero = new UseAttackHero("useAttackHero", cardAttacker.getCardattacker(), "Attacker card is frozen.");
                output.addPOJO(useAttackHero);
                return;
            }
            if (table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).attacked) {
                useAttackHero = new UseAttackHero("useAttackHero", cardAttacker.getCardattacker(), "Attacker card has already attacked this turn.");
                output.addPOJO(useAttackHero);
                return;
            }
            for (int i = 0; i < table.tablePlayer2.get(1).size(); i++) {
                if (table.tablePlayer2.get(1).get(i).getName().equals("Goliath") ||
                        table.tablePlayer2.get(1).get(i).getName().equals("Warden")) {
                    useAttackHero = new UseAttackHero("useAttackHero", cardAttacker.getCardattacker(), "Attacked card is not of type 'Tank'.");
                    output.addPOJO(useAttackHero);
                    return;
                }
            }
            deckForGameCopy.heroPlayer2.health -= table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).attackDamage;
            table.tablePlayer1.get(xAttacker).get(cardAttacker.getY()).attacked = true;
            if (deckForGameCopy.heroPlayer2.health <= 0) {

                GameOver gameOver = new GameOver("Player one killed the enemy hero.");
                output.addPOJO(gameOver);

                winner.setWinPlayer1(winner.getWinPlayer1() + 1);
                winner.setNumberOfGames(winner.getNumberOfGames() + 1);
                return;
            }
        } else {
            if (table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).frozen) {
                useAttackHero = new UseAttackHero("useAttackHero", cardAttacker.getCardattacker(), "Attacker card is frozen.");
                output.addPOJO(useAttackHero);
                return;
            }
            if (table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).attacked) {
                useAttackHero = new UseAttackHero("useAttackHero", cardAttacker.getCardattacker(), "Attacker card has already attacked this turn.");
                output.addPOJO(useAttackHero);
                return;
            }
            for (int i = 0; i < table.tablePlayer1.get(0).size(); i++) {
                if (table.tablePlayer1.get(0).get(i).getName().equals("Goliath") ||
                        table.tablePlayer1.get(0).get(i).getName().equals("Warden")) {
                    useAttackHero = new UseAttackHero("useAttackHero", cardAttacker.getCardattacker(), "Attacked card is not of type 'Tank'.");
                    output.addPOJO(useAttackHero);
                    return;
                }
            }
            deckForGameCopy.heroPlayer1.health -= table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).attackDamage;
            table.tablePlayer2.get(cardAttacker.getX()).get(cardAttacker.getY()).attacked = true;
            if (deckForGameCopy.heroPlayer1.health <= 0) {
                GameOver gameOver = new GameOver("Player two killed the enemy hero.");
                output.addPOJO(gameOver);
                //winner.addWinners(numberOfGames, 2);
                winner.setNumberOfGames(winner.getNumberOfGames() + 1);
                winner.setWinPlayer2(winner.getWinPlayer2() + 1);
                return;
            }
        }
    }

    void useHeroAbility(ArrayNode output, int affectRow, int currentPlayer) {
        UseHeroAbility useHeroAbility = null;
        if (currentPlayer == 1) {
            if (deckForGameCopy.manaP1 < deckForGameCopy.heroPlayer1.getMana()) {
                useHeroAbility = new UseHeroAbility("useHeroAbility", affectRow, "Not enough mana to use hero's ability.");
                output.addPOJO(useHeroAbility);
                return;
            }
            if (deckForGameCopy.heroPlayer1.attacked) {//resetare si hero attacked
                useHeroAbility = new UseHeroAbility("useHeroAbility", affectRow, "Hero has already attacked this turn.");
                output.addPOJO(useHeroAbility);
                return;
            }
            if (deckForGameCopy.heroPlayer1.getName().equals("Lord Royce") || deckForGameCopy.heroPlayer1.getName().equals("Empress Thorina")) {
                if (affectRow == 2 || affectRow == 3) {
                    useHeroAbility = new UseHeroAbility("useHeroAbility", affectRow, "Selected row does not belong to the enemy.");
                    output.addPOJO(useHeroAbility);
                    return;
                }
            }
            if (deckForGameCopy.heroPlayer1.getName().equals("General Kocioraw") || deckForGameCopy.heroPlayer1.getName().equals("King Mudface")) {
                if (affectRow == 0 || affectRow == 1) {
                    useHeroAbility = new UseHeroAbility("useHeroAbility", affectRow, "Selected row does not belong to the current player.");
                    output.addPOJO(useHeroAbility);
                    return;
                }
            }
            if (deckForGameCopy.heroPlayer1.getName().equals("Lord Royce")) {
                //Royce(affectRow, currentPlayer);
                int max = -1;
                int indexmax = -1;
                for (int i = 0; i < table.tablePlayer2.get(affectRow).size(); i++) {
                    if (table.tablePlayer2.get(affectRow).get(i).getAttackDamage() > max) {
                        max = table.tablePlayer2.get(affectRow).get(i).getAttackDamage();
                        indexmax = i;
                    }
                }
                table.tablePlayer2.get(affectRow).get(indexmax).frozen = true;
                if (affectRow == 0) {
                    deckForGameCopy.backRowP2.get(indexmax).frozen = true;
                } else {
                    deckForGameCopy.frontRowP2.get(indexmax).frozen = true;
                }
                deckForGameCopy.heroPlayer1.attacked = true;
                deckForGameCopy.manaP1 -= deckForGameCopy.heroPlayer1.getMana();
                return;
            }
            if (deckForGameCopy.heroPlayer1.getName().equals("Empress Thorina")) {
                int max = -1;
                int indexmax = -1;
                for (int i = 0; i < table.tablePlayer2.get(affectRow).size(); i++) {
                    if (table.tablePlayer2.get(affectRow).get(i).getHealth() > max) {
                        max = table.tablePlayer2.get(affectRow).get(i).getHealth();
                        indexmax = i;
                    }
                }
                table.tablePlayer2.get(affectRow).remove(indexmax);
                if (affectRow == 0) {
                    deckForGameCopy.backRowP2.remove(indexmax);
                } else {
                    deckForGameCopy.frontRowP2.remove(indexmax);
                }
                deckForGameCopy.heroPlayer1.attacked = true;
                deckForGameCopy.manaP1 -= deckForGameCopy.heroPlayer1.getMana();
                return;
            }
            int xAffect = affectRow - 2;
            if (deckForGameCopy.heroPlayer1.getName().equals("General Kocioraw")) {
                for (int i = 0; i < table.tablePlayer1.get(xAffect).size(); i++) {
                    table.tablePlayer1.get(xAffect).get(i).attackDamage += 1;
                }
                deckForGameCopy.heroPlayer1.attacked = true;
                deckForGameCopy.manaP1 -= deckForGameCopy.heroPlayer1.getMana();
                return;
            }
            if (deckForGameCopy.heroPlayer1.getName().equals("King Mudface")) {
                for (int i = 0; i < table.tablePlayer1.get(xAffect).size(); i++) {
                    table.tablePlayer1.get(xAffect).get(i).health += 1;
                }
                deckForGameCopy.heroPlayer1.attacked = true;
                deckForGameCopy.manaP1 -= deckForGameCopy.heroPlayer1.getMana();
                return;
            }
        } else {
            if (deckForGameCopy.manaP2 < deckForGameCopy.heroPlayer2.getMana()) {
                useHeroAbility = new UseHeroAbility("useHeroAbility", affectRow, "Not enough mana to use hero's ability.");
                output.addPOJO(useHeroAbility);
                return;
            }
            if (deckForGameCopy.heroPlayer2.attacked) {//resetare si hero attacked
                useHeroAbility = new UseHeroAbility("useHeroAbility", affectRow, "Hero has already attacked this turn.");
                output.addPOJO(useHeroAbility);
                return;
            }
            if (deckForGameCopy.heroPlayer2.getName().equals("Lord Royce") || deckForGameCopy.heroPlayer2.getName().equals("Empress Thorina")) {
                if (affectRow == 0 || affectRow == 1) {
                    useHeroAbility = new UseHeroAbility("useHeroAbility", affectRow, "Selected row does not belong to the enemy.");
                    output.addPOJO(useHeroAbility);
                    return;
                }
            }
            if (deckForGameCopy.heroPlayer2.getName().equals("General Kocioraw") || deckForGameCopy.heroPlayer2.getName().equals("King Mudface")) {
                if (affectRow == 2 || affectRow == 3) {
                    useHeroAbility = new UseHeroAbility("useHeroAbility", affectRow, "Selected row does not belong to the current player.");
                    output.addPOJO(useHeroAbility);
                    return;
                }
            }
            int xAffected = affectRow - 2;
            if (deckForGameCopy.heroPlayer2.getName().equals("Lord Royce")) {
                //Royce(affectRow, currentPlayer);
                int max = -1;
                int indexmax = -1;
                for (int i = 0; i < table.tablePlayer1.get(xAffected).size(); i++) {
                    if (table.tablePlayer1.get(xAffected).get(i).getAttackDamage() > max) {
                        max = table.tablePlayer1.get(xAffected).get(i).getAttackDamage();
                        indexmax = i;
                    }
                }
                table.tablePlayer1.get(xAffected).get(indexmax).frozen = true;
                if (affectRow == 2) {
                    deckForGameCopy.frontRowP1.get(indexmax).frozen = true;
                } else {
                    deckForGameCopy.backRowP1.get(indexmax).frozen = true;
                }
                deckForGameCopy.heroPlayer2.attacked = true;
                deckForGameCopy.manaP2 -= deckForGameCopy.heroPlayer2.getMana();
                return;
            }
            if (deckForGameCopy.heroPlayer2.getName().equals("Empress Thorina")) {
                int max = -1;
                int indexmax = -1;
                for (int i = 0; i < table.tablePlayer1.get(xAffected).size(); i++) {
                    if (table.tablePlayer1.get(xAffected).get(i).getHealth() > max) {
                        max = table.tablePlayer1.get(xAffected).get(i).getHealth();
                        indexmax = i;
                    }
                }
                table.tablePlayer1.get(xAffected).remove(indexmax);
                if (affectRow == 2) {
                    deckForGameCopy.frontRowP1.remove(indexmax);
                } else {
                    deckForGameCopy.backRowP1.remove(indexmax);
                }
                deckForGameCopy.heroPlayer2.attacked = true;
                deckForGameCopy.manaP2 -= deckForGameCopy.heroPlayer2.getMana();
                return;
            }
            if (deckForGameCopy.heroPlayer2.getName().equals("General Kocioraw")) {
                for (int i = 0; i < table.tablePlayer2.get(affectRow).size(); i++) {
                    table.tablePlayer2.get(affectRow).get(i).attackDamage += 1;
                }
                deckForGameCopy.heroPlayer2.attacked = true;
                deckForGameCopy.manaP2 -= deckForGameCopy.heroPlayer2.getMana();
                return;
            }
            if (deckForGameCopy.heroPlayer2.getName().equals("King Mudface")) {
                for (int i = 0; i < table.tablePlayer2.get(affectRow).size(); i++) {
                    table.tablePlayer2.get(affectRow).get(i).health += 1;
                }
                deckForGameCopy.heroPlayer2.attacked = true;
                deckForGameCopy.manaP2 -= deckForGameCopy.heroPlayer2.getMana();
                return;
            }
        }
    }

}


