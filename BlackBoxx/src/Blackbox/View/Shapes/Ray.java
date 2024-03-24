package Blackbox.View.Shapes;
import Blackbox.Constant.Constants.*;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.List;
import static Blackbox.Constant.Constants.*;


public class Ray {
    private Pane parentpane;
    private direction goingTo;
    private int rowIndex;
    private int colIndex;

    private double xLox;
    private double yLoc;

    public Ray(direction goingTo, double x, double y, Pane pane, Hexagon initialHex){//constrctor for the ray
        parentpane = pane;
        this.goingTo = goingTo;
        xLox = x;
        yLoc = y;
        setRowColofHex(initialHex);
        createRay();

    }
    private void createLine() {
        if (isThereNextHex()) {
            Line line = new Line(xLox, yLoc, hexList.get(rowIndex).get(colIndex).getCentreX(), hexList.get(rowIndex).get(colIndex).getCentreY());
            line.setStrokeWidth(1);
            line.setStroke(Color.YELLOW); // Set the color to blue
            parentpane.getChildren().add(line);//Add line to the parent pane
            xLox= hexList.get(rowIndex).get(colIndex).getCentreX();
            yLoc = hexList.get(rowIndex).get(colIndex).getCentreY();
        }else{
            return;
        }
    }

    public void createRay() {
        boolean continueRay = true;

        while (continueRay && isThereNextHex()) {
            Hexagon currentHex = hexList.get(rowIndex).get(colIndex);

            //continueRay = checks(goingTo); // Check for reflections or stops, and update direction

            if (! (continueRay = checks(goingTo) )) {
                System.out.println("Here");
                return;
                //break; // If checks indicate to stop, we exit the loop
            }else{
                createLine(); // Draw the line to the current hexagon
                calculateEndPoint(); // Calculate the next hexagon based on the current direction
            }
        }

        // After exiting the loop, extend the line
    }



