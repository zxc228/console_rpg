package src.actions;

import src.characters.Character;

public class PowerfulAttackDecorator extends Action {
    private Action wrappedAction;

    public PowerfulAttackDecorator(Action wrappedAction) {
        this.wrappedAction = wrappedAction;
    }

    @Override
    public void execute(Character character, Character target) {
        System.out.println("Enhancing attack with additional strength.");
        character.setStrength(character.getStrength() + 5);
        wrappedAction.execute(character, target);
    }
}
