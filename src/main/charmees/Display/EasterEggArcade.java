package charmees.Display;

import charmees.util.Display;
import java.util.Scanner;

public class EasterEggArcade {
    
    // player state 
    private final Scanner sc;
    private String playerName;
    private int playerHP    = 255;
    private int playerMaxHP = 255;
    private int playerMP    = 555;

    // lezli state 
    private int lezliRealHP    = 9999; // real HP — she cannot die
    private int lezliDisplayHP = 9999;  // fake HP shown to player
    private int lezliMaxHP     = 1;

    private int turnCount = 1;

    // dialogue flags 
    private boolean below50Shown   = false;
    private boolean below25Shown   = false;
    private boolean lezliSnapShown = false;

    // constructor 
    public EasterEggArcade(Scanner sc) {
        this.sc = sc;
    }

    // =========================================================================
    // ENTRY POINT
    // =========================================================================
    
    // run logic

    // =========================================================================
    // PLAYER ACTIONS
    // =========================================================================
    

}