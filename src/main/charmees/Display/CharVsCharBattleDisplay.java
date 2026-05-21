package charmees.Display;

import charmees.util.Character;
import charmees.util.Display;
import charmees.Display.BattleDIsplay;

import java.util.Random;
import java.util.Scanner;

public class CharVsCharBattleDisplay {

    private final Scanner sc;
    private final Random rng;

    private final Character hero;
    private final int heroMaxHP, heroMaxMP;

    private final Character opponent;
    private final int oppMaxHP, oppMaxMP;

    private final String[] combatLog = new String[4];
    private int logPointer = 0;

    private int totalDealt, totalTaken, turnCount;

    public CharVsCharBattleDisplay(
            Character hero, int heroMaxHP, int heroMaxMP,
            Character opponent, int oppMaxHP, int oppMaxMP,
            Scanner sc, Random rng) {
        this.hero = hero;
        this.heroMaxHP = heroMaxHP;
        this.heroMaxMP = heroMaxMP;
        this.opponent = opponent;
        this.oppMaxHP = oppMaxHP;
        this.oppMaxMP = oppMaxMP;
        this.sc = sc;
        this.rng = rng;
        this.turnCount = 1;
        this.totalDealt = 0;
        this.totalTaken = 0;
        clearLog();
    }

    // --- SCREENS ---

    public static void showCharVsCharDescription() {
        Display.gap();
        Display.header("CHARACTER vs CHARACTER (Class)");
        Display.gap();
        System.out.println("  Two fighters. One winner.");
        System.out.println("  Pick your hero and challenge a class rival.");
        System.out.println("  Use your skills wisely — there is no running.");
        Display.gap();
        Display.option(1, "Start");
        Display.option(2, "Back");
        Display.line();
    }

    public static void showHeroSelect(Object[][] heroDefs, String title) {
        Display.gap();
        Display.header(title);
        Display.gap();
        System.out.printf("  %-4s %-12s %-10s %-7s %-20s  HP    MP%n",
                "#", "NAME", "CLASS", "TYPE", "WEAPON");
        Display.thin();
        for (int i = 0; i < heroDefs.length; i++) {
            Object[] h = heroDefs[i];
            System.out.printf("  [%d] %-12s %-10s %-7s %-20s  %-5s %-4s%n",
                    i + 1, h[0], h[2], h[3], h[4], h[5], h[6]);
        }
        Display.gap();
        Display.option(0, "Back");
        Display.line();
    }

    public static int[] showOpponentSelect(Object[][] heroDefs, Character hero, int heroIdx) {
        Display.gap();
        Display.header("CHAR vs CHAR  -  PICK YOUR OPPONENT");
        Display.gap();
        System.out.println("  " + hero.getName() + " will fight...");
        Display.gap();
        System.out.printf("  %-4s %-12s %-10s %-7s %-20s  HP    MP%n",
                "#", "NAME", "CLASS", "TYPE", "WEAPON");
        Display.thin();

        int[] indexMap = new int[heroDefs.length];
        int mapCount = 0;
        int displayNum = 1;

        for (int i = 0; i < heroDefs.length; i++) {
            if (i == heroIdx) continue;
            Object[] h = heroDefs[i];
            indexMap[mapCount++] = i;
            System.out.printf("  [%d] %-12s %-10s %-7s %-20s  %-5s %-4s%n",
                    displayNum++, h[0], h[2], h[3], h[4], h[5], h[6]);
        }

        Display.gap();
        Display.option(0, "Back");
        Display.line();

        return indexMap;
    }

    public static void showCharVsCharHUD(
            Character hero, int heroMaxHP, int heroMaxMP,
            Character opponent, int oppMaxHP, int oppMaxMP) {

        final int BAR = 22;

        System.out.println("  YOUR HERO");
        System.out.printf("  %-14s  HP: %3d/%-3d  MP: %2d/%-2d%n",
                hero.getName(), hero.healthPoints, heroMaxHP, hero.manaPoints, heroMaxMP);
        System.out.println("  HP [" + BattleDIsplay.hpBar(hero.healthPoints, heroMaxHP, BAR)
                + "] " + pct(hero.healthPoints, heroMaxHP) + "%");
        System.out.println("  MP [" + BattleDIsplay.hpBar(hero.manaPoints, heroMaxMP, BAR)
                + "] " + pct(hero.manaPoints, heroMaxMP) + "%");
        if (hero.healthPoints <= heroMaxHP / 4)
            System.out.println("  !! LOW HP !!");
        Display.gap();

        System.out.println("  OPPONENT");
        System.out.printf("  %-14s  HP: %3d/%-3d  MP: %2d/%-2d%n",
                opponent.getName(), opponent.healthPoints, oppMaxHP, opponent.manaPoints, oppMaxMP);
        System.out.println("  HP [" + BattleDIsplay.hpBar(opponent.healthPoints, oppMaxHP, BAR)
                + "] " + pct(opponent.healthPoints, oppMaxHP) + "%");
        System.out.println("  MP [" + BattleDIsplay.hpBar(opponent.manaPoints, oppMaxMP, BAR)
                + "] " + pct(opponent.manaPoints, oppMaxMP) + "%");
        if (opponent.healthPoints <= oppMaxHP / 4)
            System.out.println("  !! LOW HP !!");
        Display.thin();
    }

