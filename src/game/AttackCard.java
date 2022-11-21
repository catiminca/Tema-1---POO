package game;

import fileio.Coordinates;

public class AttackCard {
    private int x, y;
    private Coordinates cardattacker;
    private Coordinates cardattacked;
    public AttackCard(final int x, final int y, final Coordinates cardattacker,
                      final Coordinates cardattacked) {
        this.x = x;
        this.y = y;
        this.cardattacker = cardattacker;

        this.cardattacked = cardattacked;
    }
    public AttackCard() { }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public Coordinates getCardattacker() {
        return cardattacker;
    }

    public void setCardattacker(final Coordinates cardattacker) {
        this.cardattacker = cardattacker;
    }

    public Coordinates getCardattacked() {
        return cardattacked;
    }

    public void setCardattacked(final Coordinates cardattacked) {
        this.cardattacked = cardattacked;
    }
}
