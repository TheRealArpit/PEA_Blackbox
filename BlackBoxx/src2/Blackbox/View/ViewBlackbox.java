package Blackbox.View;

import Blackbox.Model.Atom;
import Blackbox.Model.HexBoard;
import Blackbox.Constant.Constants.*;
import Blackbox.Model.WelcomeScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewBlackbox extends Application {
    private Scene hexBoardScene; // Store the hex board scene
    private Stage primaryStage; // Keep a reference to the primary stage

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage; // Initialize the primary stage reference
        Pane display = new Pane();
        startGameGUI(display);
        stageSettings(primaryStage);

        hexBoardScene = new Scene(display, 500, 500); // Store the scene

        primaryStage.setScene(hexBoardScene);
        primaryStage.setTitle("Blackbox Game");
        primaryStage.show();

    }

    private void startGameGUI(Pane display){
        WelcomeScreen welcomeScreen = new WelcomeScreen(display);
        welcomeScreen.getSubmit().setOnMouseClicked(event -> {
            startGame(display);
        });
    }
    private void startGame(Pane display){
        display.getChildren().clear();
        HexBoard hexBoard = new HexBoard();
        hexBoard.setHexboardPane(display);
        hexBoard.createHexagonalBoard();
        hexBoard.getToggleR1R2().setOnAction(actionEvent ->{
            if ("Next".equals(hexBoard.getToggleR1R2().getText())) {
                hexBoard.setRound2();
                hexBoard.setArrowTouchOnn();
            }
            else if ("Finish".equals(hexBoard.getToggleR1R2().getText())) {
                hexBoard.setGuessingRound();
                hexBoard.checkAtoms();
            }
            else if (("Confirm").equals(hexBoard.getToggleR1R2().getText())) {
                hexBoard.CheckGuessedAtoms();
            }
            else if (("Score").equals(hexBoard.getToggleR1R2().getText())) {
                hexBoard.displayScore();
            }

        });
    }

    private void switchToNewScene() {
        Pane newPane = new Pane();
        // Add components to newPane as needed...

        Scene newScene = new Scene(newPane, 500, 500); // Create a new scene
        primaryStage.setScene(newScene); // Set the new scene on the primary stage
    }

    public void switchBackToHexBoard() {
        primaryStage.setScene(hexBoardScene);
    }

    private boolean isFullScreen = true; // Track full-screen state
    private void stageSettings(Stage stage) {
        stage.setFullScreen(true);
        stage.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                if (event.isShiftDown()) {
                    stage.close();
                } else {
                    if (isFullScreen) {
                        stage.setFullScreen(false);
                    } else {
                        stage.setFullScreen(true);
                    }
                    isFullScreen = !isFullScreen;
                }
            }
        });
    }
}