    private void calculateEndPoint() {
        switch (goingTo) {
            case EAST:
                colIndex += 1;
                break;
            case WEST:
                colIndex -= 1;
                break;
            case S_EAST: //done
                if(rowIndex>=4) {
                    rowIndex += 1;
                }else{
                    colIndex += 1;
                    rowIndex += 1;
                }
                break;
            case N_EAST: // done
                //System.out.println("here");
                if(rowIndex<=4) {
                    rowIndex -= 1;
                }else{
                    colIndex += 1;
                    rowIndex -= 1;
                }

                break;
            case N_WEST: //done
                if(rowIndex<=4) {
                    rowIndex -= 1;
                    colIndex -= 1;
                }else{
                    rowIndex -= 1;
                }
                break;
            case S_WEST://done
                if(rowIndex>=4) {
                    rowIndex += 1;
                    colIndex -= 1;
                }else{
                    rowIndex += 1;
                }
                break;
            default:
                break;
        }
    }
    private boolean isThereNextHex() {
        if (rowIndex < 0 || rowIndex >= hexList.size()) {
            System.out.println("Left the hexagonalboard");
            return false;
        }
        List<Hexagon> row = hexList.get(rowIndex);
        if (colIndex < 0 || colIndex >= row.size()) {
            System.out.println("Left the hexagonalboard");
            return false;
        }
        return true;
    }
    private void setRowColofHex(Hexagon hex){
        rowIndex = hex.getRowList();
        colIndex = hex.getColList();
    }
    private boolean checks(direction goingto){
        if(isThereNextHex()){
            Hexagon hextocheck = hexList.get(rowIndex).get(colIndex);
            //Add if statements for the coi reflections
            if(hextocheck.getAtomPlacements().size()==1) {
                if (hextocheck.getAtomPlacements().contains(atomPlacement.UPRIGHT)) {
                    if (goingTo == direction.S_WEST) {
                        createLine();
                        System.out.println("hit");
                        return false;
                    } else if (goingTo == direction.S_EAST) {
                        createLine();
                        goingTo = direction.EAST;
                    } else if (goingTo == direction.WEST) {
                        createLine();
                        goingTo = direction.N_WEST;
                    }
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.UPLEFT)) {
                    if (goingTo == direction.S_EAST) {
                        createLine();
                        System.out.println("hit");
                        return false;
                    }
                    else if (goingTo == direction.EAST) {
                        createLine();
                        goingTo = direction.N_EAST;
                    }
                    else if (goingTo == direction.S_WEST) {
                        createLine();
                        goingTo = direction.WEST;
                    }
                } else if (hextocheck.getAtomPlacements().contains(atomPlacement.LEFT)) {
                    if (goingTo == direction.EAST) {
                        createLine();
                        System.out.println("hit");
                        return false;
                    }
                    else if (goingTo == direction.N_EAST) {
                        createLine();
                        goingTo = direction.N_WEST;
                    }
                    else if (goingTo == direction.S_EAST) {
                        createLine();
                        goingTo = direction.S_WEST;
                    }
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.DOWNLEFT)) {
                    if (goingTo == direction.N_EAST) {
                        createLine();
                        System.out.println("hit");
                        return false;
                    }
                    else if (goingTo == direction.EAST) {
                        createLine();
                        goingTo = direction.S_EAST;
                    }
                    else if (goingTo == direction.N_WEST) {
                        createLine();
                        goingTo = direction.WEST;
                    }
                    else{

                    }
                } else if (hextocheck.getAtomPlacements().contains(atomPlacement.DOWNRIGHT)) {
                    if (goingTo == direction.N_WEST) {
                        createLine();
                        System.out.println("hit");
                        return false;
                    }
                    else if (goingTo == direction.WEST) {
                        createLine();
                        goingTo = direction.S_WEST;
                    }
                    else if (goingTo == direction.N_EAST) {
                        createLine();
                        goingTo = direction.EAST;
                    }
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.RIGHT)) {
                    if (goingTo == direction.WEST) {
                        createLine();
                        System.out.println("hit");
                        return false;
                    }
                    else if (goingTo == direction.S_WEST) {
                        createLine();
                        goingTo = direction.S_EAST;
                    }
                    else if (goingTo == direction.N_WEST) {
                        createLine();
                        goingTo = direction.N_EAST;
                    }
                }

            }
            else if (hextocheck.getAtomPlacements().size() >= 2) {
                //Equal Distant apart:
                if(hextocheck.getAtomPlacements().contains(atomPlacement.RIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPLEFT)) {
                    goingTo = direction.N_EAST;
                } else if (hextocheck.getAtomPlacements().contains(atomPlacement.LEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPRIGHT)) {
                    goingTo = direction.N_WEST;
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.UPRIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.DOWNRIGHT)) {
                    goingTo = direction.EAST;
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.DOWNLEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPLEFT)) {
                    goingTo = direction.WEST;
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.DOWNRIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.LEFT)) {
                    goingTo = direction.S_WEST;
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.RIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.DOWNLEFT)) {
                    goingTo = direction.S_EAST;
                }
                //Everything else
                if(hextocheck.getAtomPlacements().contains(atomPlacement.RIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPRIGHT)){
                        if(goingTo == direction.S_WEST){
                            goingTo = direction.EAST;
                        } else if (goingTo==direction.WEST) {
                            goingTo = direction.N_EAST;
                        }
                    } else if (hextocheck.getAtomPlacements().contains(atomPlacement.RIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.DOWNRIGHT)) {
                        if(goingTo == direction.N_WEST){
                            goingTo = direction.EAST;
                        } else if (goingTo==direction.WEST) {
                            goingTo = direction.S_EAST;
                        }
                    }else if (hextocheck.getAtomPlacements().contains(atomPlacement.LEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.DOWNLEFT)){
                        if(goingTo == direction.EAST){
                            goingTo = direction.S_WEST;
                        } else if (goingTo==direction.N_EAST) {
                            goingTo = direction.WEST;
                        }
                    }else if (hextocheck.getAtomPlacements().contains(atomPlacement.LEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPLEFT)){
                        if(goingTo == direction.S_EAST){
                            goingTo = direction.WEST;
                        } else if (goingTo==direction.EAST) {
                            goingTo = direction.N_WEST;
                        }
                    }else if (hextocheck.getAtomPlacements().contains(atomPlacement.UPLEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPRIGHT)){
                        if(goingTo == direction.S_EAST){
                            goingTo = direction.N_EAST;
                        } else if (goingTo==direction.S_WEST) {
                            goingTo = direction.N_WEST;
                        }
                    }else if (hextocheck.getAtomPlacements().contains(atomPlacement.DOWNLEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.DOWNRIGHT)){
                        if(goingTo == direction.N_EAST){
                            goingTo = direction.S_EAST;
                        } else if (goingTo==direction.N_WEST) {
                            goingTo = direction.S_WEST;
                        }
                    }

                    //




            }

        }else{


        }
        return true;
    }
    
}
