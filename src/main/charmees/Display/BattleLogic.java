package charmees.Display;

import charmees.util.*;
import charmees.util.MobNPC;
import charmees.util.Character;
import java.util.Scanner;

public class BattleLogic {
    // This class will handle the battle logic for both Story Mode
    // It will be called by the BattleDIsplay class

    private final Character[] characters; // index 2 is always Lazuli
    private final MobNPC[] mobs;
    private final int chapter;
    private final Scanner sc;

    private int activeIdx = 0; // current character (never index 2 / Lazuli)
    private int targetIdx = 0; // current enemy target
    private int turnCount = 1; // counts the number of turns in the battle

    private final int[] charMaxHP;
    private final int[] mobMaxHP;

    // dialogue triggers for certain HP Boss thresholds
    private boolean boss50Trigger = false;
    private boolean boss10Trigger = false;

    // constructors
    public BattleLogic(Character[] characters, MobNPC[] mobs, int chapter, Scanner sc) {
        this.characters = characters;
        this.mobs = mobs;
        this.chapter = chapter;
        this.sc = sc;

        // initialize max HP arrays
        charMaxHP = new int[characters.length];
        for (int i = 0; i < characters.length; i++) {
            charMaxHP[i] = characters[i].healthPoints;
        }

        mobMaxHP = new int[mobs.length];
        for (int i = 0; i < mobs.length; i++) {
            mobMaxHP[i] = mobs[i].healthPoints;
        }

        if(activeIdx == 2) activeIdx = 0; // ensure Lazuli is not active character at start of battle
        targetIdx = firstAliveEnemy(); // set initial target to first alive enemy for the chapter
    }

    // =================================================
    // ENTRY POINT for battle logic
    // =================================================

    public void run() {
        // lore (tentative)

        //  main battle loop
        while(isBattleOngoing()){
            // show battlefield and action menu
            BattleDIsplay.showBattleField(characters, mobs, chapter, 
                activeIdx, targetIdx, charMaxHP, 
                mobMaxHP, turnCount);
            // show different action menu if active character is Lazuli
            BattleDIsplay.showPlayerPhaseHeader(characters[activeIdx]);
            BattleDIsplay.showActionDisplay(characters[activeIdx]);
            
            // read player input for action choice
            int action = Display.readInt(sc);
            
            // execute the chosen action
            switch (action) {
                case 1: onSkill(); break;
                case 2: onUlt(); break;
                case 3: onSwitch(); break;
                case 4: onLazuliHeal(); break;
                default:
                    BattleDIsplay.log("Invalid choice. Enter 1-4");
            }
        }

        // battle has ended, show 
        // victory/defeat screen and post battle dialogue if applicable
        checkBattleEnd();
    }

    // =================================================
    // PLAYER ACTIONS
    // =================================================

    // attack logic
    private void onSkill() {
        Character actor = characters[activeIdx];

        // if the active character has no skills, show message and return
        BattleDIsplay.showSkillMenu(actor);
        int pick = Display.readInt(sc);

        // if player picks invalid number, return to main menu
        if(pick < 1 || pick > Math.min(2, actor.getSkillList().length)){
            BattleDIsplay.log("Cancelled.");
            return;
        }

        // execute the picked skill
        executeCharacterSkill(actor, pick);
    }

    // ultimate move logic
    private void onUlt(){
        Character actor = characters[activeIdx];

        // if the active character has no sig, show message and return
        String[] skills = actor.getSkillList();
        if(skills.length < 3){
            BattleDIsplay.log(actor.getName() + "has no Ultimate Move available.");
            return;
        }

        BattleDIsplay.showUltimateConfirm(actor);
        int confirm = Display.readInt(sc);

        if(confirm != 1){ BattleDIsplay.log("Cancelled."); return; }

        // execute ultimate move (assumed to be skill 3)
        executeCharacterSkill(actor, 3);
    }
    

