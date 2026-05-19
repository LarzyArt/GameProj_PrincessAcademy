package charmees.Display;

import java.util.Scanner;

import charmees.Util.Characters;
import charmees.Util.Display;
import charmees.Util.MobNPC;

public class MainMenuDisplay {

    private Scanner sc;
    private Characters[] characters;
    private MobNPC[] mobs;

    // Track visits and last choice
    private static int visitCount = 0;
    private static int lastChoice = 0;

    public MainMenuDisplay(Scanner sc, Characters[] characters, MobNPC[] mobs) {
        this.sc = sc;
        this.characters = characters;
        this.mobs = mobs;
        visitCount++;
    }


    public void show() {
        int choice = 0;

        while (choice != 4) {
            Display.gap();
            Display.banner();
            Display.gap();

            // Different welcome messages
            if (visitCount == 0) {
                System.out.println(Display.CYAN + "~ Welcome, brave adventurer! ~" + Display.RESET);
            } else if (visitCount == 1) {
                System.out.println(Display.YELLOW + "~ Welcome back! Ready for more? ~" + Display.RESET);
            } else if (visitCount >= 2 && visitCount < 6) {
                System.out.println(Display.GREEN + "~ Good to see you again, champion! ~" + Display.RESET);
            } else if (visitCount >= 6) {
                System.out.println(Display.MAGENTA + "~ Legend! You're family here. ~" + Display.RESET);
            }
        }
    }
}
