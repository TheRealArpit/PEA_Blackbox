package Blackbox.Model;
import static Blackbox.Constant.Constants.*;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class WelcomeScreen {
    /**
     * Welcom screen for player
     */
    Pane welcomePage;
    public Button submit;
    public WelcomeScreen(Pane dispay){
        // Creating a Pane for the welcome screen
        welcomePage = dispay;
        welcomePage.setStyle("-fx-background-color: linear-gradient(#000000 , #001B4F);");

        // Creating Welcome text to display on the welcome screen
        Text welcomeText = new Text("Welcome to BlackBox");
        welcomeText.setX(WELCOMEBOARD_X_STARTAT); // Set the x-coordinate of the text
        welcomeText.setY(160); // Set the y-coordinate of the text
        welcomeText.setUnderline(true);

        //Set the font, font size and colour
        welcomeText.setFont(Font.font("Impact", FontWeight.BOLD, 70));
        welcomeText.setFill(Color.BLUE);


        // Adding the text node to the welcome screen pane
        welcomePage.getChildren().add(welcomeText);

        //Creating production text for welcome screen
        Text prodText = new Text("A unique game experience created by APE Productions");
        prodText.setX(WELCOMEBOARD_X_STARTAT);
        prodText.setY(190);
        prodText.setTextAlignment(TextAlignment.CENTER);
        prodText.setFont(Font.font("Impact", FontWeight.LIGHT, 14));
        prodText.setFill((Color.CADETBLUE));

        welcomePage.getChildren().add(prodText);

        //Creating Instruction text for welcome screen
        Button instructionText = new Button("""
                Game Instructions:

                Two players, A and B, compete over two rounds.

                In Round 1, A sets up 5/6 atoms while B deduces their positions and their score is calculated.

                In Round 2, roles switch: B sets up the atoms, and A deduces their positions and their score is calculated.

                The player with the highest total score wins.""");

        instructionText.setLayoutX(WELCOMEBOARD_X_STARTAT+40);
        instructionText.setLayoutY(600);
        instructionText.setFont(Font.font("Impact", FontWeight.BOLD, 12));
        instructionText.setAlignment(Pos.CENTER);
        instructionText.setStyle("-fx-background-color: linear-gradient(#001B4F, #000000 );"
                +  "-fx-text-fill: white; "
                +  "-fx-background-radius: 20;"
                +  "-fx-background-insets: 0;"
                +  "-fx-min-width: 200px;"
                +  "-fx-min-height: 100px;");

        welcomePage.getChildren().add(instructionText);

        // Creating a Start button
        submit = new Button("Start Game");
        submit.setLayoutX(200 + WELCOMEBOARD_X_STARTAT); // Set the x-coordinate of the button
        submit.setLayoutY(270); // Set the y-coordinate of the button
        submit.setAlignment(Pos.CENTER);

        //Setting button Font
        submit.setFont(Font.font("Impact", FontWeight.BOLD, 36));

        //Changing style of Button
        submit.setStyle("-fx-background-color: linear-gradient(#4ECCFC, #09729A );"
                +  "-fx-background-radius: 90;"
                +  "-fx-background-insets: 0;"
                +  "-fx-text-fill: white;"
                +  "-fx-min-width: 200px;"
                +  "-fx-min-height: 100px;");

        // Adding the button to the welcome screen pane
        welcomePage.getChildren().add(submit);

        // Creating an Exit button
        Button exitButton = new Button("Exit Game");
        exitButton.setLayoutX(200+WELCOMEBOARD_X_STARTAT); // Set the x-coordinate of the button
        exitButton.setLayoutY(430); // Set the y-coordinate of the button
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

        //Adding the button to the welcome screen pane
        welcomePage.getChildren().add(exitButton);

        //Creating additional atoms for design
        int spotx = 200;
        int spoty = 150;
        for (int i = 0; i < 6; i++) {

            Circle circle = new Circle();
            circle.setRadius(25);
            circle.setFill(Color.BLUE);
            circle.setCenterX(spotx);
            circle.setCenterY(spoty);

            Circle COI = new Circle();
            COI.setRadius(50); // Same radius as the main circle
            COI.setFill(Color.TRANSPARENT); // Transparent fill
            COI.setStroke(Color.BLUE); // Red stroke color
            COI.setStrokeWidth(3); // Stroke width
            COI.setStrokeType(StrokeType.OUTSIDE); // Stroke type
            COI.getStrokeDashArray().addAll(10d, 10d); // Dotted line
            COI.setCenterX(spotx);
            COI.setCenterY(spoty);

            spoty += 250;

            if (i == 2) {
                spotx = 1310;
                spoty = 150;
            }


            welcomePage.getChildren().add(circle);
            welcomePage.getChildren().add(COI);

        }


        // Add rays around COI
        for (int j = 0; j < 4; j++) {
            Line ray = new Line();
            ray.setStroke(Color.BLUE);
            ray.setStrokeWidth(50);


            switch(j) {
                case 0:
                    ray.setStartX(0);
                    ray.setStartY(100);
                    ray.setEndX(150);
                    ray.setEndY(-10);
                    break;
                case 1:
                    ray.setStartX(0);
                    ray.setStartY(760);
                    ray.setEndX(150);
                    ray.setEndY(870);
                    break;
                case 2:
                    ray.setStartX(1350);
                    ray.setStartY(-10);
                    ray.setEndX(1600);
                    ray.setEndY(150);
                    break;
                case 3:
                    ray.setStartX(1350);
                    ray.setStartY(880);
                    ray.setEndX(1600);
                    ray.setEndY(720);
                    break;
            }

            welcomePage.getChildren().add(ray);
        }


    }

    public Button getSubmit(){return submit;}

}