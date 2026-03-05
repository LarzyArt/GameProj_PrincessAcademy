// Abstract base class for battle UI components
import java.util.Scanner;

public abstract class BattleUIComponent {
    protected Character[] characters;
    protected MobNPC[] mobs;
    protected Scanner skillInput;
    protected int currentChapter;

    public BattleUIComponent(Character[] characters, MobNPC[] mobs, Scanner skillInput, int currentChapter) {
        this.characters = characters;
        this.mobs = mobs;
        this.skillInput = skillInput;
        this.currentChapter = currentChapter;
    }

    public void setCurrentChapter(int currentChapter) {
        this.currentChapter = currentChapter;
    }

    // Abstract method to be implemented by subclasses
    public abstract void execute();
}