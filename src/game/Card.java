package game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CardInput;

import java.util.ArrayList;

public class Card {
    private int mana;
    private String description, name;
    private ArrayList<String> colors = new ArrayList<>();
    private int attackDamage, health;

    public Card(final CardInput card) {
        this.mana = card.getMana();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
        this.health = card.getHealth();
        this.attackDamage = card.getAttackDamage();

    }
    public Card(final Card currentCard) {
        this.mana = currentCard.mana;
        this.description = currentCard.description;
        this.colors = currentCard.colors;
        this.name = currentCard.name;
        this.attackDamage = currentCard.attackDamage;
        this.health = currentCard.health;
    }
    public Card() { }

    public String toString() {
        return "Card{"
                +  "mana="
                + mana
                +  ", description='"
                + description
                + '\''
                + ", colors="
                + colors
                + ", name='"
                +  ""
                + name
                + '\''
                + '}';
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMana() {
        return mana;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(ArrayList<String> color) {
        this.colors = color;
    }
}

class Minion extends Card {
    int attackDamage, health;
    @JsonIgnore
    boolean frozen;
    @JsonIgnore
    String type;
    @JsonIgnore
    boolean attacked;

    public Minion(CardInput card) {
        super(card);
        this.health = card.getHealth();
        this.attackDamage = card.getAttackDamage();
        this.frozen = false;
        this.attacked = false;

        if(this.getName().equals("The Ripper") || this.getName().equals("Miraj") || this.getName().equals("Goliath") ||
            this.getName().equals("Warden")) {
            this.type = "front";
        } else {
            this.type = "back";
        }

    }
    public Minion(Minion currentCard){
        this.setHealth(currentCard.getHealth());
        this.setAttackDamage(currentCard.getAttackDamage());
        this.setMana(currentCard.getMana());
        this.setDescription(currentCard.getDescription());
        this.setColor(currentCard.getColors());
        this.setName(currentCard.getName());
        this.frozen = currentCard.frozen;
        this.type = currentCard.type;
        this.attacked = currentCard.attacked;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAttacked() {
        return attacked;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }
}

class Hero extends Card {
    int health;
    @JsonIgnore
    int attackDamage;
    @JsonIgnore
    boolean attacked;
    Hero(CardInput card) {
        super(card);
        this.health = 30;
        this.attacked = false;
    }
    public Hero(Hero hero){
        this.setMana(hero.getMana());
        this.setName(hero.getName());
        this.setColor(hero.getColors());
        this.setDescription(hero.getDescription());
        this.setHealth(hero.getHealth());
        this.setAttacked(hero.isAttacked());
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isAttacked() {
        return attacked;
    }


    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

}

class Environment extends Card {
    @JsonIgnore
    private int attackDamage, health;
    public Environment(CardInput card) {
        super(card);
    }

    public Environment(Card card) {
        this.setMana(card.getMana());
        this.setColor(card.getColors());
        this.setDescription(card.getDescription());
        this.setName(card.getName());
    }

}

