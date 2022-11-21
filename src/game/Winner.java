package game;

public class Winner {
    private int numberOfGames;
    private int winPlayer1;
    private int winPlayer2;

    public Winner(int numberOfGames, int winPlayer1,int winPlayer2) {
        this.numberOfGames = numberOfGames;
        this.winPlayer1 = winPlayer1;
        this.winPlayer2 = winPlayer2;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public int getWinPlayer1() {
        return winPlayer1;
    }

    public int getWinPlayer2() {
        return winPlayer2;
    }


    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public void setWinPlayer1(int winPlayer1) {
        this.winPlayer1 = winPlayer1;
    }

    public void setWinPlayer2(int winPlayer2) {
        this.winPlayer2 = winPlayer2;
    }
}
