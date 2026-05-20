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

            // Display game subtitle
            Display.centered("A story-driven turn-based RPG");
            Display.gap();


            String border = Display.CYAN + "+" + "-".repeat(Display.WIDTH - 2) + "+" + Display.RESET;
            System.out.println(border);

            System.out.println(Display.YELLOW + Display.BOLD + "|" + centerText("M E N U", Display.WIDTH - 2) + "|" + Display.RESET);
            System.out.println(border);

            // Menu option 1: Story Mode
            System.out.println(Display.GREEN + "|  [1]  Story Mode" + padRight("", Display.WIDTH - 19) + "|" + Display.RESET);
            System.out.println("|       > Experience the epic narrative" + padRight("", Display.WIDTH - 39) + "|");
            System.out.println("|" + padRight("", Display.WIDTH - 2) + "|");

            // Menu option 2: Journal
            System.out.println(Display.CYAN + "|  [2]  Journal" + padRight("", Display.WIDTH - 17) + "|" + Display.RESET);
            System.out.println("|       > Study your enemies' weaknesses" + padRight("", Display.WIDTH - 38) + "|");
            System.out.println("|" + padRight("", Display.WIDTH - 2) + "|");

            // Menu option 3: Arcade Mode
            System.out.println(Display.YELLOW + "|  [3]  Arcade Mode" + padRight("", Display.WIDTH - 20) + "|" + Display.RESET);
            System.out.println("|       > L████'s Playground" + padRight("", Display.WIDTH - 39) + "|");
            System.out.println("|" + padRight("", Display.WIDTH - 2) + "|");

            // Menu option 4: Exit
            System.out.println(Display.RED + "|  [4]  Exit" + padRight("", Display.WIDTH - 13) + "|" + Display.RESET);
            System.out.println("|       > L████: Bye Bye!" + padRight("", Display.WIDTH - 24) + "|");
            System.out.println(border);


            // Show reminder tip if user has visited more than 3 times
            if (visitCount > 3 && lastChoice != 0) {
                String tip = "Tip: Last time you chose option " + lastChoice;
                System.out.println(Display.DIM + "| " + tip + padRight("", Display.WIDTH - tip.length() - 3) + "|" + Display.RESET);
                System.out.println(border);
            }

            System.out.print(Display.CYAN + "  >> Choose an option [1-4]: " + Display.RESET);
            choice = Display.readInt(sc);

            if (choice == -1) {
                Display.gap();
                System.out.println(Display.RED + border + Display.RESET);
                System.out.println(Display.RED + "|  [!] Please enter a number [1-4]                 |" + Display.RESET);
                System.out.println(Display.RED + border + Display.RESET);
                Display.gap();
                Display.pause(sc);
                continue;
            }


            // Route to appropriate game mode
            switch (choice) {
                case 1:
                    lastChoice = 1;
                    new StoryMenuDisplay(sc, characters, mobs).show();
                    break;
                case 2:
                    lastChoice = 2;
                    new JournalDisplay.showJournalDisplay();
                    break;
                case 3:
                    lastChoice = 3;
                    new ArcadeModeDisplay(sc).show();
                    break;
                case 4:
                    Display.gap();
                    System.out.println(Display.GREEN + border + Display.RESET);
                    System.out.println(Display.GREEN + "|          L████: Thanks for playing! Goodbye~          |" + Display.RESET);
                    System.out.println(Display.GREEN + border + Display.RESET);
                    Display.gap();
                    break;
                default:
                    Display.gap();
                    System.out.println(Display.RED + border + Display.RESET);
                    System.out.println(Display.RED + "|  [!] Invalid choice. Please enter 1-4.         |" + Display.RESET);
                    System.out.println(Display.RED + border + Display.RESET);
                    Display.gap();
                    Display.pause(sc);
            }
        }
    }

    //Centers text
    private String centerText(String text, int width) {
        int spaces = (width - text.length()) / 2;
        if (spaces < 0) spaces = 0;
        return " ".repeat(spaces) + text + " ".repeat(width - spaces - text.length());
    }

    //Adds spaces to the right of text
    private String padRight(String text, int length) {
        if (length < 0) length = 0;
        return text + " ".repeat(length);
    }

}
