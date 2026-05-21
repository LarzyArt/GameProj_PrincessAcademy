package charmees.Core.Logic;

import charmees.Display.PCvPCBattleDisplay;
import charmees.util.Character;
import charmees.util.Display;
import charmees.util.MobNPC;

import java.util.Scanner;

public class PCvPCBattleLogic {

    private static final int RECOVER_MP = 8;

    private final Character player;
    private final Character opponent;
    private final Scanner sc;

    private final int playerBaseHP;
    private final int playerBaseMP;
    private final int oppBaseHP;
    private final int oppBaseMP;

    private int turnCount = 1;
    private boolean playerRetired = false;

    public PCvPCBattleLogic(Character player, Character opponent, Scanner sc) {
        this.player = player;
        this.opponent = opponent;
        this.sc = sc;

        this.playerBaseHP = player.healthPoints;
        this.playerBaseMP = player.manaPoints;
        this.oppBaseHP = opponent.healthPoints;
        this.oppBaseMP = opponent.manaPoints;
    }





    private void resetBattle() {
        player.healthPoints = playerBaseHP;
        player.manaPoints = playerBaseMP;
        opponent.healthPoints = oppBaseHP;
        opponent.manaPoints = oppBaseMP;
        turnCount = 1;
        playerRetired = false;
    }

    private boolean isBattleOngoing() {
        return player.isAlive() && opponent.isAlive() && !playerRetired;
    }

    private void playerSkill() {
        PCvPCBattleDisplay.showSkillMenu(player);
        int pick = Display.readInt(sc);

        int limit = Math.min(2, player.getSkillList().length);
        if (pick < 1 || pick > limit) {
            PCvPCBattleDisplay.log("Cancelled.");
            return;
        }

        executeSkill(player, opponent, pick, false);
    }

    private void playerUltimate() {
        if (player.getSkillList().length < 3) {
            PCvPCBattleDisplay.log(player.getName() + " has no ultimate move.");
            return;
        }

        PCvPCBattleDisplay.showUltimateConfirm(player);
        int confirm = Display.readInt(sc);

        if (confirm != 1) {
            PCvPCBattleDisplay.log("Cancelled.");
            return;
        }

        executeSkill(player, opponent, 3, false);
    }

    private void playerRecover() {
        player.restoreMP(RECOVER_MP);
        PCvPCBattleDisplay.pause(500);
    }

    private void playerRetire() {
        PCvPCBattleDisplay.showRetireConfirm();
        int confirm = Display.readInt(sc);

        if (confirm == 1) {
            playerRetired = true;
            player.healthPoints = 0;
            PCvPCBattleDisplay.logDanger(player.getName() + " retired from the trial battle.");
        } else {
            PCvPCBattleDisplay.log("The battle continues!");
        }

        PCvPCBattleDisplay.pause(500);
    }

    private void cpuTurn() {
        PCvPCBattleDisplay.showCPUPhaseHeader(opponent);

        String[] skills = opponent.getSkillList();
        int chosenSkill = chooseCpuSkill(skills);

        if (chosenSkill == 0) {
            opponent.restoreMP(5);
            PCvPCBattleDisplay.pause(600);
            return;
        }

        executeSkill(opponent, player, chosenSkill, true);
        PCvPCBattleDisplay.pause(700);
    }

    private int chooseCpuSkill(String[] skills) {
        boolean canUlt = skills.length >= 3
                && Math.random() < 0.20
                && opponent.manaPoints >= extractMPCost(skills[2]);

        if (canUlt) return 3;

        boolean canSkill2 = skills.length >= 2
                && opponent.manaPoints >= extractMPCost(skills[1]);
        boolean canSkill1 = skills.length >= 1
                && opponent.manaPoints >= extractMPCost(skills[0]);

        if (!canSkill1 && !canSkill2) return 0;
        if (canSkill2 && Math.random() < 0.55) return 2;
        if (canSkill1) return 1;

        return 2;
    }

    private void executeSkill(Character user, Character target, int skillNum, boolean cpu) {
        String[] skills = user.getSkillList();

        if (skillNum < 1 || skillNum > skills.length) {
            PCvPCBattleDisplay.log(user.getName() + " does not have that skill.");
            return;
        }

        String skillName = skills[skillNum - 1];
        String targetType = user.getSkillTargetType(skillNum);

        if (targetType.equals("ENEMY")) {
            int hpBefore = target.healthPoints;
            user.useSkill(skillNum, wrapTarget(target), null, new Character[]{user});
            int damage = hpBefore - target.healthPoints;

            if (damage > 0) {
                String cpuLabel = cpu ? " (CPU)" : "";
                PCvPCBattleDisplay.log(user.getName() + cpuLabel + " uses " + skillName
                        + " -> " + damage + " damage to " + target.getName() + "!");
            }
        } else {
            PCvPCBattleDisplay.log(user.getName() + " uses " + skillName + "!");
            user.useSkill(skillNum, null, user, new Character[]{user});
        }

        PCvPCBattleDisplay.pause(600);
    }

    private void resolveBattle() {
        if (player.isAlive() && !opponent.isAlive()) {
            PCvPCBattleDisplay.showBattleVictory(player);
        } else {
            PCvPCBattleDisplay.showBattleDefeat(opponent);
        }

        PCvPCBattleDisplay.logInfo("No HP/MP bonus added for now.");
        Display.pressEnter(sc);
    }


}


