package charmees.Display;

import java.util.Scanner;

import charmees.util.Character;
import charmees.util.Display;
import charmees.util.MobNPC;

public class MainMenuDisplay {

    private Scanner sc;
    private Character[] characters;
    private MobNPC[] mobs;

    // Track visits and last choice
    private static int visitCount = 0; // Tracks how many times menu was opened
    private static int lastChoice = 0; // Remembers last menu option selected

    public MainMenuDisplay(Scanner sc, Character[] characters, MobNPC[] mobs) {
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

            System.out.println("╔════════════════════════════════════════════════════╗");
            System.out.println("║                      M E N U                       ║");
            System.out.println("╠════════════════════════════════════════════════════╣");
            System.out.println("║                                                    ║");
            System.out.println("║  [1]  Story Mode                                   ║");
            System.out.println("║       > Experience the epic narrative              ║");
            System.out.println("║                                                    ║");
            System.out.println("║  [2]  Journal                                      ║");
            System.out.println("║       > Study your enemies' weaknesses             ║");
            System.out.println("║                                                    ║");
            System.out.println("║  [3]  Arcade Mode                                  ║");
            System.out.println("║       > L████'s Playground                         ║");
            System.out.println("║                                                    ║");
            System.out.println("║  [4]  Exit                                         ║");
            System.out.println("║       > L████: Bye Bye!                            ║");
            System.out.println("║                                                    ║");
            System.out.println("╚════════════════════════════════════════════════════╝");


            // Show reminder tip if user has visited more than 3 times
            if (visitCount > 3 && lastChoice != 0) {
                System.out.println(Display.DIM + "  Tip: Last time you chose option " + lastChoice + Display.RESET);
                Display.thin();
            }

            System.out.print(Display.CYAN + "  >> Choose an option [1-4]: " + Display.RESET);
            choice = Display.readInt(sc);

            if (choice == -1) {
                Display.gap();
                System.out.println("  [!] Please enter a number [1-4]");
                Display.gap();
                BattleDIsplay.pause(400);
                continue;
            }



            // Route to appropriate game mode
            switch (choice) {
                case 1:
                    lastChoice = 1;
                    StoryModeDisplay storymode = new StoryModeDisplay(sc, characters, mobs);
                    storymode.StartGame();
                    break;
                case 2:
                    lastChoice = 2;
                    JournalDisplay journal = new journalDisplay(sc);
                    journal.showJournalDisplay();
                    break;
                case 3:
                    lastChoice = 3;
                    ArcadeModeDisplay arcademode = new ArcadeModeDisplay();
                    arcademode.showArcadeScreen();
                    break;
                case 4:
                    Display.gap();
                    Display.line();
                    System.out.println(Display.YELLOW + "  L████: Thanks for playing! Goodbye~" + Display.RESET);
                    Display.line();
                    Display.gap();
                    break;
                default:
                    Display.gap();
                    System.out.println(Display.RED + "  [!] Invalid choice. Please enter 1-4." + Display.RESET);
                    Display.gap();
                    BatteDIsplay.pause(400); // Pause so user can read error message
            }
        }
    }
}
