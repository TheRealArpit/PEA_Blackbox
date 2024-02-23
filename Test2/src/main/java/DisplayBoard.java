import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
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

        // Create a StackPane to hold the button and text
        StackPane stackPane = new StackPane();

        Button nextButton = new Button("Next");
        nextButton.setPrefWidth(200); // Set the width of the button
        nextButton.setPrefHeight(50); // Set the height of the button
        nextButton.setStyle("-fx-font-size: 16px;"); // Set the font size of the button text

        Text textAboveButton = new Text("Are you finishing inputting the atoms?");
        textAboveButton.setStyle("-fx-font-size: 18px;"); // Set the font size of the text

        // Add the button and text to the StackPane
        stackPane.getChildren().addAll(nextButton, textAboveButton);

        // Position the StackPane
        stackPane.setLayoutX(500); // Set the x-coordinate of the StackPane
        stackPane.setLayoutY(400); // Set the y-coordinate of the StackPane

        hexBoard.getChildren().add(stackPane); // Add the StackPane to the hexBoard

        primaryStage.setScene(new Scene(hexBoard, ConstantValues.LEN_WIDTH, ConstantValues.LEN_WIDTH));
        primaryStage.show();
        nextButton.setOnAction(event -> {
            hideAtomsOnBoard();
        });

    }

    private static double getPosition(int col, double offsetX) {
        double hexagonWidth = 2 * ConstantValues.HEXAGON_RADIUS * Math.cos(Math.PI / 3); // Horizontal distance between the centers of two adjacent hexagons
        double basePosition = col * hexagonWidth; // Base x-coordinate for the hexagon in its row

        double scalingFactor = 1.75; // Factor to spread out the hexagons
        double position = (basePosition + offsetX + ConstantValues.PADDING) * ConstantValues.SCALING_FACTOR; // Calculate the final x-coordinate
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
        //ELVIS  DO THIS
        // Creating a Pane for the welcome screen
        Pane welcomeScreen = new Pane();

        // -- eLVIS you can delete the stuff u don't need. keep the button but change the size and all.
        // -- change background and stuff like that. make it look nice
        // Creating text to display on the welcome screen
        Text welcomeText = new Text("Welcome to My Application");
        welcomeText.setX(50); // Set the x-coordinate of the text
        welcomeText.setY(100); // Set the y-coordinate of the text
        welcomeText.setStyle("-fx-font-size: 24px;"); // Set the font size

        // Adding the text node to the welcome screen pane
        welcomeScreen.getChildren().add(welcomeText);

        // Creating a button
        Button nextButton = new Button("Next");
        nextButton.setLayoutX(250); // Set the x-coordinate of the button
        nextButton.setLayoutY(150); // Set the y-coordinate of the button

        // Adding the button to the welcome screen pane
        welcomeScreen.getChildren().add(nextButton);

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
                    //Atom atom = (Atom) atomNode;
                    hex.atom.hideAtom();
                }
            }
        }
    }




}