    public static void showSkillMenu(Character hero) {
        String[] skills = hero.getSkillList();
        Display.gap();
        Display.option(1, "Basic Attack  [no MP cost, 10-20 dmg]");
        for (int i = 0; i < skills.length; i++)
            Display.option(i + 2, skills[i]);
        Display.thin();
    }

    public static void showCharVsCharResult(
            Character hero, int heroMaxHP,
            Character opponent,
            int turnCount, int totalDealt, int totalTaken) {

        Display.gap();
        BattleDIsplay.pause(400);
        Display.line();

        if (hero.isAlive()) {
            BattleDIsplay.showVictoryDisplay(0);
            System.out.println("  " + hero.getName() + " defeated " + opponent.getName() + "!");
        } else {
            BattleDIsplay.showDefeatDisplay();
            System.out.println("  " + hero.getName() + " was defeated by " + opponent.getName() + "...");
        }

        Display.gap();
        System.out.println("  BATTLE SUMMARY");
        Display.thin();
        System.out.printf("  Turns survived  : %d%n", turnCount);
        System.out.printf("  Damage dealt    : %d%n", totalDealt);
        System.out.printf("  Damage taken    : %d%n", totalTaken);
        System.out.printf("  HP remaining    : %d / %d%n", Math.max(0, hero.healthPoints), heroMaxHP);
        Display.line();
    }

    public static void showPlayAgain() {
        Display.gap();
        Display.option(1, "Play again");
        Display.option(2, "Back to Arcade Menu");
        Display.line();
    }

    public static void showCombatLog(String[] combatLog, int logPointer) {
        boolean hasContent = false;
        for (String s : combatLog)
            if (s != null && !s.isEmpty()) { hasContent = true; break; }
        if (!hasContent) return;

        System.out.println("  Recent Events:");
        for (int i = 0; i < combatLog.length; i++) {
            int slot = (logPointer - combatLog.length + i + combatLog.length) % combatLog.length;
            String line = combatLog[slot];
            if (line != null && !line.isEmpty())
                System.out.println("    > " + line);
        }
        Display.thin();
    }

    // --- BATTLE INTERACTION ---

    public void run() {
        Display.gap();
        Display.header("BATTLE START!");
        Display.centered(hero.getName() + "  vs  " + opponent.getName());
        Display.line();
        Display.pressEnter(sc);

        while (hero.isAlive() && opponent.isAlive()) {
            Display.gap();
            System.out.printf("  ======  TURN %d  ======%n", turnCount);
            Display.gap();

            showCombatLog(combatLog, logPointer);
            showCharVsCharHUD(
                    hero, heroMaxHP, heroMaxMP,
                    opponent, oppMaxHP, oppMaxMP);

            BattleDIsplay.showPlayerPhaseHeader(hero);
            playerTurn();
            if (!opponent.isAlive()) break;

            BattleDIsplay.showEnemyPhaseHeader();
            opponentTurn();
            turnCount++;
        }

        showCharVsCharResult(
                hero, heroMaxHP, opponent,
                turnCount, totalDealt, totalTaken);
        Display.pressEnter(sc);
    }

    private void playerTurn() {
        showSkillMenu(hero);

        String[] skills = hero.getSkillList();
        int action = safeReadInt(1, skills.length + 1);
        Display.gap();

        int dmg;
        String moveName;

        if (action == 1) {
            dmg = rng.nextInt(11) + 10;
            moveName = "Basic Attack";
        } else {
            int skillNum = action - 1;
            switch (skillNum) {
                case 1: dmg = rng.nextInt(16) + 15; break;
                case 2: dmg = rng.nextInt(21) + 25; break;
                case 3: dmg = rng.nextInt(26) + 40; break;
                default: dmg = rng.nextInt(11) + 10;
            }
            moveName = skills[skillNum - 1];
        }

        opponent.takedamage(dmg);
        totalDealt += dmg;
        System.out.println("  " + hero.getName() + " used " + moveName + "!");
        System.out.println("  Deals " + dmg + " damage to " + opponent.getName() + "!");
        log(hero.getName() + " hit for " + dmg + ".");
    }

    private void opponentTurn() {
        String[] skills = opponent.getSkillList();
        int skillNum = rng.nextInt(skills.length) + 1;

        int dmg;
        switch (skillNum) {
            case 1: dmg = rng.nextInt(16) + 15; break;
            case 2: dmg = rng.nextInt(21) + 25; break;
            case 3: dmg = rng.nextInt(26) + 40; break;
            default: dmg = rng.nextInt(11) + 10;
        }

        hero.takedamage(dmg);
        totalTaken += dmg;
        System.out.println("  " + opponent.getName() + " used " + skills[skillNum - 1] + "!");
        System.out.println("  Deals " + dmg + " damage to " + hero.getName() + "!");
        log(opponent.getName() + " hit for " + dmg + ".");
    }

    // --- COMBAT LOG ---

    private void clearLog() {
        for (int i = 0; i < combatLog.length; i++)
            combatLog[i] = "";
        logPointer = 0;
    }

    private void log(String msg) {
        combatLog[logPointer % combatLog.length] = msg;
        logPointer++;
    }

    // --- HELPER ---

    private static int pct(int val, int max) {
        return max <= 0 ? 0 : (int) ((double) val / max * 100);
    }

    private int safeReadInt(int min, int max) {
        while (true) {
            int v = Display.readInt(sc);
            if (v == 0 && min > 0) return 0;
            if (v >= min && v <= max) return v;
            System.out.printf("  Please enter %d-%d.%n", min, max);
        }
    }
}