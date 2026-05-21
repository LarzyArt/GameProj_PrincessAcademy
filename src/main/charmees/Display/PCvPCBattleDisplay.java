package charmees.Display;

import charmees.Core.Logic.PCvPCBattleLogic

public class PCvPCBattleDisplay {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";

    public static void title() {
        System.out.println(CYAN + "\n=== TRIAL BATTLE: P/C vs P/C ===" + RESET);
    }

    public static void showStatus(PCvPCBattleLogic.Fighter a, PCvPCBattleLogic.Fighter b) {
        System.out.println();
        System.out.println(a.name + " [" + a.group + "] HP: " + a.hp + "/" + a.maxHp + " MP: " + a.mp + "/" + a.maxMp);
        System.out.println(b.name + " [" + b.group + "] HP: " + b.hp + "/" + b.maxHp + " MP: " + b.mp + "/" + b.maxMp);
    }


}
