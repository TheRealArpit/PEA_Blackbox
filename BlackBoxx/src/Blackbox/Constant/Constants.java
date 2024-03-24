package Blackbox.Constant;

import Blackbox.View.Shapes.Hexagon;
import javafx.geometry.Rectangle2D;
        import javafx.scene.paint.Color;
        import javafx.stage.Screen;

        import java.util.ArrayList;
        import java.util.List;


public class Constants {
    public static List<List<Hexagon>> hexList = new ArrayList<>();

    //---------------------Welcome
    public static final double WELCOMEBOARD_X_STARTAT = 450;
    //---------------------Hexagon
    public static final double HEXAGON_RADIUS = 42.0;

    //controls where the first hexagon is going to be made, which shifts all the hexagons
    public static final double BOARD_X_STARTAT = 250;
    public static final double BOARD_Y_STARTAT = 100;

    //Arrow stuff
    public static final double ARROW_DIS_FROM_CENTREHEX = 25.0;
    public static final double ARROW_TEXT_DIS_FROM_CENTREHEX = 65;

    //if needed later
    public static int[][] bordering = {
            // Top row
            {0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4},
            // Upper middle rows increasing
            {1, 0}, {1, 5},
            {2, 0}, {2, 6},
            {3, 0}, {3, 7},
            // Middle row (longest)
            {4, 0}, {4, 8},
            // Lower middle rows decreasing
            {5, 0}, {5, 7},
            {6, 0}, {6, 6},
            {7, 0}, {7, 5},
            // Bottom row
            {8, 0}, {8, 1}, {8, 2}, {8, 3}, {8, 4}
    };


    public static final double HEXAGON_STROKE_WIDTH = 5;
    public static final Color HEXAGON_STROKE = Color.BLUE;
    public static final Color HEXAGON_COLOR = Color.BLACK;

    public static final int MAX_ATOMS = 10;
    public static final Color ATOM_COLOR = Color.RED;
    public static final double ATOM_RADIUS = 12.0;

    public static final double PADDING = 85;  // Padding around the grid

    public static final double SCALING_FACTOR_X = 1.9;
    public static final double SCALING_FACTOR_Y = 1.65;

    public static final double COI_RADIUS = 73;

    public enum direction{
        N_EAST,
        EAST,
        S_EAST,
        S_WEST,
        WEST,
        N_WEST
    }

    public enum atomPlacement{
        LEFT,
        RIGHT,
        UPRIGHT,
        UPLEFT,
        DOWNRIGHT,
        DOWNLEFT
    }



}
