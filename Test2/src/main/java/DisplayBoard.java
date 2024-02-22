import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DisplayBoard extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //CreateWelcome(primaryStage);


        CreateBoard(primaryStage);

    }

    public void CreateBoard(Stage primaryStage) throws Exception{
        Pane hexBoard = new Pane();

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
        primaryStage.setScene(new Scene(hexBoard, 800, 800));
        primaryStage.show();
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
        // Creating a Pane for the welcome screen
        Pane welcomeScreen = new Pane();

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

        // Handling button click event
        nextButton.setOnAction(event -> {
            // Create the new scene
            Pane newScreen = new Pane();
            Text newText = new Text("This is the new scene!");
            newText.setX(50);
            newText.setY(100);
            newText.setStyle("-fx-font-size: 24px;");
            newScreen.getChildren().add(newText);

            // Set the new scene on the primary stage
            primaryStage.setScene(new Scene(newScreen, 600, 400));
        });

        // Adding the button to the welcome screen pane
        welcomeScreen.getChildren().add(nextButton);

        // Creating the Scene with the welcome screen Pane
        Scene scene = new Scene(welcomeScreen, 600, 400);

        // Setting the scene to the primary stage
        primaryStage.setScene(scene);

        // Displaying the primary stage
        primaryStage.show();
    }



}


