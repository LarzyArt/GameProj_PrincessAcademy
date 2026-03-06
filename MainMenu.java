
import java.util.Scanner;

public class MainMenu {
    private GameUI gameUI;
    private Scanner skillInput;

    public MainMenu(GameUI gameUI, Scanner skillInput) {
        this.gameUI = gameUI;
        this.skillInput = skillInput;
    }

    public void displayMenu(){
        System.out.println("==== *Princess Academy: Worlds Collide* ====\n");
        System.out.println("What do you want to do?");
        System.out.println("1. Play Story Mode");
        System.out.println("2. Exit");
        System.out.println();
        System.out.println("====================");
        System.out.print("Enter next Action: ");
    }

    public void prompt(int choice){
        if(choice == 1){ // Starts Game
                gameUI.StartGame();
                // Wait for the user to press Enter so the menu doesn't immediately reappear

                try {
                    skillInput.nextLine();
                } catch (Exception e) {

                }
            }else if(choice == 2){ // CLoses Program
                System.out.println("Exiting game...");
            }else{
                System.out.println("Invalid choice. Returning to main menu.");
        }
        System.out.println();
    }

    public void run() {
        int choice = 0;
        while(choice != 2){
            displayMenu();
            choice = skillInput.nextInt();
            System.out.println();
            prompt(choice);
        }
    }
}