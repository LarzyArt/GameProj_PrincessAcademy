package charmees.Display;

import charmees.util.Character;
import charmees.util.Display;

public class PCvPCBattleDisplay {

    public static void showTrialIntro() {
        Display.gap();
        Display.banner();
        Display.centered("[ TRIAL BATTLE - P/C vs P/C ]");
        Display.line();
        Display.centered("Choose any player or character, then choose who to fight.");
        Display.centered("No HP/MP bonus is added for now.");
        Display.gap();
        Display.thin();
    }

    public static void title() {
        showTrialIntro();
    }

    public static void showMatchUp(String playerName, String opponentName) {
        Display.gap();
        Display.line();
        Display.centered(playerName + " VS " + opponentName);
        Display.line();
    }

    public static void showBattleField(Character player, int playerMaxHP,
                                       Character opponent, int oppMaxHP,
                                       int turnCount) {
        Display.gap();
        Display.header("TRIAL BATTLE - TURN " + turnCount);

        showFighterPanel("OPPONENT", opponent, oppMaxHP);
        Display.gap();
        Display.centered(Display.YELLOW + "~ VS ~" + Display.RESET);
        Display.gap();
        showFighterPanel("YOUR FIGHTER", player, playerMaxHP);

        Display.gap();
        Display.thin();
    }

    private static void showFighterPanel(String label, Character fighter, int maxHP) {
        System.out.println(Display.CYAN + "  " + label + Display.RESET);
        Display.thin();

        System.out.println(Display.BOLD + "  " + fighter.getName()
                + " [" + fighter.charClass + " - " + fighter.type + "]"
                + Display.RESET);

        System.out.println("  HP: " + fighter.healthPoints + "/" + maxHP
                + "   MP: " + fighter.manaPoints);
    }

    public static void showPlayerPhaseHeader(Character player) {
        Display.gap();
        Display.thin();
        System.out.println(Display.GREEN + "  " + player.getName() + "'s Turn" + Display.RESET);
        Display.thin();
    }

    public static void showCPUPhaseHeader(Character opponent) {
        Display.gap();
        Display.thin();
        System.out.println(Display.RED + "  " + opponent.getName() + " acts!" + Display.RESET);
        Display.thin();
    }

    public static void showActionMenu(Character player) {
        Display.gap();
        System.out.println("  What will " + Display.BOLD + player.getName() + Display.RESET + " do?");
        System.out.println("  [1] Skill");
        System.out.println("  [2] Ultimate");
        System.out.println("  [3] Recover MP");
        System.out.println("  [4] Retire");
        Display.thin();
    }

    public static void actionMenu(Character player) {
        showActionMenu(player);
    }

    public static void showSkillMenu(Character actor) {
        String[] skills = actor.getSkillList();

        Display.gap();
        System.out.println(Display.BOLD + "  " + actor.getName() + " - Choose a Skill" + Display.RESET);
        Display.thin();

        int limit = Math.min(2, skills.length);
        for (int i = 0; i < limit; i++) {
            System.out.println("  [" + (i + 1) + "] " + skills[i]);
        }

        System.out.println("  [0] Cancel");
        Display.thin();
    }

    public static void showUltimateConfirm(Character actor) {
        String[] skills = actor.getSkillList();

        Display.gap();
        System.out.println(Display.YELLOW + Display.BOLD + "  ULTIMATE MOVE" + Display.RESET);
        Display.thin();

        if (skills.length >= 3) {
            System.out.println("  " + skills[2]);
        } else {
            System.out.println("  No ultimate move available.");
        }

        System.out.println("  [1] Use it");
        System.out.println("  [0] Cancel");
        Display.thin();
    }

    public static void showRetireConfirm() {
        Display.gap();
        Display.thin();
        System.out.println("  Retire from the trial battle?");
        System.out.println("  [1] Yes");
        System.out.println("  [0] No");
        Display.thin();
    }

    public static void showCharacterSelect(String label, String[] names, String[] classes) {
        Display.gap();
        Display.header(label);

        for (int i = 0; i < names.length; i++) {
            System.out.printf("  [%d] %-16s [%s]%n", i + 1, names[i], classes[i]);
        }

        Display.thin();
    }

    public static void showBattleVictory(Character player) {
        Display.gap();
        Display.line();
        Display.centered(Display.YELLOW + Display.BOLD
                + player.getName() + " wins the Trial Battle!" + Display.RESET);
        Display.line();
    }

    public static void showBattleDefeat(Character opponent) {
        Display.gap();
        Display.line();
        Display.centered(Display.RED + Display.BOLD
                + opponent.getName() + " wins the Trial Battle." + Display.RESET);
        Display.line();
    }

    public static void win(String name) {
        Display.gap();
        Display.centered(Display.YELLOW + name + " wins the Trial Battle!" + Display.RESET);
    }

    public static void skipped() {
        System.out.println(Display.CYAN + "\nTrial Battle skipped.\n" + Display.RESET);
    }

    public static void log(String msg) {
        System.out.println("  " + msg);
    }

    public static void logInfo(String msg) {
        System.out.println("  " + Display.CYAN + msg + Display.RESET);
    }

    public static void logDanger(String msg) {
        System.out.println("  " + Display.RED + msg + Display.RESET);
    }

    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}












