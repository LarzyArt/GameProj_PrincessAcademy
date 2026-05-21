package charmees.Display;

import charmees.util.Character;
import charmees.util.Display;
import java.util.Scanner;

public class PVCBattleLogic {
    public static final int ROUNDS_TO_WIN = 2;
    public static final int DEFAULT_ROUNDS = 3;

    private final Character player, opponent;
    private final Scanner sc;
    private final int playerBaseHP, playerBaseMP, oppBaseHP, oppBaseMP;
    private int playerWins, oppWins, round = 1, turnCount = 1;
    private boolean playerForfeited;

    public PVCBattleLogic(Character player, Character opponent, Scanner sc) {
        this.player = player;
        this.opponent = opponent;
        this.sc = sc;
        playerBaseHP = player.healthPoints;
        playerBaseMP = player.manaPoints;
        oppBaseHP = opponent.healthPoints;
        oppBaseMP = opponent.manaPoints;
    }

    public void run() {
        PVCBattleDisplay.showArcadeModeIntro();
        PVCBattleDisplay.showMatchUpBanner(player.getName(), opponent.getName());
        Display.pressEnter(sc);
        while (playerWins < ROUNDS_TO_WIN && oppWins < ROUNDS_TO_WIN) {
            resetRound();
            playRound();
        }
        if (playerWins >= ROUNDS_TO_WIN) PVCBattleDisplay.showMatchVictory(player, playerWins);
        else PVCBattleDisplay.showMatchDefeat(opponent, oppWins);
    }

    private void resetRound() {
        player.healthPoints = playerBaseHP;
        player.manaPoints = playerBaseMP;
        opponent.healthPoints = oppBaseHP;
        opponent.manaPoints = oppBaseMP;
        turnCount = 1;
        playerForfeited = false;
    }

    private void playRound() {
        PVCBattleDisplay.logHighlight("=== ROUND " + round + " — BEGIN! ===");
        PVCBattleDisplay.pause(400);
        while (player.isAlive() && opponent.isAlive()) {
            PVCBattleDisplay.showBattleField(player, playerBaseHP, opponent, oppBaseHP, round, turnCount);
            PVCBattleDisplay.showPlayerPhaseHeader(player);
            PVCBattleDisplay.showActionMenu(player);
            int action = Display.readInt(sc);
            if (action == 1) playerSkill();
            else if (action == 2) playerUltimate();
            else if (action == 3) playerForfeit();
            else PVCBattleDisplay.log("Invalid choice — enter 1 to 3.");
            if (!player.isAlive() || !opponent.isAlive()) break;
            if (!playerForfeited) cpuTurn();
            turnCount++;
        }
        resolveRound();
    }

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

