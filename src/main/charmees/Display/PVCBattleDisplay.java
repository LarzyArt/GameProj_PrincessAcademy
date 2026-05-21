package charmees.Display;

import charmees.util.Character;
import charmees.util.Display;

public class PVCBattleDisplay {
    // =========================================================
    //  SCREEN HEADER
    // =========================================================

    public static void showArcadeHeader() {
        Display.banner();
        Display.centered("[ ARCADE MODE — PLAYER vs CHARACTER ]");
        Display.line();
    }

    // =========================================================
    //  MAIN BATTLEFIELD
    // =========================================================

    public static void showBattleField(
            Character player,   int playerMaxHP,
            Character opponent, int oppMaxHP,
            int round, int turnCount) {

        Display.gap();
        Display.header("ARCADE  Round " + round + "  —  Turn " + turnCount);

        // ── Opponent field (top) ─────────────────────────────
        Display.gap();
        showOpponentField(opponent, oppMaxHP);

        // ── Divider ──────────────────────────────────────────
        Display.gap();
        showVersusBar();

        // ── Player field (bottom) ────────────────────────────
        Display.gap();
        showPlayerField(player, playerMaxHP);

        Display.gap();
        Display.thin();
    }

    // ── Opponent panel ───────────────────────────────────────
    public static void showOpponentField(Character opp, int oppMaxHP) {
        System.out.println(Display.MAGENTA + "  CPU OPPONENT" + Display.RESET);
        Display.thin();

        if (!opp.isAlive()) {
            System.out.println(Display.DIM + "  " + opp.getName()
                    + " [" + opp.charClass + "] — DEFEATED" + Display.RESET);
            return;
        }

        System.out.println(Display.BOLD + Display.MAGENTA
                + "  " + opp.getName()
                + "  [" + opp.charClass + " · " + opp.type + "]"
                + Display.RESET);

        System.out.printf("  HP: %s%d%s / %d   MP: %s%d%s%n",
                hpColour(opp.healthPoints, oppMaxHP), opp.healthPoints, Display.RESET,
                oppMaxHP,
                Display.CYAN, opp.manaPoints, Display.RESET);

        System.out.println("  " + hpBar(opp.healthPoints, oppMaxHP, 30)
                + "  " + percent(opp.healthPoints, oppMaxHP) + "%");
    }

    // ── VS bar ───────────────────────────────────────────────

    private static void showVersusBar() {
        Display.centered(Display.YELLOW + "~ VS ~" + Display.RESET);
    }

    // ── Player panel ─────────────────────────────────────────
    public static void showPlayerField(Character player, int playerMaxHP) {
        System.out.println(Display.GREEN + "  YOUR FIGHTER" + Display.RESET);
        Display.thin();

        System.out.println(Display.BOLD + Display.GREEN
                + "  " + player.getName()
                + "  [" + player.charClass + " · " + player.type + "]"
                + Display.RESET);

        System.out.printf("  HP: %s%d%s / %d   MP: %s%d%s%n",
                hpColour(player.healthPoints, playerMaxHP), player.healthPoints, Display.RESET,
                playerMaxHP,
                Display.CYAN, player.manaPoints, Display.RESET);

        System.out.println("  " + hpBar(player.healthPoints, playerMaxHP, 30)
                + "  " + percent(player.healthPoints, playerMaxHP) + "%");

        System.out.println(Display.DIM
                + "  Weapon: " + player.weapon + Display.RESET);
    }

    // =========================================================
    //  TURN PHASE HEADERS
    // =========================================================

    /** Prints "── Your Turn ──" header before the action menu. */
    public static void showPlayerPhaseHeader(Character player) {
        Display.gap();
        Display.thin();
        System.out.println(Display.GREEN + "  ── " + player.getName() + "'s Turn ──" + Display.RESET);
        Display.thin();
    }

    /** Prints "── CPU Turn ──" header before the opponent acts. */
    public static void showCPUPhaseHeader(Character opp) {
        Display.gap();
        Display.thin();
        System.out.println(Display.MAGENTA + "  ── " + opp.getName() + " (CPU) acts! ──" + Display.RESET);
        Display.thin();
    }

