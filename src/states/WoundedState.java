package src.states;

import src.characters.Character;

public class WoundedState implements State {
    @Override
    public void handle(Character character) {
        System.out.println(character.getName() + " is wounded and acts with less efficiency.");
        character.setStrength(character.getStrength() - 5);
    }
}
