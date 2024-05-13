package src.states;

import src.characters.Character;

public class NormalState implements State {
    @Override
    public void handle(Character character){
        System.out.println(character.getName()+ " is in normal state and acts normally.");
    }
}