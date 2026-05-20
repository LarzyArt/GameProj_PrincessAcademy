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

}