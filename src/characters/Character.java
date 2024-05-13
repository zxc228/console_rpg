package src.characters;

import src.states.State;
import src.states.NormalState;
import src.states.WoundedState;
import src.states.ParalyzedState;

public abstract class Character {
    protected String name;
    protected int strength;
    protected int endurance;
    protected int agility;
    private State state;

    public Character(String name, int strength, int endurance, int agility) {
        this.name = name;
        this.strength = strength;
        this.endurance = endurance;
        this.agility = agility;
        this.state = new NormalState();
    }

    public abstract void attack(Character target);
    
    public void setState(State state) {
        this.state = state;
    }

    public void performAction() {
        state.handle(this);
    }

    public void transitionToNormal() {
        setState(new NormalState());
        System.out.println(this.name + " transitions to normal state.");
    }

    public void transitionToWounded() {
        setState(new WoundedState());
        System.out.println(this.name + " transitions to wounded state.");
    }

    public void transitionToParalyzed() {
        setState(new ParalyzedState());
        System.out.println(this.name + " transitions to paralyzed state.");
    }

    public abstract void defend();

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
}
