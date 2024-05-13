package src.controllers;

import src.characters.Character;
import src.characters.Hero;
import src.characters.Enemy;
import src.factories.EnemyFactory;
import src.factories.JungleEnemyFactory;
import src.factories.SeaEnemyFactory;
import src.states.NormalState;
import src.states.ParalyzedState;
import src.states.WoundedState;
import src.strategies.Strategy;
import src.strategies.AggressiveStrategy;
import src.strategies.BalancedStrategy;
import src.strategies.DefensiveStrategy;
import src.actions.Action;
import src.actions.PowerfulAttackDecorator;
import src.actions.CriticalStrikeDecorator;
import src.actions.NormalAttackDecorator;
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

    private int heroStrengthIncrease;
    private int heroEnduranceIncrease;
    private int heroAgilityIncrease;
    private int difficulty;

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
        int strength = 5 + rand.nextInt(11); 
        int endurance = 20 + rand.nextInt(11); 
        int agility = 5 + rand.nextInt(6); 

        hero = new Hero(heroName, strength, endurance, agility);

        // Display initial hero stats
        System.out.println("\n=== Initial Hero Stats ===");
        hero.displayStats();
        System.out.println("==========================\n");

        chooseEnemy();
        chooseStrategy();
        defineAction();

        
    }

    private void chooseDifficulty() {
        System.out.println("Choose difficulty level:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        difficulty = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (difficulty) {
            case 1:
                heroStrengthIncrease = 2;
                heroEnduranceIncrease = 5;
                heroAgilityIncrease = 2;
                System.out.println("Difficulty set to Easy.");
                break;
            case 2:
                heroStrengthIncrease = 1;
                heroEnduranceIncrease = 3;
                heroAgilityIncrease = 1;
                System.out.println("Difficulty set to Medium.");
                break;
            case 3:
                heroStrengthIncrease = 1;
                heroEnduranceIncrease = 2;
                heroAgilityIncrease = 1;
                System.out.println("Difficulty set to Hard.");
                break;
            default:
                System.out.println("Invalid choice, defaulting to Medium.");
                heroStrengthIncrease = 1;
                heroEnduranceIncrease = 3;
                heroAgilityIncrease = 1;
                break;
        }
    }
    private void updateState(Character character) {
        int endurance = character.getEndurance();
        if (endurance > 20) {
            character.setState(new NormalState());
        } else if (endurance > 10) {
            character.setState(new WoundedState());
        } else {
            character.setState(new ParalyzedState());
        }
    }

    private void chooseEnemy() {
        Random rand = new Random();
    
        
        int typeChoice = rand.nextInt(2) + 1;
        EnemyFactory factory;
        if (typeChoice == 1) {
            factory = new JungleEnemyFactory();
        } else {
            factory = new SeaEnemyFactory();
        }
    
        
        int classChoice = rand.nextInt(3) + 1;
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
        }
    
        
        if (enemyCount > 0) {
            int baseIncrease = enemyCount;
            int strengthIncrease = 0;
            int enduranceIncrease = 0;
            int agilityIncrease = 0;
    
            switch (difficulty) {
                case 1: // Easy
                    strengthIncrease = baseIncrease;
                    enduranceIncrease = baseIncrease;
                    agilityIncrease = baseIncrease;
                    break;
                case 2: // Medium
                    int mediumGrowthType = rand.nextInt(2) + 1; // 1 or 2
                    strengthIncrease = (int) Math.pow(baseIncrease, mediumGrowthType);
                    enduranceIncrease = (int) Math.pow(baseIncrease, mediumGrowthType);
                    agilityIncrease = (int) Math.pow(baseIncrease, mediumGrowthType);
                    break;
                case 3: // Hard
                    int hardGrowthType = rand.nextInt(3) + 1; // 1, 2 or 3
                    strengthIncrease = (int) Math.pow(baseIncrease, hardGrowthType);
                    enduranceIncrease = (int) Math.pow(baseIncrease, hardGrowthType);
                    agilityIncrease = (int) Math.pow(baseIncrease, hardGrowthType);
                    break;
            }
    
            System.out.println("Base Increase: " + baseIncrease);
            System.out.println("Strength Increase: " + strengthIncrease);
            System.out.println("Endurance Increase: " + enduranceIncrease);
            System.out.println("Agility Increase: " + agilityIncrease);
    
            enemy.setStrength(enemy.getStrength() + strengthIncrease);
            enemy.setEndurance(enemy.getEndurance() + enduranceIncrease);
            enemy.setAgility(enemy.getAgility() + agilityIncrease);
    
            
        }
    
        System.out.println("Generating new enemy...");
        pause(1000);
        System.out.println("Your enemy is " + enemy.getName());
        System.out.println();
        System.out.println("Enemy's stats");
        enemy.displayStats();
    }
    
    

    private void chooseStrategy() {
        System.out.println();
        System.out.println("Choose strategy:");
        System.out.println("1. Aggressive - Your attacks will be stronger.");
        System.out.println("2. Balanced - Your attacks will be balanced.");
        System.out.println("3. Defensive - Your endurance will be higher.");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice == 1) {
            strategy = new AggressiveStrategy();
        } else if (choice == 2) {
            strategy = new BalancedStrategy();
        } else if (choice == 3) {
            strategy = new DefensiveStrategy();
        } else {
            System.out.println("Invalid choice, defaulting to Balanced.");
            strategy = new BalancedStrategy();
        }
    }

    private void defineAction() {
    Random rand = new Random();
    int attackType = rand.nextInt(100); // 0-99

    Action baseAction = new Action() {
        @Override
        public void execute(Character character, Character target) {
            System.out.println(character.getName() + " attacks " + target.getName() + " with base strength " + character.getStrength());
            pause(1000);
            int damage = damageCalculator.calculateDamage(character, target);
            target.setEndurance(target.getEndurance() - damage);
            System.out.println(target.getName() + " receives " + damage + " damage, remaining endurance: " + target.getEndurance());
            totalDamageDealt += damage;
            if (target.getEndurance() <= 0) {
                return;
            }
            pause(1000);
        }
    };

    if (attackType < 50) {
        action = new NormalAttackDecorator(baseAction);
    } else if (attackType < 85) {
        action = new PowerfulAttackDecorator(baseAction);
    } else {
        action = new CriticalStrikeDecorator(baseAction);
    }
}

    

    public void startBattle() {
        System.out.println("Battle starts between " + hero.getName() + " and " + enemy.getName());
        pause(1000);
        strategy.execute(hero);
        pause(1000);

        defineAction();
        action.execute(hero, enemy);

        if (enemy.getEndurance() <= 0) {
            System.out.println(enemy.getName() + " is defeated!");
            enemyCount++;
            return;
        }

        System.out.println("Enemy's turn:");
        pause(1000);
        enemy.decideNextAction(hero);
        updateState(enemy);
        pause(1000);
        if (hero.getEndurance() <= 0) {
            System.out.println(hero.getName() + " is defeated! Game Over.");
            return;
        }

        
    }

    public void runGame() {
        initializeGame();

        while (true) {
            startBattle();
            if (hero.getEndurance() <= 0) {
                displayFinalStats();
                System.out.println("Game Over. Thanks for playing!");
                break;
            }
            if (enemy.getEndurance() <= 0) {
                System.out.println("Congratulations! You defeated the enemy.");
                pause(1000);
                System.out.println("\n=== Hero Stats After Battle ===");
                hero.displayStats();
                System.out.println("==================");
                System.out.println("Do you want to continue? (yes/no)");
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("no")) {
                    displayFinalStats();
                    System.out.println("Game Over. Thanks for playing!");
                    break;
                }
                // Increase hero stats after defeating an enemy
                hero.setStrength(hero.getStrength() + heroStrengthIncrease);
                hero.setEndurance(hero.getEndurance() + heroEnduranceIncrease); // Heal hero
                hero.setAgility(hero.getAgility() + heroAgilityIncrease);

                
                
                chooseEnemy();
                chooseStrategy();
                defineAction();
                
            }
        }

        
        System.out.println("Game Over. Thanks for playing!");
    }

    private void displayFinalStats() {
        System.out.println("\n=== Final Hero Stats ===");
        hero.displayStats();
        System.out.printf("Enemies Defeated  : %d%n", enemyCount);
        System.out.printf("Total Damage Dealt: %d%n", totalDamageDealt);
        System.out.printf("Total Damage Received: %d%n", totalDamageReceived);
        System.out.println("========================\n");
    }

    private void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Hero getHero() {
        return hero;
    }

    public Enemy getEnemy() {
        return enemy;
    }
}
