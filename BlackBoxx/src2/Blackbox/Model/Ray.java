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
/**
 * Represents a ray in the game managing its behavior and interactions with other game elements
 */
public class Ray {
    private Pane parentpane;
    private HexBoard hexBoard;
    private ArrayList<ArrayList<Hexagon>> hexList;
    private ArrayList<Line> rayPathList;//track the graphical represenation of the rays path

    private direction goingTo;
    private int rowIndex;
    private int colIndex;

    private double xLox;
    private double yLoc;

    private int idRayEntered;
    private int getIdRayExited;

    private Arrow clickedArrow;
    private Arrow leftArrow = null;

    private RayType rayType = RayType.NO_ATOM;//intial ray

    /**
     * Constructor that initializes a Ray object with a specified HexBoard and Arrow
     * @param hexBoard The HexBoard object associated with this Ray
     * @param clickedArrow The Arrow from which the Ray originates
     */
    public Ray(HexBoard hexBoard, Arrow clickedArrow){
        rayPathList = new ArrayList<Line>();
        this.hexBoard = hexBoard;
        hexList = hexBoard.gethexList();
        if(!TESTING){//starting points
            xLox = clickedArrow.getCentreX();
            yLoc = clickedArrow.getCentreY();
        }
        this.clickedArrow = clickedArrow;
        if(!TESTING){
            parentpane = hexBoard.getParantPane();
        }
        if(!TESTING){ //when clicked on gui it autimatically happens to create ray
            createRay(clickedArrow); // in testing it needs to be called directly
        }//So if this isn't in testing, the test calls it twice which messes up the data in player class
    }
    /**
     * Creates and calculates the path of the ray based on the given arrow
     * Tracks the rayss interaction with hexagons and handles its visual representation
     * @param arrowclicked The arrow from which the ray is being sent
     * @return A string representing the outcome of the ray's path
     */
    public String createRay(Arrow arrowclicked){
        hexBoard.checkNumberStack();
        idRayEntered = arrowclicked.getIdArrow();//set the entry point for the ray
        Hexagon startingHex = clickedArrow.getHex();// starting hexagon for the ray
        boolean continueRay = true;
        rowIndex = clickedArrow.getHex().getRowList();
        colIndex = clickedArrow.getHex().getColList();
        goingTo = arrowclicked.getGoingTo();
        Hexagon currentHex = hexList.get(rowIndex).get(colIndex);
//loop to determine ray path until it leaves the board or hits an atom
        while (continueRay && isThereNextHex()) {
            if (!(continueRay = checks(goingTo))) {
                currentHex = hexList.get(rowIndex).get(colIndex);
                if(!TESTING) {
                    createLine();
                }
                displayEntryExitPoints();
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
    /**
     * Checks if there is a next hexagon in the current direction of the ray
     * @return true if there is a next hexagon, false otherwise
     */
    private boolean isThereNextHex() {
        //checks if the next hexagon exists in the grid
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
    /**
     * Performs checks for the rays interaction with hexagons based on the current direction.
     * Determines the rays behavior when it encounters atoms and how it reflects or stops
     * @param goingto The current direction of the ray
     * @return true if the ray continues, false if the ray hits an atom or edge
     */
    private boolean checks(direction goingto){
        //logic to check the next hexagon based on direction
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
    //check if a rays direction shoul dbe changed based on the placement of atoms around the hexagons
    /**
     * Calculates if a rays direction should be changed based on the placement of atoms around a hexagon
     * @param goingto The direction the ray is going
     * @param hextocheck The hexagon to check for neighbouring atoms
     * @return true if the ray's direction is altered false if it continues
     */
    private boolean InsideAtomReflect(direction goingto, Hexagon hextocheck) {
        //Check placement of atom around the hexagon and refelct if condition meet
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
        return false;//if no conditions are met do not reflect the ray
    }
    //return the opposite direction of the current one
    /**
     * Returns the opposite direction to the current direction of the ray
     * @return The opposite direction
     */
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
    //endpoint of the rays current path based on its direction
    /**
     * Calculates the endpoint of the rays current path based on its direction.
     * Updates the rowIndex and colIndex to the next hexagon the ray will travel to
     */
    private void calculateEndPoint() {
        //adjust rowindex and column index based on the current direction to calculate the next hexagon the ray will travel to
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
    //create a visual line representing the rays travel
    /**
     * Creates a visual line representing the rays path
     * Visually tracks the ray's journey through the hexagons
     */
    private void createLine() {
        if (isThereNextHex()) {
            Line line = new Line(xLox, yLoc, hexList.get(rowIndex).get(colIndex).getCentreX(), hexList.get(rowIndex).get(colIndex).getCentreY());
            line.setStrokeWidth(3);
            line.setStroke(Color.YELLOW);
            line.setStroke(Color.TRANSPARENT);
            rayPathList.add(line);
            parentpane.getChildren().add(line);
            //update the current location to the endpoint of the last drawn line
            xLox= hexList.get(rowIndex).get(colIndex).getCentreX();
            yLoc = hexList.get(rowIndex).get(colIndex).getCentreY();
        }else{
            return;
        }
    }
    //draw last part of the ray when it exits the board or hits an atom
    /**
     * Draws the final part of the ray when it exits the board or hits an atom.
     * Determines the final direction of the ray and draws to the corresponding arrow exit point
     * @param currentHex The current hexagon from which the ray is exiting or where it has stopped
     */
    private void drawLastLine(Hexagon currentHex){
        //determine the final direction of the ray and draw to the corresponding arrow exit point
        direction endArrowDir = opposite();
        for (Arrow a: currentHex.getArrowList()){
            if(a.getDirection() == endArrowDir){
                leftArrow = a;
                getIdRayExited = leftArrow.getIdArrow();
                if (!TESTING){
                    Line line = new Line(xLox, yLoc, leftArrow.getCentreX(), leftArrow.getCentreY());
                    line.setStrokeWidth(3);
                    line.setStroke(Color.TRANSPARENT);
                    rayPathList.add(line);
                    parentpane.getChildren().add(line);
                }
            }
        }
    }
    /**
     * Displays the entry and exit points of the ray.
     */
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
            if(!TESTING){
                clickedArrow.setMarkerColor(Color.GREEN);
            }
            hexBoard.getPlayer().addNumMarkers(1);
            msg += "\tType:" + rayType.toString();
        } else if (rayType == RayType.REFLECTED) {
            msg += "\tLeft at: " + getIdRayExited + "\nType: " + rayType.toString();
            hexBoard.getPlayer().addNumMarkers(2);
            if(!TESTING){
                clickedArrow.setMarkerColor(Color.WHITE);
                leftArrow.setMarkerColor(Color.WHITE);
            }
        }else if (rayType == RayType.NO_ATOM) {
            msg += "\tLeft at: " + getIdRayExited + "\nType: " + rayType.toString();
            hexBoard.getPlayer().addNumMarkers(2);

            if(!TESTING){
                clickedArrow.setMarkerColor(Color.RED);
                leftArrow.setMarkerColor(Color.RED);
            }
        }
        hexBoard.getPlayer().getHistory().add(msg);
        message.setText(msg);

        hexBoard.updateNumberStack(message);


    }
    /**
     * Gets the list of lines representing the rays path
     * @return The list of line objects
     */
    public ArrayList<Line> getRayPathList(){
        return rayPathList;
    }
    /**
     * Gets the type of the ray (HIT, REFLECTED, NO_ATOM).
     * @return The RayType enum value representing the rays type
     */
    public RayType getRayType(){
        return rayType;
    }



}
