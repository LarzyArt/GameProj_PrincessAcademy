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


}


