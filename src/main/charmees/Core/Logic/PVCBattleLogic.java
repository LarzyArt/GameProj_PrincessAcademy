package charmees.Core.Logic;

import charmees.util.Character;
import charmees.util.Display;
import charmees.Display.PVCBattleDisplay;
import java.util.Scanner;

/**
 * Handles the battle logic for Player vs CPU battles in arcade mode
 * Manages rounds, turns, player actions, and CPU behavior
 */
public class PVCBattleLogic {
    // Constants for match configuration
    public static final int ROUNDS_TO_WIN = 2;
    public static final int DEFAULT_ROUNDS = 3;

    // Battle state variables
    private final Character player, opponent;
    private final Scanner sc;
    private final int playerBaseHP, playerBaseMP;
    private final int oppBaseHP, oppBaseMP;
    private int playerWins, oppWins;
    private int round = 1, turnCount = 1;
    private boolean playerForfeited;


    //Constructor - initializes battle with player and opponent characters
    public PVCBattleLogic(Character player, Character opponent, Scanner sc) {
        this.player = player;
        this.opponent = opponent;
        this.sc = sc;
        // Store base stats to restore after each round
        playerBaseHP = player.healthPoints;
        playerBaseMP = player.manaPoints;
        oppBaseHP = opponent.healthPoints;
        oppBaseMP = opponent.manaPoints;
    }

    //Main battle loop - runs the entire match (best of 3 rounds)

    public void run() {
        // Display intro and match setup
        PVCBattleDisplay.showArcadeModeIntro();
        PVCBattleDisplay.showMatchUpBanner(player.getName(), opponent.getName());
        Display.pressEnter(sc);

        // Continue rounds until someone wins the match
        while (playerWins < ROUNDS_TO_WIN && oppWins < ROUNDS_TO_WIN) {
            resetRound();  // Restore health/mana for new round
            playRound();   // Play one round
        }

        // Announce final match result
        if (playerWins >= ROUNDS_TO_WIN)
            PVCBattleDisplay.showMatchVictory(player, playerWins);
        else
            PVCBattleDisplay.showMatchDefeat(opponent, oppWins);
    }

    //Reset character stats to their base values for a new round
    private void resetRound() {
        player.healthPoints = playerBaseHP;
        player.manaPoints = playerBaseMP;
        opponent.healthPoints = oppBaseHP;
        opponent.manaPoints = oppBaseMP;
        turnCount = 1;          // Reset turn counter
        playerForfeited = false; // Reset surrender flag
    }

    //Execute a single round of combat (turn-based battle until someone dies)

    private void playRound() {
        PVCBattleDisplay.logHighlight("=== ROUND " + round + " — BEGIN! ===");
        PVCBattleDisplay.pause(400);

        // Continue turns while both characters are alive
        while (player.isAlive() && opponent.isAlive()) {
            // Display current battle status
            PVCBattleDisplay.showBattleField(player, playerBaseHP, opponent, oppBaseHP, round, turnCount);

            // --- Player's Turn ---
            PVCBattleDisplay.showPlayerPhaseHeader(player);
            PVCBattleDisplay.showActionMenu(player);
            int action = Display.readInt(sc);

            // Process player's chosen action
            if (action == 1)
                playerSkill();      // Use normal skill
            else if (action == 2)
                playerUltimate();   // Use ultimate move
            else if (action == 3)
                playerForfeit();    // Surrender the round
            else
                PVCBattleDisplay.log("Invalid choice — enter 1 to 3.");

            // Check if battle ended after player's action
            if (!player.isAlive() || !opponent.isAlive())
                break;

            // --- CPU's Turn (only if player didn't forfeit) ---
            if (!playerForfeited)
                cpuTurn();

            turnCount++; // Increment turn counter for next iteration
        }

        resolveRound(); // Determine winner and update scores
    }

    //Handle player using a normal skill (options 1 or 2)

    private void playerSkill() {
        PVCBattleDisplay.showSkillMenu(player);
        int pick = Display.readInt(sc);
        int limit = Math.min(2, player.getSkillList().length);

        if (pick < 1 || pick > limit) {
            PVCBattleDisplay.log("Cancelled.");
            return;
        }
        executePlayerSkill(pick);
    }

