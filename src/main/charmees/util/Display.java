package src.main.charmees.util;

import java.util.Scanner;

// ============================================================
//  Put anything here that more than one screen needs.
// ============================================================
public class Display {

    // constants so every screen uses the same width
    public static final int WIDTH = 50;
    public static final String LINE = "=".repeat(WIDTH);
    public static final String THIN = "-".repeat(WIDTH);

    // ── Terminal colours ────────────────────────────────────
    public static final String RESET   = "\033[0m";
    public static final String BOLD    = "\033[1m";
    public static final String DIM     = "\033[2m";
    public static final String CYAN    = "\033[36m";
    public static final String YELLOW  = "\033[33m";
    public static final String GREEN   = "\033[32m";
    public static final String RED     = "\033[31m";
    public static final String MAGENTA = "\033[35m";

    //border / header helpers

    /**
     * Prints a full-width "===...===" line
     */
    public static void line() {
        System.out.println(LINE);
    }

    /**
     * Prints a full-width "---...---" line
     */
    public static void thin() {
        System.out.println(THIN);
    }

    /**
     * Prints the game title
     * Call this at the top of every screen.
     */
    public static void banner() {
        line();
        centered("Princess Académie: Jumelles de la Destinée");
        line();
    }

    /**
     * Prints a section header like:
     * ==================================================
     * |              YOUR TITLE HERE                   |
     * ==================================================
     */
    public static void header(String title) {
        line();
        centered("[ " + title + " ]");
        line();
    }

    /**
     * Centers a string within WIDTH characters and prints it.
     */
    public static void centered(String text) {
        int spaces = (WIDTH - text.length()) / 2;
        if (spaces < 0) spaces = 0;
        System.out.println(" ".repeat(spaces) + text);
    }

    // menu helpers

    /**
     * Prints a numbered menu option, e.g. "  [1] Play Story Mode"
     */
    public static void option(int num, String label) {
        System.out.println("  [" + num + "] " + label);
    }

    /**
     * Prints a labelled field, e.g. "  Name    : Audry"
     * Pads the label to 10 chars so values line up.
     */
    public static void field(String label, String value) {
        System.out.printf("  %-10s: %s%n", label, value);
    }

    //input helpers------------------------------------

    /**
     * Prints "> " and reads the next integer from the scanner.
     * Returns -1 if the user types something that isn't a number.
     */
    public static int readInt(Scanner sc) {
        System.out.print("\n> ");
        try {
            int val = sc.nextInt();
            sc.nextLine(); // clear leftover newline
            return val;
        } catch (Exception e) {
            sc.nextLine(); // clear bad input
            return -1;
        }
    }

    /**
     * Waits for the user to press Enter before continuing.
     * Useful at the end of info screens.
     */
    public static void pressEnter(Scanner sc) {
        System.out.println("\n  Press Enter to continue...");
        System.out.print("> ");
        sc.nextLine();
    }

    /**
     * Prints a blank line (just a shortcut so screens look cleaner).
     */
    public static void gap() {
        System.out.println();
    }
}