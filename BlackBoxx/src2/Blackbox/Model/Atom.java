package Blackbox.Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;

import static Blackbox.Constant.Constants.*;
/**
 * Class for the atom. user tries to guess the location of this
 * created when a hexagon is clicked or during tests
 */
public class Atom {

    private HexBoard hexBoard;
    private ArrayList<ArrayList<Hexagon>> hexList;

    public Hexagon getHexOfAtom() {
        return hexOfAtom;
    }

    private Hexagon hexOfAtom;//hexagon representing atom

    private Circle circle;//visual representation of atom
    private Circle COI;//circle of influence

    public int getRowList() {
        return rowList;
    }

    public int getColList() {
        return colList;
    }
//coordinates in hexlist
    private int rowList;
    private int colList;
    /**
     * constructor for an atom with row and column indeces and a specific hexagon
     * Used for testing
     */
    public Atom(int row, int col, Hexagon hex){
        rowList = row;
        colList = col;
        hexOfAtom = hex;
    }

    /**
     * constructor for atom with its location on the hexboard
     */
    public Atom(HexBoard hexb, int row, int col, Boolean Finished){
        hexBoard = hexb;
        rowList = row;
        colList = col;
        if(!TESTING) {
            //create the atom only if no in testing mode
            createAtom(hexb.gethexList().get(row).get(col).getCentreX(), hexb.gethexList().get(row).get(col).getCentreY());
        }else{
            this.rowList =row;
            this.colList = col;
        }
    }


    /**
     * method to create the visual represnetation of atom
     */
    public void createAtom(double x, double y){
        circle = new Circle();
        circle.setRadius(ATOM_RADIUS);//set radius
        circle.setFill(ATOM_COLOR);// set color
        circle.setCenterX(x);//coordinates
        circle.setCenterY(y);
        circle.setMouseTransparent(true); // Make the circle transparent to mouse events
        COI = new Circle();
        COI.setRadius(COI_RADIUS); // Same radius as the main circle
        COI.setFill(Color.TRANSPARENT); // Transparent fill
        COI.setStroke(Color.RED); // Red stroke color
        COI.setStrokeWidth(3); // Stroke width
        COI.setStrokeType(StrokeType.OUTSIDE); // Stroke type
        COI.getStrokeDashArray().addAll(10d, 10d); // Dotted line
        COI.setMouseTransparent(true); // Make the circle transparent to mouse events
        COI.setCenterX(x);
        COI.setCenterY(y);
        hexBoard.getParantPane().getChildren().addAll(COI,circle);
    }
    public void removeAtom(){
        hexBoard.getParantPane().getChildren().removeAll(circle,COI);}


    public  void hideAtom(){//hides the atom by setting the visibility of both the main circle and the coi to false
        circle.setVisible(false);
        COI.setVisible(false);
    }
    public  void showAtom(){//showes the atom by settign the visibility of both the main circle and the coi to true
        circle.setVisible(true);
        COI.setVisible(true);
    }


    @Override
    public String toString(){
        return "("+rowList+", "+colList+")";
    }
}