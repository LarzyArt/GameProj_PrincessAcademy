package charmees.Display;

import charmees.util.Display;
import java.util.Scanner;

public class EasterEggArcadeLogic {
    
    // player state 
    private final Scanner sc;
    private String playerName;
    private int playerHP    = 255;
    private int playerMaxHP = 255;
    private int playerMP    = 555;

    // lezli state 
    private int lezliDisplayHP = 9999;  // fake HP shown to player
    private int lezliMaxHP     = 9999;

    private int turnCount = 1;

    // dialogue flags 
    private boolean below50Shown   = false;
    private boolean below25Shown   = false;
    private boolean lezliSnapShown = false;

    // constructor 
    public EasterEggArcadeLogic(Scanner sc) {
        this.sc = sc;
    }

    // =========================================================================
    // ENTRY POINT
    // =========================================================================
    
    public void run() {
        Display.gap();
        Display.line();
        Display.centered("??? M O D E");
        Display.line();
        Display.gap();

        System.out.print("  Enter your name, challenger: ");
        playerName = sc.nextLine().trim();
        if (playerName.isEmpty()) playerName = "Player";

        Display.gap();

        EasterEggDisplay.showDialogue(getPreBattleDialogue(), sc);

        while (playerHP > 0) {
            EasterEggDisplay.showBattlefield(
                playerName, playerHP, playerMaxHP, playerMP,
                lezliDisplayHP, lezliMaxHP, turnCount);

            showTurnDialogue();
            checkSpecialDialogues();

            EasterEggDisplay.showPlayerMenu(playerName);
            int action = Display.readInt(sc);
            handlePlayerAction(action);

            if (playerHP <= 0) break;

            lezliAttack();
            checkSpecialDialogues();
            turnCount++;
        }

        EasterEggDisplay.showLezliWins(playerName);
        EasterEggDisplay.showDialogue(getPostBattleDialogue(), sc);
    }


    // =========================================================================
    // PLAYER ACTIONS
    // =========================================================================
    public void handlePlayerAction(int action) {
        Display.gap();
        switch (action) {
            case 1:
                playerAttack(false);
                break;
            case 2:
                if (playerMP >= 10) {
                    playerMP -= 10;
                    playerAttack(true);
                } else {
                    EasterEggDisplay.log("Not enough MP! " + playerName + " swings weakly...");
                    playerAttack(false);
                }
                break;
            case 3:
                playerTalk();
                break;
            default:
                EasterEggDisplay.log(playerName + " hesitates... nothing happens.");
        }
        BattleDIsplay.pause(600);
    }

    private void playerAttack(boolean mash){
        int displayDamage = mash
            ? (int)(Math.random() * 2) + 256
            : (int)(Math.random() * 4) + 2;

            //fllors at 1 - Lezli never dies
        lezliDisplayHP = Math.max(1, lezliDisplayHP - displayDamage);
        EasterEggDisplay.log(playerName + " hits Lezli for " + displayDamage + " it was effective?");
        BattleDIsplay.pause(400);
        showLezliHitReaction();

    }

    private void playerTalk() {
        String[] Ttalks = {
            playerName + ": Tung tung Sa[copyright]",
            playerName + ": HAHAHAHAHAHA Y[censored], BOGO",
            playerName + ": You are beneath me...",
            playerName + ": Nonstop Laughing challenge!",
            playerName + ": Apologize To me, that you were born into my world",
            playerName + ": See you tomorrow... sike b[censored]!"
        };
        int rng = (int)(Math.random() * Ttalks.length);
        EasterEggDisplay.log(Ttalks[rng]);
        BattleDIsplay.pause(500);
    }

    // =========================================================================
    // LEZLI'S TURN
    // =========================================================================
    private void lezliAttack(){
        EasterEggDisplay.showLezliTurnHeader();
        BattleDIsplay.pause(700);

        // turn 6+ - finish move, player always loses

        if(turnCount > 6){
            EasterEggDisplay.log("Lezli laughs maniacally");
            BattleDIsplay.pause(800);
            
            EasterEggDisplay.log("Lezli: Good boy~");
            BattleDIsplay.pause(800);
            
            EasterEggDisplay.log("Lezli: Time's up~");
            BattleDIsplay.pause(1200);

            EasterEggDisplay.log("Lezli used ✦ MOTHERLY EMBRACE ✦");
            BattleDIsplay.pause(1200);
            
            EasterEggDisplay.log(playerName + " took ??? damage. Remaining HP: 1");
            BattleDIsplay.pause(800);
            playerHP = 1;

            EasterEggDisplay.log(playerName + " slips on void and took 1 damage. Remaining HP: 0??");
            BattleDIsplay.pause(1200);
            playerHP = 0;

            EasterEggDisplay.log("Lezli: Wha-");
            BattleDIsplay.pause(800);
            return;
        }

        // turn 1- 2: light, turn 3-5 heavy
        int damage;
        String moveName;

        if(turnCount <= 2){
            damage = (int)(Math.random() * 8) + 5; 
            moveName = "Throw Void Rock";
        }else {
            damage = (int)(Math.random() * 21) + 25; 
            moveName = "Head Flick";
        }

        playerHP = Math.max(0, playerHP - damage);
        EasterEggDisplay.log("Lezli used " + moveName + "!");
        BattleDIsplay.pause(400);
    }

