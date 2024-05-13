package src.actions;

import src.characters.Character;

public abstract class Action {
    public abstract void execute(Character character, Character target);
}
