package Blackbox.Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;

import static Blackbox.Constant.Constants.*;

public class Atom {
    private HexBoard hexBoard;
    private ArrayList<ArrayList<Hexagon>> hexList;

    public Hexagon getHexOfAtom() {
        return hexOfAtom;
    }

    private Hexagon hexOfAtom;

    private Circle circle;
    private Circle COI;

    public int getRowList() {
        return rowList;
    }

    public int getColList() {
        return colList;
    }

    private int rowList;
    private int colList;

    public Atom(int row, int col, Hexagon hex){
        rowList = row;
        colList = col;
        hexOfAtom = hex;
    }


    public Atom(HexBoard hexb, int row, int col, Boolean Finished){
        hexBoard = hexb;
        rowList = row;
        colList = col;
        if(!TESTING) {
            createAtom(hexb.gethexList().get(row).get(col).getCentreX(), hexb.gethexList().get(row).get(col).getCentreY());
        }else{
            this.rowList =row;
            this.colList = col;
        }
    }


    public void createAtom(double x, double y){
        circle = new Circle();
        circle.setRadius(ATOM_RADIUS);
        circle.setFill(ATOM_COLOR);
        circle.setCenterX(x);
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
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if (o == null)return false;
        if(o instanceof Atom){
            Atom atom = (Atom) o;
            return colList == atom.getColList() && rowList == atom.getRowList();
        }
        return false;
    }

    @Override
    public String toString(){
        return "("+rowList+", "+colList+")";
    }
}