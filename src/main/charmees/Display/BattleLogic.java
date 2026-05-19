package charmees.Display;

import charmees.Util.*;
import charmees.util.MobNPC;

import java.util.ArrayList;
import java.util.Scanner;

public class BattleLogic {
    // This class will handle the battle logic for both Story Mode
    // It will be called by the BattleDisplay class

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

        // constructor for lazuli
    }

    // =================================================
    // ENTRY POINT for battle logic
    // =================================================

    public void run() {
        // lore (tentative)

        // Main battle loop

    }

    // =================================================
    // PLAYER ACTIONS
    // =================================================

    // SWITCH — swap active fighter (Lazuli never an option)
    private void onSwitch() {
        BattleDisplay.showSwitchMenu(characters, activeIdx, charMaxHP);
        int pick = Display.readInt(sc);

        if (pick == 0) { BattleDisplay.log("Cancelled."); return; }

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
                BattleDisplay.log(oldName + " switched out!  "
                        + characters[activeIdx].getName() + " enters the battle!");
                endPlayerTurn();
                return;
            }
        }

        BattleDisplay.log("Invalid choice.");
    }
    // LAZULI HEAL — if Lazuli is in the party, player can choose to heal with her instead of attacking
    private void onLazuliHeal() {
        Character lazuli = characters[2];
        if (!lazuli.isAlive()) {
            BattleDisplay.log("Lazuli has fallen and cannot heal.");
            return;
        }

        BattleDisplay.showHealMenu(lazuli);
        int pick = Display.readInt(sc);

        String[] skills = lazuli.getSkillList();
        if (pick < 1 || pick > skills.length) {
            BattleDisplay.log("Cancelled.");
            return;
        }

        String tType = lazuli.getSkillTargetType(pick);

        if (tType.equals("ALLY")) {
            Character ally = pickAlly();
            if (ally == null) return;
            BattleDisplay.log("Lazuli heals " + ally.getName() + "!");
            lazuli.useSkill(pick, null, ally, characters);
        } else { // ALL
            BattleDisplay.log("Lazuli uses " + skills[pick - 1] + "!");
            lazuli.useSkill(pick, null, lazuli, characters);
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

        battleDisplay.showEnemyPhaseHeader();

        for (MobNPC mob : mobs)
            if (mob.chapter != chapter || !mob.isAlive())
                continue;

        Character target = characters[activeIdx];
        int skill = (int) (Math.random() * mob.getSkillCount()) + 1;
        mob.useSkill(skill, target);

        if (!Characters[activeTdx].isAlive()) {
            for (int i = 0; i < characters.length; i++) {
                if (i != 2 && characters[i].isAlive()) {
                    BattleDisplay.log(characters[activeIdx].getName() + " fell!  "
                            + characters[i].getName() + " steps in!");
                    activeIdx = i;
                    break;
                }
            }
        }

        if (!isBattleOngoing())
            break;
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
            BattleDisplay.showVictory(chapter);
            BattleDisplay.showDialogue(BattleDialogue.getPostBattleLines(chapter), sc);
        } else if (!anyFighter) {
            BattleDisplay.showDefeat();
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
        BattleDisplay.showEnemyPicker(mobs, chapter, mobMaxHP);
        int pick = Display.readInt(sc);

        // if player picks invalid number, return null to indicate cancelled action
        if (pick < 1 || pick > aliveCount) {
            BattleDisplay.log("Cancelled.");
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

        BattleDisplay.showAllyPicker(characters, charMaxHP);
        int pick = Display.readInt(sc);

        if (pick < 1 || pick > aliveCount) {
            BattleDisplay.log("Cancelled.");
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