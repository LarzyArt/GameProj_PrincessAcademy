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
}


