package src.characters;

public class Hero extends Character {
    public Hero(String name, int strength, int endurance, int agility) {
        super(name, strength, endurance, agility);
    }

    @Override
    public void attack(Character target) {
        System.out.println(this.getName() + " attacks " + target.getName() + " with power " + this.getStrength());
    }

    @Override
    public void defend() {
        System.out.println(this.getName() + " defends with endurance " + this.getEndurance());
    }
}
