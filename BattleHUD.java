
import java.util.Scanner;

public class BattleHUD extends BattleUIComponent {

    public BattleHUD(Character[] characters, MobNPC[] mobs, Scanner skillInput, int currentChapter) {
        super(characters, mobs, skillInput, currentChapter);
    }

    @Override
    public void execute() {
        System.out.println("\n===============================");
        System.out.println("         BATTLE STATUS");
        System.out.println("===============================");

        //party stats
        System.out.println("\nParty:");
        for (Character party : characters) {
            if (party.isAlive())
                System.out.println(party.name + " | HP: " + party.healthPoints + " | MP: " + party.manaPoints);
            else
                System.out.println(party.name + " | has been Defeated!!!");
        }

        //mob stats
        System.out.println("\nEnemies: ");
        for (MobNPC enemies : mobs) {
            // only show enemies for the active chapter
            if (enemies.chapter != this.currentChapter) continue;
            if (enemies.isAlive())
                System.out.println(enemies.name + " | HP: " + enemies.healthPoints);
            else
                System.out.println(enemies.name + " | has been Defeated!!!");

        }
        System.out.println("===============================");
    }
}