import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.List;

public class ConstantValues {

    public static List<List<Hexagon>> hexList = new ArrayList<>();

    //controls where the first hexagon is going to be made, which shifts all the hexagons
    public static final double BOARD_X_STARTAT = 300;
    public static final double BOARD_Y_STARTAT = 150;

    public static final double WELCOMEBOARD_X_STARTAT = 350;

    public static final double HEXAGON_RADIUS = 40.0;
    public static final double HEXAGON_STROKE_WIDTH = 5;
    public static final Color HEXAGON_STROKE = Color.BLUE;
    public static final Color HEXAGON_COLOR = Color.BLACK;

    public static final int MAX_ATOMS = 5;
    public static final Color ATOM_COLOR = Color.RED;
    public static final double ATOM_RADIUS = 12.0;

    public static final double PADDING = 85;  // Padding around the grid

    public static final double SCALING_FACTOR_X = 1.9;
    public static final double SCALING_FACTOR_Y = 1.65;

    public static final double LEN_WIDTH = 900;
    public static final double COI_RADIUS = 73;

    public enum direction{
        N_EAST,
        EAST,
        S_EAST,
        S_WEST,
        WEST,
        N_WEST
    }

    //do not change or the way border atoms are calculated is messed up
    public static double getScreenWidth() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//        return primaryScreenBounds.getWidth();
        return 800;
    }

    public static double getScreenHeight() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        //return primaryScreenBounds.getHeight();
        return 800;
    }


}
