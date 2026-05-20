package charmees.Display;

import charmees.util.Character;
import charmees.util.Display;

import charmees.Entity.characters.Audry;
import charmees.Entity.characters.Giantha;
import charmees.Entity.characters.Lazuli;
import charmees.Entity.characters.Lynzi;
import charmees.Entity.characters.Shiera;

import java.util.Random;
import java.util.Scanner;

public class CharVsCharLogic {

    private final Scanner sc;
    private final Random rng = new Random();

    static final Object[][] HERO_DEFS = {
            { "Audry",   "audry",   "Assassin", "Melee",  "Acidic Slime",      100, 25 },
            { "Giantha", "giantha", "Tank",     "Melee",  "World Tree Branch", 250, 20 },
            { "Lazuli",  "lazuli",  "Healer",   "Ranged", "Staff",             150, 30 },
            { "Lynzi",   "lynzi",   "Dealer",   "Melee",  "Star Magic",        170, 30 },
            { "Shiera",  "shiera",  "Support",  "Ranged", "Earth Magic",       120, 25 },
    };

    private Character hero;
    private Character opponent;
    private int heroMaxHP, heroMaxMP;
    private int oppMaxHP, oppMaxMP;
    private int heroIdx;

    public CharVsCharLogic(Scanner sc) {
        this.sc = sc;
    }

    public void show() {
        boolean running = true;
        while (running) {
            CharVsCharDisplay.showArcadeScreen();
            int mode = safeReadInt(0, 2);

            if (mode == 0) break;

            if (mode == 1) {
                runCharVsCharFlow();
            } else if (mode == 2) {
                Display.gap();
                System.out.println("  [Coming soon]");
                Display.pressEnter(sc);
            }
        }
    }

    private void runCharVsCharFlow() {
        CharVsCharDisplay.showCharVsCharDescription();
        int choice = safeReadInt(1, 2);
        if (choice == 2) return;

        CharVsCharDisplay.showHeroSelect(HERO_DEFS, "CHAR vs CHAR  -  PICK YOUR FIGHTER");
        int heroPick = safeReadInt(0, HERO_DEFS.length);
        if (heroPick == 0) return;
        heroIdx = heroPick - 1;
        buildHero(heroIdx);

        int[] indexMap = CharVsCharDisplay.showOpponentSelect(HERO_DEFS, hero, heroIdx);
        int oppPick = safeReadInt(0, HERO_DEFS.length - 1);
        if (oppPick == 0) return;
        buildOpponent(indexMap[oppPick - 1]);

        CharVsCharBattleDisplay battleDisplay = new CharVsCharBattleDisplay(
                hero, heroMaxHP, heroMaxMP,
                opponent, oppMaxHP, oppMaxMP,
                sc, rng);
        battleDisplay.run();

        CharVsCharDisplay.showPlayAgain();
        if (safeReadInt(1, 2) == 1) runCharVsCharFlow();
    }

    private void buildHero(int idx) {
        hero = makeCharacter(idx);
        heroMaxHP = hero.healthPoints;
        heroMaxMP = hero.manaPoints;
    }

    private void buildOpponent(int idx) {
        opponent = makeCharacter(idx);
        oppMaxHP = opponent.healthPoints;
        oppMaxMP = opponent.manaPoints;
    }

    public static Character makeCharacter(int idx) {
        Object[] h = HERO_DEFS[idx];
        String key = (String) h[1];
        String n  = (String) h[0];
        String cl = (String) h[2];
        String t  = (String) h[3];
        String w  = (String) h[4];
        int hp = (int) h[5];
        int mp = (int) h[6];
        switch (key) {
            case "audry":   return new Audry(n, cl, t, w, hp, mp);
            case "giantha": return new Giantha(n, cl, t, w, hp, mp);
            case "lazuli":  return new Lazuli(n, cl, t, w, hp, mp);
            case "lynzi":   return new Lynzi(n, cl, t, w, hp, mp);
            default:        return new Shiera(n, cl, t, w, hp, mp);
        }
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