    // =========================================================
    //  ACTION MENU
    // =========================================================

    public static void showActionMenu(Character player) {
        Display.gap();
        Display.thin();
        System.out.println("  What will " + Display.BOLD + player.getName() + Display.RESET + " do?");
        Display.gap();
        System.out.println("  [1] " + Display.CYAN + "SKILL" + Display.RESET
                + "    — Use Skill 1 or 2");
        System.out.println("  [2] " + Display.YELLOW + "ULTIMATE" + Display.RESET
                + "  — Unleash Signature Move (Skill 3)");
        System.out.println("  [3] " + Display.RED + "FORFEIT" + Display.RESET
                + "   — Give up the current match");
        Display.thin();
    }

    // =========================================================
    //  SKILL MENUS
    // =========================================================

    /**
     * Shows Skill 1 / Skill 2 selection for the active character.
     */
    public static void showSkillMenu(Character actor) {
        String[] skills = actor.getSkillList();
        Display.gap();
        System.out.println(Display.BOLD + "  " + actor.getName() + " — Choose a Skill:"
                + Display.RESET);
        Display.thin();
        int limit = Math.min(2, skills.length);
        for (int i = 0; i < limit; i++) {
            System.out.println("  [" + (i + 1) + "] " + skills[i]);
        }
        System.out.println("  [0] Cancel");
        Display.thin();
    }

    /**
     * Shows the Signature/Ultimate move confirmation screen.
     */
    public static void showUltimateConfirm(Character actor) {
        String[] skills = actor.getSkillList();
        Display.gap();
        System.out.println(Display.BOLD + Display.YELLOW
                + "  ★ ULTIMATE MOVE ★" + Display.RESET);
        Display.thin();
        System.out.println("  " + skills[2]);
        Display.thin();
        System.out.println("  Go all in?");
        System.out.println("  [1] Use it!");
        System.out.println("  [0] Cancel");
        Display.thin();
    }

    // =========================================================
    //  ROUND RESULT SCREENS
    // =========================================================

    /**
     * Full victory splash after the player defeats the CPU opponent.
     */
    public static void showRoundVictory(Character player, int round) {
        Display.gap();
        pause(400);
        Display.line();
        pause(250);
        Display.centered(Display.YELLOW + Display.BOLD
                + "★  ROUND " + round + " — VICTORY!  ★" + Display.RESET);
        pause(250);
        Display.centered(player.getName() + " wins the round!");
        pause(400);
        Display.thin();
        Display.gap();
    }

    /**
     * Defeat splash after the player's character is knocked out.
     */
    public static void showRoundDefeat(Character opp, int round) {
        Display.gap();
        pause(400);
        Display.line();
        pause(250);
        Display.centered(Display.RED + Display.BOLD
                + "✗  ROUND " + round + " — DEFEAT...  ✗" + Display.RESET);
        pause(250);
        Display.centered(opp.getName() + " wins the round.");
        pause(400);
        Display.thin();
        Display.gap();
    }

    /**
     * Full-match victory splash (best-of-N won).
     */
    public static void showMatchVictory(Character player, int wins) {
        Display.gap();
        pause(500);
        Display.line();
        pause(300);
        Display.centered(Display.YELLOW + Display.BOLD
                + "★★  MATCH COMPLETE — YOU WIN!  ★★" + Display.RESET);
        pause(300);
        Display.centered(player.getName() + " goes home a champion! (" + wins + " rounds won)");
        pause(600);
        Display.line();
        Display.gap();
    }

    /**
     * Full-match defeat splash (CPU won best-of-N).
     */
    public static void showMatchDefeat(Character opp, int oppWins) {
        Display.gap();
        pause(500);
        Display.line();
        pause(300);
        Display.centered(Display.RED + Display.BOLD
                + "✗✗  MATCH OVER — YOU LOST  ✗✗" + Display.RESET);
        pause(300);
        Display.centered(opp.getName() + " proved too much. (" + oppWins + " rounds won by CPU)");
        pause(600);
        Display.line();
        Display.gap();
    }

