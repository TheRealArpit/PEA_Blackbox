package Blackbox;

import Blackbox.Model.GameModel;
import Blackbox.View.GameView;
import Blackbox.Controller.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane display = new Pane();

        GameModel model = new GameModel();
        GameView view = new GameView(display);
        GameController controller = new GameController(model, view);

        controller.startGame();

        stageSettings(primaryStage);
        primaryStage.setScene(new Scene(display,500,500));
        primaryStage.setTitle("Blackbox Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
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
