package charmees.Core;

import java.util.Scanner;
import charmees.util.Character;
import charmees.util.MobNPC;
import charmees.Entity.characters.*;
import charmees.Entity.boss.*;
import charmees.Entity.mobs.*;
import charmees.Display.*;


public class Main {
    public static void main(String[] args) {
        Scanner skillinput = new Scanner(System.in);
        Character[] characters = {
            new Audry("Audry", "Assassin", "Melee", "Acidic Slime", 100, 25),
            new Giantha("Giantha", "Tank", "Melee", "World Tree Branch", 250, 20),
            new Lazuli("Lazuli", "Healer", "Ranged", "Staff", 150, 30),
            new Lynzi("Lynzi", "Dealer", "Melee", "Star Magic", 170, 30),
            new Shiera("Shiera", "Support", "Ranged", "Earth Magic", 120, 25)
        };

        Character[] arcadeFighters = {
                new Abby   ("Abby",    "Drama Queen",  "SL Energy",   "Script",      160, 30),
                new Cenicen("Cenicen", "Stan",          "Idol Energy", "Lightstick",  150, 30),
                new Kimni  ("Kimni",   "Caster",        "Magic",       "Wand",        140, 40),
                new Larzy  ("Larzy",   "Knight",        "Physical",    "Spear",       180, 20),
                new Mewods ("Mewods",  "Rogue",         "Shadow",      "Dagger",      145, 35),
                new Audry  ("Audry",   "Slime Mage",    "Magic",       "Staff",       150, 30),
                new Giantha("Giantha", "Giant",         "Physical",    "Club",        200, 15),
                new Lynzi  ("Lynzi",   "Duelist",       "Wind",        "Rapier",      155, 30),
                new Shiera ("Shiera",  "Paladin",       "Holy",        "Shield",      175, 25)
        };
        MobNPC[] mobs = {
            //Boss
            new Twinkle("Twinkle", "Boss", "Melee", "Puppet", 500,3),
            //Minibosses
            new Kassundre("Kassundre", "Miniboss", "Ranged", "Dark Magic", 300,3),
            new SirenEmpress("Siren Empress", "Miniboss", "Ranged", "Water Magic", 320,2),
            new Resonara("Resonara", "Miniboss", "Ranged", "Sound Magic", 310,1),
            //Mobs
            new CorruptedSkeleton("Corrupted Skeleton", "Minion", "Melee", "Bone Sword", 120,3),
            new WaterSprite("Water Sprite", "Minion", "Ranged", "Water Magic", 130,1),
            new EchoImp("Echo Imp", "Minion", "Ranged", "Sound Magic", 130, 2),
            new PrincessPuppet("Princess Puppet", "Minion", "Melee", "wand", 100,3),
            new MagmaSkeleton("Magma Skeleton", "Minion", "Melee", "Bone Sword", 120,3),
            new WaterBlob("Water Blob", "Minion", "Ranged", "Water Magic", 130,1),
            new ResonanceGoblin("Resonance Goblin", "Minion", "Ranged", "Sound Magic", 130, 2),
            new MoonSprite("Moon Sprite", "Minion", "Melee", "Astral magic", 110, 2),
        };

        //BattleLogic battlelogic = new BattleLogic(characters, mobs, 1, skillinput);
        //battlelogic.run();
        MainMenuDisplay mainmenu = new MainMenuDisplay(skillinput, characters, mobs);
        mainmenu.show();



    skillinput.close();
    }

}
