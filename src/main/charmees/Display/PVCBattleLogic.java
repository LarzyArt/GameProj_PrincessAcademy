package charmees.Display;

import charmees.util.Character;
import charmees.util.Display;
import java.util.Scanner;


public class PVCBattleLogic {
    // ── Constants-----------------------------
    public static final int ROUNDS_TO_WIN = 2;   // first to this many round wins takes the match
    public static final int DEFAULT_ROUNDS = 3;  // best-of-3 by default

    // ── State--------------------------------
    private final Character player;
    private final Character opponent;
    private final Scanner   sc;

    private final int playerBaseHP;
    private final int playerBaseMP;
    private final int oppBaseHP;
    private final int oppBaseMP;

    private int playerWins = 0;
    private int oppWins    = 0;
    private int round      = 1;
    private int turnCount  = 1;

    private boolean playerForfeited = false;

    
    //  CONSTRUCTOR---------------------------------------
    public PVCBattleLogic(Character player, Character opponent, Scanner sc) {
        this.player     = player;
        this.opponent   = opponent;
        this.sc         = sc;

        // snapshot base stats so each round resets cleanly
        this.playerBaseHP = player.healthPoints;
        this.playerBaseMP = player.manaPoints;
        this.oppBaseHP    = opponent.healthPoints;
        this.oppBaseMP    = opponent.manaPoints;
    }

   
    //  ENTRY POINT--------------------------------------------
    public void run() {
        PVCBattleDisplay.showArcadeModeIntro();
        PVCBattleDisplay.showMatchUpBanner(player.getName(), opponent.getName());
        Display.pressEnter(sc);

        // ── Match loop ───────────────────────────────────────
        while (playerWins < ROUNDS_TO_WIN && oppWins < ROUNDS_TO_WIN) {
            resetRound();
            playRound();
        }

        // ── Final result ─────────────────────────────────────
        if (playerWins >= ROUNDS_TO_WIN) {
            PVCBattleDisplay.showMatchVictory(player, playerWins);
        } else {
            PVCBattleDisplay.showMatchDefeat(opponent, oppWins);
        }
    }

    
    //  ROUND FLOW-------------------------------------------
    private void resetRound() {
        player.healthPoints   = playerBaseHP;
        player.manaPoints     = playerBaseMP;
        opponent.healthPoints = oppBaseHP;
        opponent.manaPoints   = oppBaseMP;
        turnCount             = 1;
        playerForfeited       = false;
    }

    /** Runs a single round until one fighter is KO'd or player forfeits. */
    private void playRound() {
        PVCBattleDisplay.logHighlight("=== ROUND " + round + " — BEGIN! ===");
        PVCBattleDisplay.pause(400);

        while (isRoundOngoing()) {
            // ── Render battlefield ───────────────────────────
            PVCBattleDisplay.showBattleField(
                    player, playerBaseHP,
                    opponent, oppBaseHP,
                    round, turnCount);

            // ── Player's turn ────────────────────────────────
            PVCBattleDisplay.showPlayerPhaseHeader(player);
            PVCBattleDisplay.showActionMenu(player);
            int action = Display.readInt(sc);

            switch (action) {
                case 1: playerSkill();    break;
                case 2: playerUltimate(); break;
                case 3: playerForfeit();  break;
                default:
                    PVCBattleDisplay.log("Invalid choice — enter 1 to 3.");
            }

            if (!isRoundOngoing()) break;

            // ── CPU turn (only fires if player didn't forfeit) ─
            if (!playerForfeited) {
                cpuTurn();
            }

            turnCount++;
        }

        // ── Round result ─────────────────────────────────────
        resolveRound();
    }

    
    //  PLAYER ACTIONS---------------------------------------------
    private void playerSkill() {
        PVCBattleDisplay.showSkillMenu(player);
        int pick = Display.readInt(sc);

        int limit = Math.min(2, player.getSkillList().length);
        if (pick < 1 || pick > limit) {
            PVCBattleDisplay.log("Cancelled.");
            return;
        }

        executePlayerSkill(pick);
    }

    private void playerUltimate() {
        if (player.getSkillList().length < 3) {
            PVCBattleDisplay.log(player.getName() + " has no Ultimate Move.");
            return;
        }

        PVCBattleDisplay.showUltimateConfirm(player);
        int confirm = Display.readInt(sc);
        if (confirm != 1) { PVCBattleDisplay.log("Cancelled."); return; }

        executePlayerSkill(3);
    }

    private void playerForfeit() {
        PVCBattleDisplay.showForfeitConfirm();
        int confirm = Display.readInt(sc);
        if (confirm == 1) {
            playerForfeited = true;
            PVCBattleDisplay.logDanger(player.getName()
                    + " throws in the towel. Round conceded.");
            PVCBattleDisplay.pause(600);
            // mark player HP as 0 so isRoundOngoing() exits cleanly
            player.healthPoints = 0;
        } else {
            PVCBattleDisplay.log("Back in it!");
        }
    }


