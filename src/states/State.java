package src.states;

import src.characters.Character;

public interface State {
    void handle(Character character);
} 