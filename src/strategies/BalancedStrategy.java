package src.strategies;

import src.characters.Character;

public class BalancedStrategy implements Strategy {
    @Override
    public void execute(Character character) {
        System.out.println(character.getName() + " is using balanced strategy.");
        character.setStrength(character.getStrength() + 2);
        character.setEndurance(character.getEndurance() + 2);
    }
}
