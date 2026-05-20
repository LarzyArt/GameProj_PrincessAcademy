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

}