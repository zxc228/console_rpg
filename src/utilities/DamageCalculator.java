package src.utilities;

import src.characters.Character;

public class DamageCalculator {
    private static DamageCalculator instance;

    private DamageCalculator() {
        // Приватный конструктор предотвращает создание новых экземпляров
    }

    public static synchronized DamageCalculator getInstance() {
        if (instance == null) {
            instance = new DamageCalculator();
        }
        return instance;
    }

    public int calculateDamage(Character attacker, Character target) {
        int baseDamage = attacker.getStrength();
        // Пример логики: урон уменьшается на 50% от выносливости цели
        int damage = baseDamage - (target.getEndurance() / 2);
        return Math.max(damage, 0); // Урон не может быть отрицательным
    }
}
