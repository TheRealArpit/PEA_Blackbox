package Blackbox.View.Shapes;
import Blackbox.View.HexBoard;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;
import static Blackbox.Constant.Constants.*;


public class Ray {
    private Pane parentpane;
    private HexBoard hexBoard;
    public List<List<Hexagon>> hexList;

    private direction goingTo;
    private int rowIndex;
    private int colIndex;

    private double xLox;
    private double yLoc;

    private int idRayEntered;
    private int getIdRayExited;

    private Arrow enteredArrow;
    private Arrow leftArrow = null;

    private RayType rayType = RayType.NO_ATOM;

    public Ray(direction goingTo, double x, double y, Pane pane, Hexagon initialHex, Arrow initialArrow, HexBoard hexBoard, List<List<Hexagon>> hexList){//constrctor for the ray
        this.hexList = hexList;
        parentpane = pane;
        this.hexBoard = hexBoard;
        this.goingTo = goingTo;
        xLox = x;
        yLoc = y;
        rowIndex = initialHex.getRowList();
        colIndex = initialHex.getColList();
        enteredArrow = initialArrow;
        idRayEntered = initialArrow.getIdArrow();
        createRay();
    }
    public Ray(List<List<Hexagon>> hexLit){
        hexList = hexLit;
    }
    public String sendRay(int arrowId,List<List<Hexagon>> hexList){
        idRayEntered = arrowId;
        Hexagon startingHex = null;
        goingTo = null;
        searchLoop:
        for (List<Hexagon> hexRow : hexList) {
            for (Hexagon hex : hexRow) {
                for (Arrow arrow : hex.getArrowList()) {
                    if (arrow.getIdArrow() == arrowId) {
                        startingHex = hex;
                        goingTo = arrow.getDirection();
                        break searchLoop; // Exit all loops once the starting hexagon is found
                    }
                }
            }
        }

        boolean continueRay = true;
        Hexagon currentHex = hexList.get(rowIndex).get(colIndex);
        colIndex = startingHex.getColList();
        rowIndex = startingHex.getRowList();
        while (continueRay && isThereNextHex()) {
            if (!(continueRay = checks(goingTo))) {
                //System.out.println("Ray absorbed.");
                currentHex = hexList.get(rowIndex).get(colIndex);
                return currentHex.getRowList() + "," + currentHex.getColList()+":Hit";
            } else {
                currentHex = hexList.get(rowIndex).get(colIndex);
                calculateEndPoint(); // Calculate the next hexagon based on the current direction
            }
        }
        direction endArrowDir = opposite();
        int i=0;

        for (Arrow a: currentHex.getArrowList()){
            if(a.getDirection() == endArrowDir){
                leftArrow = a;
                getIdRayExited = leftArrow.getIdArrow();
            }
        }

        //return idRayEntered + "->" + getIdRayExited;
        return currentHex.getRowList() + "," + currentHex.getColList();
    }

