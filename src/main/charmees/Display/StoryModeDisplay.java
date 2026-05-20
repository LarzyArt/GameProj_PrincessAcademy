package charmees.Display;
import charmees.util.Display;
import java.util.Scanner;

public class StoryModeDisplay {

    static Scanner scanner = new Scanner(System.in);

    static boolean Chap1 = false;
    static boolean Chap2  = false;
    static boolean Chap3   = false;
    static boolean Epilogue = false;

    static final String SECRET = "LEZLI.BOOK.ZIP"; // Word that will unlock chap 1 to epilogue

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        while (true) {
            printMenu();

            String input = scanner.nextLine().trim();

            switch(input){
                case "1" -> Chap1();
                case "2" -> {
                    if (Chap1) {
                        Chap2();
                    } else {
                        System.out.println(Display.RED + "\n[LOCKED] Complete Chapter 1 first to proceed.\n" + Display.RESET);
                    }
                }
                case "3" -> {
                    if (Chap1 && Chap2) {
                        Chap3();
                    } else {
                        System.out.println(Display.RED + "\n[LOCKED] Complete Chapter 1  and 2 first to proceed.\n" + Display.RESET);
                    }
                }
                case "4" -> Prologue();
                case "5" ->
                {
                    if (Chap1 && Chap2 && Chap3) {
                        Epilogue();
                    } else {
                        System.out.println(Display.RED + "\n[LOCKED] Complete Chapter 1 , 2 and 3 first to proceed.\n" + Display.RESET);
                    }
                }
                case "0" -> {
                    System.out.println(Display.CYAN"\nGoodbye!\n" + Display.RESET);
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
        System.out.println("     ┌─ Choose Your Story ────────┐");
        System.out.printf ("     │  [1] (Chapter 1)  %s       │%n", tag(true));
        System.out.printf ("     │  [2] (Chapter 2)  %s       │%n", tag(Chap1));
        System.out.printf ("     │  [3] (Chapter 3)  %s       │%n", tag(Chap1 && Chap2));
        System.out.println("     └────────────────────────────┘");
        System.out.println();
        System.out.println("     ┌─ Extra ────────────────────┐");
        System.out.printf ("     │  [4] (Prologue)   %s       │%n", tag(true));
        System.out.printf ("     │  [5] (Epilogue)   %s       │%n", tag(Chap1 && Chap2 && Chap3));
        System.out.println("     └────────────────────────────┘");
        System.out.println();
        System.out.println("     [0] Exit");
        System.out.println();
        System.out.print(Display.YELLOW + "Enter choice: " + Display.RESET);
    }

    public static String tag(boolean unlocked) {
        if (unlocked) {
            return GREEN + "[OPEN]" + RESET;
        } else {
            return RED + "[LOCKED]" + RESET;
        }
    }

    public static void handleSecretWord(String input) {
        if (input.equals(SECRET)) {
            // Unlock everything at once
            Chap1 = true;
            Chap2  = true;
            Chap3   = true;
            Epilogue    = true;
            System.out.println("\n  All chapters unlocked!\n");
        } else {
            System.out.println("\n  Invalid choice. Try again.\n");
        }
    }

    // ── Story sections ───────────────────────────────────────────────

    public static void Prologue() {
        System.out.println("\n--- (Prologue) ---");
        System.out.println("Your prologue content here...");
        System.out.println();
    }

    public static void Chap1() {
        System.out.println("\n--- (Chapter 1) ---");
        System.out.println("Your Chapter 1 content here...");
        Chap1 = true;
        System.out.println("\n[Chapter 1 complete!]\n");
    }

    public static void Chap2() {
        System.out.println("\n--- (Chapter 2) ---");
        System.out.println("Your Chapter 2 content here...");
        Chap2 = true;
        System.out.println("\n[Chapter 2 complete!]\n");
    }

    public static void Chap3() {
        System.out.println("\n--- (Chapter 3) ---");
        System.out.println("Your Chapter 3 content here...");
        Chap3 = true;
        System.out.println("\n[Chapter 3 complete!]\n");
    }

    public static void Epilogue() {
        System.out.println("\n--- (Epilogue) ---");
        System.out.println("Your epilogue content here...");
        Epilogue = true;
        System.out.println("\n[Epilogue complete!]\n");
    }
}