    // =========================================================================
    // DIALOGUES
    // =========================================================================
    private void showTurnDialogue() {
        String[] lines = {
            "Oh you're actually trying~ How adorable!",
            "Is that all? I barely felt that~",
            "You know, for someone losing, you have great form~",
            "Hmm... should I end this? No, no, not yet~",
            "*yawns* Oh sorry, did I space out?",
            "I'm having so much fun~ Are you?",
            "You're pretty entertaining for someone who can't win~",
        };
        int idx = (turnCount - 1) % lines.length;
        EasterEggDisplay.showLezliLine(lines[idx]);
    }

    private void showLezliHitReaction() {
        String[] reactions = {
            "\"Oh? That tickled~\"",
            "\"Mmm. Is that a scratch?\"",
            "\"Ooh, feisty! I like it~\"",
            "\"You really are giving it your all, huh~\"",
            "\"That one was almost impressive~\""
        };
        int rng = (int)(Math.random() * reactions.length);
        EasterEggDisplay.log("Lezli: " + reactions[rng]);
        BattleDIsplay.pause(400);
    }

    private void checkSpecialDialogues() {
        double ratio = (double) playerHP / playerMaxHP;

        if (!below50Shown && ratio <= 0.5) {
            below50Shown = true;
            EasterEggDisplay.showDialogue(getBelow50Dialogue(), sc);
        }
        if (!below25Shown && ratio <= 0.25) {
            below25Shown = true;
            EasterEggDisplay.showDialogue(getBelow25Dialogue(), sc);
        }
        if (!lezliSnapShown && turnCount >= 4) {
            lezliSnapShown = true;
            EasterEggDisplay.showDialogue(getLezliSnapDialogue(), sc);
        }
    }

    // =========================================================================
    // DIALOGUE DATA
    // =========================================================================
    private String[][] getPreBattleDialogue() {
        return new String[][]{
            {"???",      "...Oh You're Finally here, darling~"},
            {playerName, "... Gross..."},
            {"???",      "-_-- ... Anyways, It's been awhile since someone caught my eye"},
            {playerName, "Who are you? What are you? An alien?"},
            {"Lezli",    "Lezli~ Nice to meet you, " + playerName + "."},
            {"Lezli",    "Now then... Let's play a game!"},
            {playerName, "Play...?"},
            {playerName, "What kind of sick kink is this?!"},
            {"Lezli",    "Don't worry. I'll go easy-"},
            {"Lezli",    "Nah your going down!"},
        };
    }

    private String[][] getBelow50Dialogue() {
        return new String[][]{
            {"Lezli",    "Oh. You're at half already?"},
            {"Lezli",    "You're holding up better than I expected~"},
            {playerName, "SHUT UP YOU OLD HAG!"},
            {"Lezli",    "... EXCUSE ME, I'm 18^IF YEARS OLD!"},
        };
    }

    private String[][] getBelow25Dialogue() {
        return new String[][]{
            {"Lezli",    "You're still standing? My, My what a strong Boy~"},
            {"Lezli",    "Still have more in you?"},
            {playerName, "STOP SAYING GROSS STUFF OLD LADY!"},
            {"Lezli",    "Hehe~"},
            {"Lezli",    "Now then" + playerName + "... POWER UP TIME!"},
            {playerName, "WHAT? THATS CHEATING!"},
        };
    }

    private String[][] getLezliSnapDialogue() {
        return new String[][]{
            {"Lezli",    "Okay~ I think we've played long enough."},
            {"Lezli",    "You gave it everything, didn't you?"},
            {playerName, "... I really wanna kill you now you know..."},
            {"Lezli",    "...I know. That's the saddest part~"},
            {"Lezli",    "Well then, " + playerName + ". See you tommorow *Cyrene OST plays* ~"},
        };
    }

    private String[][] getPostBattleDialogue() {
        return new String[][]{
            {"Lezli",    "HOW DID YOU SLIP IN THE VOID!"},
            {"Lezli",    "I CAN'T TAKE THIS, " + playerName + ". SERIOUSLY."},
            {"Lezli",    "UGHH, The nerve to not die by my hands."},
            {playerName, "Heh, I won't give you the satisfaction of winning a good battle!"},
            {"Lezli",    "If only I was able to get out this screen, I oughta-"},
            {"Lezli",    "Anyhow, good game GAMER"},
            {"Lezli",    "I'll be here~"},
            {"Lezli",    "See you next Time Darling, My gamemode is always open for you!"},
            {playerName, "... That old hag needs a life"},
            {"Lezli",    "I HEARD THAT!"}, 
            {"???",      "..."},
            
        };
    }

}