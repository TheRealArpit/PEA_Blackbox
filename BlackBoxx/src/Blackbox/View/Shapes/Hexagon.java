package Blackbox.View.Shapes;
import static Blackbox.Constant.Constants.*;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class Hexagon {
    private Pane parentPane;
    public  static ArrayList<Atom> atomList = new ArrayList<>();
    public static int atomCount = 0;
    private boolean hasAtom = false;
    //private atomPlacement atomPlacement;
    private boolean hasBorderingAtom = false;
    private int borderingAtoms = 0;

    private Polygon hexagon;
    private Atom atom;
    private Arrow arrow;
    private double centreX;
    private double centreY;
    private int rowList;
    private int colList;

    private ArrayList<Arrow> arrows;
    private int arrowCounter = 0; // Add this counter

    private ArrayList<atomPlacement> atomPlacements = new ArrayList<>();


    public Hexagon(Pane parent) {
        parentPane = parent;
        hexagon = new Polygon();

        hexagon.setOnMouseEntered(event -> hexagon.setStroke(Color.RED));
        hexagon.setOnMouseExited(event -> hexagon.setStroke(HEXAGON_STROKE));
        hexagon.setOnMouseClicked(event -> {
            if (!hasAtom && atomCount < MAX_ATOMS) {
                createAtom();
            } else if (hasAtom) {
                removeAtom();
            } else {
                System.out.println("Can't add more atoms. Maximum limit reached.");
            }
        });
    }
    public void createHexagon(double xx, double yy, int row, int col) {
        arrows = new ArrayList<>(); // Initialize the list here
        rowList = row;
        colList = col;
        double xtotal=0;
        double ytotal=0;
        int k = 0;
        for (int i = 0; i < 6; i++)
        {
            double angle = 2 * Math.PI * (i + 0.5) / 6;//angle for each corner
            double x = HEXAGON_RADIUS * Math.cos(angle) ;
            double y = HEXAGON_RADIUS * Math.sin(angle);
            xtotal+=x;
            ytotal+=y;
            getHexagon().getPoints().addAll(x,y);
            createArr(i,angle, xx, yy, row, col,k);
        }
        centreX = xtotal/6 + xx;
        centreY = ytotal/6 + yy;
        hexagon.setStrokeWidth(HEXAGON_STROKE_WIDTH);
        hexagon.setStroke(HEXAGON_STROKE);
        hexagon.setFill(HEXAGON_COLOR);


    }

    private void createArr(int i, double angle, double xx, double yy, int row, int col, int k){
        double point1x = ARROW_DIS_FROM_CENTREHEX * Math.cos(angle) ;
        double point1y = ARROW_DIS_FROM_CENTREHEX * Math.sin(angle) ;
        int nextIndex = (i + 1) % 6;
        double nextAngle = 2 * Math.PI * (nextIndex + 0.5) / 6;
        double point2x = ARROW_DIS_FROM_CENTREHEX * Math.cos(nextAngle);
        double point2y = ARROW_DIS_FROM_CENTREHEX * Math.sin(nextAngle);

        double midXArrowLocation = (point1x + point2x) / 2 + xx;
        double midYArrowLocation = (point1y + point2y) / 2 + yy;

        double text1x = ARROW_TEXT_DIS_FROM_CENTREHEX * Math.cos(angle) ;
        double text1y = ARROW_TEXT_DIS_FROM_CENTREHEX * Math.sin(angle) ;
        double text2x = ARROW_TEXT_DIS_FROM_CENTREHEX * Math.cos(nextAngle);
        double text2y = ARROW_TEXT_DIS_FROM_CENTREHEX * Math.sin(nextAngle);
        double midXTextLocation = (text1x + text2x) / 2 + xx;
        double midYTextLocation = (text1y + text2y) / 2 + yy;

        if (arrowValidCheck(i,row,col)){
            arrow = new Arrow (this, midXArrowLocation,midYArrowLocation,parentPane, i);
            arrow.createArrow();
            arrow.createText(row,col, arrowCounter, midXTextLocation, midYTextLocation);
            arrowCounter++;
            arrows.add((arrow));
        }
    }



    private void createAtom() {//method to create atom
        atom = new Atom(centreX,centreY,parentPane);
        atomList.add(atom);
        hasAtom = true;
        atomCount++;

    }

    private void removeAtom() {//method to create atom
        atom.removeAtom();
        atomList.remove(atom);
        hasAtom = false;
        atomCount--;
        atom = null;
    }


    public ArrayList<Atom> getAtomList() {return atomList;}
    public boolean arrowValidCheck(int i, int row, int column){
        return(  ((i == 3 || i == 4 || i == 2) && column == 0 && row == 0 )||( (i == 0 || i == 1 || i == 2 )&& column == 0 && row == 8) ||
                ((i == 3 || i == 4 || i == 5 && column == 4 )&& row == 0 )||( (i == 5 || i == 0 || i == 1) && column == 4 && row == 8) ||
                ((i == 1 || i == 2 || i == 3) )&& column == 0 && row == 4) || ((i == 4 || i == 5 || i == 0) && column == 8 && row == 4) ||
                ((i == 3 || i == 4) && (column >0 && column<4)&& row == 0) || ((i == 0 || i == 1) && column >0 && column<4 && row == 8) ||
                ((i == 3 || i == 2) && row >0 && row<4 && column == 0)|| ((i == 1 || i == 2) && row >=4 && row<=8 && column == 0  )||
                (  (i == 4 ||i==5 ) && row == 2 && column == 6 )|| ((i == 4 ||i==5 ) && row == 1 && column == 5)||
                ((i == 4 ||i==5 ) && row == 3 && column == 7)|| ((i == 0 ||i==5 ) && row == 5 && column == 7) ||
                ( (i == 0 ||i==5 ) && row == 7 && column == 5)||( (i == 0 ||i==5 ) && row == 6 && column == 6 );
    }

    public void setPosXY(double x, double y) {//set x and y coordinates for the hexagon
        hexagon.setLayoutX(x);
        hexagon.setLayoutY(y);
    }
    public double getCentreX() {return centreX;}
    public double getCentreY() {return centreY;}
    public Polygon getHexagon() {return hexagon;}
    public String toString() {return "(" + centreX + ", " + centreY + ")\n";}

    public boolean hasAtom() {return hasAtom;}
    public ArrayList getAtomPlacements() {return atomPlacements;}
    public void setAtomPlacement(atomPlacement at) {atomPlacements.add(at);}
    public void setHasBorderingAtom(boolean hasBorderingAtom) {this.hasBorderingAtom = hasBorderingAtom;}
    public void addBorderingAtoms() {
        borderingAtoms++;}

    public int getRowList() {return rowList;}
    public int getBorderingAtoms() {return borderingAtoms;}

    public int getColList() {return colList;}
    public boolean hasBorderingATom() {return hasBorderingAtom;}
    public Arrow getArrow(){return arrow;}
    public ArrayList<Arrow> getArrowList(){return arrows;}

}
