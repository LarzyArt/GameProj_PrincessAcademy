package charmees.gui;

import charmees.util.Character;
import charmees.util.MobNPC;
import charmees.util.Display;
import charmees.battle.BattleDialogue;
import java.util.Scanner;

public class BattleUI {

    // ── game data ──────────────────────────────────────────────────────────
    private final Character[] characters; // index 2 is always Lazuli
    private final MobNPC[]    mobs;
    private final int         chapter;
    private final Scanner     sc;

    private int activeIdx = 0; // current fighter (never index 2)
    private int targetIdx = 0; // which enemy is selected
    private int turnCount = 1;

    // stored at start since entities don't keep their own max HP
    private final int[] charMaxHP;
    private final int[] mobMaxHP;

    // boss dialogue fires only once each per battle
    private boolean boss50Triggered = false;
    private boolean boss10Triggered = false;

    // ── constructor ────────────────────────────────────────────────────────
    public BattleUI(Character[] characters, MobNPC[] mobs,
                    int chapter, Scanner sc) {
        this.characters = characters;
        this.mobs       = mobs;
        this.chapter    = chapter;
        this.sc         = sc;

        charMaxHP = new int[characters.length];
        for (int i = 0; i < characters.length; i++)
            charMaxHP[i] = characters[i].healthPoints;

        mobMaxHP = new int[mobs.length];
        for (int i = 0; i < mobs.length; i++)
            mobMaxHP[i] = mobs[i].healthPoints;

        if (activeIdx == 2) activeIdx = 0;
        targetIdx = firstAliveEnemyIdx();
    }

    // =========================================================================
    // ENTRY POINT
    // =========================================================================
    public void run() {
        // lore first, then pre battle dialogue
        BattleDisplay.showDialogue(BattleDialogue.getLoreLines(chapter), sc);
        BattleDisplay.showDialogue(BattleDialogue.getPreBattleLines(chapter), sc);

        // main battle loop
        while (isBattleOngoing()) {
            BattleDisplay.showBattlefield(
                characters, mobs, chapter,
                activeIdx, targetIdx,
                charMaxHP, mobMaxHP, turnCount);

            BattleDisplay.showPlayerPhaseHeader(characters[activeIdx]);
            BattleDisplay.showActionMenu(characters[activeIdx]);

            int action = Display.readInt(sc);

            switch (action) {
                case 1: onSkill();      break;
                case 2: onChar();       break;
                case 3: onSwitch();     break;
                case 4: onLazuliHeal(); break;
                default:
                    BattleDisplay.log("Invalid choice. Enter 1-4.");
            }
        }

        checkBattleEnd();
    }

    // =========================================================================
    // PLAYER ACTIONS
    // =========================================================================

    // SKILL — uses skill 1 or 2
    private void onSkill() {
        Character actor = characters[activeIdx];
        if (stunCheck(actor)) return;

        BattleDisplay.showSkillMenu(actor);
        int pick = Display.readInt(sc);

        if (pick < 1 || pick > Math.min(2, actor.getSkillList().length)) {
            BattleDisplay.log("Cancelled.");
            return;
        }

        executeCharacterSkill(actor, pick);
    }

    // CHAR — uses skill 3 (signature move)
    private void onChar() {
        Character actor = characters[activeIdx];
        if (stunCheck(actor)) return;

        String[] skills = actor.getSkillList();
        if (skills.length < 3) {
            BattleDisplay.log(actor.getName() + " has no signature move.");
            return;
        }

        BattleDisplay.showCharConfirm(actor);
        int confirm = Display.readInt(sc);

        if (confirm != 1) { BattleDisplay.log("Cancelled."); return; }

        executeCharacterSkill(actor, 3);
    }

    // shared skill execution used by both onSkill and onChar
    private void executeCharacterSkill(Character actor, int skillNum) {
        String tType = actor.getSkillTargetType(skillNum);

        if (tType.equals("ENEMY")) {
            MobNPC target = pickEnemy();
            if (target == null) return;
            BattleDisplay.log(actor.getName() + " uses "
                + actor.getSkillList()[skillNum - 1] + " on " + target.getName() + "!");
            actor.useSkill(skillNum, target, actor, characters);

        } else if (tType.equals("ALLY")) {
            Character ally = pickAlly();
            if (ally == null) return;
            BattleDisplay.log(actor.getName() + " uses "
                + actor.getSkillList()[skillNum - 1] + " on " + ally.getName() + "!");
            actor.useSkill(skillNum, null, ally, characters);

        } else { // SELF or ALL
            BattleDisplay.log(actor.getName() + " uses "
                + actor.getSkillList()[skillNum - 1] + "!");
            actor.useSkill(skillNum, null, actor, characters);
        }

        endPlayerTurn();
    }

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

    // LAZULI HEAL — always available, always costs a turn
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

