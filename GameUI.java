import java.util.Scanner;

public class GameUI {
    private Character[] characters;
    private MobNPC[] mobs;
    private Scanner skillInput;
    private int currentChapter = -1;
    private BattleHUD battleHUD;
    private BattlePhases battlePhases;

    public GameUI(Character[] characters, MobNPC[] mobs, Scanner skillInput) {
        this.characters = characters;
        this.mobs = mobs;
        this.skillInput = skillInput;
        this.battleHUD = new BattleHUD(characters, mobs, skillInput, currentChapter);
        this.battlePhases = new BattlePhases(characters, mobs, skillInput, currentChapter);
    }

    public void StartGame(){
        int chapter = -1;
        boolean tag2 = false;
        boolean tag3 = false;
        boolean tag4 = false;
        boolean tag5 = false;
        boolean tag6 = false;

        System.out.println("==== Story Mode ====\n");
        System.out.println("Chapter 1: Battle of the Sea");
        System.out.println("Chapter 2: The Survival");
        System.out.println("Chapter 3: Darkness and Void");
        System.out.println("Chapter 4: Heart of the Living Forest");
        System.out.println("Chapter 5: The Castle Memoir");
        System.out.println("Chapter 6: The Final Show");
        System.out.println("====================");
        System.out.println("Enter Chapter Number to play (1-6), or 0 to return to the main menu.");
        System.out.print("Enter Action: ");

        try {
            chapter = skillInput.nextInt();
        } catch (Exception e) {
            System.out.println();
            // invalid input, consume the token and return
            skillInput.next();
            System.out.println("Invalid input. Returning to main menu...");
            return;
        }

        if (chapter == 0) {
            System.out.println("Returning to main menu...");
            return; // return to menu
        }

        if (chapter >= 1 && chapter <= 6) {
            this.currentChapter = chapter;
            battleHUD.setCurrentChapter(currentChapter);
            battlePhases.setCurrentChapter(currentChapter);
        } else {
            System.out.println("Invalid chapter. Returning to main menu...");
        }
        switch(chapter){
            case 1:
                battleHUD.execute();
                StartBattle(characters, mobs);// enemies take their turn and attack alive characters
                if (!isChapterEnemyAlive(chapter)) {
                    tag2 = true;
                } else {
                    System.out.println("[Error]: That can't be right... You lost...");
                }
                break;
            case 2:
                if(tag2 == true){
                    System.out.println("\n====================");
                    System.out.println("[Error]: You have not finished chapter 1.");
                    System.out.println("Finish chapter 1 to unlock chapter 2.\n");
                }else{
                    battleHUD.execute();
                    StartBattle(characters, mobs);// enemies take their turn and attack alive characters
                    if (!isChapterEnemyAlive(chapter)) {
                        tag3 = true;
                    } else {
                        System.out.println("[Error]: That can't be right... You lost...");
                    }
                }
                break;
            case 3:
                if(tag3 == false){
                    System.out.println("[Error]: You have not finished chapter 1.");
                    System.out.println("Finish chapter 2 to unlock chapter 3\n");
                }else{
                    battleHUD.execute();
                    StartBattle(characters, mobs);// enemies take their turn and attack alive characters
                    if (!isChapterEnemyAlive(chapter)) {
                        tag4 = true;
                    } else {
                        System.out.println("[Error]: That can't be right... You lost...");
                    }
                }
                break;
            case 4:
                if(tag4 == false){
                    System.out.println("[Error]: You have not finished chapter 1.");
                    System.out.println("Finish chapter 3 to unlock chapter 4\n");
                }else{
                    battleHUD.execute();
                    StartBattle(characters, mobs);// enemies take their turn and attack alive characters
                    if (!isChapterEnemyAlive(chapter)) {
                        tag5 = true;
                    } else {
                        System.out.println("[Error]: That can't be right... You lost...");
                    }
                }
                break;
            case 5:
                if(tag5 == false){
                    System.out.println("[Error]: You have not finished chapter 1.");
                    System.out.println("Finish chapter 4 to unlock chapter 5\n");
                }else{
                    battleHUD.execute();
                    StartBattle(characters, mobs);// enemies take their turn and attack alive characters
                    if (!isChapterEnemyAlive(chapter)) {
                        tag6 = true;
                    } else {
                        System.out.println("[Error]: That can't be right... You lost...");
                    }
                }
                break;
            case 6:
                if(tag6 == false){
                    System.out.println("[Error]: You have not finished chapter 1.");
                    System.out.println("Finish chapter 5 to unlock chapter 6\n");
                }else{
                    battleHUD.execute();
                    StartBattle(characters, mobs);// enemies take their turn and attack alive characters
                    if (!isChapterEnemyAlive(chapter)) {
                        // victory message
                    } else {
                        System.out.println("[Error]: That can't be right... You lost...");
                    }
                }
                break;
        }

        try{
            System.out.println("Press Enter to return to main menu...");
            skillInput.nextLine();
        }
        catch(Exception e){
            System.out.println();
            System.out.println("Returning to main menu...");
        }
    }

    //returns true if any enemy for the given chapter is alive
    private boolean isChapterEnemyAlive(int chapter) {
        for (MobNPC enemy : mobs) {
            if (enemy.chapter != chapter) continue;
            if (enemy.isAlive()) return true;
        }
        return false;
    }
    //check if battle is ongoing
    private boolean isBattleOngoing(){
        boolean partyAlive = false, enemiesAlive = false;
        for (Character party : characters) if (party.isAlive()) partyAlive = true;
        // only count enemies that belong to the current chapter
        for (MobNPC enemies : mobs) if (enemies.chapter == this.currentChapter && enemies.isAlive()) enemiesAlive = true;
        return partyAlive && enemiesAlive;
    }

    //turn based battle here
    public void StartBattle(Character[] characters, MobNPC[] mobs){
        int turnCount = 1;
        while (isBattleOngoing()) {
            System.out.println("\n========== TURN " + turnCount + " ==========");
            // enemy phase: each alive enemy in the current chapter attacks a random alive character
            battlePhases.characterPhase();
            // enemy phase: each alive enemy in the current chapter attacks a random alive character
                battlePhases.enemyPhase();
            // show status after the turn
            battleHUD.execute();
            // tick status durations for characters and mobs
            for (Character c : characters) c.tickStatus();
            for (MobNPC m : mobs) m.tickStatus();
            turnCount++;
            if (turnCount > 1000) break;
        }
    }
}