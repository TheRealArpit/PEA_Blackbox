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
import javafx.scene.text.Font;
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

    //player 2
    public void Player2Turn(Pane display){
        display.getChildren().clear();
        hexBoard = new HexBoard();
        hexBoard.setViewBlackbox(this);
        hexBoard.setHexboardPane(display);
        hexBoard.createHexagonalBoard();
        hexBoard.setPlayer(player2);
        turnCounter++;
        if(turnCounter >= TOTAL_TURNS){
            setDisplay(display);
        }
    }
    /*private void displayScore(Pane display) { //Elvis do this
        display.getChildren().clear();  // Clear the board
        String scoreText = "Score: Player 1: " + player1.getScore() + ", Player 2: " + player2.getScore();

        Text scoreDisplay = new Text(scoreText);
        scoreDisplay.setFill(Color.RED);
        scoreDisplay.setX(10);
        scoreDisplay.setY(50);
        Button restartButton = new Button("Restart Game");
        restartButton.setLayoutX(10);
        restartButton.setLayoutY(100);
        restartButton.setOnAction(event ->
        {
            startGame(display);// Set the action to restart the game
        });

        // Add the button to the display
        display.getChildren().add(restartButton);
        display.getChildren().add(scoreDisplay);  // Display scores
    }*/
    private void displayScore(Pane display) {
        display.getChildren().clear();  // Clear the board

        // Determine the winner based on the lower score
        String winnerText;
        if (player1.getScore() < player2.getScore()) {
            winnerText = "Winner: Player 1";
        } else if (player2.getScore() < player1.getScore()) {
            winnerText = "Winner: Player 2";
        } else {
            winnerText = "It's a tie!";
        }

        // Display the score and the winner
        String scoreText = "Score - Player 1: " + player1.getScore() + ", Player 2: " + player2.getScore() + "\n" + winnerText;
        Text scoreDisplay = new Text(scoreText);
        scoreDisplay.setFill(Color.RED);
        scoreDisplay.setFont(new Font(20)); // Increase text size

        // Center the score display on the display pane
        scoreDisplay.setX((display.getWidth() - scoreDisplay.getLayoutBounds().getWidth()) / 2);
        scoreDisplay.setY(50);

        // Restart button
        Button restartButton = new Button("Restart Game");
        restartButton.setLayoutX(10);
        restartButton.setLayoutY(100);
        restartButton.setOnAction(event -> startGame(display));  // Set the action to restart the game

        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setLayoutX(10);
        exitButton.setLayoutY(150);
        exitButton.setOnAction(event -> {
            // Close the window or the application
            Stage stage = (Stage) display.getScene().getWindow();
            stage.close();
        });

        // Add the buttons and the text to the display
        display.getChildren().add(restartButton);
        display.getChildren().add(exitButton);
        display.getChildren().add(scoreDisplay);  // Display scores and winner
    }

    public void setDisplay(Pane display){
        displayScore(display);
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