package game;

import fileio.ActionsInput;

public class Actions {
    String command;
    int handIdx;
    AttackCard cardAttacker = new AttackCard();
    AttackCard cardAttacked = new AttackCard();
    int affectedRow, playerIdx;
    int x, y;
    public Actions(final ActionsInput actionsInput) {
        this.command = actionsInput.getCommand();
        this.handIdx = actionsInput.getHandIdx();
        this.affectedRow = actionsInput.getAffectedRow();
        this.playerIdx = actionsInput.getPlayerIdx();
        if (actionsInput.getCardAttacker() != null && actionsInput.getCardAttacked() != null) {
            this.cardAttacker.setCardattacker(actionsInput.getCardAttacker());
            this.cardAttacked.setCardattacked(actionsInput.getCardAttacked());
            this.cardAttacker.setX(actionsInput.getCardAttacker().getX());
            this.cardAttacker.setY(actionsInput.getCardAttacker().getY());
            this.cardAttacked.setX(actionsInput.getCardAttacked().getX());
            this.cardAttacked.setY(actionsInput.getCardAttacked().getY());
        } else if (actionsInput.getCardAttacker() != null) {
            this.cardAttacker.setCardattacker(actionsInput.getCardAttacker());
            this.cardAttacker.setX(actionsInput.getCardAttacker().getX());
            this.cardAttacker.setY(actionsInput.getCardAttacker().getY());
        }

        this.x = actionsInput.getX();
        this.y = actionsInput.getY();
    }

    public Actions(final Actions actions) {
        this.command = actions.command;
        this.handIdx = actions.handIdx;

        if (actions.cardAttacker != null && actions.cardAttacked != null) {
            this.cardAttacker = actions.cardAttacker;
            this.cardAttacked = actions.cardAttacked;
            this.cardAttacker.setX(actions.cardAttacker.getX());
            this.cardAttacker.setY(actions.cardAttacker.getY());
            this.cardAttacked.setX(actions.cardAttacked.getX());
            this.cardAttacked.setY(actions.cardAttacked.getY());
        } else if (actions.cardAttacker != null) {
            this.cardAttacker = actions.cardAttacker;
            this.cardAttacker.setX(actions.cardAttacker.getX());
            this.cardAttacker.setY(actions.cardAttacker.getY());
        }

        this.affectedRow = actions.affectedRow;
        this.playerIdx = actions.playerIdx;
        this.x = actions.x;
        this.y = actions.y;
    }
}
