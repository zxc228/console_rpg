package src.utilities;

import src.characters.Character;

public class DamageCalculator {
    private static DamageCalculator instance;

    private DamageCalculator() {
        
    }

    public static synchronized DamageCalculator getInstance() {
        if (instance == null) {
            instance = new DamageCalculator();
        }
        return instance;
    }

    public int calculateDamage(Character attacker, Character target) {
        int baseDamage = attacker.getStrength();
        
        int damage = baseDamage - (target.getEndurance() / 2);
        return Math.max(damage, 0); 
    }
}
