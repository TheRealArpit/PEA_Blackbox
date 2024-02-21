import javafx.scene.shape.Polygon;

import javax.swing.plaf.PanelUI;

public class Ray {
    public direction cameFrom;

    public enum direction{
        N_EAST,
        EAST,
        S_EAST,
        S_WEST,
        WEST,
        N_WEST
    }
    private static Polygon createHexagon (double radius){
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * i; // Calculate the angle for each vertex
            double x = radius * Math.cos(angle); // Calculate the x-coordinate
            double y = radius * Math.sin(angle); // Calculate the y-coordinate
            hexagon.getPoints().addAll(x, y);
        }
        return hexagon;
    }
}