    /**
     * Shows the between-round score tally.
     */
    public static void showScoreboard(String playerName, int playerWins,
                                      String oppName,    int oppWins,
                                      int totalRounds) {
        Display.gap();
        Display.header("SCOREBOARD");
        System.out.printf("  %-20s  %d wins%n", playerName, playerWins);
        System.out.printf("  %-20s  %d wins%n", oppName + " (CPU)", oppWins);
        System.out.println("  Best of " + totalRounds);
        Display.thin();
        Display.gap();
    }

    // =========================================================
    //  CHARACTER SELECT SCREENS
    // =========================================================

    /**
     * Arcade mode title card shown before character selection.
     */
    public static void showArcadeModeIntro() {
        Display.gap();
        showArcadeHeader();
        Display.gap();
        Display.centered("Test your skills against the roster.");
        Display.centered("Choose your fighter. Choose your rival.");
        Display.centered("Only one leaves standing.");
        Display.gap();
        Display.thin();
        Display.gap();
    }


    public static void showCharacterSelect(String label, String[] names, String[] classes) {
        Display.gap();
        Display.header(label + " — SELECT CHARACTER");
        for (int i = 0; i < names.length; i++) {
            System.out.printf("  [%d] %-16s  [%s]%n", i + 1, names[i], classes[i]);
        }
        Display.thin();
    }

    /**
     * Prints the match-up banner once both fighters are chosen.
     */
    public static void showMatchUpBanner(String playerName, String oppName) {
        Display.gap();
        Display.line();
        Display.centered(Display.BOLD
                + playerName + "  VS  " + oppName + Display.RESET);
        Display.line();
        pause(600);
        Display.gap();
    }

    // =========================================================
    //  FORFEIT CONFIRMATION
    // =========================================================

    public static void showForfeitConfirm() {
        Display.gap();
        Display.thin();
        System.out.println("  Really forfeit? This counts as a round loss.");
        System.out.println("  [1] Yes, give up");
        System.out.println("  [0] No, keep fighting");
        Display.thin();
    }

    // =========================================================
    //  LOG / MISC
    // =========================================================

    /** Prints a battle log line (prefixed with two spaces). */
    public static void log(String msg) {
        System.out.println("  " + msg);
    }

    /** Prints a battle log line highlighted in yellow (for crits, ultimates, etc.). */
    public static void logHighlight(String msg) {
        System.out.println("  " + Display.YELLOW + msg + Display.RESET);
    }

    /** Prints a dimmed informational line (status messages, low-HP warnings, etc.). */
    public static void logInfo(String msg) {
        System.out.println("  " + Display.DIM + msg + Display.RESET);
    }

    /** Prints a red danger line (character KO, fatal hit, etc.). */
    public static void logDanger(String msg) {
        System.out.println("  " + Display.RED + msg + Display.RESET);
    }

    // =========================================================
    //  HELPERS  — delegates to BattleDIsplay to avoid duplication
    // =========================================================

    /** HP bar — delegates to BattleDIsplay.hpBar(). */
    public static String hpBar(int hp, int max, int width) {
        return BattleDIsplay.hpBar(hp, max, width);
    }

    /** Integer percentage of hp/max — delegates to BattleDIsplay. */
    public static int percent(int hp, int max) {
        if (max <= 0) return 0;
        return Math.max(0, Math.min(100, (int)((double) hp / max * 100)));
    }

    /** Thread.sleep wrapper — delegates to BattleDIsplay.pause(). */
    public static void pause(int ms) {
        BattleDIsplay.pause(ms);
    }

    /** Returns an ANSI colour based on remaining HP percentage. */
    private static String hpColour(int hp, int max) {
        double r = max > 0 ? (double) hp / max : 0;
        return r > 0.5 ? Display.GREEN : r > 0.25 ? Display.YELLOW : Display.RED;
    }
}