package charmees.Core.Logic;

import charmees.Display.PCvPCBattleDisplay;
import java.util.Random;
import java.util.Scanner;

public class PCvPCBattleLogic {
    static Random random = new Random();

    public static void startTrial(Scanner sc) {
        Fighter[] roster = createRoster();

        PCvPCBattleDisplay.title();

        Fighter player = chooseFighter(sc, roster, "Choose your fighter");
        Fighter enemy = chooseEnemy(sc, roster, player);

        System.out.println("\n" + player.name + " VS " + enemy.name);
        boolean won = battle(sc, player, enemy);

        if (won) {
            PCvPCBattleDisplay.win(player.name);
        } else {
            PCvPCBattleDisplay.win(enemy.name);
        }

        System.out.println("\nNo HP/MP bonus added for now.");
    }

    private static boolean battle(Scanner sc, Fighter player, Fighter enemy) {
        int turn = 1;

    }
}