    //Handle player using ultimate move (skill #3)
    private void playerUltimate() {
        // Check if character actually has an ultimate move
        if (player.getSkillList().length < 3) {
            PVCBattleDisplay.log(player.getName() + " has no Ultimate Move.");
            return;
        }

        PVCBattleDisplay.showUltimateConfirm(player);
        if (Display.readInt(sc) != 1) {
            PVCBattleDisplay.log("Cancelled.");
            return;
        }
        executePlayerSkill(3);
    }

    //Handle player forfeiting the current round
    private void playerForfeit() {
        PVCBattleDisplay.showForfeitConfirm();
        if (Display.readInt(sc) == 1) {
            playerForfeited = true;
            PVCBattleDisplay.logDanger(player.getName() + " throws in the towel. Round conceded.");
            PVCBattleDisplay.pause(600);
            player.healthPoints = 0; // Force defeat
        } else {
            PVCBattleDisplay.log("Back in it!");
        }
    }

    //Execute the actual skill logic for player

    private void executePlayerSkill(int skillNum) {
        String targetType = player.getSkillTargetType(skillNum);
        String skillName = player.getSkillList()[skillNum - 1];

        // Check if skill targets enemy or self
        if ("ENEMY".equals(targetType)) {
            // Attack skill - damage opponent
            int hpBefore = opponent.healthPoints;
            player.useSkill(skillNum, wrap(opponent), null, new Character[]{player});
            int dealt = hpBefore - opponent.healthPoints;
            if (dealt > 0)
                PVCBattleDisplay.log(player.getName() + " uses " + skillName + " → " + dealt + " damage to " + opponent.getName() + "!");
        } else {
            // Support skill - buff/heal self
            PVCBattleDisplay.log(player.getName() + " uses " + skillName + "!");
            player.useSkill(skillNum, null, player, new Character[]{player});
        }
        PVCBattleDisplay.pause(600);
    }

    //CPU opponent's turn logic with simple AI decision making

    private void cpuTurn() {
        PVCBattleDisplay.showCPUPhaseHeader(opponent);
        String[] skills = opponent.getSkillList();

        // Check if CPU can and should use ultimate (20% chance)
        boolean tryUlt = skills.length >= 3 &&
                cpuUltChance() &&
                opponent.manaPoints >= extractMPCost(skills[2]);

        int chosenSkill;
        if (tryUlt) {
            chosenSkill = 3;  // Use ultimate
        } else {
            // Check which skills are affordable
            boolean can2 = skills.length >= 2 && opponent.manaPoints >= extractMPCost(skills[1]);
            boolean can1 = opponent.manaPoints >= extractMPCost(skills[0]);

            // If no skills available, regain MP
            if (!can1 && !can2) {
                opponent.manaPoints += 5;
                PVCBattleDisplay.logInfo(opponent.getName() + " (CPU) gathers focus… (+5 MP)");
                PVCBattleDisplay.pause(600);
                return;
            }

            // 55% chance to use skill 2 if available, otherwise use skill 1
            chosenSkill = (can2 && Math.random() < 0.55) ? 2 : 1;
        }

        // Execute the chosen skill
        int hpBefore = player.healthPoints;
        opponent.useSkill(chosenSkill, wrap(player), null, new Character[]{opponent});
        int rawDamage = hpBefore - player.healthPoints;

        if (rawDamage > 0)
            PVCBattleDisplay.log(opponent.getName() + " (CPU) uses " + opponent.getSkillList()[chosenSkill - 1] +
                    " → " + rawDamage + " damage to " + player.getName() + "!");

        PVCBattleDisplay.pause(700);
    }

    //Determine if CPU should attempt ultimate move (20% chance)
    private boolean cpuUltChance() {
        return Math.random() < 0.20;
    }

    //Determine round winner and update match scores
    private void resolveRound() {
        if (!player.isAlive()) {
            PVCBattleDisplay.showRoundDefeat(opponent, round);
            oppWins++;  // CPU wins the round
        } else if (!opponent.isAlive()) {
            PVCBattleDisplay.showRoundVictory(player, round);
            playerWins++;  // Player wins the round
        } else {
            PVCBattleDisplay.log("Round " + round + " ended unexpectedly — no winner.");
        }

        // Display current scoreboard
        PVCBattleDisplay.showScoreboard(player.getName(), playerWins, opponent.getName(), oppWins, DEFAULT_ROUNDS);
        round++; // Move to next round

        // Wait for player acknowledgment if match isn't over
        if (playerWins < ROUNDS_TO_WIN && oppWins < ROUNDS_TO_WIN)
            Display.pressEnter(sc);
    }