    // helper method to execute a character skill after picking the skill and target
    private void executeCharacterSkill(Character actor, int skillNum){
        String targetType = actor.getSkillTargetType(skillNum);

        // if the skill targets an enemy, open enemy picker and pass the picked target to the useSkill method
        if(targetType.equals("ENEMY")){
            MobNPC target = pickEnemy();
            if(target == null) return; //return if player cancelled target pick
            BattleDIsplay.log(actor.getName() + " uses "
                +actor .getSkillList()[skillNum - 1] + " on " + target.getName() + "!");
            actor.useSkill(skillNum, target, null, characters);
            BattleDIsplay.pause(700);
        } 
        // if the skill targets an ally, open ally picker instead and pass the picked ally to the useSkill method
        else if(targetType.equals("ALLY")){
            Character ally = pickAlly();
            if(ally == null) return; //return if player cancelled target pick
            BattleDIsplay.log(actor.getName() + " uses "
                +actor .getSkillList()[skillNum - 1] + " on " + ally.getName() + "!");
            actor.useSkill(skillNum, null, ally, characters);
            BattleDIsplay.pause(700);
        }
        // if the skill targets self, just pass null for the target and let the useSkill method handle it
        else {
            BattleDIsplay.log(actor.getName() + " uses "
                +actor .getSkillList()[skillNum - 1] + "!");
            actor.useSkill(skillNum, null, null, characters);
            BattleDIsplay.pause(700);
        }

        endPlayerTurn();
    }



    // SWITCH — swap active fighter (Lazuli never an option)
    private void onSwitch() {
        BattleDIsplay.showSwitchMenu(characters, activeIdx, charMaxHP);
        int pick = Display.readInt(sc);

        if (pick == 0) { BattleDIsplay.log("Cancelled."); return; }

        // walk through valid options and find the one the player picked
        int count = 0;
        for (int i = 0; i < characters.length; i++) {
            if (i == 2) continue;
            if (i == activeIdx) continue;
            if (!characters[i].isAlive()) continue;

            count++;
            if (count == pick) {
                String oldName = characters[activeIdx].getName();
                activeIdx = i;
                BattleDIsplay.log(oldName + " switched out!  "
                        + characters[activeIdx].getName() + " enters the battle!");
                BattleDIsplay.pause(500);
                endPlayerTurn();
                return;
            }
        }

        BattleDIsplay.log("Invalid choice.");
    }
    // LAZULI HEAL — if Lazuli is in the party, player can choose to heal with her instead of attacking
    private void onLazuliHeal() {
        Character lazuli = characters[2];
        if (!lazuli.isAlive()) {
            BattleDIsplay.log("Lazuli has fallen and cannot heal.");
            return;
        }

        BattleDIsplay.showHealMenu(lazuli);
        int pick = Display.readInt(sc);

        String[] skills = lazuli.getSkillList();
        if (pick < 1 || pick > skills.length) {
            BattleDIsplay.log("Cancelled.");
            return;
        }

        String tType = lazuli.getSkillTargetType(pick);

        if (tType.equals("ALLY")) {
            Character ally = pickAlly();
            if (ally == null) return;
            BattleDIsplay.log("Lazuli heals " + ally.getName() + "!");
            lazuli.useSkill(pick, null, ally, characters);
            BattleDIsplay.pause(600);
        } else { // ALL
            BattleDIsplay.log("Lazuli uses " + skills[pick - 1] + "!");
            lazuli.useSkill(pick, null, lazuli, characters);
            BattleDIsplay.pause(600);
        }

        // cap all HP at max — no overhealing
        for (int i = 0; i < characters.length; i++) {
            if (characters[i].healthPoints > charMaxHP[i])
                characters[i].healthPoints = charMaxHP[i];
        }

        endPlayerTurn();
    }

    // =================================================
    // TURN FLOW
    // =================================================
    private void endPlayerTurn() {
        // dialogue trigger tenattive

        if (!isBattleOngoing())
            return;

        BattleDIsplay.showEnemyPhaseHeader();

        for (MobNPC mob : mobs){
            if (mob.chapter != chapter || !mob.isAlive())
                continue;

            Character target = characters[activeIdx];
            int skill = (int) (Math.random() * mob.getSkillCount()) + 1;
            mob.useSkill(skill, target);
            BattleDIsplay.pause(600);
            

            if (!characters[activeIdx].isAlive()) {
                for (int i = 0; i < characters.length; i++) {
                    if (i != 2 && characters[i].isAlive()) {
                        BattleDIsplay.log(characters[activeIdx].getName() + " fell!  "
                                + characters[i].getName() + " steps in!");
                        activeIdx = i;
                        break;
                    }
                }
            }

        if (!isBattleOngoing())
            break;
        }

        // if the current target is dead after the enemy's turn, switch to the first alive enemy (if any alive)
        if(targetIdx < 0 || targetIdx >= mobs.length
                || !mobs[targetIdx].isAlive())
            targetIdx = firstAliveEnemy();

        turnCount++;
    }

