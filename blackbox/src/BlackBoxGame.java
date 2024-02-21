package com.example.blackbox;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class BlackBoxGame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();
        double radius = 50;
        int[] rows = {5, 6, 7, 8, 9, 8, 7, 6, 5}; // number of hexagons in each row

        for (int row = 0; row < rows.length; row++) {
            // Loop through each column in the current row
            for (int col = 0; col < rows[row]; col++) {
                //create hexagon shape
                Polygon hexagon = new Polygon();
                for (int i = 0; i < 6; i++) {
                    double angle = 2 * Math.PI * (i + 0.5) / 6; // Calculate the angle for each vertex
                    double x = radius * Math.cos(angle); // Calculate the x-coordinate
                    double y = radius * Math.sin(angle); // Calculate the y-coordinate
                    hexagon.getPoints().addAll(x, y);
                }

                double offsetX = getOffsetX(rows, row, radius);

                //set the position of the hexagon
                double position = getPosition(radius, col, offsetX);

                hexagon.setLayoutX(position);

                hexagon.setLayoutY(row * 1.5 * radius + 100);
                hexagon.setStroke(Color.YELLOW); // set the outline color to yellow
                //add the hexagon to the root pane
                root.getChildren().add(hexagon);
            }
        }

        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
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


