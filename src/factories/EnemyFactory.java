package src.factories;

import src.characters.Enemy;

public interface EnemyFactory {
    Enemy createWarrior();
    Enemy createSorcerer();
    Enemy createMutant();
}