    // =================================================
    // BATTLE STATE
    // =================================================
    private boolean isBattleOngoing() {
        boolean anyFighter = false;
        for (int i = 0; i < characters.length; i++)
            if (i != 2 && characters[i].isAlive())
                anyFighter = true;

        boolean anyEnemy = false;
        for (MobNPC m : mobs)
            if (m.chapter == chapter && m.isAlive())
                anyEnemy = true;

        return anyFighter && anyEnemy;
    }

    private void checkBattleEnd() {
        boolean anyFighter = false;
        for (int i = 0; i < characters.length; i++)
            if (i != 2 && characters[i].isAlive())
                anyFighter = true;

        boolean anyEnemy = false;
        for (MobNPC m : mobs)
            if (m.chapter == chapter && m.isAlive())
                anyEnemy = true;

        if (!anyEnemy) {
            BattleDIsplay.showVictoryDisplay(chapter);
            // BattleDIsplay.showDialogue(BattleDialogue.getPostBattleLines(chapter), sc);
        } else if (!anyFighter) {
            BattleDIsplay.showDefeatDisplay();
        }
    }

    // =================================================
    // TARGET PICKERS
    // =================================================

    private MobNPC pickEnemy() {
        int aliveCount = 0;
        // count alive enemies for the current chapter
        for (MobNPC m : mobs)
            if (m.chapter == this.chapter && m.isAlive())
                aliveCount++;

        if (aliveCount == 0)
            return null;

        // if only 1 enemy alive, skip picker and return that enemy
        if (aliveCount == 1) {
            for (int i = 0; i < mobs.length; i++) {
                if (mobs[i].chapter == chapter && mobs[i].isAlive()) {
                    targetIdx = i;
                    return mobs[i];
                }
            }
        }

        // show enemy picker if more than 1 enemy alive
        BattleDIsplay.showEnemyPicker(mobs, chapter, mobMaxHP);
        int pick = Display.readInt(sc);

        // if player picks invalid number, return null to indicate cancelled action
        if (pick < 1 || pick > aliveCount) {
            BattleDIsplay.log("Cancelled.");
            return null;
        }

        int count = 0;
        // walk through enemies and return the one that corresponds
        // to the player's pick (skip dead enemies and enemies from other chapters)
        for (int i = 0; i < mobs.length; i++) {
            if (mobs[i].chapter != chapter || !mobs[i].isAlive())
                continue;
            count++;
            if (count == pick) {
                targetIdx = i;
                return mobs[i];
            }
        }

        return null; // should never reach here
    }

    private Character pickAlly() {
        // similar logic to pickEnemy but for allies
        // exclude the active character and any dead characters
        int aliveCount = 0;
        for (Character c : characters)
            if (c.isAlive())
                aliveCount++;

        if (aliveCount == 0)
            return null;

        // if only 1 ally alive, skip picker and return that ally
        if (aliveCount == 1) {
            for (Character c : characters)
                if (c.isAlive())
                    return c;
        }

        BattleDIsplay.showAllyPicker(characters, charMaxHP);
        int pick = Display.readInt(sc);

        if (pick < 1 || pick > aliveCount) {
            BattleDIsplay.log("Cancelled.");
            return null;
        }

        int count = 0;
        for (Character c : characters) {
            if (!c.isAlive())
                continue;
            count++;
            if (count == pick)
                return c;
        }

        return null; // should never reach here
    }

    // =================================================
    // HELPERS
    // =================================================

    // returns the index of the first alive enemy for the current chapter,
    // or -1 if none are alive
    private int firstAliveEnemy() {
        for (int i = 0; i < mobs.length; i++) {
            if (mobs[i].chapter == this.chapter && mobs[i].isAlive())
                return i;
        }

        return -1; // no alive enemies
    }
}