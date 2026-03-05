// Class for handling battle phases (enemy and character turns)
import java.util.ArrayList;
import java.util.Scanner;

public class BattlePhases extends BattleUIComponent {

    public BattlePhases(Character[] characters, MobNPC[] mobs, Scanner skillInput, int currentChapter) {
        super(characters, mobs, skillInput, currentChapter);
    }

    @Override
    public void execute() {
        // Not used, as phases are called separately
    }

    // enemies attack: each alive enemy in the active chapter uses a random skill on a random alive character
    public void enemyPhase() {
          ArrayList<Integer> aliveCharacterIndexes = new ArrayList<>();
        for (int i = 0; i < characters.length; i++) {
            if (characters[i].isAlive()) aliveCharacterIndexes.add(i);
        }
        if (aliveCharacterIndexes.isEmpty()) return; // no targets

        for (MobNPC enemy : mobs) {
            if (enemy.chapter != this.currentChapter) continue;
            if (!enemy.isAlive()) continue;

            // if monster is stunned, they skip their action
            if (enemy.isStunned()) {
                System.out.println(enemy.getName() + " is stunned and cannot act this turn.");
                continue;
            }

            // prefer taunted party members if any
            ArrayList<Integer> tauntedTargets = new ArrayList<>();
            for (int i = 0; i < characters.length; i++) {
                if (characters[i].isAlive() && characters[i].tauntTurns > 0) tauntedTargets.add(i);
            }

            int randIndex = -1;
            if (!tauntedTargets.isEmpty()) {
                int pick = (int) (Math.random() * tauntedTargets.size());
                randIndex = tauntedTargets.get(pick);
            } else {
                int randomListIndex = (int) (Math.random() * aliveCharacterIndexes.size());
                randIndex = aliveCharacterIndexes.get(randomListIndex);
            }
            Character target = characters[randIndex];

            // pick a random skill between 1 and 3 (most mobs define 1-3)
            int skill = (int) (Math.random() * 3) + 1;

            System.out.println();
            // handle lava beast AoE case explicitly
            if (enemy.getName().equalsIgnoreCase("lava beast") && skill == 3) {
                int damage = (int)(Math.random() * 31) + 20;
                System.out.println(enemy.getName() + " used Corrupted Eruption! Deals " + damage + " damage to all party members!");
                for (Character c : characters) {
                    if (c.isAlive()) c.takedamage(damage);
                }
            } else {
                enemy.useSkill(skill, target);
            }

            // remove target from list if they died
            if (!target.isAlive()) {
                // rebuild aliveCharacterIndexes to avoid duplicates and removed dead targets
                aliveCharacterIndexes.clear();
                for (int i = 0; i < characters.length; i++) {
                    if (characters[i].isAlive()) aliveCharacterIndexes.add(i);
                }
                if (aliveCharacterIndexes.isEmpty()) return; // all characters dead
            }
        }
    }

