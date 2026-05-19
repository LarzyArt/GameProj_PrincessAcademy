package charmees.Display;

import charmees.util.Character;
import charmees.util.Display;

public class ArcadeModeDisplay {

    public static void showArcadeScreen() {
        Display.gap();
        Display.banner();
        Display.header("ARCADE MODE");
        Display.gap();
        System.out.println("  Current Modes:");
        Display.gap();
        System.out.println("  [1] Character vs Character (Class)");
        System.out.println("      Pick your fighter and face a class rival.");
        Display.gap();
        System.out.println("  [2] Character vs Easter Egg");
        System.out.println("      You think this is a game?  ...It literally is.");
        Display.gap();
        Display.option(0, "Back to Main Menu");
        Display.line();
    }
}

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
        System.out.println("  HP [" + BattleDisplay.hpBar(hero.healthPoints, heroMaxHP, BAR)
                + "] " + pct(hero.healthPoints, heroMaxHP) + "%");
        System.out.println("  MP [" + BattleDisplay.hpBar(hero.manaPoints, heroMaxMP, BAR)
                + "] " + pct(hero.manaPoints, heroMaxMP) + "%");
        if (hero.healthPoints <= heroMaxHP / 4)
            System.out.println("  !! LOW HP !!");
        Display.gap();

        System.out.println("  OPPONENT");
        System.out.printf("  %-14s  HP: %3d/%-3d  MP: %2d/%-2d%n",
                opponent.getName(), opponent.healthPoints, oppMaxHP, opponent.manaPoints, oppMaxMP);
        System.out.println("  HP [" + BattleDisplay.hpBar(opponent.healthPoints, oppMaxHP, BAR)
                + "] " + pct(opponent.healthPoints, oppMaxHP) + "%");
        System.out.println("  MP [" + BattleDisplay.hpBar(opponent.manaPoints, oppMaxMP, BAR)
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
        BattleDisplay.pause(400);
        Display.line();

        if (hero.isAlive()) {
            BattleDisplay.showVictoryDisplay(0);
            System.out.println("  " + hero.getName() + " defeated " + opponent.getName() + "!");
        } else {
            BattleDisplay.showDefeatDisplay();
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

    private static int pct(int val, int max) {
        return max <= 0 ? 0 : (int) ((double) val / max * 100);
    }


    