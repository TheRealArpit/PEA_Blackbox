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

    /**
     * Represents the hexagonal game board used in Blackbox
     * Manages all the game elements like hexagons, atoms, rays and GUI
     */
    public HexBoard(){ //constructor
        history = new ArrayList<String>();
        hexList = new ArrayList<ArrayList<Hexagon>>();
        atomsOnBoard = new ArrayList<Atom>();
        raysOnBoard = new ArrayList<Ray>();
        arrowList = new ArrayList<Arrow>();
        guessedAtomlist = new ArrayList<Atom>();
    }

    //---Hexagonal Board Methods

    /**
     * Creates the hexagonal layout for the board based on row lengths
     */
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

    /**
     *Calculates the X position for a hexagon within its row based on column and offset
     * @param col Column index of the hexagon
     * @param offsetX The calculated offset for the row
     * @return The X position for the hexagon
     */
    private static double getPosition(int col, double offsetX) {
        double basePosition = col * HEXAGON_RADIUS; // Base x-coordinate for the hexagon in its row
        return SCALING_FACTOR_X * (basePosition + offsetX + PADDING);// Calculate the final x-coordinate
    }

    /**
     * Calculates the initial X offset for hexagons in a row based on the maximum row length
     * @param rows Array of row lengths
     * @param row The current row index
     * @return The offset X for the row
     */
    private static double getOffsetX(int[] rows, int row) {
        int maxHexagons = rows.length; //Maximum number of hexagons in a row
        int currentHexagons = rows[row]; //Number of hexagons in the current row
        double hexagonWidth = 2 * HEXAGON_RADIUS * Math.cos(Math.PI / 3); //Horizontal distance between the centers of two adjacent hexagons
        int difference = maxHexagons - currentHexagons; //Difference between the maximum number of hexagons and the number in the current row
        return difference * hexagonWidth / 2;
    }

    /**
     * Initializes hexagons near an atom
     */
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

    /**
     * Checks if a hexagon exists at the specified row and column
     * @param newRow The row index
     * @param newCol The column index
     * @return true if the hexagon exists, otherwise false
     */
    public boolean isHexThere(int newRow, int newCol){
        return newRow >= 0 && newRow < hexList.size() &&
                newCol >= 0 && newCol < hexList.get(newRow).size();
    }
    //--- End of Hexagonal Board Methods
    //UI interaction setup
    /**
     * Sets up UI components related to game text like round information and instructions
     */
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
    /**
     * Manages logic for the action button to transition game states
     */
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
    /**
     * Displays the game score and updates UI components to reflect the game state
     */
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
    /**
     * Visualizes ray paths on the board and  coloring them based on their type
     */
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
    /**
     * Sets the game to Round 2 updating UI components and game state appropriately
     */
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
    /**
     * Checks the positions of guessed atoms against actual atom positions
     */
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
    /**
     * Checks if the guessed atom is at the specified row and column
     * @param row The row index
     * @param col The column index
     * @return true if an atom exists at the specified location, otherwise false
     */
    public boolean checkGuessedAtomAt(int row, int col){
        if(!isHexThere(row,col)){
            throw  new IndexOutOfBoundsException("Row or col not in the coordinates");
        }
        return getHexagon(row,col).hasAtom();
    }
    /**
     * Sets the round as finished, updating the state of hexagons
     */
    public void setFinishedRound(){         //used for the hexagon class knows whether to put an atom or a guessed atom
        for(ArrayList<Hexagon> hexRow: hexList){
            for(Hexagon hex: hexRow){
                hex.setFinishedRound(true);
            }
        }
    }
    /**
     * Sets the game to the guessing round updating UI components and instructions
     */
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

    /**
     * Hides all atoms on the board from the player's view
     */
    public void hideAtomsOnBoard(){
        for(Atom x: atomsOnBoard){
            x.hideAtom();
        }
    }
    /**
     * Reveals all atoms on the board to the player
     */
    public void showAtomsOnBoard(){
        for(Atom x: atomsOnBoard){
            x.showAtom();
        }
    }
    /**
     * Disables interactions with hexagons on the board
     */
    public void setHexagonTouchOff() {
        for (ArrayList<Hexagon> row : hexList) {
            for(Hexagon hex: row){
                hex.getHexagon().setMouseTransparent(true);
            }
        }
    }
    /**
     * Enables interactions with hexagons on the board
     */
    public void setHexagonTouchOnn() {
        for (ArrayList<Hexagon> row : hexList) {
            for(Hexagon hex: row){
                hex.getHexagon().setMouseTransparent(false);
            }
        }
    }
    //testing
    /**
     * Creates an atom at the specified hexagon coordinates on the board
     * @param x The x-coordinate (row index) of the hexagon
     * @param y The y-coordinate (column index) of the hexagon
     */
    public void createAtomAthexagon(int x, int y){
        Atom atom = new Atom(this,0,0,finishedRound);
        getHexagon(x,y).setAtom(atom);
    }
    /**
     * Deletes an atom from the specified hexagon coordinates on the board
     * @param x The x-coordinate (row index) of the hexagon
     * @param y The y-coordinate (column index) of the hexagon
     */
    public void deleteAtomAthexagon(int x, int y){
        getHexagon(x,y).unsetAtom();
    }
    /**
     * Sends a ray from the specified arrow on the board
     * @param rayNum The identifier for the arrow from which the ray is sent
     * @return A string describing the result of the ray interaction
     */
    public String sendRayat(int rayNum){
        Arrow arrowClicked = getArrowClickedfromint(rayNum);
        Ray ray = new Ray(this,arrowClicked);
        return ray.createRay(arrowClicked);
    }
    /**
     * Sets a guessed atom at the specified row and column
     * @param row The row index where the guess is made
     * @param col The column index where the guess is made
     */
    public void setGuessAtomAt(int row, int col){
        Atom guessedAtom = new Atom(row,col,getHexagon(row,col));
        getHexagon(row,col).createGuessAtom(row,col, guessedAtom);
        getHexagon(row,col).setHasGuessedAtom(true);
        getPlayer().addNumOfGuesses();
    }
    /**
     * Checks if there is a guessed atom at the specified hexagon
     * @param row The row index to check
     * @param col The column index to check
     * @return true if there is a guessed atom, false otherwise
     */
    public Boolean CheckGuessAtomAt(int row, int col){
        return getHexagon(row,col).hasGuessedAtom();
    }
    /**
     * Disables interaction with all arrows on the board making them non-responsive to mouse events
     */
    private void setArrowTouchoff() {
        for (Arrow arrr : arrowList) {
            arrr.getArrow().setMouseTransparent(true);
        }
    }
    /**
     * Enables interaction with all arrows on the board allowing them to respond to mouse events
     */
    public void setArrowTouchOnn() {
        for (Arrow arrr : arrowList) {
            arrr.getArrow().setMouseTransparent(false);
        }
    }

    /**
     * Sets the ViewBlackbox
     * @param v The ViewBlackbox to set
     */
    public void setViewBlackbox(ViewBlackbox v){  // set from View
        viewBlackbox = v;                        // Needed to handle the progression of the game. from p1 to p2 then 3end
    }

    //------- Getters
    /**
     * Returns the parent pane of the hexboard
     * @return The parent pane
     */
    public Pane getParantPane(){return parantPane;}
    /**
     * Returns a list of all hexagon rows on the board
     * @return List of hexagon rows.
     */
    public ArrayList<ArrayList<Hexagon>> gethexList(){return hexList;}
    /**
     * Returns the hexagon at the specified coordinate
     * @param x Row index of the hexagon
     * @param y Column index of the hexagon
     * @return The hexagon at the specified coordinates
     */
    public Hexagon getHexagon(int x, int y){
        return hexList.get(x).get(y);
    }
    /**
     * Returns the list of guessed atoms on the board
     * @return The list of guessed atoms
     */
    public ArrayList<Atom> getGuessedAtomlist() { //for a lot of classes and methods need the atomlist
        return guessedAtomlist;
    }/**
     * Returns the list of all atoms on the board
     * @return The list of atoms
     */
    public ArrayList<Atom> getAtomList(){return atomsOnBoard;}
    /**
     * Returns the list of rays on the board
     * @return The list of rays
     */
    public ArrayList<Ray> getRayList(){  //same
        return raysOnBoard;
    }
    /**
     * Returns the button used to toggle between rounds
     * @return The toggle button
     */
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
    /**
     * Returns the list of hexagons on the board
     * @return List of hexagons
     */
    public ArrayList<ArrayList<Hexagon>> getHexList() {
        return hexList;
    }
    /**
     * Returns the game history list
     * @return The history list
     */
    public ArrayList<String> getHistory() {
        return history;
    }
    /**
     * Returns the player associated with this board
     * @return The player
     */
    public Player getPlayer() {
        return player;
    }
    /**
     * Sets the display pane for the hexboard and initializes UI components if not testing
     * @param display The pane to set as the display
     */
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
    /**
     * Adds a node to the number stack, managing the stack overflow by ensuring it does not exceed 10 items
     * @param oo The node to add
     */
    public void updateNumberStack(Node oo){
        numberStack.getChildren().add(oo);
    }
    /**
     * Clears the number stack if it contains more than 10 items
     */
    public void checkNumberStack() {
        if (numberStack.getChildren().size() > 10) {
            numberStack.getChildren().clear();
        }
    }
    /**
     * Sets the position of the number stack on the display
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public void setNumberStackXY(int x, int y) {
        numberStack.setLayoutX(x);
        numberStack.setLayoutY(y);
    }
    /**
     * Sets the player for this hexboard
     * @param playar The player to set
     */
    public void setPlayer(Player playar){
        this.player = playar;
    }
    /**
     * Returns the ViewBlackbox of this hexboard
     * @return The ViewBlackbox
     */
    public ViewBlackbox getViewBlackbox(){
        return viewBlackbox;
    }

}
