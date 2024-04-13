package Blackbox.Model;

import static Blackbox.Constant.Constants.*;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HexBoard {
    public Button getToggleR1R2() {
        return ToggleR1R2;
    }

    private Button ToggleR1R2;

    private Pane parantPane;
    private ArrayList<String> history;

    private ArrayList<ArrayList<Hexagon>> hexList;
    private ArrayList<Arrow> arrowList;
    private ArrayList<Atom> atomsOnBoard;
    private ArrayList<Ray> raysOnBoard;


    public ArrayList<Atom> getGuessedAtomlist() {
        return guessedAtomlist;
    }

    private ArrayList<Atom> guessedAtomlist;
    private Boolean finishedRound = false;

    private VBox numberStack = new VBox(5);

    public HexBoard(){
        history = new ArrayList<String>();
        hexList = new ArrayList<ArrayList<Hexagon>>();
        atomsOnBoard = new ArrayList<Atom>();
        raysOnBoard = new ArrayList<Ray>();
        arrowList = new ArrayList<Arrow>();
        guessedAtomlist = new ArrayList<Atom>();
    }
    public void setHexboardPane(Pane display){
        parantPane = display;
        parantPane.setStyle("-fx-background-color: black;"); // Set the background color to black
        parantPane.getChildren().add(numberStack);
        if(!TESTING){
            createText();
        }
    }

    public void createHexagonalBoard(){
        int[] rowLength = {5, 6, 7, 8, 9, 8, 7, 6, 5}; // number of hexagons in each row
        for (int row = 0; row < 9; row++) {
            hexList.add(new ArrayList<>()); //each row of is a list
            for(int col = 0; col < rowLength[row]; col++) {
                Hexagon hex = new Hexagon(this);
                double offsetX = getOffsetX(rowLength, row) ;//calculate offset and position of the hexagon
                double positionX = getPosition( col, offsetX) + BOARD_X_STARTAT ;
                double positionY = row * SCALING_FACTOR_Y * HEXAGON_RADIUS + BOARD_Y_STARTAT;
                hex.setPosXY(positionX,positionY);
                hex.createHexagon(positionX,positionY, row, col);
                hexList.get(row).add(hex);
                if(!TESTING){
                    parantPane.getChildren().add(hex.getHexagon());
                }
                for(Arrow arr: hex.getArrowList()){
                    arrowList.add(arr);
                    if(!TESTING){
                        parantPane.getChildren().add(arr.getArrow());
                        parantPane.getChildren().add(arr.getMarker());
                    }
                }
            }
        }
        setArrowTouchoff();
    }
    private static double getPosition(int col, double offsetX) {
        double basePosition = col * HEXAGON_RADIUS; // Base x-coordinate for the hexagon in its row
        double position = SCALING_FACTOR_X * (basePosition + offsetX + PADDING);// Calculate the final x-coordinate
        return position;
    }
    private static double getOffsetX(int[] rows, int row) {
        int maxHexagons = rows.length; //Maximum number of hexagons in a row
        int currentHexagons = rows[row]; //Number of hexagons in the current row
        double hexagonWidth = 2 * HEXAGON_RADIUS * Math.cos(Math.PI / 3); //Horizontal distance between the centers of two adjacent hexagons
        int difference = maxHexagons - currentHexagons; //Difference between the maximum number of hexagons and the number in the current row
        return difference * hexagonWidth / 2;
    }

    public void initializeHexagonsNearAtom() {
        for(int row = 0; row < hexList.size(); row++) {
            for (int col = 0; col < hexList.get(row).size(); col++) {
                Hexagon hexagon = hexList.get(row).get(col);
                if (hexagon.hasAtom()) {
                    int newRow;
                    int newCol;
                    //RightHexagon
                    newRow = row;
                    newCol = col + 1;
                    if(isHexThere(newRow,newCol) && !hexList.get(newRow).get(newCol).hasAtom()){
                        Hexagon hexRight = hexList.get(row).get(col + 1);
                        //hexRight.getHexagon().setFill(Color.RED);
                        hexRight.setAtomPlacement(atomPlacement.RIGHT);
                        hexRight.setHasBorderingAtom(true);
                        hexRight.addBorderingAtoms();;
                    }
                    //LeftHexagon
                    newRow = row;
                    newCol = col - 1;
                    if(isHexThere(newRow,newCol) && !hexList.get(newRow).get(newCol).hasAtom()){
                        Hexagon hexLeft = hexList.get(row).get(col - 1);
                        //hexLeft.getHexagon().setFill(Color.PURPLE);
                        hexLeft.setAtomPlacement(atomPlacement.LEFT);
                        hexLeft.setHasBorderingAtom(true);
                        hexLeft.addBorderingAtoms();
                    }

                    //bottomRight
                    if(row>=4) {
                        newRow = row+1;
                        newCol = col;
                    }else{
                        newRow = row+1;
                        newCol = col+1;
                    }
                    if(isHexThere(newRow,newCol) && !hexList.get(newRow).get(newCol).hasAtom()){
                        Hexagon hexdownRight = hexList.get(newRow).get(newCol);
                        // hexdownRight.getHexagon().setFill(Color.PURPLE);
                        hexdownRight.setAtomPlacement(atomPlacement.DOWNRIGHT);
                        hexdownRight.setHasBorderingAtom(true);
                        hexdownRight.addBorderingAtoms();
                    }
                    //bottomLeft
                    if(row>=4) {
                        newRow = row+1;
                        newCol = col-1;
                    }else{
                        newRow = row+1;
                        newCol = col;
                    }
                    if(isHexThere(newRow,newCol) && !hexList.get(newRow).get(newCol).hasAtom()){
                        Hexagon hexDownLeft = hexList.get(newRow).get(newCol);
                        //hexDownLeft.getHexagon().setFill(Color.PURPLE);
                        hexDownLeft.setAtomPlacement(atomPlacement.DOWNLEFT);
                        hexDownLeft.setHasBorderingAtom(true);
                        hexDownLeft.addBorderingAtoms();
                    }
                    //upRight
                    if(row <= 4) {
                        newRow= row -1;
                        newCol = col;
                    }else{
                        newRow = row -1;
                        newCol = col +1;
                    }
                    if(isHexThere(newRow,newCol) && !hexList.get(newRow).get(newCol).hasAtom()){
                        Hexagon hexUpRight = hexList.get(newRow).get(newCol);
                        //hexUpRight.getHexagon().setFill(Color.PURPLE);
                        hexUpRight.setAtomPlacement(atomPlacement.UPRIGHT);
                        hexUpRight.setHasBorderingAtom(true);
                        hexUpRight.addBorderingAtoms();
                    }

                    //upLeft
                    if(row<=4) {
                        newRow = row - 1;
                        newCol = col - 1;
                    }else{
                        newRow = row - 1;
                        newCol = col;
                    }
                    if(isHexThere(newRow,newCol) && !hexList.get(newRow).get(newCol).hasAtom()){
                        Hexagon hexUpLeft = hexList.get(newRow).get(newCol);
                        //hexUpLeft.getHexagon().setFill(Color.PURPLE);
                        hexUpLeft.setAtomPlacement(atomPlacement.UPLEFT);
                        hexUpLeft.setHasBorderingAtom(true);
                        hexUpLeft.addBorderingAtoms();
                    }
                }
            }
        }
    }
    public boolean isHexThere(int newRow, int newCol){
        return newRow >= 0 && newRow < hexList.size() &&
                newCol >= 0 && newCol < hexList.get(newRow).size();
    }
    public ArrayList<ArrayList<Hexagon>> gethexList(){return hexList;}
    public Pane getParantPane(){return parantPane;}
    public ArrayList<Atom> getAtomList(){return atomsOnBoard;}
    private Text Round1;
    private Text welcomeText;
    private Button instructionText;
    public void createText(){
        Round1 = new Text("Round 1");
        Round1.setUnderline(true);
        Round1.setLayoutX(100);
        Round1.setLayoutY(150);
        Round1.setFont(Font.font("Impact", FontWeight.BOLD, 70));
        Round1.setFill(Color.BLUE);
        parantPane.getChildren().add(Round1);

        //Instructions Pane, can move all the boxes at once if needed
        Pane SetterInstructions = new Pane();
        welcomeText = new Text("SETTER");
        welcomeText.setUnderline(true);

        welcomeText.setFont(Font.font("Impact", FontWeight.BOLD, 70)); //Set the font, font size and colour
        welcomeText.setFill(Color.BLUE);

        SetterInstructions.getChildren().add(welcomeText);
        //create a button for setter instructions
         instructionText = new Button("""
                Setter Instructions:
                
                set up atoms by clicking a hexagon
                
                remove atom by clicking the same hexagon again
                
                choose 5 atoms
                
                Click Next when all atoms are chosen
                """);

        instructionText.setLayoutY(15);

        instructionText.setFont(Font.font("Impact", FontWeight.BOLD, 12));
        instructionText.setAlignment(Pos.CENTER);
        instructionText.setStyle("-fx-background-color: linear-gradient(#001B4F, #000000 );"
                +  "-fx-text-fill: white; " +  "-fx-background-radius: 20;"
                +  "-fx-background-insets: 0;" +  "-fx-min-width: 200px;" +  "-fx-min-height: 100px;");
        SetterInstructions.getChildren().add(instructionText);

        SetterInstructions.setLayoutX(1200);
        SetterInstructions.setLayoutY(150);
        parantPane.getChildren().add(SetterInstructions);

        //button for advancing to next step
        ToggleR1R2 = new Button("Next");
        //Changing style of Button
        ToggleR1R2.setStyle("-fx-background-color: linear-gradient(#4ECCFC, #09729A );"
                +  "-fx-background-radius: 10;"
                +  "-fx-background-insets: 0;"
                +  "-fx-text-fill: white;"
                +  "-fx-min-width: 200px;"
                +  "-fx-min-height: 100px;");
        ToggleR1R2.setFont(Font.font("Impact", FontWeight.BOLD, 36));
        ToggleR1R2.setPrefWidth(101); // Set the width and Height of the button
        ToggleR1R2.setPrefHeight(50);
        ToggleR1R2.setLayoutX(1200); //positions
        ToggleR1R2.setLayoutY(500);
        //button for showing atoms on the board
        Button ShowAtomsButton = new Button("Show Atoms");
        parantPane.getChildren().add(ShowAtomsButton);
        parantPane.getChildren().add(ToggleR1R2);
        ToggleR1R2.setOnAction(event -> {
            if (ToggleR1R2.getText() == "Finish"){
                for(String str:  history){
                    System.out.println(str);
                }
                createHexagonalBoard();
            }
        });

        ShowAtomsButton.setOnAction(event -> showAtomsOnBoard());
    }

    public void setRound2(){
        Round1.setText("Round 2");
        ToggleR1R2.setText("Finish");
        welcomeText.setText("EXPERIMENTER");
        instructionText.setText("""
                    Experimentor Instructions:

                    Shoot Rays by clicking the arrows/triangles

                    Deduce Atom positions using the interactions 

                    between the ray and atoms

                    Click Finish you are sure you have found all the atoms.
                    """);
        hideAtomsOnBoard();
        initializeHexagonsNearAtom();
    }
    public void hideAtomsOnBoard(){
        for(Atom x: atomsOnBoard){
            x.hideAtom();
        }
    }
    public void showAtomsOnBoard(){
        for(Atom x: atomsOnBoard){
            x.showAtom();
        }
    }
    public void checkNumberStack() {
        // Check if the historyStack has more than 10 children
        if (numberStack.getChildren().size() > 10) {
            // Clear the VBox if it has more than 10 children
            numberStack.getChildren().clear();
        }
    }
    public ArrayList<String> getHistory() {
        return history;
    }
    public void setNumberStack(Node o){
        numberStack.getChildren().add(o);
    }
    public void updateNumberStack(Node oo){
        numberStack.getChildren().add(oo);
    }
    public void setNumberStackXY(int x, int y) {
        numberStack.setLayoutX(x);
        numberStack.setLayoutY(y);
    }
    public ArrayList<ArrayList<Hexagon>> getHexList() {
        return hexList;
    }
    public void createAtomAthexagon(int x, int y){
        Atom atom = new Atom(this,0,0,finishedRound);
        getHexagon(x,y).setAtom(atom);
    }
    public Hexagon getHexagon(int x, int y){
        Hexagon hex = hexList.get(x).get(y);
        return hex;
    }

    public void deleteAtomAthexagon(int x, int y){
        getHexagon(x,y).unsetAtom();
    }
    public String sendRayat(int rayNum){
        Arrow arrowClicked = getArrowClickedfromint(rayNum);
        Ray ray = new Ray(this,arrowClicked);
        return ray.createRay(arrowClicked);
    }

    private Arrow getArrowClickedfromint(int rayNum) {
    for(Arrow arrr: arrowList){
        if(arrr.getIdArrow() == rayNum){
            return arrr;
        }
    }
    throw new NullPointerException("Arrow Id not in hexboard   (1-54)");
    }
    public void checkAtoms(){
        ToggleR1R2.setText("END");
        for(ArrayList<Hexagon> hexRow: hexList){
            for(Hexagon hex: hexRow){
                hex.finishedRound = true;
            }
        }
    }
    private void setArrowTouchoff() {
        for (Arrow arrr : arrowList) {
            arrr.getArrow().setMouseTransparent(true);
        }
    }
    public void setArrowTouchOnn() {
        for (Arrow arrr : arrowList) {
            arrr.getArrow().setMouseTransparent(false);
        }
    }

    public void CheckGuessedAtoms() {
        for (Atom p1 : guessedAtomlist) {
            for (Atom p2 : atomsOnBoard) {
                if (p1.equals(p2)) {
                    p1.getHexOfAtom().getHexagon().setFill(Color.GREEN);
                    break;
                }else {
                    p1.getHexOfAtom().getHexagon().setFill(Color.RED);
                }
            }
        }
        showAtomsOnBoard();
    }
}