    private void executePlayerSkill(int skillNum) {
        String targetType = player.getSkillTargetType(skillNum);
        String skillName  = player.getSkillList()[skillNum - 1];

        if (targetType.equals("ENEMY")) {
            // Snapshot opponent HP before skill fires, diff = damage dealt
            int hpBefore = opponent.healthPoints;
            player.useSkill(skillNum, wrapOpponent(), null, new Character[]{player});
            int dealt = hpBefore - opponent.healthPoints;

            if (dealt > 0) {
                PVCBattleDisplay.log(player.getName() + " uses " + skillName
                        + " → " + dealt + " damage to " + opponent.getName() + "!");
            }
        } else {
            // ALLY / SELF / ALL — apply to player
            PVCBattleDisplay.log(player.getName() + " uses " + skillName + "!");
            player.useSkill(skillNum, null, player, new Character[]{player});
        }

        PVCBattleDisplay.pause(600);
    }

    
    //  CPU TURN------------------------------------------------------
    private void cpuTurn() {
        PVCBattleDisplay.showCPUPhaseHeader(opponent);

        String[] skills    = opponent.getSkillList();
        int      skillCount = skills.length;

        // Decide whether to attempt Ultimate
        boolean tryUlt = skillCount >= 3
                && cpuUltChance()
                && opponent.manaPoints >= extractMPCost(skills[2]);

        int chosenSkill;
        if (tryUlt) {
            chosenSkill = 3;
        } else {
            // pick skill 1 or 2 that the CPU can afford
            boolean canSkill2 = skillCount >= 2
                    && opponent.manaPoints >= extractMPCost(skills[1]);
            boolean canSkill1 = opponent.manaPoints >= extractMPCost(skills[0]);

            if (!canSkill1 && !canSkill2) {
                // CPU recovers MP
                opponent.manaPoints += 5;
                PVCBattleDisplay.logInfo(opponent.getName()
                        + " (CPU) gathers focus… (+5 MP)");
                PVCBattleDisplay.pause(600);
                return;
            }

            // Weighted random: prefer skill 2 when affordable
            if (canSkill2 && Math.random() < 0.55) {
                chosenSkill = 2;
            } else if (canSkill1) {
                chosenSkill = 1;
            } else {
                chosenSkill = 2;
            }
        }

        // Fire the chosen skill, intercept damage via HP snapshot
        int hpBefore = player.healthPoints;
        opponent.useSkill(chosenSkill, wrapPlayer(), null, new Character[]{opponent});
        int rawDamage = hpBefore - player.healthPoints;

        if (rawDamage > 0) {
            PVCBattleDisplay.log(opponent.getName() + " (CPU) uses "
                    + opponent.getSkillList()[chosenSkill - 1]
                    + " → " + rawDamage + " damage to " + player.getName() + "!");
        }

        PVCBattleDisplay.pause(700);
    }

    /** True if the CPU should attempt its ultimate this turn. */
    private boolean cpuUltChance() {
        return Math.random() < 0.20;
    }


    //  ROUND / MATCH RESOLUTION
    private boolean isRoundOngoing() {
        return player.isAlive() && opponent.isAlive();
    }

    private void resolveRound() {
        if (!player.isAlive()) {
            PVCBattleDisplay.showRoundDefeat(opponent, round);
            oppWins++;
        } else if (!opponent.isAlive()) {
            PVCBattleDisplay.showRoundVictory(player, round);
            playerWins++;
        } else {
            // Should not normally reach here, but treat as draw / CPU win
            PVCBattleDisplay.log("Round " + round + " ended unexpectedly — no winner.");
        }

        PVCBattleDisplay.showScoreboard(
                player.getName(), playerWins,
                opponent.getName(), oppWins,
                DEFAULT_ROUNDS);

        round++;
        if (playerWins < ROUNDS_TO_WIN && oppWins < ROUNDS_TO_WIN) {
            Display.pressEnter(sc); // wait before next round
        }
    }

