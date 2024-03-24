package Blackbox.View;
import javafx.scene.layout.Pane;

public class GameView {
    private Pane display;
    public GameView(Pane display) {
        this.display = display;
    }

    public void start() throws Exception{
        display.getChildren().clear();
        WelcomePage welcomePage = new WelcomePage(display);
        welcomePage.submit.setOnMouseClicked(event -> {
            startGame();
        });
    }
    public void startGame(){
        display.getChildren().clear();
        HexBoard hexBoard = new HexBoard(display);
    }



}

