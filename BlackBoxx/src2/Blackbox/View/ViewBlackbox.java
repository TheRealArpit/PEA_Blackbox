package Blackbox.View;

import Blackbox.Model.HexBoard;
import Blackbox.Model.Player;
import Blackbox.Model.WelcomeScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewBlackbox extends Application {
    private Scene hexBoardScene;
    private Stage primaryStage;
    private HexBoard hexBoard;
    private int turnCounter = 0;
    private Player player1,player2;
    private static final int TOTAL_TURNS = 2;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Pane display = new Pane();
        startGameGUI(display);
        stageSettings(primaryStage);
        hexBoardScene = new Scene(display, 500, 500);
        primaryStage.setScene(hexBoardScene);
        primaryStage.setTitle("Blackbox Game");
        primaryStage.show();
    }

    //Welcome screen then once button pressed game starts
    private void startGameGUI(Pane display) {
        WelcomeScreen welcomeScreen = new WelcomeScreen(display);
        welcomeScreen.getSubmit().setOnMouseClicked(event -> {
            restartGame(display);  // Use restartGame to handle resetting completely
        });
    }

    private void restartGame(Pane display) {
        display.getChildren().clear();  // Clear the existing components
        startGame(display);  // Reinitialize the game logic
    }

    private void startGame(Pane display) {
        turnCounter = 0;
        display.getChildren().clear();
        player1 = new Player();
        player2 = new Player();

        hexBoard = new HexBoard();
        hexBoard.setViewBlackbox(this);
        hexBoard.setHexboardPane(display);
        hexBoard.createHexagonalBoard();
        hexBoard.setPlayer(player1);
    }

    private void stageSettings(Stage stage) {
        stage.setFullScreen(true);
        stage.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                if (event.isShiftDown()) {
                    stage.close();
                } else {
                    stage.setFullScreen(!stage.isFullScreen());
                }
            }

        });
    }
}