    private charmees.util.MobNPC wrapOpponent() {
        return new charmees.util.MobNPC(
                opponent.getName(),
                opponent.charClass,
                opponent.type,
                opponent.weapon,
                opponent.healthPoints,
                0) {

            @Override
            public void takedamage(int damage) {
                opponent.healthPoints -= damage;
                if (opponent.healthPoints < 0) opponent.healthPoints = 0;
                // Sync wrapper field so callers reading this object also see it
                this.healthPoints = opponent.healthPoints;
            }

            @Override
            public boolean isAlive() {
                return opponent.healthPoints > 0;
            }
        };
    }

    
    private charmees.util.MobNPC wrapPlayer() {
        return new charmees.util.MobNPC(
                player.getName(),
                player.charClass,
                player.type,
                player.weapon,
                player.healthPoints,
                0) {

            @Override
            public void takedamage(int damage) {
                player.healthPoints -= damage;
                if (player.healthPoints < 0) player.healthPoints = 0;
                this.healthPoints = player.healthPoints;
            }

            @Override
            public boolean isAlive() {
                return player.healthPoints > 0;
            }
        };
    }

   
    //  STAT PARSING HELPERS-----------------------------------------------
    private int extractMPCost(String skillLabel) {
        try {
            int idx = skillLabel.indexOf("Cost:");
            if (idx < 0) return 0;
            String sub = skillLabel.substring(idx + 5).trim();
            // consume digits
            StringBuilder sb = new StringBuilder();
            for (char c : sub.toCharArray()) {
                if (c >= '0' && c <= '9') sb.append(c);
                else if (sb.length() > 0) break;
            }
            return sb.length() > 0 ? Integer.parseInt(sb.toString()) : 0;
        } catch (Exception e) {
            return 0;
        }
    }

  
    //  STATIC FACTORY — convenience entry point------------------------------------------
    public static void startArcade(Scanner sc) {
        // ── Build the selectable roster ──────────────────────
        // Players (index 0-4)
        charmees.Entity.players.Abby    abby    = new charmees.Entity.players.Abby   ("Abby",    "Drama Queen",  "SL Energy",  "Lightstick",  160, 30);
        charmees.Entity.players.Cenicen cenicen = new charmees.Entity.players.Cenicen("Cenicen", "Stan",         "Idol Energy","Lightstick",  150, 30);
        charmees.Entity.players.Kimni   kimni   = new charmees.Entity.players.Kimni  ("Kimni",   "Caster",       "Magic",      "Wand",        140, 40);
        charmees.Entity.players.Larzy   larzy   = new charmees.Entity.players.Larzy  ("Larzy",   "Knight",       "Physical",   "Spear",       180, 20);
        charmees.Entity.players.Mewods  mewods  = new charmees.Entity.players.Mewods ("Mewods",  "Rogue",        "Shadow",     "Dagger",      145, 35);

        // Characters (index 5-8, used as CPU opponents)
        charmees.Entity.characters.Audry   audry   = new charmees.Entity.characters.Audry  ("Audry",   "Slime Mage",   "Magic",   "Staff",    150, 30);
        charmees.Entity.characters.Giantha giantha = new charmees.Entity.characters.Giantha("Giantha", "Giant",        "Physical","Club",     200, 15);
        charmees.Entity.characters.Lynzi   lynzi   = new charmees.Entity.characters.Lynzi  ("Lynzi",   "Duelist",      "Wind",    "Rapier",   155, 30);
        charmees.Entity.characters.Shiera  shiera  = new charmees.Entity.characters.Shiera ("Shiera",  "Paladin",      "Holy",    "Shield",   175, 25);

        Character[] allFighters = { abby, cenicen, kimni, larzy, mewods,
                                    audry, giantha, lynzi, shiera };

        String[] names   = new String[allFighters.length];
        String[] classes = new String[allFighters.length];
        for (int i = 0; i < allFighters.length; i++) {
            names[i]   = allFighters[i].getName();
            classes[i] = allFighters[i].charClass;
        }

        // ── Player chooses their fighter ─────────────────────
        int playerChoice = -1;
        while (playerChoice < 1 || playerChoice > allFighters.length) {
            PVCBattleDisplay.showCharacterSelect("YOUR FIGHTER", names, classes);
            playerChoice = Display.readInt(sc);
            if (playerChoice < 1 || playerChoice > allFighters.length) {
                PVCBattleDisplay.log("Pick a number between 1 and " + allFighters.length + ".");
            }
        }
        Character chosenPlayer = allFighters[playerChoice - 1];

        // ── Player chooses the CPU opponent ──────────────────
        int oppChoice = -1;
        while (oppChoice < 1 || oppChoice > allFighters.length || oppChoice == playerChoice) {
            PVCBattleDisplay.showCharacterSelect("CPU OPPONENT", names, classes);
            oppChoice = Display.readInt(sc);
            if (oppChoice == playerChoice) {
                PVCBattleDisplay.log("Pick someone other than yourself!");
            } else if (oppChoice < 1 || oppChoice > allFighters.length) {
                PVCBattleDisplay.log("Pick a number between 1 and " + allFighters.length + ".");
            }
        }
        Character chosenOpp = allFighters[oppChoice - 1];

        // ── Launch the match ──────────────────────────────────
        new PVCBattleLogic(chosenPlayer, chosenOpp, sc).run();
    }
}