    // =========================================================================
    // TURN FLOW
    // =========================================================================
    private void endPlayerTurn() {
        // check if boss crossed 50% or 10% HP
        checkBossDialogueTriggers();

        if (!isBattleOngoing()) return;

        BattleDisplay.showEnemyPhaseHeader();

        for (MobNPC mob : mobs) {
            if (mob.chapter != chapter || !mob.isAlive()) continue;

            if (mob.isStunned()) {
                BattleDisplay.log(mob.getName() + " is stunned and skips their turn.");
                continue;
            }

            Character target = characters[activeIdx];
            int skill = (int)(Math.random() * mob.getSkillCount()) + 1;
            mob.useSkill(skill, target);

            // if active fighter just died, auto switch to first alive non Lazuli
            if (!characters[activeIdx].isAlive()) {
                for (int i = 0; i < characters.length; i++) {
                    if (i != 2 && characters[i].isAlive()) {
                        BattleDisplay.log(characters[activeIdx].getName()
                            + " fell!  " + characters[i].getName() + " steps in!");
                        activeIdx = i;
                        break;
                    }
                }
            }

            if (!isBattleOngoing()) break;
        }

        // tick all status effects at end of round
        for (Character c : characters) c.tickStatus();
        for (MobNPC m : mobs)         m.tickStatus();

        // fix targetIdx if current target just died
        if (targetIdx < 0 || targetIdx >= mobs.length
                || !mobs[targetIdx].isAlive())
            targetIdx = firstAliveEnemyIdx();

        turnCount++;
    }

    // =========================================================================
    // BATTLE STATE
    // =========================================================================
    private boolean isBattleOngoing() {
        boolean anyFighter = false;
        for (int i = 0; i < characters.length; i++)
            if (i != 2 && characters[i].isAlive()) anyFighter = true;

        boolean anyEnemy = false;
        for (MobNPC m : mobs)
            if (m.chapter == chapter && m.isAlive()) anyEnemy = true;

        return anyFighter && anyEnemy;
    }

    private void checkBattleEnd() {
        boolean anyFighter = false;
        for (int i = 0; i < characters.length; i++)
            if (i != 2 && characters[i].isAlive()) anyFighter = true;

        boolean anyEnemy = false;
        for (MobNPC m : mobs)
            if (m.chapter == chapter && m.isAlive()) anyEnemy = true;

        if (!anyEnemy) {
            BattleDisplay.showVictory(chapter);
            BattleDisplay.showDialogue(BattleDialogue.getPostBattleLines(chapter), sc);
        } else if (!anyFighter) {
            BattleDisplay.showDefeat();
        }
    }

    // fires boss dialogue at 50% and 10% HP, once each
    private void checkBossDialogueTriggers() {
        for (int i = 0; i < mobs.length; i++) {
            MobNPC m = mobs[i];
            if (m.chapter != chapter) continue;
            if (!m.charClass.equals("Boss") && !m.charClass.equals("Miniboss")) continue;
            if (!m.isAlive()) continue;

            double ratio = (double) m.healthPoints / mobMaxHP[i];

            if (!boss50Triggered && ratio <= 0.50) {
                boss50Triggered = true;
                BattleDisplay.showDialogue(BattleDialogue.getBoss50Lines(chapter), sc);
            }
            if (!boss10Triggered && ratio <= 0.10) {
                boss10Triggered = true;
                BattleDisplay.showDialogue(BattleDialogue.getBoss10Lines(chapter), sc);
            }
        }
    }

    // =========================================================================
    // TARGET PICKERS
    // =========================================================================
    private MobNPC pickEnemy() {
        int aliveCount = 0;
        for (MobNPC m : mobs)
            if (m.chapter == chapter && m.isAlive()) aliveCount++;

        if (aliveCount == 0) return null;

        // only one alive — pick automatically
        if (aliveCount == 1) {
            for (int i = 0; i < mobs.length; i++) {
                if (mobs[i].chapter == chapter && mobs[i].isAlive()) {
                    targetIdx = i;
                    return mobs[i];
                }
            }
        }

        BattleDisplay.showEnemyPicker(mobs, chapter, mobMaxHP);
        int pick = Display.readInt(sc);

        if (pick < 1 || pick > aliveCount) {
            BattleDisplay.log("Cancelled.");
            return null;
        }

        // walk through and return the pick-th alive enemy
        int count = 0;
        for (int i = 0; i < mobs.length; i++) {
            if (mobs[i].chapter != chapter || !mobs[i].isAlive()) continue;
            count++;
            if (count == pick) {
                targetIdx = i;
                return mobs[i];
            }
        }

        return null;
    }

    private Character pickAlly() {
        int aliveCount = 0;
        for (Character c : characters)
            if (c.isAlive()) aliveCount++;

        if (aliveCount == 0) return null;

        // only one alive — return automatically
        if (aliveCount == 1) {
            for (Character c : characters)
                if (c.isAlive()) return c;
        }

        BattleDisplay.showAllyPicker(characters, charMaxHP);
        int pick = Display.readInt(sc);

        if (pick < 1 || pick > aliveCount) {
            BattleDisplay.log("Cancelled.");
            return null;
        }

        // walk through and return the pick-th alive ally
        int count = 0;
        for (Character c : characters) {
            if (!c.isAlive()) continue;
            count++;
            if (count == pick) return c;
        }

        return null;
    }

    // =========================================================================
    // HELPERS
    // =========================================================================
    private boolean stunCheck(Character c) {
        if (c.isStunned()) {
            BattleDisplay.log(c.getName()
                + " is stunned! Use SWITCH to send someone else in.");
            return true;
        }
        return false;
    }

    private int firstAliveEnemyIdx() {
        for (int i = 0; i < mobs.length; i++)
            if (mobs[i].chapter == chapter && mobs[i].isAlive()) return i;
        return -1;
    }
}