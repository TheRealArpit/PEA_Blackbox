package Blackbox.Model;

import static Blackbox.Constant.Constants.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Arrow {
    private ArrayList<ArrayList<Hexagon>> hexList;
    private Pane parentPane;
    private HexBoard hexBoard;

    private Hexagon hex;

    private double centreX;
    private double centreY;
    private direction goingTo;
    private double rotation;
    private Path arrow;
    private int idArrow;

    public Arrow(Hexagon hexOfArrow, HexBoard hexBoard, double cX, double cY, int i){
        this.hexBoard = hexBoard;
        hexList = hexBoard.gethexList();
        hex = hexOfArrow;
        centreX = cX;
        centreY = cY;
        if(!TESTING){
            parentPane = hexBoard.getParantPane();
        }
        setArrDirection(i);
    }
    private  void setArrDirection(int i){
        switch (i) {
            case 1:
                rotation = 35; // North-East
                goingTo = direction.N_EAST;
                break;
            case 2:
                rotation = 90; // East
                goingTo = direction.EAST;
                break;
            case 3:
                rotation = 150; // South_East
                goingTo = direction.S_EAST;
                break;
            case 4:
                rotation = -150; // South-West
                goingTo = direction.S_WEST;
                break;
            case 5:
                rotation = -90; // West
                goingTo = direction.WEST;
                break;
            case 0:
                rotation = -30; // North-West
                goingTo = direction.N_WEST;
                break;
            default:
                break;
        }
    }

    public void createArrow() {
        arrow = new Path();
        final double length = 30; // Length of the arrow line
        final double headWidth = 10; // Width of the arrowhead
        final double headLength = 10; // Length of the arrowhead

        // Start point
        double lineStartY = centreY + length;

        // Line
        MoveTo moveTo = new MoveTo(centreX, lineStartY);
        LineTo lineTo = new LineTo(centreX, centreY);

        // Building the arrowhead as path
        // Move to the tip of the arrowhead
        MoveTo moveToHead = new MoveTo(centreX, centreY);
        //creating triangle
        LineTo lineToLeft = new LineTo(centreX - headWidth / 2, centreY + headLength);
        LineTo lineToRight = new LineTo(centreX + headWidth / 2, centreY + headLength);
        LineTo lineBackToTip = new LineTo(centreX, centreY);

        // Add elements to the path
        arrow.getElements().addAll(moveTo, lineTo, moveToHead, lineToLeft, lineToRight, lineBackToTip);

        arrow.setStrokeWidth(2);
        arrow.setStroke(Color.RED);
        arrow.setFill(Color.RED);

        Rotate rotate = new Rotate(rotation, centreX, centreY);
        arrow.getTransforms().add(rotate);

        arrow.setStroke(Color.RED);
        arrow.setOnMouseEntered(event -> {
            arrow.setStroke(Color.YELLOW);
        });
        arrow.setOnMouseExited(event -> {
            arrow.setStroke(Color.RED);
        });
        arrow.setOnMouseClicked(event -> {
            Ray ray = new Ray(hexBoard, this);
        });
    }

    public void setArrowId(int row, int col, int k, double midx, double midy) {
        idArrow = array[row][col][k];
        if(!TESTING) {
            Text text = new Text(idArrow+"");
            text.setFill(Color.WHITE);
            text.setFont(new Font("Arial", 14));
            text.setLayoutX(midx);
            text.setLayoutY(midy);
            hexBoard.getParantPane().getChildren().add(text);

        }
    }

    public static int[][][] array = {
            { {2, 1, 54}, {53,52},{51,50},{49,48},{47,46,45}},// Top row
            {{4 ,3}, {-1},{-1},{-1},{-1},{44,43}},// Second row
            {{6 ,5}, {-1},{-1},{-1},{-1},{-1},{42,41}},// Third row
            {{8 ,7}, {-1},{-1},{-1},{-1},{-1},{-1},{40,39}},// Fourth row
            {{11 ,10,9}, {-1},{-1},{-1},{-1},{-1},{-1},{-1},{36,38,37}},// Fourth row
            {{13 ,12}, {-1},{-1},{-1},{-1},{-1},{-1},{34,35}},// fifth row
            {{15 ,14}, {-1},{-1},{-1},{-1},{-1},{32,33}},// sixth row
            {{17 ,16}, {-1},{-1},{-1},{-1},{30,31}},// Second row
            {{20, 19, 18}, {22,21},{24,23},{26,25},{28,27,29}}// Top row
    };

    //getters
    public Hexagon getHex() {
        return hex;
    }

    public double getCentreX() {
        return centreX;
    }

    public double getCentreY() {
        return centreY;
    }

    public direction getGoingTo() {
        return goingTo;
    }
    public int getIdArrow(){
        return idArrow;
    }
    public direction getDirection(){
        return goingTo;
    }
    public Path getArrow(){
        return arrow;
    }

}