    private void playerUltimate() {
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

    private void playerForfeit() {
        PVCBattleDisplay.showForfeitConfirm();
        if (Display.readInt(sc) == 1) {
            playerForfeited = true;
            PVCBattleDisplay.logDanger(player.getName() + " throws in the towel. Round conceded.");
            PVCBattleDisplay.pause(600);
            player.healthPoints = 0;
        } else {
            PVCBattleDisplay.log("Back in it!");
        }
    }

    private void executePlayerSkill(int skillNum) {
        String targetType = player.getSkillTargetType(skillNum);
        String skillName = player.getSkillList()[skillNum - 1];
        if ("ENEMY".equals(targetType)) {
            int hpBefore = opponent.healthPoints;
            player.useSkill(skillNum, wrap(opponent), null, new Character[]{player});
            int dealt = hpBefore - opponent.healthPoints;
            if (dealt > 0) PVCBattleDisplay.log(player.getName() + " uses " + skillName + " → " + dealt + " damage to " + opponent.getName() + "!");
        } else {
            PVCBattleDisplay.log(player.getName() + " uses " + skillName + "!");
            player.useSkill(skillNum, null, player, new Character[]{player});
        }
        PVCBattleDisplay.pause(600);
    }

    private void cpuTurn() {
        PVCBattleDisplay.showCPUPhaseHeader(opponent);
        String[] skills = opponent.getSkillList();
        boolean tryUlt = skills.length >= 3 && cpuUltChance() && opponent.manaPoints >= extractMPCost(skills[2]);
        int chosenSkill;
        if (tryUlt) chosenSkill = 3;
        else {
            boolean can2 = skills.length >= 2 && opponent.manaPoints >= extractMPCost(skills[1]);
            boolean can1 = opponent.manaPoints >= extractMPCost(skills[0]);
            if (!can1 && !can2) {
                opponent.manaPoints += 5;
                PVCBattleDisplay.logInfo(opponent.getName() + " (CPU) gathers focus… (+5 MP)");
                PVCBattleDisplay.pause(600);
                return;
            }
            chosenSkill = (can2 && Math.random() < 0.55) ? 2 : 1;
        }
        int hpBefore = player.healthPoints;
        opponent.useSkill(chosenSkill, wrap(player), null, new Character[]{opponent});
        int rawDamage = hpBefore - player.healthPoints;
        if (rawDamage > 0) PVCBattleDisplay.log(opponent.getName() + " (CPU) uses " + opponent.getSkillList()[chosenSkill - 1] + " → " + rawDamage + " damage to " + player.getName() + "!");
        PVCBattleDisplay.pause(700);
    }

    private boolean cpuUltChance() {
        return Math.random() < 0.20;
    }

    private void resolveRound() {
        if (!player.isAlive()) {
            PVCBattleDisplay.showRoundDefeat(opponent, round);
            oppWins++;
        } else if (!opponent.isAlive()) {
            PVCBattleDisplay.showRoundVictory(player, round);
            playerWins++;
        } else {
            PVCBattleDisplay.log("Round " + round + " ended unexpectedly — no winner.");
        }
        PVCBattleDisplay.showScoreboard(player.getName(), playerWins, opponent.getName(), oppWins, DEFAULT_ROUNDS);
        round++;
        if (playerWins < ROUNDS_TO_WIN && oppWins < ROUNDS_TO_WIN) Display.pressEnter(sc);
    }

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

    private int extractMPCost(String skillLabel) {
        try {
            int idx = skillLabel.indexOf("Cost:");
            if (idx < 0) return 0;
            String sub = skillLabel.substring(idx + 5).trim();
            StringBuilder sb = new StringBuilder();
            for (char c : sub.toCharArray()) {
                if (java.lang.Character.isDigit(c)) sb.append(c);
                else if (sb.length() > 0) break;
            }
            return sb.length() > 0 ? Integer.parseInt(sb.toString()) : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static void startArcade(Scanner sc, Character[] players, Character[] opponents) {
        String[] playerNames = names(players);
        String[] playerClasses = classes(players);
        String[] oppNames = names(opponents);
        String[] oppClasses = classes(opponents);
        while (true) {
            Character chosenPlayer = players[choose(sc, "YOUR FIGHTER", playerNames, playerClasses)];
            Character chosenOpp = opponents[choose(sc, "CPU OPPONENT", oppNames, oppClasses)];
            while (true) {
                new PVCBattleLogic(cloneCharacter(chosenPlayer), cloneCharacter(chosenOpp), sc).run();
                int postChoice = validChoice(sc, 3, PVCBattleDisplay::showPostMatchMenu);
                if (postChoice == 1) continue;
                if (postChoice == 2) break;
                return;
            }
        }
    }

    private static int choose(Scanner sc, String label, String[] names, String[] classes) {
        int choice = -1;
        while (choice < 1 || choice > names.length) {
            PVCBattleDisplay.showCharacterSelect(label, names, classes);
            choice = Display.readInt(sc);
            if (choice < 1 || choice > names.length) PVCBattleDisplay.log("Pick a number between 1 and " + names.length + ".");
        }
        return choice - 1;
    }

    private static int validChoice(Scanner sc, int max, Runnable displayMenu) {
        int choice = -1;
        while (choice < 1 || choice > max) {
            displayMenu.run();
            choice = Display.readInt(sc);
            if (choice < 1 || choice > max) PVCBattleDisplay.log("Pick a number between 1 and " + max + ".");
        }
        return choice;
    }

    private static Character cloneCharacter(Character original) {
        try {
            return original.getClass().getConstructor(String.class, String.class, String.class, String.class, int.class, int.class)
                    .newInstance(original.getName(), original.charClass, original.type, original.weapon, original.healthPoints, original.manaPoints);
        } catch (Exception e) {
            return original;
        }
    }

    private static String[] names(Character[] pool) {
        String[] result = new String[pool.length];
        for (int i = 0; i < pool.length; i++) result[i] = pool[i].getName();
        return result;
    }

    private static String[] classes(Character[] pool) {
        String[] result = new String[pool.length];
        for (int i = 0; i < pool.length; i++) result[i] = pool[i].charClass;
        return result;
    }
}
