package Blackbox.Constant;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static boolean TESTING = false;


    //---------------------Welcome
    public static final double WELCOMEBOARD_X_STARTAT = 450;
    //---------------------Hexagon
    public static final double HEXAGON_RADIUS = 42.0;
    public static final double HEXAGON_STROKE_WIDTH = 5;
    public static final Color HEXAGON_STROKE = Color.BLUE;
    public static final Color HEXAGON_COLOR = Color.BLACK;

    public static final int MAX_ATOMS = 5;
    public static final Color ATOM_COLOR = Color.RED;
    public static final double ATOM_RADIUS = 12.0;
    public static final double COI_RADIUS = 73;


    //controls where the first hexagon is going to be made, which shifts all the hexagons
    public static final double BOARD_X_STARTAT = 250;
    public static final double BOARD_Y_STARTAT = 100;
    public static final double PADDING = 85;  // Padding around the grid
    public static final double SCALING_FACTOR_X = 1.9;
    public static final double SCALING_FACTOR_Y = 1.65;

    //Arrow stuff
    public static final double ARROW_DIS_FROM_CENTREHEX = 25.0;
    public static final double ARROW_TEXT_DIS_FROM_CENTREHEX = 65;


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

    public enum RayType{
        HIT,
        REFLECTED,
        NO_ATOM,
        TOTAL_REFLECTION
    }





}
