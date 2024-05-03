package Blackbox.View;

import Blackbox.Model.HexBoard;
import Blackbox.Model.Player;
import Blackbox.Model.WelcomeScreen;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import static Blackbox.Constant.Constants.WELCOMEBOARD_X_STARTAT;

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

        //Setting background display appearance
        display.setStyle("-fx-background-color: linear-gradient(#000000 , #001B4F);");

        //Player faces for end screen
        String face1 = "(:";
        String face2 = ":)";

        // Determine the winner based on the lower score
        String winnerText = getWinner(player1, player2);
        if (player1.getScore() < player2.getScore()) {
            face2 = ":(";
        } else if (player2.getScore() < player1.getScore()) {
            face1 = "):";
        }

        // Display the score and the winner
        String scoreText = "Score\nPlayer 1: " + player1.getScore() + "\t\t\t\t\tPlayer 2: " + player2.getScore() + "\n\n" + winnerText;

        Text scoreDisplay = new Text(scoreText);
        scoreDisplay.setFill(Color.WHITE);
        scoreDisplay.setFont(Font.font("Impact", FontWeight.BOLD, 70)); // Increase text size
        scoreDisplay.setTextAlignment(TextAlignment.CENTER);

        // Create a drop shadow effect for outline
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setColor(Color.RED);
        scoreDisplay.setEffect(dropShadow);

        // Center the score display on the display pane
        scoreDisplay.setX(((display.getWidth() - scoreDisplay.getLayoutBounds().getWidth()) / 2 ) + 10);
        scoreDisplay.setY(100);


        // Restart button
        Button restartButton = new Button("Restart Game");
        restartButton.setOnAction(event -> startGame(display));  // Set the action to restart the game

        restartButton.setLayoutX(200 + WELCOMEBOARD_X_STARTAT); // Set the x-coordinate of the button
        restartButton.setLayoutY(450); // Set the y-coordinate of the button
        restartButton.setAlignment(Pos.CENTER);

        //Setting button Font
        restartButton.setFont(Font.font("Impact", FontWeight.BOLD, 36));

        //Changing style of Button
        restartButton.setStyle("-fx-background-color: linear-gradient(#4ECCFC, #09729A );"
                +  "-fx-background-radius: 90;"
                +  "-fx-background-insets: 0;"
                +  "-fx-text-fill: white;"
                +  "-fx-min-width: 200px;"
                +  "-fx-min-height: 100px;");


        // Exit button
        Button exitButton = new Button("Exit Game");
        exitButton.setLayoutX(217+WELCOMEBOARD_X_STARTAT); // Set the x-coordinate of the button
        exitButton.setLayoutY(610); // Set the y-coordinate of the button
        exitButton.setAlignment(Pos.CENTER);

        //Setting button Font
        exitButton.setFont(Font.font("Impact", FontWeight.BOLD, 36));

        //Changing style of Button
        exitButton.setStyle("-fx-background-color: linear-gradient(#4ECCFC, #09729A );"
                +  "-fx-background-radius: 90;"
                +  "-fx-background-insets: 0;"
                +  "-fx-text-fill: white;"
                +  "-fx-min-width: 210px;"
                +  "-fx-min-height: 100px;");

        exitButton.setOnAction(event -> {
            // Close the window or the application
            Stage stage = (Stage) display.getScene().getWindow();
            stage.close();
        });


        Button player1Face = new Button(face1);
        player1Face.setStyle("-fx-background-color: linear-gradient(#F155F6, #FA1536 );"
                +  "-fx-background-radius: 90;"
                +  "-fx-background-insets: 0;"
                +  "-fx-text-fill: white;"
                +  "-fx-min-width: 210px;"
                +  "-fx-min-height: 100px;");
        player1Face.setFont(Font.font("Impact", FontWeight.BOLD, 100)); // Increase text size

        // Center the face on the display pane
        player1Face.setLayoutX(-180+WELCOMEBOARD_X_STARTAT);
        player1Face.setLayoutY(420);


        //Setting rotation for face text 1
        Rotate rotate1 = new Rotate(180);
        player1Face.getTransforms().add(rotate1);

        Button player2Face = new Button(face2);
        player2Face.setStyle("-fx-background-color: linear-gradient(#F155F6, #FA1536 );"
                +  "-fx-background-radius: 90;"
                +  "-fx-background-insets: 0;"
                +  "-fx-text-fill: white;"
                +  "-fx-min-width: 210px;"
                +  "-fx-min-height: 100px;");
        player2Face.setFont(Font.font("Impact", FontWeight.BOLD, 100)); // Increase text size

        // Center the face on the display pane
        player2Face.setLayoutX(825+WELCOMEBOARD_X_STARTAT);
        player2Face.setLayoutY(210);

        //Setting rotation for face text 2
        Rotate rotate2 = new Rotate(90);
        player1Face.getTransforms().add(rotate2);

        player2Face.getTransforms().add(rotate2);




        // Add the buttons and the text to the display
        display.getChildren().add(restartButton);
        display.getChildren().add(exitButton);
        display.getChildren().add(scoreDisplay);  // Display scores and winner
        display.getChildren().add(player1Face);
        display.getChildren().add(player2Face);
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

    public String getWinner(Player playar1, Player player2){
        if (playar1.getScore() < player2.getScore()) {
            return "Winner: Player 1";
        } else if (player2.getScore() < player1.getScore()) {
            return "Winner: Player 2";
        } else {
            return "It's a tie!";
        }
    }
}