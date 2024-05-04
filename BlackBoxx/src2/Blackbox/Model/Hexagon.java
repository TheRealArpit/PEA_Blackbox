package Blackbox.Model;

import static Blackbox.Constant.Constants.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;


public class Hexagon {
    private Pane parentPane;
    private HexBoard hexBoard;
    private ArrayList<ArrayList<Hexagon>> hexList;
    private ArrayList<atomPlacement> atomPlacements = new ArrayList<>();

    private boolean hasAtom = false;

    private boolean hasGuessedAtom = false;
    private Atom GuessedAtom;
    private boolean hasBorderingAtom = false;
    private int borderingAtoms = 0;

    private ArrayList<Arrow> arrowList;
    private ArrayList<Ray> rays;

    private Polygon hexagon;

    public Atom getAtom() {
        return atom;
    }

    private Atom atom;
    private double centreX;
    private double centreY;

    private int rowList;
    private int colList;
    //f
    public Boolean finishedRound = false;

    public Hexagon(HexBoard hexboard){
        hexagon = new Polygon();
        atomPlacements = new ArrayList<>();
        this.hexBoard = hexboard;
        arrowList = new ArrayList<Arrow>();
        rays = new ArrayList<Ray>();
        hexList = this.hexBoard.gethexList();
        parentPane = hexboard.getParantPane();
        hexagon.setOnMouseEntered(event -> hexagon.setStroke(Color.RED));
        hexagon.setOnMouseExited(event -> hexagon.setStroke(HEXAGON_STROKE));
        hexagon.setOnMouseClicked(event -> {
            if(!TESTING) {
                if (!finishedRound) {
                    if (!hasAtom && hexboard.getAtomList().size() < MAX_ATOMS) {
                        createAtom(rowList, colList);
                    } else if (hasAtom) {
                        removeAtom(rowList, colList);
                    } else {
                        System.out.println("Can't add more atoms. Maximum limit reached.");
                    }
                } else {
                    if (!hasGuessedAtom && hexboard.getGuessedAtomlist().size() < MAX_ATOMS) {
                        GuessedAtom = new Atom(rowList, colList, this);
                        hexBoard.getGuessedAtomlist().add(GuessedAtom);
                        hexagon.setFill(Color.DARKGOLDENROD);
                        hasGuessedAtom = true;
                        hexBoard.getPlayer().addNumOfGuesses();
                    } else if (hasGuessedAtom) {
                        hexboard.getGuessedAtomlist().remove(GuessedAtom);
                        hasGuessedAtom = false;
                        hexagon.setFill(Color.BLACK);
                        hexBoard.getPlayer().subNumofCorrectGuesses();
                    } else {
                        System.out.println("Can't add more atoms. Maximum limit reached.");
                    }
                }
            }
        });

    }
    public void createHexagon(double xx, double yy, int row, int col) {
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
            if(!TESTING){
                hexagon.getPoints().addAll(x,y);
            }
            createArr(i,angle, xx, yy, row, col,k);
        }
        centreX = xtotal/6 + xx;
        centreY = ytotal/6 + yy;
        if(!TESTING) {
            hexagon.setStrokeWidth(HEXAGON_STROKE_WIDTH);
            hexagon.setStroke(HEXAGON_STROKE);
            hexagon.setFill(HEXAGON_COLOR);
        }
    }
    private int arrowCounter = 0;
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
            Arrow arrow = new Arrow(this, hexBoard, midXArrowLocation,midYArrowLocation,i);
            if(!TESTING){
                arrow.createArrow();
            }
            arrow.setArrowId(row,col,arrowCounter,midXTextLocation,midYTextLocation);
            arrowCounter++;
            arrowList.add((arrow));
        }
    }
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

    public  void createAtom(int row, int col) {
        if(!TESTING){
            atom = new Atom(hexBoard, row,col,finishedRound); //adds to parent pane here
            hexBoard.getAtomList().add(atom);
            hasAtom = true;
        }else{
            atom = new Atom(hexBoard, row,col,finishedRound);
            hasAtom = true;
            hexBoard.getAtomList().add(atom);
        }

    }
    public void removeAtom(int row,int col) {//method to create atom
        if(!TESTING){
            atom.removeAtom();
            hexBoard.getAtomList().remove(atom);
        }
        hasAtom = false;
        atom = null;
    }
    public void createGuessAtom(int row, int col, Atom atom){
        this.GuessedAtom = atom;
        this.hasGuessedAtom = true;
        hexBoard.getGuessedAtomlist().add(GuessedAtom);
    }

    public boolean hasAtom() {return hasAtom;}
    public ArrayList<atomPlacement> getAtomPlacements() {return atomPlacements;}
    public void setAtomPlacement(atomPlacement at) {atomPlacements.add(at);}
    public void setHasBorderingAtom(boolean hasBorderingAtom) {this.hasBorderingAtom = hasBorderingAtom;}
    public int getBorderingAtoms() {return borderingAtoms;}
    public boolean hasBorderingATom() {return hasBorderingAtom;}

    public void setPosXY(double x, double y) {//set x and y coordinates for the hexagon
         hexagon.setLayoutX(x);
         hexagon.setLayoutY(y);
    }

    public void addBorderingAtoms() {borderingAtoms++;}

    public int getColList() {return colList;}
    public int getRowList() {return rowList;}
    public double getCentreX() {return centreX;}
    public double getCentreY() {return centreY;}
    public Polygon getHexagon() {return hexagon;}

    public boolean hasGuessedAtom() {
        return hasGuessedAtom;
    }

    public ArrayList<Arrow> getArrowList() {return arrowList;}
    public void setAtom(Atom atom11){atom = atom11;    hasAtom = true;}
    public void unsetAtom(){atom = null; hasAtom = false;}
    public void setFinishedRound(Boolean finishedRound) {
        this.finishedRound = finishedRound;
    }
    @Override
    public String toString(){
        return "("+rowList+", "+colList+")";
    }

    public void setHasGuessedAtom(boolean tf) {
        hasGuessedAtom = tf;
    }
}
