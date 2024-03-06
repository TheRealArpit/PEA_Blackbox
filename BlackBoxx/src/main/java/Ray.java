import javafx.scene.shape.Polygon;

import javax.swing.plaf.PanelUI;

public class Ray {
    public direction cameFrom;
    int x;
    int y;

    public enum direction{
        N_EAST,
        EAST,
        S_EAST,
        S_WEST,
        WEST,
        N_WEST
    }


}
