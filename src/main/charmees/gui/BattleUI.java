package charmees.gui;

import charmees.util.*;
import charmees.util.Character;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class BattleUI extends JFrame{
    private final Character[] characters; //full party(index 0-4)
    private final MobNPC[] mobs;
    private final int chapter;

    //Actuve fighter - character in battle
    private int activeIdx = 0;  // starts at active character index 0(Audry)
    private int targetIdx = 0; // which enemy is currenyly being targeted
    private int turnCount = 1;
    private boolean playerTurn = true; // true if it's the player's turn, false for mobs    

    private int[] charMaxHP;
    private int[] mobMaxHP;

    //UI Components
    private ScenePanel scenePanel; //Sprite Area
    private Jpanel allEnemyBars; //show all enemy HP bars
    private Jpanel activeCharCard; //top-left stat card for active character
    private Jpanel targetCard; // top-right stat card for targeted enemy
    private JTextArea logArea; // battle log
    private JPanel benchPanel; // bottom row of character portraits for switching
    private JButton skillBtn; // skill button - opens skill menu for active character
    private JButton sigBtn; // signature move button - only open when condition is met
    private JButton switchBtn; // switch active character
    private JButton HealBtn; //Heal characters - always open
    private JLabel turnLabel;

    //Constructors
    public BattleUI(Character[] characters, MobNPC[] mobs, int chapter) {
        this.characters = characters;
        this.mobs = mobs;
        this.chapter = chapter;

        // initialize max HP arrays for characters and mobs(before any battle damage)
        charMaxHP = new int[characters.length];
        for (int i = 0; i < characters.length; i++)
            charMaxHP[i] = characters[i].healthPoints;

        //counts only mobs for the current chapter being in the battle, so we can have a smaller array for mob HP bars
        ArrayList<MobNPC> chMobs = new ArrayList<>();
        for (MobNPC m : mobs)
            if (m.chapter == chapter)
                chMobs.add(m);
        mobMaxHP = new int[mobs.length];
        for (int i = 0; i < chMobs.size(); i++)
            mobMaxHP[i] = chMobs.get(i).healthPoints;

        //activeIdx 2 is not used for character 
        if (activeIdx == 2) activeIdx = 0;

        // find the first alive enemy in the current chapter to target at the start of battle
        //targetIdx = firstAliveEnemyIdx();

        //loadSprites();

        //build and show window
        //buildUI();
        setVisible(true);        

    }

}