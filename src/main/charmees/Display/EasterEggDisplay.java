package charmees.Display;

import  charmees.util.Display;

public class EasterEggDisplay{

    // =========================================================================
    // BATTLEFIELD
    // =========================================================================
    public static void showBattlefield(String playerName, int playerHP, int playerMaxHP,
                                       int playerMP, int lezliDisplayHP, int lezliMaxHP, int turnCount){

        Display.gap();
        Display.header("??? MODE - TURN " + turnCount );

        Display.gap();
        Display.thin();
        System.out.println(" OPPONENTS: ");
        Display.gap();
        System.out.println(" Lezli" + "  HP: " + lezliDisplayHP + "/" + lezliMaxHP);
        System.out.println("  " + BattleDIsplay.hpBar(lezliDisplayHP, lezliMaxHP, 30)
                + "  " + percent(lezliDisplayHP,lezliMaxHP) + "%");
        Display.thin();
    }

    // =========================================================================
    // PLAYER MENU
    // =========================================================================

    public static void showPlayerMenu(String playerName) {
        Display.gap();
        Display.thin();
        System.out.println("  What will " + playerName + " do?");
        Display.gap();
        System.out.println("  [1] BUTTON B     - The attack button (cost 5 MP)");
        System.out.println("  [2] BUTTON MASH  - The click of random buttons(costs 6 MP)");
        System.out.println("  [3] TRASH TALK   - Say something brave (costs your sanity probably)");
        Display.thin();
    }

    // =========================================================================
    // LEZLI TURN HEADER
    // =========================================================================
    public static void showLezliTurnHeader() {
        Display.gap();
        Display.thin();
        System.out.println("  -- Lezli: My Turn! --");
        Display.thin();
        Display.gap();
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
        Display.gap();
        Display.line();
        for (String[] line : lines) {
            Display.gap();
            System.out.println("  " + line[0] + ":");
            System.out.println("  \"" + line[1] + "\"");
            Display.gap();
            Display.pressEnter(sc);
        }
        Display.line();
        Display.gap();
    }

    // =========================================================================
    // RESULT SCREENS
    // =========================================================================
    public static void showLezliWins(String playerName) {
        Display.gap();
        BattleDIsplay.pause(500);
        Display.line();
        BattleDIsplay.pause(300);
        Display.centered("Lezli: L e z l i  W i n s . . .");
        BattleDIsplay.pause(500);
        Display.centered("Lezli: Heh... "+ playerName + " has fallen.");
        Display.line();
        Display.gap();
    }

    // =========================================================================
    // HELPERS
    // =========================================================================
    private static int percent(int hp, int max){
        if(max <= 0) return 0;
        return (int)((double) hp / max * 100);
    }

}