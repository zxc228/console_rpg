package src.states;

import src.characters.Character;

public class ParalyzedState implements State {
    @Override
    public void handle(Character character) {
        System.out.println(character.getName() + " is paralyzed and cannot act.");
        
    }
}
