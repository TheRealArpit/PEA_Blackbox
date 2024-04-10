package Blackbox.Model;

import static Blackbox.Constant.Constants.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class Ray {
    private Pane parentpane;
    private HexBoard hexBoard;
    private ArrayList<ArrayList<Hexagon>> hexList;

    private direction goingTo;
    private int rowIndex;
    private int colIndex;

    private double xLox;
    private double yLoc;

    private int idRayEntered;
    private int getIdRayExited;

    private Arrow clickedArrow;
    private Arrow leftArrow = null;

    private RayType rayType = RayType.NO_ATOM;

    public Ray(HexBoard hexBoard, Arrow clickedArrow){
        this.hexBoard = hexBoard;
        hexList = hexBoard.gethexList();
        if(!TESTING){
            xLox = clickedArrow.getCentreX();
            yLoc = clickedArrow.getCentreY();
        }
        this.clickedArrow = clickedArrow;
        if(!TESTING){
            parentpane = hexBoard.getParantPane();
        }
        createRay(clickedArrow);
    }

    public String createRay(Arrow arrowclicked){
        hexBoard.checkNumberStack();
        idRayEntered = arrowclicked.getIdArrow();
        Hexagon startingHex = clickedArrow.getHex();
        boolean continueRay = true;
        rowIndex = clickedArrow.getHex().getRowList();
        colIndex = clickedArrow.getHex().getColList();
        goingTo = arrowclicked.getGoingTo();
        Hexagon currentHex = hexList.get(rowIndex).get(colIndex);

        while (continueRay && isThereNextHex()) {
            if (!(continueRay = checks(goingTo))) {
                currentHex = hexList.get(rowIndex).get(colIndex);
                if(!TESTING) {
                    createLine();
                    displayEntryExitPoints();
                }
                return idRayEntered + "->"+ "Hit";
            } else {

                if(!TESTING) {
                    createLine();
                }
                currentHex = hexList.get(rowIndex).get(colIndex);
                calculateEndPoint(); // Calculate the next hexagon based on the current direction
            }
        }
        drawLastLine(currentHex);
        displayEntryExitPoints();


        return idRayEntered + "->" + getIdRayExited;

    }
    private boolean isThereNextHex() {
        if (rowIndex < 0 || rowIndex >= hexList.size()) {
            //System.out.println("Left the hexagonalboard");
            return false;
        }
        List<Hexagon> row = hexList.get(rowIndex);
        if (colIndex < 0 || colIndex >= row.size()) {
            //System.out.println("Left the hexagonalboard");
            return false;
        }
        return true;
    }
    private boolean checks(direction goingto){
        if(isThereNextHex()){
            Hexagon hextocheck = hexList.get(rowIndex).get(colIndex);
            //Add if statements for the coi reflections
            if (hextocheck.hasAtom()) {
                rayType = RayType.HIT;
                return false;
            }
            if(hextocheck.getAtomPlacements().size()==1) {
                if (InsideAtomReflect(goingto,hextocheck)){
                    rayType = RayType.REFLECTED;
                }
                else if (hextocheck.getAtomPlacements().contains(atomPlacement.UPRIGHT)) {
                    if (goingTo == direction.S_WEST) {
                        rayType = RayType.HIT;
                        return false;
                    } else if (goingTo == direction.S_EAST) {
                        rayType = RayType.REFLECTED;
                        goingTo = direction.EAST;
                    } else if (goingTo == direction.WEST) {
                        rayType = RayType.REFLECTED;
                        goingTo = direction.N_WEST;
                    }
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.UPLEFT)) {
                    if (goingTo == direction.S_EAST) {
                        rayType = RayType.HIT;
                        return false;
                    }
                    else if (goingTo == direction.EAST) {
                        goingTo = direction.N_EAST;
                        rayType = RayType.REFLECTED;
                    }
                    else if (goingTo == direction.S_WEST) {
                        goingTo = direction.WEST;
                        rayType = RayType.REFLECTED;
                    }
                } else if (hextocheck.getAtomPlacements().contains(atomPlacement.LEFT)) {
                    if (goingTo == direction.EAST) {
                        rayType = RayType.HIT;
                        return false;
                    }
                    else if (goingTo == direction.N_EAST) {
                        goingTo = direction.N_WEST;
                        rayType = RayType.REFLECTED;
                    }
                    else if (goingTo == direction.S_EAST) {
                        goingTo = direction.S_WEST;
                        rayType = RayType.REFLECTED;
                    }
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.DOWNLEFT)) {
                    if (goingTo == direction.N_EAST) {
                        rayType = RayType.HIT;
                        return false;
                    }
                    else if (goingTo == direction.EAST) {
                        //createLine();
                        goingTo = direction.S_EAST;
                        rayType = RayType.REFLECTED;
                    }
                    else if (goingTo == direction.N_WEST) {
                        //createLine();
                        goingTo = direction.WEST;
                        rayType = RayType.REFLECTED;
                    }
                } else if (hextocheck.getAtomPlacements().contains(atomPlacement.DOWNRIGHT)) {
                    if (goingTo == direction.N_WEST) {
                        rayType = RayType.HIT;
                        return false;
                    }
                    else if (goingTo == direction.WEST) {
                        goingTo = direction.S_WEST;
                        rayType = RayType.REFLECTED;

                    }
                    else if (goingTo == direction.N_EAST) {
                        goingTo = direction.EAST;
                        rayType = RayType.REFLECTED;

                    }
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.RIGHT)) {
                    if (goingTo == direction.WEST) {
                        rayType = RayType.HIT;
                        return false;
                    }
                    else if (goingTo == direction.S_WEST) {
                        goingTo = direction.S_EAST;
                        rayType = RayType.REFLECTED;
                    }
                    else if (goingTo == direction.N_WEST) {
                        //createLine();
                        goingTo = direction.N_EAST;
                        rayType = RayType.REFLECTED;
                    }
                }
            }
            else if (hextocheck.getAtomPlacements().size() >= 2) {
                //Equal Distant apart:
                if(hextocheck.getAtomPlacements().contains(atomPlacement.RIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPLEFT)) {
                    goingTo = direction.N_EAST;
                    rayType = RayType.REFLECTED;
                } else if (hextocheck.getAtomPlacements().contains(atomPlacement.LEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPRIGHT)) {
                    goingTo = direction.N_WEST;
                    rayType = RayType.REFLECTED;
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.UPRIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.DOWNRIGHT)) {
                    //goingTo = direction.EAST;
                    goingTo = opposite();
                    rayType = RayType.REFLECTED;
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.DOWNLEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPLEFT)) {
                    goingTo = direction.WEST;
                    rayType = RayType.REFLECTED;
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.DOWNRIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.LEFT)) {
                    goingTo = direction.S_WEST;
                    rayType = RayType.REFLECTED;
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.RIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.DOWNLEFT)) {
                    goingTo = direction.S_EAST;
                    rayType = RayType.REFLECTED;
                }
                rayType = RayType.REFLECTED;
                //Everything else
                if(hextocheck.getAtomPlacements().contains(atomPlacement.RIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPRIGHT)){
                    if(goingTo == direction.S_WEST){
                        goingTo = direction.EAST;
                    } else if (goingTo==direction.WEST) {
                        goingTo = direction.N_EAST;
                    }
                    rayType = RayType.REFLECTED;
                    if(hextocheck.getBorderingAtoms() >2){
                        rayType = RayType.REFLECTED;
                    }
                } else if (hextocheck.getAtomPlacements().contains(atomPlacement.RIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.DOWNRIGHT)) {
                    if(goingTo == direction.N_WEST){
                        goingTo = direction.EAST;
                    } else if (goingTo==direction.WEST) {
                        goingTo = direction.S_EAST;
                    }
                    rayType = RayType.REFLECTED;
                    if(hextocheck.getBorderingAtoms() >2){
                        rayType = RayType.REFLECTED;
                    }
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.LEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.DOWNLEFT)){
                    if(goingTo == direction.EAST){
                        goingTo = direction.S_WEST;
                    } else if (goingTo==direction.N_EAST) {
                        goingTo = direction.WEST;
                    }
                    rayType = RayType.REFLECTED;
                    if(hextocheck.getBorderingAtoms() >2){
                        rayType = RayType.REFLECTED;
                    }
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.LEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPLEFT)){
                    if(goingTo == direction.S_EAST){
                        goingTo = direction.WEST;
                    } else if (goingTo==direction.EAST) {
                        goingTo = direction.N_WEST;
                    }
                    if(hextocheck.getBorderingAtoms() >2){
                        rayType = RayType.REFLECTED;
                    }
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.UPLEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPRIGHT)){
                    if(goingTo == direction.S_EAST){
                        goingTo = direction.N_EAST;
                    } else if (goingTo==direction.S_WEST) {
                        goingTo = direction.N_WEST;
                    }
                    rayType = RayType.REFLECTED;
                    if(hextocheck.getBorderingAtoms() >2){
                        rayType = RayType.REFLECTED;
                    }
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.DOWNLEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.DOWNRIGHT)){
                    if(goingTo == direction.N_EAST){
                        goingTo = direction.S_EAST;
                    } else if (goingTo==direction.N_WEST) {
                        goingTo = direction.S_WEST;
                    }
                    rayType = RayType.REFLECTED;
                    if(hextocheck.getBorderingAtoms() >2){
                        rayType = RayType.REFLECTED;
                    }
                }
            }
        }
        return true;
    }
    private boolean InsideAtomReflect(direction goingto, Hexagon hextocheck) {
        //bottom Hexagon
        if (hextocheck.getAtomPlacements().contains(atomPlacement.LEFT)) {
            if(goingto == direction.N_WEST||goingto == direction.S_WEST){
                goingTo = opposite();
                return true;
            }
        } else if (hextocheck.getAtomPlacements().contains(atomPlacement.RIGHT)){
            if(goingto == direction.N_EAST || goingto == direction.S_EAST){
                goingTo = opposite();
                return true;
            }
        }
        else if(hextocheck.getAtomPlacements().contains(atomPlacement.DOWNRIGHT)){        //            \ hexagon
            if(goingto == direction.EAST ||goingto == direction.S_WEST){
                goingTo = opposite();
                return true;
            }
        }else if(hextocheck.getAtomPlacements().contains(atomPlacement.UPLEFT)){
            if(goingto == direction.N_EAST || goingto == direction.WEST){
                goingTo = opposite();
                return true;
            }
        }else if(hextocheck.getAtomPlacements().contains(atomPlacement.UPRIGHT)){
            if(goingto == direction.N_WEST ||goingto == direction.EAST){
                goingTo = opposite();
                return true;
            }
        }else if(hextocheck.getAtomPlacements().contains(atomPlacement.DOWNLEFT)) {
            if (goingto == direction.WEST||goingto == direction.S_EAST) {
                goingTo = opposite();
                return true;
            }
        }
        else{
            System.out.println("fal");
            return false;
        }
        return false;
    }
    private direction opposite() {
        switch (goingTo) {
            case EAST:
                return direction.WEST;
            case WEST:
                return direction.EAST;
            case S_EAST:
                return direction.N_WEST;
            case N_EAST:
                return direction.S_WEST;
            case N_WEST:
                return direction.S_EAST;
            case S_WEST:
                return direction.N_EAST;
            default:
                return null;
        }
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
    private void createLine() {
        if (isThereNextHex()) {
            Line line = new Line(xLox, yLoc, hexList.get(rowIndex).get(colIndex).getCentreX(), hexList.get(rowIndex).get(colIndex).getCentreY());
            line.setStrokeWidth(1);
            line.setStroke(Color.YELLOW);
            parentpane.getChildren().add(line);
            xLox= hexList.get(rowIndex).get(colIndex).getCentreX();
            yLoc = hexList.get(rowIndex).get(colIndex).getCentreY();
        }else{
            return;
        }
    }
    private void drawLastLine(Hexagon currentHex){
        direction endArrowDir = opposite();
        for (Arrow a: currentHex.getArrowList()){
            if(a.getDirection() == endArrowDir){
                leftArrow = a;
                getIdRayExited = leftArrow.getIdArrow();
                if (!TESTING){
                    Line line = new Line(xLox, yLoc, leftArrow.getCentreX(), leftArrow.getCentreY());
                    line.setStrokeWidth(1);
                    line.setStroke(Color.YELLOW);
                    parentpane.getChildren().add(line);
                }
            }
        }
    }
   public void displayEntryExitPoints() {

       Text message = new Text();
       if(!TESTING){
            hexBoard.checkNumberStack();
            hexBoard.setNumberStackXY(10,170);
            message.setFill(Color.WHITE);
            message.setFont(Font.font(18));
            hexBoard.setNumberStackXY(10,170);
            message = new Text();
            message.setFill(Color.WHITE);
            message.setFont(Font.font(18));
        }
       String msg = "Entered at: " + idRayEntered;

       if(rayType == RayType.HIT){
           clickedArrow.setMarkerColor(Color.GREEN);
           msg += "\tType:" + rayType.toString();
       } else if (rayType == RayType.REFLECTED) {
           msg += "\tLeft at: " + getIdRayExited + "\nType: " + rayType.toString();
           if(!TESTING){
               clickedArrow.setMarkerColor(Color.WHITE);
               leftArrow.setMarkerColor(Color.WHITE);
           }

       }else if (rayType == RayType.NO_ATOM) {
           msg += "\tLeft at: " + getIdRayExited + "\nType: " + rayType.toString();
           if(!TESTING){
               clickedArrow.setMarkerColor(Color.RED);
               leftArrow.setMarkerColor(Color.RED);
           }
       }

       message.setText(msg);
       hexBoard.getHistory().add(msg);
       hexBoard.updateNumberStack(message);

   }




}