    //Wraps a Character into a MobNPC for compatibility with skill system
    //This adapter allows the use of Character objects where MobNPC is expected
    private charmees.util.MobNPC wrap(Character who) {
        return new charmees.util.MobNPC(who.getName(), who.charClass, who.type, who.weapon, who.healthPoints, 0) {
            @Override
            public void takedamage(int damage) {
                who.healthPoints -= damage;
                if (who.healthPoints < 0) who.healthPoints = 0;
                this.healthPoints = who.healthPoints;
            }

            @Override
            public boolean isAlive() {
                return who.healthPoints > 0;
            }
        };
    }

    //Extract MP cost from skill label string
    //Skills are formatted like "Fireball (Cost: 10)"
    private int extractMPCost(String skillLabel) {
        try {
            int idx = skillLabel.indexOf("Cost:");
            if (idx < 0) return 0;
            String sub = skillLabel.substring(idx + 5).trim();
            StringBuilder sb = new StringBuilder();
            for (char c : sub.toCharArray()) {
                if (java.lang.Character.isDigit(c))
                    sb.append(c);
                else if (sb.length() > 0)
                    break;
            }
            return sb.length() > 0 ? Integer.parseInt(sb.toString()) : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    //Static method to start the arcade mode character selection and battle flow
    public static void startArcade(Scanner sc, Character[] players, Character[] opponents) {
        // Extract names and classes for display
        String[] playerNames = names(players);
        String[] playerClasses = classes(players);
        String[] oppNames = names(opponents);
        String[] oppClasses = classes(opponents);

        while (true) {
            // Character selection phase
            Character chosenPlayer = players[choose(sc, "YOUR FIGHTER", playerNames, playerClasses)];
            Character chosenOpp = opponents[choose(sc, "CPU OPPONENT", oppNames, oppClasses)];

            // Battle phase - continue rematching until player chooses to exit
            while (true) {
                new PVCBattleLogic(cloneCharacter(chosenPlayer), cloneCharacter(chosenOpp), sc).run();
                int postChoice = validChoice(sc, 3, PVCBattleDisplay::showPostMatchMenu);
                if (postChoice == 1) continue;  // Rematch with same characters
                if (postChoice == 2) break;      // Choose different characters
                return;                          // Exit arcade mode
            }
        }
    }

    //Display character selection menu and get player's choice
    private static int choose(Scanner sc, String label, String[] names, String[] classes) {
        int choice = -1;
        while (choice < 1 || choice > names.length) {
            PVCBattleDisplay.showCharacterSelect(label, names, classes);
            choice = Display.readInt(sc);
            if (choice < 1 || choice > names.length)
                PVCBattleDisplay.log("Pick a number between 1 and " + names.length + ".");
        }
        return choice - 1;
    }

    //Validate user input is within allowed range
    private static int validChoice(Scanner sc, int max, Runnable displayMenu) {
        int choice = -1;
        while (choice < 1 || choice > max) {
            displayMenu.run();
            choice = Display.readInt(sc);
            if (choice < 1 || choice > max)
                PVCBattleDisplay.log("Pick a number between 1 and " + max + ".");
        }
        return choice;
    }

    //Create a fresh copy of a character to avoid modifying the original
    // Uses reflection to call the constructor with all parameters
    private static Character cloneCharacter(Character original) {
        try {
            return original.getClass().getConstructor(
                            String.class, String.class, String.class, String.class, int.class, int.class)
                    .newInstance(original.getName(), original.charClass, original.type,
                            original.weapon, original.healthPoints, original.manaPoints);
        } catch (Exception e) {
            return original; // Fallback to original if cloning fails
        }
    }

    //Extract names from character array for display
    private static String[] names(Character[] pool) {
        String[] result = new String[pool.length];
        for (int i = 0; i < pool.length; i++)
            result[i] = pool[i].getName();
        return result;
    }

    //Extract classes from character array for display
    private static String[] classes(Character[] pool) {
        String[] result = new String[pool.length];
        for (int i = 0; i < pool.length; i++)
            result[i] = pool[i].charClass;
        return result;
    }
}