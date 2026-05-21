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

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\n--- Turn " + turn + " ---");
            PCvPCBattleDisplay.showStatus(player, enemy);

            PCvPCBattleDisplay.actionMenu(player);
            int choice = readInt(sc);

            playerAction(choice, player, enemy);

            if (!enemy.isAlive()) break;

            cpuAction(enemy, player);
            turn++;
        }

        return player.isAlive();
    }

    private static void playerAction(int choice, Fighter user, Fighter target) {
        switch (choice) {
            case 1 -> basicAttack(user, target);
            case 2 -> skill(user, target);
            case 3 -> ultimate(user, target);
            case 4 -> recover(user);
            default -> System.out.println("Invalid choice. Turn wasted.");
        }
    }

    private static void cpuAction(Fighter cpu, Fighter player) {
        System.out.println("\n" + cpu.name + "'s turn!");

        int roll = random.nextInt(100);

        if (roll < 20 && cpu.mp >= 18) {
            ultimate(cpu, player);
        } else if (roll < 70 && cpu.mp >= 8) {
            skill(cpu, player);
        } else {
            basicAttack(cpu, player);
        }
    }

}
