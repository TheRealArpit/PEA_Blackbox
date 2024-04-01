package Blackbox.View;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import Blackbox.View.Shapes.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static Blackbox.Constant.Constants.*;


public class HexBoard {

    private Pane hexboard;
    //private  List<List<Hexagon>> hexList = new ArrayList<>();
    private static  List<Atom> atomLIst = new ArrayList<>();
    private ArrayList<String> history;
    public  VBox numberStack = new VBox(5); // VBox to stack numbers, with spacing of 5

    public VBox getNumberStack() {
        return numberStack;
    }
    public void setNumberStack(Node o){
        numberStack.getChildren().add(o);
    }



    public HexBoard(Pane display) {
        history = new ArrayList<>();
        hexboard = display;
        hexboard.setStyle("-fx-background-color: black;"); // Set the background color to black
        addNumberStacktoPane();
        createHexagonalBoard();
    }
    public void createHexagonalBoard(){
        int[] rowLength = {5, 6, 7, 8, 9, 8, 7, 6, 5}; // number of hexagons in each row
        for (int row = 0; row < 9; row++) {
            hexList.add(new ArrayList<>()); //each row of is a list
            for(int col = 0; col < rowLength[row]; col++) {
                Hexagon hex = new Hexagon(hexboard, this);
                double offsetX = getOffsetX(rowLength, row) ;//calculate offset and position of the hexagon
                double positionX = getPosition( col, offsetX) + BOARD_X_STARTAT ;
                double positionY = row * SCALING_FACTOR_Y * HEXAGON_RADIUS + BOARD_Y_STARTAT;
                hex.setPosXY(positionX,positionY);

                hex.createHexagon(positionX,positionY, row, col);
                hexboard.getChildren().add(hex.getHexagon()); // Add the Text component of the arrow

                for (Arrow arrow : hex.getArrowList()) { // Assuming getArrowList() returns a list of Arrow objects
                    hexboard.getChildren().add(arrow.getArrow()); // Add the Path component of the arrow
                    hexboard.getChildren().add(arrow.getText()); // Add the Text component of the arrow
                }
                hexList.get(row).add(hex);
            }
        }
    }

    public void createText(){
        Text Round1 = new Text("Round 1");
        Round1.setUnderline(true);
        Round1.setLayoutX(100);
        Round1.setLayoutY(150);
        Round1.setFont(Font.font("Impact", FontWeight.BOLD, 70));
        Round1.setFill(Color.BLUE);
        hexboard.getChildren().add(Round1);

        //Instructions Pane, can move all the boxes at once if needed
        Pane SetterInstructions = new Pane();
        Text welcomeText = new Text("SETTER");
        welcomeText.setUnderline(true);

        welcomeText.setFont(Font.font("Impact", FontWeight.BOLD, 70)); //Set the font, font size and colour
        welcomeText.setFill(Color.BLUE);

        SetterInstructions.getChildren().add(welcomeText);
        //create a button for setter instructions
        Button instructionText = new Button("""
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
        hexboard.getChildren().add(SetterInstructions);

        //button for advancing to next step
        Button ToggleR1R2 = new Button("Next");
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
        hexboard.getChildren().add(ShowAtomsButton);
        hexboard.getChildren().add(ToggleR1R2);
        ToggleR1R2.setOnAction(event -> {
            if (ToggleR1R2.getText() == "Next"){
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

            }else if(ToggleR1R2.getText() == "Finish"){
            }
        });

    ShowAtomsButton.setOnAction(event -> showAtomsOnBoard());
}

    public void hideAtomsOnBoard(){
    for(Atom x: Hexagon.atomList){
        x.hideAtom();
    }
}
    public void showAtomsOnBoard(){
        for(Atom x: Hexagon.atomList){
        x.showAtom();
    }
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

    public void createAtomAthexagon(int x, int y){
        Atom atom = new Atom(5,5,hexboard);
        getHexagon(x,y).setAtom(atom);
        System.out.println( getHexagon(x,y).hasAtom());

    }

    public Hexagon getHexagon(int x, int y){
        Hexagon hex = hexList.get(x).get(y);
        return hex;
    }

    public Pane getHexboard() {return hexboard;}
    public ArrayList<String> getHistory() {return history;}

    public void updateNumberStack(Node oo){
        numberStack.getChildren().add(oo);
    }public void setNumberStackXY(int x, int y) {
        numberStack.setLayoutX(x);
        numberStack.setLayoutY(y);
    }
    public void addNumberStacktoPane(){
        hexboard.getChildren().add(numberStack);
    }
    public void checkNumberStack() {
        // Check if the historyStack has more than 10 children
        if (numberStack.getChildren().size() > 10) {
            // Clear the VBox if it has more than 10 children
            numberStack.getChildren().clear();
        }
    }


}
