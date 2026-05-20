package charmees.Display;

import charmees.util.Display;

public class EasterEggDisplay{

    // =========================================================================
    // BATTLEFIELD
    // =========================================================================
    public static void showBattlefield(String playerName, int playerHP, int playerMaxHP,
                                       int playerMP, int lezliDisplayHP, int lezliMaxHP, int turnCount){

        charmees.util.Display.gap();
        charmees.util.Display.header("??? MODE - TURN " + turnCount );

        charmees.util.Display.gap();
        charmees.util.Display.thin();
        System.out.println(" OPPONENTS: ");
        charmees.util.Display.gap();
        System.out.println(" Lezli" + "  HP: " + lezliDisplayHP + "/" + lezliMaxHP);
        System.out.println("  " + BattleDIsplay.hpBar(lezliDisplayHP, lezliMaxHP, 30)
                + "  " + percent(lezliDisplayHP,lezliMaxHP) + "%");
        charmees.util.Display.thin();
    }

    // =========================================================================
    // PLAYER MENU
    // =========================================================================

    public static void showPlayerMenu(String playerName) {
        charmees.util.Display.gap();
        charmees.util.Display.thin();
        System.out.println("  What will " + playerName + " do?");
        charmees.util.Display.gap();
        System.out.println("  [1] BUTTON B     - The attack button (cost 5 MP)");
        System.out.println("  [2] BUTTON MASH  - The click of random buttons(costs 6 MP)");
        System.out.println("  [3] TRASH TALK   - Say something brave (costs your sanity probably)");
        charmees.util.Display.thin();
    }


    // =========================================================================
// LEZLI TURN HEADER
// =========================================================================
    public static void showLezliTurnHeader() {
        charmees.util.Display.gap();
        charmees.util.Display.thin();
        System.out.println("  -- Lezli: My Turn! --");
        charmees.util.Display.thin();
        charmees.util.Display.gap();
    }

    // =========================================================================
    // LEZLI DIALOGUE LINE — single line, no enter needed
    // =========================================================================
    public static void showLezliLine(String text) {
        System.out.println("  Lezli: \"" + text + "\"");
        BattleDIsplay.pause(600);
    }

    // =========================================================================
    // BATTLE LOG
    // =========================================================================
    public static void log(String msg) {
        System.out.println("  " + msg);
    }

    // =========================================================================
    // DIALOGUE — waits for Enter between lines
    // =========================================================================
    public static void showDialogue(String[][] lines, java.util.Scanner sc) {
        if (lines == null || lines.length == 0) return;
        charmees.util.Display.gap();
        charmees.util.Display.line();
        for (String[] line : lines) {
            charmees.util.Display.gap();
            System.out.println("  " + line[0] + ":");
            System.out.println("  \"" + line[1] + "\"");
            charmees.util.Display.gap();
            charmees.util.Display.pressEnter(sc);
        }
        charmees.util.Display.line();
        charmees.util.Display.gap();
    }


}