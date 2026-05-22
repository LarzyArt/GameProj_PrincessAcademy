package charmees.Display;
import charmees.util.Character;
import charmees.util.Dialogue;
import charmees.util.Display;
import charmees.util.MobNPC;
import charmees.util.Character;
import java.util.Scanner;
import charmees.Core.Logic.PCvPCBattleLogic;
import charmees.Core.Logic.*;
import charmees.Display.MainMenuDisplay;
import charmees.util.MobNPC;


public class StoryModeDisplay {
    static Scanner scanner = new Scanner(System.in);
    private static Character[] characters;
    private static MobNPC[] mobs;
    static boolean Chap1 = false;
    static boolean Chap2 = false;
    static boolean Chap3 = false;
    static boolean Epilogue = false;
    static boolean SpecialEpisode = false;

    //Secret Code to unlock all the chapters
    static final String SECRET = "LEZLI.BOOK.ZIP";

    public static void startGame() {
        BattleLogic chap1 = new BattleLogic(characters, mobs, 1, scanner);
        BattleLogic chap2 = new BattleLogic(characters, mobs, 2, scanner);
        BattleLogic chap3 = new BattleLogic(characters, mobs, 3, scanner);
        while (true) {
            printMenu();

            System.out.print(Display.YELLOW + "Enter choice: " + Display.RESET);
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> chap1.run();

                case "2" -> {
                    if (Chap1) {
                        chap2.run();
                    } else {
                        System.out.println("\n[LOCKED] Complete Chapter 1 first.\n");
                    }
                }

                case "3" -> {
                    if (Chap1 && Chap2) {
                        chap3.run();
                    } else {
                        System.out.println("\n[LOCKED] Complete Chapter 1 and 2 first.\n");
                    }
                }

                case "4" -> BattleDIsplay.showDialogue(Dialogue.getPrologue(), scanner);

                case "5" -> {
                    if (Chap1 && Chap2 && Chap3) {
                        BattleDIsplay.showDialogue(Dialogue.getEpilogue(), scanner);
                    } else {
                        System.out.println("\n[LOCKED] Complete Chapter 1, 2, and 3 first.\n");
                    }
                }

                case "6" -> {
                    if (Chap1 && Chap2 && Chap3) {
                        BattleDIsplay.showDialogue(Dialogue.getSpecialEpilogue(), scanner);
                    } else {
                        System.out.println("\n[LOCKED] Complete Chapter 1, 2, and 3 first.\n");
                    }
                }

                case "0" -> {
                    System.out.println("\nGoodbye!\n");
                    BattleDIsplay.pause(400);
                    MainMenuDisplay mainmenu = new MainMenuDisplay(scanner, characters, mobs);
                    mainmenu.show();
                 return;
                }

                default -> handleSecretWord(input);
            }
        }
    }

    public static void printMenu() {
        System.out.println(Display.CYAN + "     ╔══════════════════════════════════════╗" + Display.RESET);
        System.out.println(Display.CYAN + "     ║        STORY MODE SELECTION          ║" + Display.RESET);
        System.out.println(Display.CYAN + "     ╚══════════════════════════════════════╝" + Display.RESET);
        System.out.println();
        System.out.println("     ┌─ Choose Your Story ───────────────┐");
        System.out.printf ("       [1] (Chapter 1)  %s              %n", tag(true));
        System.out.printf ("       [2] (Chapter 2)  %s              %n", tag(Chap1));
        System.out.printf ("       [3] (Chapter 3)  %s              %n", tag(Chap1 && Chap2));
        System.out.println("     └───────────────────────────────────┘");
        System.out.println();
        System.out.println("     ┌─ Extra ───────────────────────────┐");
        System.out.printf ("       [4] (Prologue)          %s       %n", tag(true));
        System.out.printf ("       [5] (Epilogue)          %s       %n", tag(Chap1 && Chap2 && Chap3));
        System.out.printf ("       [6] (Special Episode)   %s       %n", tag(Chap1 && Chap2 && Chap3));
        System.out.println("     └───────────────────────────────────┘");
        System.out.println();
        System.out.println("     [0] Exit");
        System.out.println();
    }

    public static String tag(boolean unlocked) {
        if (unlocked) {
            return Display.GREEN + "[OPEN]" + Display.RESET;
        } else {
            return Display.RED + "[LOCKED]" + Display.RESET;
        }
    }

    public static void handleSecretWord(String input) {
        if (input.equals(SECRET)) {
            Chap1 = true;
            Chap2 = true;
            Chap3 = true;
            Epilogue = true;
            SpecialEpisode = true;
            System.out.println("\nAll chapters unlocked!\n");
        } else {
            System.out.println("\nInvalid choice. Try again.\n");
        }
    }

    public static boolean allMainChaptersComplete() {
        return Chap1 && Chap2 && Chap3;
    }

    public static void maybeOfferTrialBattle() {
        if (allMainChaptersComplete()) {
            return;
        }

        if (Math.random() >= 0.40) {
            return;
        }

        System.out.println("\nA Trial Battle appeared!");
        System.out.println("[1] Enter Trial Battle");
        System.out.println("[0] Skip");
        System.out.print("Choose: ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            PCvPCBattleLogic.startTrial(scanner);
        } else {
            PCvPCBattleDisplay.skipped();
        }
    }

    public static void Prologue() {
        System.out.println("\n--- Prologue ---");
        System.out.println("Your prologue content here...");
    }

    public static void Chap1() {
        System.out.println("\n--- Chapter 1 ---");
        System.out.println("Your Chapter 1 content here...");
        Chap1 = true;
        System.out.println("\n[Chapter 1 complete!]");
        maybeOfferTrialBattle();
    }

    public static void Chap2() {
        System.out.println("\n--- Chapter 2 ---");
        System.out.println("Your Chapter 2 content here...");
        Chap2 = true;
        System.out.println("\n[Chapter 2 complete!]");
        maybeOfferTrialBattle();
    }

    public static void Chap3() {
        System.out.println("\n--- Chapter 3 ---");
        System.out.println("Your Chapter 3 content here...");
        Chap3 = true;
        System.out.println("\n[Chapter 3 complete!]");
        maybeOfferTrialBattle();
    }

    public static void Epilogue() {
        System.out.println("\n--- Epilogue ---");
        System.out.println("Your epilogue content here...");
        Epilogue = true;
        System.out.println("\n[Epilogue complete!]");
    }

    public static void SpecialEpisode() {
        System.out.println("\n--- Special Episode ---");
        System.out.println("Your Special Episode content here...");
        Epilogue = true;
        System.out.println("\n[Special Episode complete!]");
    }
}
