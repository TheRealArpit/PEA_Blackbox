import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class DisplayBoard extends Application {
    private Pane hexBoard;

    public static void main(String[] args) {
            launch(args);
        }


    @Override
    public void start(Stage primaryStage) throws Exception {
        CreateWelcome(primaryStage);
    }

    public void CreateBoard(Stage primaryStage) throws Exception{
         hexBoard = new Pane();
            hexBoard.setStyle("-fx-background-color: black;"); // Set the background color to red

        int[] rowss = {5, 6, 7, 8, 9, 8, 7, 6, 5}; // number of hexagons in each row

        for(int y = 0; y < 9; y++){
            for(int x = 0; x <rowss[y]; x++){
                Hexagon hex = new Hexagon();
                hex.createHex();
                double offsetX = getOffsetX(rowss, y) ;
                double positionX = getPosition( x, offsetX) -50 ;
                double positionY = y * 1.5 * ConstantValues.HEXAGON_RADIUS + 100;
                hex.setLayoutX(positionX);
                hex.setLayoutY(positionY);
                hex.setPosXY(positionX,positionY);
                hexBoard.getChildren().add(hex);
            }
            System.out.println();
        }



        Button HideAtomsButton = new Button("Hide Atoms");
        Button ShowAtomsButton = new Button("Show Atoms");

        HideAtomsButton.setPrefWidth(200); // Set the width of the button
        HideAtomsButton.setPrefHeight(50);

        ShowAtomsButton.setPrefWidth(200); // Set the width of the button
        ShowAtomsButton.setPrefHeight(50);

        ShowAtomsButton.setLayoutX(0);
        ShowAtomsButton.setLayoutY(50);

        hexBoard.getChildren().addAll(ShowAtomsButton, HideAtomsButton); // Add the StackPane to the hexBoard

        primaryStage.setScene(new Scene(hexBoard, ConstantValues.LEN_WIDTH, ConstantValues.LEN_WIDTH, Color.RED));
        primaryStage.show();
        HideAtomsButton.setOnAction(event -> {
            hideAtomsOnBoard();
        });
        ShowAtomsButton.setOnAction(event -> {
            ShowAtomsOnBoard();
        });
    }


    private static double getPosition(int col, double offsetX) {
        double hexagonWidth = ConstantValues.HEXAGON_RADIUS; // Horizontal distance between the centers of two adjacent hexagons
        double basePosition = col * hexagonWidth; // Base x-coordinate for the hexagon in its row
        double position = ConstantValues.SCALING_FACTOR * (basePosition + offsetX + ConstantValues.PADDING);// Calculate the final x-coordinate
        return position;
}

    private static double getOffsetX(int[] rows, int row) {
        int maxHexagons = rows.length; //Maximum number of hexagons in a row
        int currentHexagons = rows[row]; //Number of hexagons in the current row
        double hexagonWidth = 2 * ConstantValues.HEXAGON_RADIUS * Math.cos(Math.PI / 3); //Horizontal distance between the centers of two adjacent hexagons
        int difference = maxHexagons - currentHexagons; //Difference between the maximum number of hexagons and the number in the current row
        return difference * hexagonWidth / 2;
    }

    public void CreateWelcome(Stage primaryStage) throws Exception {

        // Creating a Pane for the welcome screen
        Pane welcomeScreen = new Pane();
        //Set color of background to black
        welcomeScreen.setStyle("-fx-background-color: linear-gradient(#000000 , #001B4F);");

        // Creating Welcome text to display on the welcome screen
        Text welcomeText = new Text("Welcome to BlackBox");
        welcomeText.setX(90); // Set the x-coordinate of the text
        welcomeText.setY(160); // Set the y-coordinate of the text
        welcomeText.setUnderline(true);

        //Set the font, font size and colour
        welcomeText.setFont(Font.font("Impact", FontWeight.BOLD, 70));
        welcomeText.setFill(Color.BLUE);


        // Adding the text node to the welcome screen pane
        welcomeScreen.getChildren().add(welcomeText);

        //Creating production text for welcome screen
        Text prodText = new Text("A unique game experience created by APE Productions");
        prodText.setX(90);
        prodText.setY(190);
        prodText.setTextAlignment(TextAlignment.CENTER);
        prodText.setFont(Font.font("Impact", FontWeight.LIGHT, 14));
        prodText.setFill((Color.CADETBLUE));

        welcomeScreen.getChildren().add(prodText);

        //Creating Instruction text for welcome screen
        Button instructionText = new Button("""
                Game Instructions:

                Two players, A and B, compete over two rounds.

                In Round 1, A sets up 5/6 atoms while B deduces their positions and their score is calculated.

                In Round 2, roles switch: B sets up the atoms, and A deduces their positions and their score is calculated.

                The player with the highest total score wins.""");

        instructionText.setLayoutX(130);
        instructionText.setLayoutY(600);
        instructionText.setFont(Font.font("Impact", FontWeight.BOLD, 12));
        instructionText.setAlignment(Pos.CENTER);
        instructionText.setStyle("-fx-background-color: linear-gradient(#001B4F, #000000 );"
                                 +  "-fx-text-fill: white; "
                                 +  "-fx-background-radius: 20;"
                                 +  "-fx-background-insets: 0;"
                                 +  "-fx-min-width: 200px;"
                                 +  "-fx-min-height: 100px;");

        welcomeScreen.getChildren().add(instructionText);

        // Creating a Start button
        Button nextButton = new Button("Start Game");
        nextButton.setLayoutX(300); // Set the x-coordinate of the button
        nextButton.setLayoutY(300); // Set the y-coordinate of the button
        nextButton.setAlignment(Pos.CENTER);

        //Setting button Font
        nextButton.setFont(Font.font("Impact", FontWeight.BOLD, 36));

        //Changing style of Button
        nextButton.setStyle("-fx-background-color: linear-gradient(#4ECCFC, #09729A );"
                            +  "-fx-background-radius: 90;"
                            +  "-fx-background-insets: 0;"
                            +  "-fx-text-fill: white;"
                            +  "-fx-min-width: 200px;"
                            +  "-fx-min-height: 100px;");

        // Adding the button to the welcome screen pane
        welcomeScreen.getChildren().add(nextButton);

        // Creating an Exit button
        Button exitButton = new Button("Exit Game");
        exitButton.setLayoutX(300); // Set the x-coordinate of the button
        exitButton.setLayoutY(450); // Set the y-coordinate of the button
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

        // Adding the button to the welcome screen pane
        welcomeScreen.getChildren().add(exitButton);



        // Creating the Scene with the welcome screen Pane
        Scene scene = new Scene(welcomeScreen, ConstantValues.LEN_WIDTH, ConstantValues.LEN_WIDTH);

        // Setting the scene to the primary stage
        primaryStage.setScene(scene);

        // Displaying the primary stage
        primaryStage.show();
        // Handling button click event
        nextButton.setOnAction(event -> {
            // Create the new scene
            try {
                CreateBoard(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Set the new scene on the primary stage
            //primaryStage.setScene(new Scene(newScreen, 600, 400));
        });


    }

    public void hideAtomsOnBoard() {
        for (Node hexagon : hexBoard.getChildren()) {
            if (hexagon instanceof Hexagon) {
                Hexagon hex = (Hexagon) hexagon;
                if(hex.hasAtom){
                    hex.atom.hideAtom();
                }
            }
        }
    }
    public void ShowAtomsOnBoard() {
        for (Node hexagon : hexBoard.getChildren()) {
            if (hexagon instanceof Hexagon) {
                Hexagon hex = (Hexagon) hexagon;
                if(hex.hasAtom){
                    hex.atom.showAtom();
                }
            }
        }
    }




}


