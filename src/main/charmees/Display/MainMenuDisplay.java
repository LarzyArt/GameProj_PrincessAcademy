package charmees.Display;

import java.util.Scanner;

import charmees.Util.Characters;
import charmees.Util.Display;
import charmees.Util.MobNPC;

public class MainMenuDisplay {

    private Scanner sc;
    private Characters[] characters;
    private MobNPC[] mobs;

    // Track visits and last choice
    private static int visitCount = 0;
    private static int lastChoice = 0;

    public MainMenuDisplay(Scanner sc, Characters[] characters, MobNPC[] mobs) {
        this.sc = sc;
        this.characters = characters;
        this.mobs = mobs;
        visitCount++;
    }
}
