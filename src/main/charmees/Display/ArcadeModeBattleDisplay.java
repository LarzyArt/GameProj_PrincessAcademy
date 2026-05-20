package charmees.Display;

import charmees.util.Character;
import charmees.util.Display;

import java.util.Random;
import java.util.Scanner;

public class ArcadeModeBattleDisplay {

    private final Scanner sc;
    private final Random rng;

    private final Character hero;
    private final int heroMaxHP, heroMaxMP;

    private final Character opponent;
    private final int oppMaxHP, oppMaxMP;

    private final String[] combatLog = new String[4];
    private int logPointer = 0;

    private int totalDealt, totalTaken, turnCount;

    public ArcadeModeBattleDisplay(
            Character hero, int heroMaxHP, int heroMaxMP,
            Character opponent, int oppMaxHP, int oppMaxMP,
            Scanner sc, Random rng) {
        this.hero       = hero;
        this.heroMaxHP  = heroMaxHP;
        this.heroMaxMP  = heroMaxMP;
        this.opponent   = opponent;
        this.oppMaxHP   = oppMaxHP;
        this.oppMaxMP   = oppMaxMP;
        this.sc         = sc;
        this.rng        = rng;
        this.turnCount  = 1;
        this.totalDealt = 0;
        this.totalTaken = 0;
        clearLog();
    }

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

            ArcadeModeDisplay.showCombatLog(combatLog, logPointer);
            ArcadeModeDisplay.showCharVsCharHUD(
                    hero, heroMaxHP, heroMaxMP,
                    opponent, oppMaxHP, oppMaxMP);

            BattleDisplay.showPlayerPhaseHeader(hero);
            playerTurn();
            hero.tickStatus();
            if (!opponent.isAlive()) break;

            BattleDisplay.showEnemyPhaseHeader();
            opponentTurn();
            opponent.tickStatus();
            turnCount++;
        }

        ArcadeModeDisplay.showCharVsCharResult(
                hero, heroMaxHP, opponent,
                turnCount, totalDealt, totalTaken);
        Display.pressEnter(sc);
    }

    private void playerTurn() {
        ArcadeModeDisplay.showSkillMenu(hero);

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
                case 1:  dmg = rng.nextInt(16) + 15; break;
                case 2:  dmg = rng.nextInt(21) + 25; break;
                case 3:  dmg = rng.nextInt(26) + 40; break;
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
            case 1:  dmg = rng.nextInt(16) + 15; break;
            case 2:  dmg = rng.nextInt(21) + 25; break;
            case 3:  dmg = rng.nextInt(26) + 40; break;
            default: dmg = rng.nextInt(11) + 10;
        }

        hero.takedamage(dmg);
        totalTaken += dmg;
        System.out.println("  " + opponent.getName() + " used " + skills[skillNum - 1] + "!");
        System.out.println("  Deals " + dmg + " damage to " + hero.getName() + "!");
        log(opponent.getName() + " hit for " + dmg + ".");
    }

    private void clearLog() {
        for (int i = 0; i < combatLog.length; i++)
            combatLog[i] = "";
        logPointer = 0;
    }

    private void log(String msg) {
        combatLog[logPointer % combatLog.length] = msg;
        logPointer++;
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