    public void displayEntryExitPoints() {
        hexBoard.checkNumberStack();
        hexBoard.setNumberStackXY(10,170);
        Text message = new Text();
        message.setFill(Color.WHITE);
        message.setFont(Font.font(18));
        if(leftArrow == null){
            String msg = "Entered at: "+ enteredArrow.getIdArrow() + "\tType:" + rayType.toString();
            hexBoard.getHistory().add(msg);
            message.setText(msg);
            hexBoard.updateNumberStack(message);
        }else{
            String msg = "Entered at: " + enteredArrow.getIdArrow() +"\tLeft at: "+ leftArrow.getIdArrow() + "\nType:  " + rayType.toString();
            hexBoard.getHistory().add(msg);
            message.setText(msg);
            hexBoard.getHistory().add(msg);
            hexBoard.updateNumberStack(message);
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

    public void createRay()
    {
        boolean continueRay = true;
        Hexagon currentHex = hexList.get(rowIndex).get(colIndex);

        while (continueRay && isThereNextHex()) {
            if (!(continueRay = checks(goingTo))) {
                //System.out.println("Ray absorbed.");
                createLine();
                displayEntryExitPoints();
                return;
            } else {
                currentHex = hexList.get(rowIndex).get(colIndex);
                createLine(); // Draw the line to the current hexagon
                calculateEndPoint(); // Calculate the next hexagon based on the current direction
            }
        }
        drawFinalLine(currentHex);
        displayEntryExitPoints();
}

private void setExitpoints(){

}
    private void drawFinalLine(Hexagon currentHex) {
        direction endArrowDir = opposite();
        int i=0;
        for (Arrow a: currentHex.getArrowList()){
            if(a.getDirection() == endArrowDir){
                leftArrow = a;
                //System.out.println("End arrow Dir is" + endArrowDir.toString());
                Line line = new Line(xLox, yLoc, currentHex.getArrowList().get(i).getCentreX(), currentHex.getArrowList().get(i).getCentreY());
                line.setStrokeWidth(1);
                line.setStroke(Color.YELLOW);
                parentpane.getChildren().add(line);
            }else{
                i++;
            }
        }


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
                    rayType = RayType.TOTAL_REFLECTION;
                }

                else if (hextocheck.getAtomPlacements().contains(atomPlacement.UPRIGHT)) {
                    if (goingTo == direction.S_WEST) {
                        //createLine();
                        rayType = RayType.HIT;
                        return false;
                    } else if (goingTo == direction.S_EAST) {
                        //createLine();
                        rayType = RayType.REFLECTED;
                        goingTo = direction.EAST;
                    } else if (goingTo == direction.WEST) {
                        //createLine();
                        rayType = RayType.REFLECTED;
                        goingTo = direction.N_WEST;
                    }
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.UPLEFT)) {
                    if (goingTo == direction.S_EAST) {
                        //createLine();
                        rayType = RayType.HIT;
                        return false;
                    }
                    else if (goingTo == direction.EAST) {
                        //createLine();
                        goingTo = direction.N_EAST;
                        rayType = RayType.REFLECTED;
                    }
                    else if (goingTo == direction.S_WEST) {
                        //createLine();
                        goingTo = direction.WEST;
                        rayType = RayType.REFLECTED;

                    }
                } else if (hextocheck.getAtomPlacements().contains(atomPlacement.LEFT)) {
                    if (goingTo == direction.EAST) {
                        //createLine();
                        rayType = RayType.HIT;
                        return false;
                    }
                    else if (goingTo == direction.N_EAST) {
                        //createLine();
                        goingTo = direction.N_WEST;
                        rayType = RayType.REFLECTED;

                    }
                    else if (goingTo == direction.S_EAST) {
                        //createLine();
                        goingTo = direction.S_WEST;
                        rayType = RayType.REFLECTED;

                    }
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.DOWNLEFT)) {
                    if (goingTo == direction.N_EAST) {
                        //createLine();
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
                    else{

                    }
                } else if (hextocheck.getAtomPlacements().contains(atomPlacement.DOWNRIGHT)) {
                    if (goingTo == direction.N_WEST) {
                        //createLine();
                        rayType = RayType.HIT;
                        return false;
                    }
                    else if (goingTo == direction.WEST) {
                        //createLine();
                        goingTo = direction.S_WEST;
                        rayType = RayType.REFLECTED;

                    }
                    else if (goingTo == direction.N_EAST) {
                        //createLine();
                        goingTo = direction.EAST;
                        rayType = RayType.REFLECTED;

                    }
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.RIGHT)) {
                    if (goingTo == direction.WEST) {
                        //createLine();
                        rayType = RayType.HIT;
                        return false;
                    }
                    else if (goingTo == direction.S_WEST) {
                        //createLine();
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
                    rayType = RayType.TOTAL_REFLECTION;
                } else if (hextocheck.getAtomPlacements().contains(atomPlacement.LEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPRIGHT)) {
                    goingTo = direction.N_WEST;
                    rayType = RayType.TOTAL_REFLECTION;
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.UPRIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.DOWNRIGHT)) {
                    goingTo = direction.EAST;
                    rayType = RayType.TOTAL_REFLECTION;
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.DOWNLEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPLEFT)) {
                    goingTo = direction.WEST;
                    rayType = RayType.TOTAL_REFLECTION;
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.DOWNRIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.LEFT)) {
                    goingTo = direction.S_WEST;
                    rayType = RayType.TOTAL_REFLECTION;
                }else if (hextocheck.getAtomPlacements().contains(atomPlacement.RIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.DOWNLEFT)) {
                    goingTo = direction.S_EAST;
                    rayType = RayType.TOTAL_REFLECTION;
                }
                rayType = RayType.TOTAL_REFLECTION;
                //Everything else
                if(hextocheck.getAtomPlacements().contains(atomPlacement.RIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPRIGHT)){
                        if(goingTo == direction.S_WEST){
                            goingTo = direction.EAST;
                        } else if (goingTo==direction.WEST) {
                            goingTo = direction.N_EAST;
                        }
                        rayType = RayType.REFLECTED;
                    if(hextocheck.getBorderingAtoms() >2){
                        rayType = RayType.TOTAL_REFLECTION;
                    }
                } else if (hextocheck.getAtomPlacements().contains(atomPlacement.RIGHT) && hextocheck.getAtomPlacements().contains(atomPlacement.DOWNRIGHT)) {
                        if(goingTo == direction.N_WEST){
                            goingTo = direction.EAST;
                        } else if (goingTo==direction.WEST) {
                            goingTo = direction.S_EAST;
                        }
                        rayType = RayType.REFLECTED;
                    if(hextocheck.getBorderingAtoms() >2){
                        rayType = RayType.TOTAL_REFLECTION;
                    }
                    }else if (hextocheck.getAtomPlacements().contains(atomPlacement.LEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.DOWNLEFT)){
                        if(goingTo == direction.EAST){
                            goingTo = direction.S_WEST;
                        } else if (goingTo==direction.N_EAST) {
                            goingTo = direction.WEST;
                        }
                        rayType = RayType.REFLECTED;
                    if(hextocheck.getBorderingAtoms() >2){
                        rayType = RayType.TOTAL_REFLECTION;
                    }
                    }else if (hextocheck.getAtomPlacements().contains(atomPlacement.LEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPLEFT)){
                        if(goingTo == direction.S_EAST){
                            goingTo = direction.WEST;
                        } else if (goingTo==direction.EAST) {
                            goingTo = direction.N_WEST;
                        }
                    if(hextocheck.getBorderingAtoms() >2){
                        rayType = RayType.TOTAL_REFLECTION;
                    }
                    }else if (hextocheck.getAtomPlacements().contains(atomPlacement.UPLEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.UPRIGHT)){
                        if(goingTo == direction.S_EAST){
                            goingTo = direction.N_EAST;
                        } else if (goingTo==direction.S_WEST) {
                            goingTo = direction.N_WEST;
                        }
                        rayType = RayType.REFLECTED;
                    if(hextocheck.getBorderingAtoms() >2){
                        rayType = RayType.TOTAL_REFLECTION;
                    }
                    }else if (hextocheck.getAtomPlacements().contains(atomPlacement.DOWNLEFT) && hextocheck.getAtomPlacements().contains(atomPlacement.DOWNRIGHT)){
                        if(goingTo == direction.N_EAST){
                            goingTo = direction.S_EAST;
                        } else if (goingTo==direction.N_WEST) {
                            goingTo = direction.S_WEST;
                        }
                        rayType = RayType.REFLECTED;
                    if(hextocheck.getBorderingAtoms() >2){
                        rayType = RayType.TOTAL_REFLECTION;
                    }
                    }else{
                }
                    //
            }
            else{
            }

        }else{


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
        //System.out.println("passed checks");

        return false;
    }

    private void createLine(Color color) {
        if (isThereNextHex()) {
            Line line = new Line(xLox, yLoc, hexList.get(rowIndex).get(colIndex).getCentreX(), hexList.get(rowIndex).get(colIndex).getCentreY());
            line.setStrokeWidth(5);
            line.setStroke(color);
            parentpane.getChildren().add(line);
            xLox= hexList.get(rowIndex).get(colIndex).getCentreX();
            yLoc = hexList.get(rowIndex).get(colIndex).getCentreY();
        }else{
            return;
        }
    }
    public void setHexList(List<List<Hexagon>> hexL){hexList = hexL;}
    
}
