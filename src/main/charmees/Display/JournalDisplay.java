package charmees.Display;
import charmees.util.Display;
import java.util.Scanner;

public class JournalDisplay {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        showJournalDisplay();
    }

    // Helpers
    static void printCharacter(String name, String desc, String stats) {
        System.out.println(Display.BOLD + Display.GREEN+ "  / " + name + Display.RESET);
        System.out.println("    " + desc);
        System.out.println(Display.DIM + "    " + stats + Display.RESET);
        System.out.println();
    }

    static void printEnemy(String name, String desc, String stats) {
        System.out.println(Display.BOLD + Display.RED + "  X " + name + Display.RESET);
        System.out.println("    " + desc);
        System.out.println(Display.DIM + "    " + stats + Display.RESET);
        System.out.println();
    }

    static void showTitleScreen() {
        clearScreen();
        printHeader("TITLE SCREEN");
        System.out.println(Display.GREEN + "  Welcome! Press Enter to return to the Journal." + Display.RESET);
        System.out.println();
        scanner.nextLine();
    }

    static void printHeader(String title) {
        String border = "═".repeat(40);
        int width = 38;
        int padding = (width - title.length()) / 2;
        String centered = " ".repeat(Math.max(0, padding)) + title;
        System.out.println(Display.YELLOW + Display.BOLD + "  ╔" + border + "╗" + Display.RESET);
        System.out.printf(Display.YELLOW + Display.BOLD + "  ║  %-38s║%n" + Display.RESET, centered);
        System.out.println(Display.YELLOW + Display.BOLD + "  ╚" + border + "╝" + Display.RESET);
        System.out.println();
    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pressEnterToGoBack() {
        System.out.print(Display.DIM + "  Press Enter to go back..." + Display.RESET);
        scanner.nextLine();
    }

    static void showError(String msg) {
        System.out.println(Display.RED + "\n  ✖ " + msg + Display.RESET);
        scanner.nextLine();
    }

    // MAIN JOURNAL SCREEN
    static void showJournalDisplay() {
        while (true) {
            clearScreen();
            printHeader("JOURNAL");
            System.out.println(Display.CYAN + " [1] CREDITS " + Display.RESET);
            System.out.println(Display.GREEN + " [2] INFO " + Display.RESET);
            System.out.println(Display.RED + " [3] BACK " + Display.RESET);
            System.out.println();
            System.out.print(Display.BOLD + "Choose an option: " + Display.RESET);
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> showCreditsScreen();
                case "2" -> showInfoScreen();
                case "3" -> showTitleScreen();
                default -> showError("Invalid option. Press Enter to try again.");
            }
        }
    }

    // CREDIT SCREEN
    static void showCreditsScreen() {
        clearScreen();
        printHeader("CREDITS");
        System.out.println(Display.BOLD + Display.MAGENTA + " DEVELOPER" + Display.RESET);
        System.out.println("    Laurence Andrey Baraga");
        System.out.println();

        System.out.println(Display.BOLD + Display.MAGENTA + " DEVELOPER" + Display.RESET);
        System.out.println("    Melody Ness Ecarma");
        System.out.println();

        System.out.println(Display.BOLD + Display.MAGENTA + " DEVELOPER" + Display.RESET);
        System.out.println("    Kimberly Daydayan");
        System.out.println();

        System.out.println(Display.BOLD + Display.MAGENTA + " DEVELOPER" + Display.RESET);
        System.out.println("    Neilcen Pedrosa");
        System.out.println();

        System.out.println(Display.BOLD + Display.MAGENTA + "  DEVELOPER" + Display.RESET);
        System.out.println("    Abigail Rodrigo");
        System.out.println();

        System.out.println(Display.BOLD + Display.CYAN + "  SPECIAL THANKS" + Display.RESET);
        System.out.println("    Everyone who playtested and supported this project.");
        System.out.println();

        System.out.println(Display.DIM + "  CSIT228 - OBJECT-ORIENTED PROGRAMMING 2 - G11 | 2026 " + Display.RESET);
        System.out.println();
        pressEnterToGoBack();
    }

    // INFO SCREEN
    static void showInfoScreen() {
        while (true) {
            clearScreen();
            printHeader("INFO");
            System.out.println(Display.DIM + "  Learn about the world: characters, mobs & bosses." + Display.RESET);
            System.out.println();
            System.out.println(Display.CYAN + "  [1] CHARACTERS" + Display.RESET);
            System.out.println(Display.CYAN + "  [2] MOBS" + Display.RESET);
            System.out.println(Display.CYAN + "  [3] BOSS" + Display.RESET);
            System.out.println(Display.RED + "  [4] BACK" + Display.RESET);
            System.out.println();
            System.out.print(Display.BOLD + "Choose an option: " + Display.RESET);
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> showCharactersScreen();
                case "2" -> showMobsScreen();
                case "3" -> showBossScreen();
                case "4" -> {
                    return;
                }
                default -> showError("Invalid option. Press Enter to try again.");
            }
        }
    }

    // CHARACTER INFO
    static void showCharactersScreen() {
        clearScreen();
        printHeader("CHARACTER");

        printCharacter(
                "Audry",
                "An Assassin who excels in close-quarters combat with Acidic Slime.",
                "Role: Assassin  |  Melee  |  Acidic Slime  |  HP: 100  |  MP: 25"
        );
        printCharacter(
                "Giantha",
                "A resilient Tank who holds the front line with the World Tree Branch.",
                "Role: Tank  |  Melee  |  World Tree Branch  |  HP: 250  |  MP: 20"
        );
        printCharacter(
                "Lazuli",
                "A devoted Healer who supports allies from range with a Staff.",
                "Role: Healer  |  Ranged  |  Staff  |  HP: 150  |  MP: 30"
        );
        printCharacter(
                "Lynzi",
                "A hard-hitting Dealer who channels Star Magic in melee.",
                "Role: Dealer  |  Melee  |  Star Magic  |  HP: 170  |  MP: 30"
        );
        printCharacter(
                "Shiera",
                "A versatile Support who controls the battlefield with Earth Magic from range.",
                "Role: Support  |  Ranged  |  Earth Magic  |  HP: 120  |  MP: 25"
        );

        pressEnterToGoBack();
    }

    // MOBS SCREEN
    static void showMobsScreen() {
        clearScreen();
        printHeader("MOBS");

        printEnemy(
                "Corrupted Skeleton",
                "A minion that fights in melee with a Bone Sword.",
                "Role: Minion  |  Melee  |  Bone Sword  |  HP: 120  |  MP: 2"
        );
        printEnemy(
                "Water Sprite",
                "A ranged minion that attacks with Water Magic.",
                "Role: Minion  |  Ranged  |  Water Magic  |  HP: 130  |  MP: 1"
        );
        printEnemy(
                "Echo Imp",
                "A ranged minion that channels Sound Magic.",
                "Role: Minion  |  Ranged  |  Sound Magic  |  HP: 130  |  MP: 4"
        );
        printEnemy(
                "Princess Puppet",
                "A melee minion wielding a wand.",
                "Role: Minion  |  Melee  |  Wand  |  HP: 100  |  MP: 5"
        );
        printEnemy(
                "Magma Skeleton",
                "A blazing melee minion armed with a Bone Sword.",
                "Role: Minion  |  Melee  |  Bone Sword  |  HP: 120  |  MP: 2"
        );
        printEnemy(
                "Water Blob",
                "A ranged minion that hurls Water Magic.",
                "Role: Minion  |  Ranged  |  Water Magic  |  HP: 130  |  MP: 1"
        );
        printEnemy(
                "Resonance Goblin",
                "A ranged minion that blasts with Sound Magic.",
                "Role: Minion  |  Ranged  |  Sound Magic  |  HP: 130  |  MP: 4"
        );
        printEnemy(
                "Moon Sprite",
                "A melee minion that strikes with Astral Magic.",
                "Role: Minion  |  Melee  |  Astral Magic  |  HP: 110  |  MP: 3"
        );

        pressEnterToGoBack();
    }

    // BOSS SCREEN
    static void showBossScreen() {
        clearScreen();
        printHeader("BOSS");

        printEnemy(
                "Kassundre",
                "A corrupted soul twisted by dark energy. Once a friend,\n    now driven by sorrow and rage.\n    Skills: Corrupted Tears | Corrupted Hug | Corrupted Flora",
                "Role: Miniboss  |  Ranged  |  Dark Magic  |  HP: 300  |  MP: 5"
        );
        printEnemy(
                "Siren Empress",
                "Ruler of the deep seas, she lures enemies to their doom\n    with tidal force and crushing pressure.\n    Skills: Water Whip | Tidal Wave | Abyssal Crush",
                "Role: Miniboss  |  Ranged  |  Water Magic  |  HP: 320  |  MP: 1"
        );
        printEnemy(
                "Resonara",
                "A being of pure sound that weaponizes harmony itself.\n    Her voice can shatter steel and shred the mind.\n    Skills: Sonic Blast | Echo Strike | Harmonic Destruction",
                "Role: Miniboss  |  Ranged  |  Sound Magic  |  HP: 310  |  MP: 4"
        );
        printEnemy(
                "Twinkle",
                "A puppet master cloaked in starlight and corruption.\n    The final boss — her strings control everything.\n    Skills: Puppet Slash | Lazer Devastation | Corruption",
                "Role: Boss  |  Melee  |  Puppet  |  HP: 500  |  MP: 6"
        );

        pressEnterToGoBack();
    }
}