    //character attack: character attacks via user input and heals  via user input
    public void characterPhase() {
        ArrayList<Integer> aliveEnemyIndexes = new ArrayList<>();
        // collect indices of mobs that belong to the current chapter and are alive
        for (int i = 0; i < mobs.length; i++) {
            if (mobs[i].chapter == this.currentChapter && mobs[i].isAlive()) {
                aliveEnemyIndexes.add(i);
            }
        }
        if (aliveEnemyIndexes.isEmpty()) return; // no targets

        for (Character party : characters) {
            if (!party.isAlive()) continue;
            // if the character is stunned, they skip their action this turn
            if (party.isStunned()) {
                System.out.println(party.getName() + " is stunned and cannot act this turn.");
                continue;
            }
            // if the character is stunned, they skip their action this turn

            //displayss character info
            System.out.println("\n" + party.name + " | HP: " + party.healthPoints + " | MP: " + party.manaPoints);
            System.out.println("===============================");

            // choose skill first
            // show skill list for this character
            int skill = -1;
            String[] skills = party.getSkillList();
            if (skills.length == 0) {
                System.out.println("\n" + party.getName() + " has no usable skills. Skipping action for this character.");
                continue; // skip to next character
            }
            System.out.println("\n" + party.getName() + " Skill list:");
            for (String s : skills) System.out.println(s);
            System.out.println("===============================");
            while (true) {
                System.out.print("Choose skill for " + party.getName() + " (1-" + skills.length + "): ");
                String tok = skillInput.next();
                try {
                    int pick = Integer.parseInt(tok);
                    if (pick >= 1 && pick <= skills.length) {
                        skill = pick;
                        break;
                    }
                } catch (NumberFormatException ex) {
                }
                System.out.println("Invalid skill. Try again.");
            }

            String targetType = party.getSkillTargetType(skill);
            MobNPC target = null;
            Character ally = null;

            if (targetType.equals("ENEMY")) {
                // display enemy targets and choose one
                System.out.println("\nChoose target for " + party.getName() + ":");
                System.out.println("===============================");
                for (int i = 0; i < aliveEnemyIndexes.size(); i++) {
                    MobNPC m = mobs[aliveEnemyIndexes.get(i)];
                    System.out.println((i + 1) + ". " + m.getName() + " | HP: " + m.healthPoints);
                }
                System.out.println("===============================");
                int chosenEnemyIndex = -1;
                while (true) {
                    System.out.print("Choose target (1-" + aliveEnemyIndexes.size() + "): ");
                    String tok = skillInput.next();
                    try {
                        int pick = Integer.parseInt(tok);
                        if (pick >= 1 && pick <= aliveEnemyIndexes.size()) {
                            chosenEnemyIndex = aliveEnemyIndexes.get(pick - 1);
                            break;
                        }
                    } catch (NumberFormatException ex) {
                    }
                    System.out.println("Invalid choice. Try again.");
                }
                target = mobs[chosenEnemyIndex];
                System.out.println("\n" + party.getName() + " uses skill " + skill + " on " + target.getName() + "...");
                party.useSkill(skill, target, party, characters);
            } else if (targetType.equals("ALLY")) {
                // choose an alive ally (including self)
                ArrayList<Integer> aliveAllyIndexes = new ArrayList<>();
                for (int i = 0; i < characters.length; i++) {
                    if (characters[i].isAlive()) aliveAllyIndexes.add(i);
                }
                if (aliveAllyIndexes.isEmpty()) {
                    System.out.println("No alive allies to target. Skill canceled.");
                } else {
                    System.out.println("\nChoose ally target for " + party.getName() + ":");
                    for (int i = 0; i < aliveAllyIndexes.size(); i++) {
                        Character c = characters[aliveAllyIndexes.get(i)];
                        System.out.println((i + 1) + ". " + c.getName() + " | HP: " + c.healthPoints + " | MP: " + c.manaPoints);
                    }
                    int chosenAllyIndex = -1;
                    while (true) {
                        System.out.print("Choose ally (1-" + aliveAllyIndexes.size() + "): ");
                        String tok = skillInput.next();
                        try {
                            int pick = Integer.parseInt(tok);
                            if (pick >= 1 && pick <= aliveAllyIndexes.size()) {
                                chosenAllyIndex = aliveAllyIndexes.get(pick - 1);
                                break;
                            }
                        } catch (NumberFormatException ex) {
                        }
                        System.out.println("Invalid choice. Try again.");
                    }
                    ally = characters[chosenAllyIndex];
                    System.out.println(party.getName() + " uses skill " + skill + " on ally " + ally.getName() + "...");
                    party.useSkill(skill, null, ally, characters);
                }
            } else if (targetType.equals("ALL") || targetType.equals("SELF")) {
                // 'ALL' affects all allies; 'SELF' affects self (pass party as ally)
                System.out.println(party.getName() + " uses skill " + skill + "...");
                party.useSkill(skill, null, party, characters);
            } else {
                // default fallback treat as enemy-target
                int chosenEnemyIndex = -1;
                while (true) {
                    System.out.print("Choose Enemy (1-" + aliveEnemyIndexes.size() + "): ");
                    String tok = skillInput.next();
                    try {
                        int pick = Integer.parseInt(tok);
                        if (pick >= 1 && pick <= aliveEnemyIndexes.size()) {
                            chosenEnemyIndex = aliveEnemyIndexes.get(pick - 1);
                            break;
                        }
                    } catch (NumberFormatException ex) {
                    }
                    System.out.println("Invalid choice. Try again.");
                }
                target = mobs[chosenEnemyIndex];
                System.out.println(party.getName() + " uses skill " + skill + " on " + target.getName() + "...");
                party.useSkill(skill, target, party, characters);
            }

            // if the target exists and died, rebuild the list of alive enemies for the chapter
            if (target != null && !target.isAlive()) {
                aliveEnemyIndexes.clear();
                for (int i = 0; i < mobs.length; i++) {
                    if (mobs[i].chapter == this.currentChapter && mobs[i].isAlive()) {
                        aliveEnemyIndexes.add(i);
                    }
                }
                if (aliveEnemyIndexes.isEmpty()) return; // all enemies dead
            }
        }
    }
}