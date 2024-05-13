package src.controllers;

import src.characters.Character;
import src.characters.Hero;
import src.characters.Enemy;
import src.factories.EnemyFactory;
import src.factories.JungleEnemyFactory;
import src.factories.SeaEnemyFactory;
import src.strategies.Strategy;
import src.strategies.AggressiveStrategy;
import src.strategies.BalancedStrategy;
import src.actions.Action;
import src.actions.PowerfulAttackDecorator;
import src.actions.CriticalStrikeDecorator;
import src.utilities.DamageCalculator;

import java.util.Scanner;
import java.util.Random;

public class GameController {
    private DamageCalculator damageCalculator;
    private Hero hero;
    private Enemy enemy;
    private Strategy strategy;
    private Action action;
    private Scanner scanner;
    private int enemyCount;
    private int totalDamageDealt;
    private int totalDamageReceived;

    // Переменные для хранения приростов
    private int heroStrengthIncrease;
    private int heroEnduranceIncrease;
    private int heroAgilityIncrease;
    private int enemyStrengthIncrease;
    private int enemyEnduranceIncrease;
    private int enemyAgilityIncrease;

    public GameController() {
        damageCalculator = DamageCalculator.getInstance();
        scanner = new Scanner(System.in);
        enemyCount = 0;
        totalDamageDealt = 0;
        totalDamageReceived = 0;
    }

    public void initializeGame() {
        System.out.println("Enter your hero's name:");
        String heroName = scanner.nextLine();

        chooseDifficulty();

        Random rand = new Random();
        int strength = 5 + rand.nextInt(11); // Сила от 5 до 15
        int endurance = 20 + rand.nextInt(11); // Выносливость от 20 до 30
        int agility = 5 + rand.nextInt(6); // Ловкость от 5 до 10

        hero = new Hero(heroName, strength, endurance, agility);

        // Display initial hero stats
        System.out.println("\n=== Initial Hero Stats ===");
        System.out.printf("Name      : %s%n", hero.getName());
        System.out.printf("Strength  : %d%n", hero.getStrength());
        System.out.printf("Endurance : %d%n", hero.getEndurance());
        System.out.printf("Agility   : %d%n", hero.getAgility());
        System.out.println("==========================\n");

        chooseEnemy();
        chooseStrategy();
        defineAction();

        displayStats();
    }

    private void chooseDifficulty() {
        System.out.println("Choose difficulty level:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                heroStrengthIncrease = 2;
                heroEnduranceIncrease = 5;
                heroAgilityIncrease = 2;
                enemyStrengthIncrease = 2;
                enemyEnduranceIncrease = 4;
                enemyAgilityIncrease = 1;
                System.out.println("Difficulty set to Easy.");
                break;
            case 2:
                heroStrengthIncrease = 1;
                heroEnduranceIncrease = 3;
                heroAgilityIncrease = 1;
                enemyStrengthIncrease = 4;
                enemyEnduranceIncrease = 8;
                enemyAgilityIncrease = 3;
                System.out.println("Difficulty set to Medium.");
                break;
            case 3:
                heroStrengthIncrease = 1;
                heroEnduranceIncrease = 2;
                heroAgilityIncrease = 1;
                enemyStrengthIncrease = 5;
                enemyEnduranceIncrease = 10;
                enemyAgilityIncrease = 4;
                System.out.println("Difficulty set to Hard.");
                break;
            default:
                System.out.println("Invalid choice, defaulting to Medium.");
                heroStrengthIncrease = 1;
                heroEnduranceIncrease = 3;
                heroAgilityIncrease = 1;
                enemyStrengthIncrease = 4;
                enemyEnduranceIncrease = 8;
                enemyAgilityIncrease = 3;
                break;
        }
    }

    private void chooseEnemy() {
        System.out.println("Choose enemy type:");
        System.out.println("1. Jungle");
        System.out.println("2. Sea");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        EnemyFactory factory;
        System.out.println("Choose enemy class:");
        System.out.println("1. Warrior");
        System.out.println("2. Sorcerer");
        System.out.println("3. Mutant");
        int classChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice == 1) {
            factory = new JungleEnemyFactory();
        } else {
            factory = new SeaEnemyFactory();
        }

        switch (classChoice) {
            case 1:
                enemy = factory.createWarrior();
                break;
            case 2:
                enemy = factory.createSorcerer();
                break;
            case 3:
                enemy = factory.createMutant();
                break;
            default:
                System.out.println("Invalid choice, defaulting to Warrior.");
                enemy = factory.createWarrior();
                break;
        }

