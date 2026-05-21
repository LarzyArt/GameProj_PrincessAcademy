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

    public static void showMatchUp(String playerName, String opponentName) {
        Display.gap();
        Display.line();
        Display.centered(playerName + " VS " + opponentName);
        Display.line();
    }

    public static void showBattleField(Character player, int playerMaxHP,
                                       Character opponent, int oppMaxHP,
                                       int turnCount) {
        Display.gap();
        Display.header("TRIAL BATTLE - TURN " + turnCount);

        showFighterPanel("OPPONENT", opponent, oppMaxHP);
        Display.gap();
        Display.centered(Display.YELLOW + "~ VS ~" + Display.RESET);
        Display.gap();
        showFighterPanel("YOUR FIGHTER", player, playerMaxHP);

        Display.gap();
        Display.thin();
    }

}












