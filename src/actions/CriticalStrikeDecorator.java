package src.actions;

import src.characters.Character;

public class CriticalStrikeDecorator extends Action {
    private Action wrappedAction;

    public CriticalStrikeDecorator(Action wrappedAction) {
        this.wrappedAction = wrappedAction;
    }

    @Override
    public void execute(Character character, Character target) {
        System.out.println("Executing a critical strike!");
        character.setStrength(character.getStrength() + 10);
        wrappedAction.execute(character, target);
        character.setStrength(character.getStrength() - 10);
    }
}
