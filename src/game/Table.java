package game;

import java.util.ArrayList;
import java.util.Objects;

public class Table {
    ArrayList<ArrayList<Minion>> tablePlayer1 = new ArrayList<>();
    ArrayList<ArrayList<Minion>> tablePlayer2 = new ArrayList<>();

    public Table() {
        for (int i = 0; i < 2; i++) {
            tablePlayer1.add(new ArrayList<>());
            tablePlayer2.add(new ArrayList<>());
        }
    }

    //copie la fiecare row
    ArrayList<Minion> copyRow(int row) {
        ArrayList<Minion> copy = new ArrayList<>();
        ArrayList<Minion> copiedRow = switch (row) {
            case 0 -> tablePlayer2.get(0);
            case 1 -> tablePlayer2.get(1);
            case 2 -> tablePlayer1.get(0);
            case 3 -> tablePlayer1.get(1);
            default -> null;
        };
        for(int i = 0; i < Objects.requireNonNull(copiedRow).size(); i++) {
            Minion minion = new Minion(copiedRow.get(i));
            copy.add(minion);
        }
        return copy;
    }
    void addCardForPlayer(Minion card, int player, int row) {
        if(player == 1){
            tablePlayer1.get(row).add(card);
        }
        if(player == 2){
            tablePlayer2.get(row).add(card);
        }
    }
}
