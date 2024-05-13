package src.factories;

import src.characters.Enemy;

public class SeaEnemyFactory implements EnemyFactory {

    @Override
    public Enemy createWarrior() {
        return new Enemy("Sea Warrior", 11, 9, 8);
    }

    @Override
    public Enemy createSorcerer() {
        return new Enemy("Sea Sorcerer", 7, 7, 10);
    }

    @Override
    public Enemy createMutant() {
        return new Enemy("Sea Mutant", 13, 7, 6);
    }
}
