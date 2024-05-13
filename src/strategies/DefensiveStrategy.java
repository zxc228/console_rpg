package src.strategies;

import src.characters.Character;

public class DefensiveStrategy implements Strategy {
    @Override
    public void execute(Character character) {
        System.out.println(character.getName() + " is using defensive strategy.");
        character.setEndurance(character.getEndurance() + 2);
    }
}
