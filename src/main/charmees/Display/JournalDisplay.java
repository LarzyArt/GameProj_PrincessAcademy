package src.main.charmees.Display;
import static src.main.charmees.util.Display.*;

import java.util.Scanner;

public class JournalDisplay {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[]args){
        showJournalDisplay();
    }
// Helpers
    static void printCharacter(String name, String desc, String stats) {
        System.out.println(BOLD + GREEN + "  / " + name + RESET);
        System.out.println("    " + desc);
        System.out.println(DIM  + "    " + stats + RESET);
        System.out.println();
    }

    static void printEnemy(String name, String desc, String stats) {
        System.out.println(BOLD + RED + "  X " + name + RESET);
        System.out.println("    " + desc);
        System.out.println(DIM  + "    " + stats + RESET);
        System.out.println();
    }

    static void showTitleScreen() {
        clearScreen();
        printHeader("TITLE SCREEN");
        System.out.println(GREEN + "  Welcome! Press Enter to return to the Info Screen." + RESET);
        System.out.println();
        scanner.nextLine();
    }

    static void printHeader(String title) {
        String border = "═".repeat(40);
        int width = 38;
        int padding = (width - title.length()) / 2;
        String centered = " ".repeat(Math.max(0, padding)) + title;
        System.out.println(YELLOW + BOLD + "  ╔" + border + "╗" + RESET);
        System.out.printf(YELLOW + BOLD + "  ║  %-38s║%n" + RESET, centered);
        System.out.println(YELLOW + BOLD + "  ╚" + border + "╝" + RESET);
        System.out.println();
    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pressEnterToGoBack() {
        System.out.print(DIM + "  Press Enter to go back..." + RESET);
        scanner.nextLine();
    }

    static void showError(String msg) {
        System.out.println(RED + "\n  ✖ " + msg + RESET);
        scanner.nextLine();
    }

    // MAIN JOURNAL SCREEN
    static void showJournalDisplay(){
        while (true){
            clearScreen();
            printHeader("JOURNA;");
            System.out.println(CYAN + " [1] CREDITS "+ RESET);
            System.out.println(GREEN + " [2] CREDITS " + RESET);
            System.out.println(RED + " [3] CREDITS " + RESET);
            System.out.println();
            String input = scanner.nextLine().trim();
            switch (input){
                case "1" -> showCreditsScreen();
                case "2" -> showInfoScreen();
                case "3" -> showTitleScreen();
            }
        }
    }

    // CREDIT SCREEN
    static void showCreditsScreen(){
        clearScreen();
        printHeader("CREDITS");
        System.out.println(BOLD + MAGENTA + " DEVELOPER" + RESET);
        System.out.println("    Laurence Andrey Baraga");
        System.out.println();

        System.out.println(BOLD + MAGENTA " DEVELOPER" + RESET);
        System.out.println("    Melody Ness Ecarma");
        System.out.println();

        System.out.println(BOLD + MAGENTA + "  DEVELOPER" + RESET);
        System.out.println("    Kimberly Daydayan");
        System.out.println();

        System.out.println(BOLD + MAGENTA + "  DEVELOPER" + RESET);
        System.out.println("    Neilcen Pedrosa");
        System.out.println();

        System.out.println(BOLD + MAGENTA + "  DEVELOPER" + RESET);
        System.out.println("    Abigail Rodrigo");
        System.out.println();

        System.out.println(BOLD + CYAN + "  SPECIAL THANKS" + RESET);
        System.out.println("    Everyone who playtested and supported this project.");
        System.out.println();

        System.out.println(DIM + "  CSIT228 - OBJECT-ORIENTED PROGRAMMING 2 - G11 | 2026 " + RESET);
        System.out.println();
        pressEnterToGoBack();
       }
    }
}
