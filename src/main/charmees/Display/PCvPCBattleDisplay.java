package charmees.Display;

import charmees.util.Character;
import charmees.util.Display;

public class PCvPCBattleDisplay {

    public static void showTrialIntro() {
        Display.gap();
        Display.banner();
        Display.centered("[ TRIAL BATTLE - P/C vs P/C ]");
        Display.line();
        Display.centered("Choose any player or character, then choose who to fight.");
        Display.centered("No HP/MP bonus is added for now.");
        Display.gap();
        Display.thin();
    }

    public static void title() {
        showTrialIntro();
    }












