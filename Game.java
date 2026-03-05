import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner skillinput = new Scanner(System.in);
        Character[] characters = {
            new Audry("Audry", "Assassin", "Melee", "Acidic Slime", 100, 25),
            new Giantha("Giantha", "Tank", "Melee", "World Tree Branch", 250, 20),
            new Lazuli("Lazuli", "Healer", "Ranged", "Staff", 150, 30),
            new Lynzi("Lynzi", "Dealer", "Melee", "Star Magic", 170, 30),
            new Shiera("Shiera", "Support", "Ranged", "Earth Magic", 120, 25)
        };
        MobNPC[] mobs = {
            //Boss
            new Twinkle("Twinkle", "Boss", "Melee", "Puppet", 500,6),
            //Minibosses
            new Kassundre("Kassundre", "Miniboss", "Ranged", "Dark Magic", 300,5),
            new LavaBeast("Lava Beast", "Miniboss", "Melee", "Fire Magic", 350,2),
            new SirenEmpress("Siren Empress", "Miniboss", "Ranged", "Water Magic", 320,1),
            new Resonara("Resonara", "Miniboss", "Ranged", "Sound Magic", 310,4),
            new EclipseCore("Eclipse Core", "Miniboss", "Melee", "Astral Magic", 350, 3),
            //Mobs
            new StudentPuppet("Student Puppet", "Minion", "Melee", "Wooden Sword", 100,5),
            new CorruptedSkeleton("Corrupted Skeleton", "Minion", "Melee", "Bone Sword", 120,2),
            new WaterSprite("Water Sprite", "Minion", "Ranged", "Water Magic", 130,1),
            new EchoImp("Echo Imp", "Minion", "Ranged", "Sound Magic", 130, 4),
            new AstralGlob("Astral Glob", "Minion", "Melee", "Astral Slime", 110, 3),
            new PrincessPuppet("Princess Puppet", "Minion", "Melee", "wand", 100,5),
            new MagmaSkeleton("Magma Skeleton", "Minion", "Melee", "Bone Sword", 120,2),
            new WaterBlob("Water Blob", "Minion", "Ranged", "Water Magic", 130,1),
            new ResonanceGoblin("Resonance Goblin", "Minion", "Ranged", "Sound Magic", 130, 4),
            new MoonSprite("Moon Sprite", "Minion", "Melee", "Astral magic", 110, 3),
        };

        GameUI gameUI = new GameUI(characters, mobs, skillinput);
        MainMenu mainMenu = new MainMenu(gameUI, skillinput);

        GameSystem gameSystem = new GameSystem(mainMenu);
        gameSystem.MainMenu();

    skillinput.close();
    }

}
