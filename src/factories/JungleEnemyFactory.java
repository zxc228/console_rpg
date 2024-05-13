package src.factories;

import src.characters.Enemy;

public class JungleEnemyFactory implements EnemyFactory {

    @Override
    public Enemy createWarrior() {
        return new Enemy("Jungle Warrior", 10, 10, 7);
    }

    @Override
    public Enemy createSorcerer() {
        return new Enemy("Jungle Sorcerer", 8, 6, 9);
    }

    @Override
    public Enemy createMutant() {
        return new Enemy("Jungle Mutant", 12, 8, 5);
    }
}
