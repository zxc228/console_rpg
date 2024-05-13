package src.strategies;

import src.characters.Character;

public class AggressiveStrategy implements Strategy {
    @Override
    public void execute(Character character) {
        System.out.println(character.getName() + " is using aggressive strategy.");
        character.setStrength(character.getStrength() + 3);
    }
}
