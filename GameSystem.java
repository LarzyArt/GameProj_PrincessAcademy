
public class GameSystem {
    protected MainMenu mainMenu;

    public GameSystem(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }
    public void MainMenu(){
        mainMenu.run();
    }
}
