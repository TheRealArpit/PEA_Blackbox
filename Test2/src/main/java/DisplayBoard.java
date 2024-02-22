import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class DisplayBoard extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage = CreateBoard(primaryStage);

    }

    public Stage CreateBoard(Stage primaryStage) throws Exception{
        Pane hexBoard = new Pane();
        double radius = 40;

        int[] rowss = {5, 6, 7, 8, 9, 8, 7, 6, 5}; // number of hexagons in each row

        for(int y = 0; y < 9; y++){
            for(int x = 0; x <rowss[y]; x++){
                Hexagon hex = new Hexagon();
                hex.createHex(40);
                double offsetX = getOffsetX(rowss, y, radius) ;
                double positionX = getPosition(radius, x, offsetX) -50 ;
                double positionY = y * 1.5 * radius + 100;
                hex.setLayoutX(positionX);
                hex.setLayoutY(positionY);
                hex.setPosXY(positionX,positionY);
                hex.setStroke(Color.YELLOW); // set the outline color to yellow
                hexBoard.getChildren().add(hex);
                //System.out.println(hex.toString());
            }
            System.out.println();
        }


        primaryStage.setScene(new Scene(hexBoard, 800, 800));
        primaryStage.show();
        return primaryStage;
    }


    private static double getPosition(double radius, int col, double offsetX) {
        double hexagonWidth = 2 * radius * Math.cos(Math.PI / 3); // Horizontal distance between the centers of two adjacent hexagons
        double basePosition = col * hexagonWidth; // Base x-coordinate for the hexagon in its row
        double padding = 100; // Padding around the grid
        double scalingFactor = 1.75; // Factor to spread out the hexagons
        double position = (basePosition + offsetX + padding) * scalingFactor; // Calculate the final x-coordinate
        return position;
    }

    private static double getOffsetX(int[] rows, int row, double radius) {
        int maxHexagons = rows.length; //Maximum number of hexagons in a row

        int currentHexagons = rows[row]; //Number of hexagons in the current row

        double hexagonWidth = 2 * radius * Math.cos(Math.PI / 3); //Horizontal distance between the centers of two adjacent hexagons

        int difference = maxHexagons - currentHexagons; //Difference between the maximum number of hexagons and the number in the current row

        return difference * hexagonWidth / 2;
    }


}


