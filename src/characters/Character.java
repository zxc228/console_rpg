package src.characters;

import src.states.NormalState;
import src.states.State;
import src.states.ParalyzedState;
import src.states.WoundedState;

public abstract class Character {
    protected String name;
    protected int strength;
    protected int endurance;
    protected int agility;
    protected State state;

    public Character(String name, int strength, int endurance, int agility) {
        this.name = name;
        this.strength = strength;
        this.endurance = endurance;
        this.agility = agility;
        this.state = new NormalState(); 
    }

    public abstract void attack(Character target);

    public void defend() {
        state.handle(this); 
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void transitionToNormal() {
        this.state = new NormalState();
    }

    public void transitionToWounded() {
        this.state = new WoundedState();
    }

    public void transitionToParalyzed() {
        this.state = new ParalyzedState();
    }

    public void performAction() {
        state.handle(this); 
    }
    
    public void displayStats() {
        System.out.printf("Name      : %s%n", this.name);
        System.out.printf("Strength  : %d%n", this.strength);
        System.out.printf("Endurance : %d%n", this.endurance);
        System.out.printf("Agility   : %d%n", this.agility);
        System.out.printf("State     : %s%n", this.state.getClass().getSimpleName());
    }
}