        // Увеличение характеристик врага с каждым новым врагом
        enemy.setStrength(enemy.getStrength() + enemyCount * enemyStrengthIncrease);
        enemy.setEndurance(enemy.getEndurance() + enemyCount * enemyEnduranceIncrease);
        enemy.setAgility(enemy.getAgility() + enemyCount * enemyAgilityIncrease);
    }

    private void chooseStrategy() {
        System.out.println("Choose strategy:");
        System.out.println("1. Aggressive");
        System.out.println("2. Balanced");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice == 1) {
            strategy = new AggressiveStrategy();
        } else {
            strategy = new BalancedStrategy();
        }
    }

    private void defineAction() {
        action = new PowerfulAttackDecorator(new Action() {
            @Override
            public void execute(Character character, Character target) {
                System.out.println(character.getName() + " attacks " + target.getName() + " with base strength " + character.getStrength());
                int damage = damageCalculator.calculateDamage(character, target);
                target.setEndurance(target.getEndurance() - damage);
                System.out.println(target.getName() + " receives " + damage + " damage, remaining endurance: " + target.getEndurance());
                totalDamageDealt += damage;
            }
        });
    }

    private void displayStats() {
        System.out.println("\n=== Hero Stats ===");
        System.out.printf("Name      : %s%n", hero.getName());
        System.out.printf("Strength  : %d%n", hero.getStrength());
        System.out.printf("Endurance : %d%n", hero.getEndurance());
        System.out.printf("Agility   : %d%n", hero.getAgility());
        System.out.println("==================");

        System.out.println("\n=== Enemy Stats ===");
        System.out.printf("Name      : %s%n", enemy.getName());
        System.out.printf("Strength  : %d%n", enemy.getStrength());
        System.out.printf("Endurance : %d%n", enemy.getEndurance());
        System.out.printf("Agility   : %d%n", enemy.getAgility());
        System.out.println("==================\n");
    }

    public void startBattle() {
        System.out.println("Battle starts between " + hero.getName() + " and " + enemy.getName());
        
        strategy.execute(hero);
        action.execute(hero, enemy);
        
        if (enemy.getEndurance() > 0) {
            System.out.println("Enemy's turn:");
            int damageReceived = damageCalculator.calculateDamage(enemy, hero);
            hero.setEndurance(hero.getEndurance() - damageReceived);
            System.out.println(hero.getName() + " receives " + damageReceived + " damage, remaining endurance: " + hero.getEndurance());
            totalDamageReceived += damageReceived;
        }

        // Check hero's endurance and apply state transitions
        if (hero.getEndurance() < 5 && hero.getEndurance() > 0) {
            hero.transitionToWounded();
        }
        if (hero.getEndurance() <= 0) {
            hero.transitionToParalyzed();
            System.out.println("Hero is defeated!");
        }

        // Check enemy's endurance and apply state transitions
        if (enemy.getEndurance() < 5 && enemy.getEndurance() > 0) {
            System.out.println(enemy.getName() + " is wounded.");
        }
        if (enemy.getEndurance() <= 0) {
            System.out.println(enemy.getName() + " is defeated!");
        }

        displayStats();
    }

    public void runGame() {
        initializeGame();
        
        while (hero.getEndurance() > 0) {
            startBattle();
            if (hero.getEndurance() <= 0) {
                System.out.println("Game Over. You have been defeated.");
                break;
            }
            if (enemy.getEndurance() <= 0) {
                System.out.println("Congratulations! You defeated the enemy.");
                System.out.println("Do you want to continue? (yes/no)");
                String continueGame = scanner.nextLine();
                if (!continueGame.equalsIgnoreCase("yes")) {
                    System.out.println("Game Over. Thanks for playing!");
                    break;
                }
                // Increase hero stats after defeating an enemy
                hero.setStrength(hero.getStrength() + heroStrengthIncrease);
                hero.setEndurance(hero.getEndurance() + heroEnduranceIncrease); // Heal hero
                hero.setAgility(hero.getAgility() + heroAgilityIncrease);

                enemyCount++;
                System.out.println("Choose your next enemy:");
                chooseEnemy();
                chooseStrategy();
                defineAction();
            }
        }
        
        displayFinalStats();
        System.out.println("Game Over. Thanks for playing!");
    }
    
    private void displayFinalStats() {
        System.out.println("\n=== Final Hero Stats ===");
        System.out.printf("Name              : %s%n", hero.getName());
        System.out.printf("Strength          : %d%n", hero.getStrength());
        System.out.printf("Endurance         : %d%n", hero.getEndurance());
        System.out.printf("Agility           : %d%n", hero.getAgility());
        System.out.printf("Enemies Defeated  : %d%n", enemyCount);
        System.out.printf("Total Damage Dealt: %d%n", totalDamageDealt);
        System.out.printf("Total Damage Received: %d%n", totalDamageReceived);
        System.out.println("========================\n");
    }
    
    public Hero getHero() {
        return hero;
    }
    
    public Enemy getEnemy() {
        return enemy;
    }
}
