package src.actions;

import src.characters.Character;

public class NormalAttackDecorator extends Action {
    private Action wrappedAction;

    public NormalAttackDecorator(Action wrappedAction) {
        this.wrappedAction = wrappedAction;
    }

    @Override
    public void execute(Character character, Character target) {
        System.out.println("Executing a normal attack");
        wrappedAction.execute(character, target);
        
    }
}
