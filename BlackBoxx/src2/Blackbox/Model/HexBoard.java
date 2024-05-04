package Blackbox.Model;

import static Blackbox.Constant.Constants.*;

import Blackbox.View.ViewBlackbox;
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
    private ViewBlackbox viewBlackbox;
    private Player player;
    private Pane parantPane;

    //ArrayLists of the classes on the hexagonal board
    //hexagons, arrows, atoms, guessed atoms, rays.
    // all these classes have a reference to the hexboard
    private ArrayList<String> history;
    private ArrayList<ArrayList<Hexagon>> hexList;
    private ArrayList<Arrow> arrowList;
    private ArrayList<Atom> atomsOnBoard;
    private ArrayList<Ray> raysOnBoard;
    private ArrayList<Atom> guessedAtomlist;

    private Boolean finishedRound = false;
    //Buttons, Texts and others that are changed frequently during round changes or updated
    private Button ToggleR1R2;
    private VBox numberStack = new VBox(5);
    private Text Round;
    private Text welcomeText;
    private Button instructionText;

    public HexBoard(){ //constructor
        history = new ArrayList<String>();
        hexList = new ArrayList<ArrayList<Hexagon>>();
        atomsOnBoard = new ArrayList<Atom>();
        raysOnBoard = new ArrayList<Ray>();
        arrowList = new ArrayList<Arrow>();
        guessedAtomlist = new ArrayList<Atom>();
    }

    //---Hexagonal Board Methods
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
        if(!TESTING){
            setArrowTouchoff();
        }
    }
    private static double getPosition(int col, double offsetX) {
        double basePosition = col * HEXAGON_RADIUS; // Base x-coordinate for the hexagon in its row
        return SCALING_FACTOR_X * (basePosition + offsetX + PADDING);// Calculate the final x-coordinate
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
                        //hexUpRight.getHexagon().setFill(Color.PURfPLE);
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
    //--- End of Hexagonal Board Methods
    //UI interaction setup
    public void createText(){
        Round = new Text("Round 1");
        Round.setUnderline(true);
        Round.setLayoutX(100);
        Round.setLayoutY(150);
        Round.setFont(Font.font("Impact", FontWeight.BOLD, 70));
        Round.setFill(Color.BLUE);
        parantPane.getChildren().add(Round);

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
        //Button ShowAtomsButton = new Button("Show Atoms");
        //parantPane.getChildren().add(ShowAtomsButton);
        parantPane.getChildren().add(ToggleR1R2);

//        ShowAtomsButton.setOnAction(event -> showAtomsOnBoard());
    }
    private void buttonLogic() {
        Button actionButton = getToggleR1R2(); // Assuming this is the button managing transitions
        actionButton.setOnAction(event -> {
            String action = actionButton.getText();
            switch (action) {
                case "Next":
                    setArrowTouchOnn();
                    setHexagonTouchOff();
                    setRound2();
                    break;
                case "Finish":
                    setFinishedRound();
                    setHexagonTouchOnn();
                    setArrowTouchoff();
                    setGuessingRound();
                    break;
                case "Confirm":
                    setArrowTouchoff();
                    setHexagonTouchOff();
                    checkGuessedAtoms();
                    break;
                case "Score":
                    displayScore();
                    break;
                case "NextRound":
                    viewBlackbox.Player2Turn(parantPane);
                default:
            }
        });
    }
    public void displayScore(){
       // player.setNumOfWrongGuesses(player.getNumOfGuesses() - player.getNumofCorrectGuesses());
        Round.setText("History");
        welcomeText.setText("Score");

        ToggleR1R2.setText("NextRound");
        instructionText.setFont(Font.font("Impact", FontWeight.BOLD, 15));
        instructionText.setText("Number of Ray:\t"+getRayList().size() +
                "\n\n Number of correct guesses:\t"+ player.getNumofCorrectGuesses() +
                "\n\n Number of wrong Guesses:\t" + player.getNumofWrongGuesses()+
                "\n\n Number of Atoms:\t" + atomsOnBoard.size() +
                "\n\n Number of Markers:\t" + player.getNumofMarkers() +
                "\n\n Total Score:\t" + player.calculateScore()
        );
        displayRayPaths();
    }
    public void displayRayPaths(){  //For the full game view
        for(Ray ray: raysOnBoard){
            for(Line line: ray.getRayPathList()){
                if(ray.getRayType() == RayType.HIT)
                    line.setStroke(Color.GREEN);
                if(ray.getRayType() == RayType.REFLECTED)
                    line.setStroke(Color.WHITE);
                if(ray.getRayType() == RayType.NO_ATOM)
                    line.setStroke(Color.RED);
            }
        }
    }

    public void setRound2(){
        Round.setText("Round 2");
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
    public void checkGuessedAtoms() {
        if(!TESTING)
            getToggleR1R2().setText("Score");
        for(Atom guessedAtom: guessedAtomlist){
            if(checkGuessedAtomAt(guessedAtom.getRowList(),guessedAtom.getColList())){
               // System.out.println("True for"+ guessedAtom.getRowList() + "," +guessedAtom.getColList());
                if(!TESTING){
                    guessedAtom.getHexOfAtom().getHexagon().setFill(Color.GREEN);
                }
                player.addNumofCorrectGuesses();
            }
            else{
                if(!TESTING){
                    guessedAtom.getHexOfAtom().getHexagon().setFill(Color.RED);
                }
            }
        }
        player.setNumOfWrongGuesses(player.getNumOfGuesses() - player.getNumofCorrectGuesses());
        if(!TESTING){
            showAtomsOnBoard();
        }

    }
    public boolean checkGuessedAtomAt(int row, int col){
        if(!isHexThere(row,col)){
            throw  new IndexOutOfBoundsException("Row or col not in the coordinates");
        }
        return getHexagon(row,col).hasAtom();
    }

    public void setFinishedRound(){         //used for the hexagon class knows whether to put an atom or a guessed atom
        for(ArrayList<Hexagon> hexRow: hexList){
            for(Hexagon hex: hexRow){
                hex.setFinishedRound(true);
            }
        }
    }
    public void setGuessingRound(){
        Round.setText("Guess Atoms");
        ToggleR1R2.setText("Confirm");
        welcomeText.setText("Guess");
        instructionText.setText("""
                    Experimentor Instructions:

                    Click the hexagons where you believe the Atoms Are

                    Delete Guesses by Clicking on the same hexagon again

                    Click Confirm Guesses when you are sure.
                    
                    you have found all the atoms.
                    """);
        hideAtomsOnBoard();
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

    public void setHexagonTouchOff() {
        for (ArrayList<Hexagon> row : hexList) {
            for(Hexagon hex: row){
                hex.getHexagon().setMouseTransparent(true);
            }
        }
    }
    public void setHexagonTouchOnn() {
        for (ArrayList<Hexagon> row : hexList) {
            for(Hexagon hex: row){
                hex.getHexagon().setMouseTransparent(false);
            }
        }
    }
    //testing
    public void createAtomAthexagon(int x, int y){
        Atom atom = new Atom(this,0,0,finishedRound);
        getHexagon(x,y).setAtom(atom);
    }
    public void deleteAtomAthexagon(int x, int y){
        getHexagon(x,y).unsetAtom();
    }
    public String sendRayat(int rayNum){
        Arrow arrowClicked = getArrowClickedfromint(rayNum);
        Ray ray = new Ray(this,arrowClicked);
        return ray.createRay(arrowClicked);
    }

    public void setGuessAtomAt(int row, int col){
        Atom guessedAtom = new Atom(row,col,getHexagon(row,col));
        getHexagon(row,col).createGuessAtom(row,col, guessedAtom);
        getHexagon(row,col).setHasGuessedAtom(true);
        getPlayer().addNumOfGuesses();
    }
    public Boolean CheckGuessAtomAt(int row, int col){
        return getHexagon(row,col).hasGuessedAtom();
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


    public void setViewBlackbox(ViewBlackbox v){  // set from View
        viewBlackbox = v;                        // Needed to handle the progression of the game. from p1 to p2 then 3end
    }

    //------- Getters
    public Pane getParantPane(){return parantPane;}
    public ArrayList<ArrayList<Hexagon>> gethexList(){return hexList;}
    public Hexagon getHexagon(int x, int y){
        return hexList.get(x).get(y);
    }
    public ArrayList<Atom> getGuessedAtomlist() { //for a lot of classes and methods need the atomlist
        return guessedAtomlist;
    }
    public ArrayList<Atom> getAtomList(){return atomsOnBoard;}
    public ArrayList<Ray> getRayList(){  //same
        return raysOnBoard;
    }
    public Button getToggleR1R2() {   //for button logic
        return ToggleR1R2;
    }
    private Arrow getArrowClickedfromint(int rayNum) { //needed for the rayLogic in testing
        for(Arrow arrr: arrowList){
            if(arrr.getIdArrow() == rayNum){
                return arrr;
            }
        }
        throw new NullPointerException("Arrow Id not in hexboard   (1-54)");
    }
    public ArrayList<ArrayList<Hexagon>> getHexList() {
        return hexList;
    }
    public ArrayList<String> getHistory() {
        return history;
    }
    public Player getPlayer() {
        return player;
    }

    public void setHexboardPane(Pane display){
        parantPane = display;
        parantPane.setStyle("-fx-background-color: black;"); // Set the background color to black
        parantPane.getChildren().add(numberStack);
        if(!TESTING){
            createText();
            buttonLogic();
        }
    }

    //Number stack
    public void updateNumberStack(Node oo){
        numberStack.getChildren().add(oo);
    }
    public void checkNumberStack() {
        if (numberStack.getChildren().size() > 10) {
            numberStack.getChildren().clear();
        }
    }
    public void setNumberStackXY(int x, int y) {
        numberStack.setLayoutX(x);
        numberStack.setLayoutY(y);
    }
    public void setPlayer(Player playar){
        this.player = playar;
    }
    public ViewBlackbox getViewBlackbox(){
        return viewBlackbox;
    }

}
