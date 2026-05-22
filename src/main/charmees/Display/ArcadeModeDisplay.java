package charmees.Display;

import charmees.util.Character;
import charmees.util.Display;

import java.util.Scanner;

public class ArcadeModeDisplay {

    private final Scanner sc;

    public ArcadeModeDisplay(Scanner sc) {
        this.sc = sc;
    }

    public void show() {
        boolean running = true;
        while (running) {
            showArcadeScreen();
            int mode = safeReadInt(0, JournalDisplay.easterEggUnlocked ? 3 : 2);

            if (mode == 0)
                break;

            if (mode == 1) {
                CharVsCharLogic charVsChar = new CharVsCharLogic(sc);
                charVsChar.run();
            } else if (mode == 2) {
                Character[] roster = buildRoster();
                PVCBattleLogic.startArcade(sc, roster, roster);
            } else if (mode == 3) {
                EasterEggArcadeLogic easterEgg = new EasterEggArcadeLogic(sc);
                easterEgg.run();
            }
        }
    }

    public Character[] buildRoster() {
        Character[] roster = new Character[CharVsCharLogic.HERO_DEFS.length];
        for (int i = 0; i < CharVsCharLogic.HERO_DEFS.length; i++) {
            roster[i] = CharVsCharLogic.makeCharacter(i);
        }
        return roster;
    }

    public static void showArcadeScreen() {
        Display.gap();
        Display.banner();
        Display.header("ARCADE MODE");
        Display.gap();
        System.out.println("  Current Modes:");
        Display.gap();
        System.out.println("  [1] Character vs Character");
        System.out.println("      Pick your fighter and face a class rival.");
        Display.gap();
        System.out.println("  [2] Player vs Character");
        System.out.println("      You somehow got inside the game??");
        Display.gap();
        if (JournalDisplay.easterEggUnlocked) {
            System.out.println("  [3] Character vs Easter Egg");
            System.out.println("      You think this is a game?  ...It literally is.");
            Display.gap();
        }
        Display.option(0, "Back to Main Menu");
        Display.line();
    }

    private int safeReadInt(int min, int max) {
        while (true) {
            int v = Display.readInt(sc);
            if (v == 0 && min > 0)
                return 0;
            if (v >= min && v <= max)
                return v;
            System.out.printf("  Please enter %d-%d.%n", min, max);
        }
    }
}