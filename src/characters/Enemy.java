package src.characters;

public class Enemy extends Character {
    public Enemy(String name, int strength, int endurance, int agility) {
        super(name, strength, endurance, agility);
    }

    @Override
    public void attack(Character target) {
        System.out.println(this.getName() + " attacks " + target.getName() + " fiercely with power " + this.getStrength());
        target.setEndurance(target.getEndurance() - this.getStrength());
    }

    @Override
    public void defend() {
        System.out.println(this.getName() + " tries to defend with agility " + this.getAgility());
    }

    public void decideNextAction(Character target) {
        if (this.getStrength() > target.getEndurance()) {
            attack(target);
        } else {
            defend();
        }
    }
}
