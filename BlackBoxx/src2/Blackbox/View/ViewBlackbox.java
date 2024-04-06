package Blackbox.View;

import Blackbox.Model.HexBoard;
import Blackbox.Model.Hexagon;
import Blackbox.Model.WelcomeScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewBlackbox extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane display = new Pane();
        startGameGUI(display);
        stageSettings(primaryStage);
        primaryStage.setScene(new Scene(display, 500, 500));
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
