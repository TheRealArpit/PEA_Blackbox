import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class DisplayBoard extends Application {
    private Pane hexBoard;

    public static void main(String[] args) {
            launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        CreateWelcome(primaryStage);
        primaryStage.setFullScreen(true);
    }
    public void CreateBoard(Stage primaryStage) throws Exception{
         hexBoard = new Pane();//pane to hold the board
         hexBoard.setStyle("-fx-background-color: black;"); // Set the background color to black

        int[] rowss = {5, 6, 7, 8, 9, 8, 7, 6, 5}; // number of hexagons in each row

        for(int y = 0; y < 9; y++){//iterate over each row
            ConstantValues.hexList.add((new ArrayList<>()));//make a new arraylist for the hexagons in the current row
            for(int x = 0; x <rowss[y]; x++){//iterate over the hexagons in the current row
                Hexagon hex = new Hexagon();//create a new hexagon
                hex.setPane(hexBoard);//set the pane of the hexagon to the board
                double offsetX = getOffsetX(rowss, y) ;//calculate offset and position of the hexagon
                double positionX = getPosition( x, offsetX) + ConstantValues.BOARD_X_STARTAT ;
                double positionY = y * ConstantValues.SCALING_FACTOR_Y * ConstantValues.HEXAGON_RADIUS + ConstantValues.BOARD_Y_STARTAT;
                hex.setLayoutX(positionX);//set the layout position
                hex.setLayoutY(positionY);
                hex.setPosXY(positionX,positionY);
                hex.createHex(y,x);//create the visual representation fo the hexagon
                hexBoard.getChildren().add(hex);//add it to the game board
                Atom arr = new Atom(hex.centreX, hex.centreY);//create an atom for the hexagon and add it to hte board
                arr.setPane(hexBoard);
                arr.createCentre(hex);
                ConstantValues.hexList.get(y).add(hex);
            }
            /*
            //print coordinates fo hexagons in the current row
            for(int f=0;f<ConstantValues.hexList.size();f++){
                for (int l = 0;l< ConstantValues.hexList.get(f).size(); l++){
                    System.out.print("("+f+","+l+")");
                }
                System.out.println();
            }
           */
        }
        //create a text element for displaying round info
        Text Round1 = new Text("ROUND 1");
        Round1.setUnderline(true);
        Round1.setLayoutX(100);
        Round1.setLayoutY(150);
        Round1.setFont(Font.font("Impact", FontWeight.BOLD, 70));
        Round1.setFill(Color.BLUE);
        hexBoard.getChildren().add(Round1);

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
        hexBoard.getChildren().add(SetterInstructions);

        //button for advancing to next step
        Button NextButton = new Button("Next");
        //Changing style of Button
        NextButton.setStyle("-fx-background-color: linear-gradient(#4ECCFC, #09729A );"
                +  "-fx-background-radius: 10;"
                +  "-fx-background-insets: 0;"
                +  "-fx-text-fill: white;"
                +  "-fx-min-width: 200px;"
                +  "-fx-min-height: 100px;");
        NextButton.setFont(Font.font("Impact", FontWeight.BOLD, 36));
        NextButton.setPrefWidth(100); // Set the width and Height of the button
        NextButton.setPrefHeight(50);
        NextButton.setLayoutX(1200); //positions
        NextButton.setLayoutY(500);
        //button for showing atoms on the board
        Button ShowAtomsButton = new Button("Show Atoms");
        hexBoard.getChildren().add(ShowAtomsButton);
        hexBoard.getChildren().add(NextButton);
        //setting up the primary stage
        primaryStage.setScene(new Scene(hexBoard, Color.RED));
        primaryStage.setFullScreen(true);
        primaryStage.show();
        //event handler for next button
        NextButton.setOnAction(event -> {
            hideAtomsOnBoard();
            initializeHexagonsNearAtom();
        });
        ShowAtomsButton.setOnAction(event -> showAtomsOnBoard());
        // event handler for closing the application with esc key
        primaryStage.addEventHandler(javafx.scene.input.KeyEvent.KEY_RELEASED, (key) -> {
            if (KeyCode.ESCAPE == key.getCode()) {
                primaryStage.close();
            }
        });
    }
    private static double getPosition(int col, double offsetX) {
        double hexagonWidth = ConstantValues.HEXAGON_RADIUS; // Horizontal distance between the centers of two adjacent hexagons
        double basePosition = col * hexagonWidth; // Base x-coordinate for the hexagon in its row
        double position = ConstantValues.SCALING_FACTOR_X * (basePosition + offsetX + ConstantValues.PADDING);// Calculate the final x-coordinate
        return position;
}
    private static double getOffsetX(int[] rows, int row) {
        int maxHexagons = rows.length; //Maximum number of hexagons in a row
        int currentHexagons = rows[row]; //Number of hexagons in the current row
        double hexagonWidth = 2 * ConstantValues.HEXAGON_RADIUS * Math.cos(Math.PI / 3); //Horizontal distance between the centers of two adjacent hexagons
        int difference = maxHexagons - currentHexagons; //Difference between the maximum number of hexagons and the number in the current row
        return difference * hexagonWidth / 2;
    }
    public void CreateWelcome(Stage primaryStage) throws Exception {

        // Creating a Pane for the welcome screen
        Pane welcomeScreen = new Pane();
        //Set color of background to black
        welcomeScreen.setStyle("-fx-background-color: linear-gradient(#000000 , #001B4F);");

        // Creating Welcome text to display on the welcome screen
        Text welcomeText = new Text("Welcome to BlackBox");
        welcomeText.setX(90 + ConstantValues.WELCOMEBOARD_X_STARTAT); // Set the x-coordinate of the text
        welcomeText.setY(160); // Set the y-coordinate of the text
        welcomeText.setUnderline(true);

        //Set the font, font size and colour
        welcomeText.setFont(Font.font("Impact", FontWeight.BOLD, 70));
        welcomeText.setFill(Color.BLUE);


        // Adding the text node to the welcome screen pane
        welcomeScreen.getChildren().add(welcomeText);

        //Creating production text for welcome screen
        Text prodText = new Text("A unique game experience created by APE Productions");
        prodText.setX(90+ConstantValues.WELCOMEBOARD_X_STARTAT);
        prodText.setY(190);
        prodText.setTextAlignment(TextAlignment.CENTER);
        prodText.setFont(Font.font("Impact", FontWeight.LIGHT, 14));
        prodText.setFill((Color.CADETBLUE));

        welcomeScreen.getChildren().add(prodText);

        //Creating Instruction text for welcome screen
        Button instructionText = new Button("""
                Game Instructions:

                Two players, A and B, compete over two rounds.

                In Round 1, A sets up 5/6 atoms while B deduces their positions and their score is calculated.

                In Round 2, roles switch: B sets up the atoms, and A deduces their positions and their score is calculated.

                The player with the highest total score wins.""");

        instructionText.setLayoutX(130+ConstantValues.WELCOMEBOARD_X_STARTAT);
        instructionText.setLayoutY(600);
        instructionText.setFont(Font.font("Impact", FontWeight.BOLD, 12));
        instructionText.setAlignment(Pos.CENTER);
        instructionText.setStyle("-fx-background-color: linear-gradient(#001B4F, #000000 );"
                                 +  "-fx-text-fill: white; "
                                 +  "-fx-background-radius: 20;"
                                 +  "-fx-background-insets: 0;"
                                 +  "-fx-min-width: 200px;"
                                 +  "-fx-min-height: 100px;");

        welcomeScreen.getChildren().add(instructionText);

        // Creating a Start button
        Button nextButton = new Button("Start Game");
        nextButton.setLayoutX(300+ConstantValues.WELCOMEBOARD_X_STARTAT); // Set the x-coordinate of the button
        nextButton.setLayoutY(300); // Set the y-coordinate of the button
        nextButton.setAlignment(Pos.CENTER);

        //Setting button Font
        nextButton.setFont(Font.font("Impact", FontWeight.BOLD, 36));

        //Changing style of Button
        nextButton.setStyle("-fx-background-color: linear-gradient(#4ECCFC, #09729A );"
                            +  "-fx-background-radius: 90;"
                            +  "-fx-background-insets: 0;"
                            +  "-fx-text-fill: white;"
                            +  "-fx-min-width: 200px;"
                            +  "-fx-min-height: 100px;");

        // Adding the button to the welcome screen pane
        welcomeScreen.getChildren().add(nextButton);

        // Creating an Exit button
        Button exitButton = new Button("Exit Game");
        exitButton.setLayoutX(300+ConstantValues.WELCOMEBOARD_X_STARTAT); // Set the x-coordinate of the button
        exitButton.setLayoutY(450); // Set the y-coordinate of the button
        exitButton.setAlignment(Pos.CENTER);

        //Setting button Font
        exitButton.setFont(Font.font("Impact", FontWeight.BOLD, 36));

        //Changing style of Button
        exitButton.setStyle("-fx-background-color: linear-gradient(#4ECCFC, #09729A );"
                +  "-fx-background-radius: 90;"
                +  "-fx-background-insets: 0;"
                +  "-fx-text-fill: white;"
                +  "-fx-min-width: 210px;"
                +  "-fx-min-height: 100px;");

        //Adding the button to the welcome screen pane
        welcomeScreen.getChildren().add(exitButton);

        //Creating the Scene with the welcome screen Pane
        Scene scene = new Scene(welcomeScreen, ConstantValues.LEN_WIDTH, ConstantValues.LEN_WIDTH);

        //Setting the scene to the primary stage
        primaryStage.setScene(scene);

        //Displaying the primary stage
        primaryStage.show();
        nextButton.setOnAction(event -> {
            //Create the new scene
            try {
                CreateBoard(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //Set the new scene on the primary stage
        });
        primaryStage.addEventHandler(javafx.scene.input.KeyEvent.KEY_RELEASED, (key) -> {
            if (KeyCode.ESCAPE == key.getCode()) {
                primaryStage.close();
            }
        });

    }


    public void hideAtomsOnBoard(){ //better way to hide atoms
        for(int f=0;f<ConstantValues.hexList.size();f++){
            for (int l = 0;l< ConstantValues.hexList.get(f).size(); l++){
                Hexagon hexToHide = ConstantValues.hexList.get(f).get(l);
                if(hexToHide.hasAtom){
                 hexToHide.atom.hideAtom();
                }
            }
        }
    }
    public void showAtomsOnBoard(){ //better way to hide atoms
        for(int f=0;f<ConstantValues.hexList.size();f++){
            for (int l = 0;l< ConstantValues.hexList.get(f).size(); l++){
                Hexagon hexToHide = ConstantValues.hexList.get(f).get(l);
                if(hexToHide.hasAtom){
                    hexToHide.atom.showAtom();
                }
            }
        }
    }


    public void initializeHexagonsNearAtom() {
        for(int row = 0; row < ConstantValues.hexList.size(); row++) {
            for (int col = 0; col < ConstantValues.hexList.get(row).size(); col++) {
                Hexagon hexagon = ConstantValues.hexList.get(row).get(col);
                if (hexagon.hasAtom) {
                    int newRow;
                    int newCol;

                    //RightHexagon
                    newRow = row;
                    newCol = col + 1;
                    if(isHexThere(newRow,newCol) && !ConstantValues.hexList.get(newRow).get(newCol).hasAtom){
                        Hexagon hexRight = ConstantValues.hexList.get(row).get(col + 1);
                        hexRight.setFill(Color.RED);
                        hexRight.atomPlacement = ConstantValues.atomPlacement.RIGHT;
                        hexRight.hasBorderingAtom = true;
                        hexRight.BorderingAtoms++;
                    }
                    //LeftHexagon
                    newRow = row;
                    newCol = col - 1;
                    if(isHexThere(newRow,newCol) && !ConstantValues.hexList.get(newRow).get(newCol).hasAtom){
                        Hexagon hexLeft = ConstantValues.hexList.get(row).get(col - 1);
                        hexLeft.setFill(Color.PURPLE);
                        hexLeft.atomPlacement = ConstantValues.atomPlacement.LEFT;
                        hexLeft.hasBorderingAtom = true;
                        hexLeft.BorderingAtoms++;;
                    }

                    //bottomRight
                    if(row>=4) {
                        newRow = row+1;
                        newCol = col;
                    }else{
                        newRow = row+1;
                        newCol = col+1;
                    }
                    if(isHexThere(newRow,newCol) && !ConstantValues.hexList.get(newRow).get(newCol).hasAtom){
                        Hexagon hexdownRight = ConstantValues.hexList.get(newRow).get(newCol);
                        hexdownRight.setFill(Color.PURPLE);
                        hexdownRight.atomPlacement = ConstantValues.atomPlacement.DOWNRIGHT;
                        hexdownRight.hasBorderingAtom = true;
                        hexdownRight.BorderingAtoms++;
                    }
                    //bottomLeft
                    if(row>=4) {
                        newRow = row+1;
                        newCol = col-1;
                    }else{
                        newRow = row+1;
                        newCol = col;
                    }
                    if(isHexThere(newRow,newCol) && !ConstantValues.hexList.get(newRow).get(newCol).hasAtom){
                        Hexagon hexDownLeft = ConstantValues.hexList.get(newRow).get(newCol);
                        hexDownLeft.setFill(Color.PURPLE);
                        hexDownLeft.atomPlacement = ConstantValues.atomPlacement.DOWNLEFT;
                        hexDownLeft.hasBorderingAtom = true;
                        hexDownLeft.BorderingAtoms++;
                    }
                    //upRight
                    if(row <= 4) {
                        newRow= row -1;
                        newCol = col;
                    }else{
                        newRow = row -1;
                        newCol = col +1;
                    }
                    if(isHexThere(newRow,newCol) && !ConstantValues.hexList.get(newRow).get(newCol).hasAtom){
                        Hexagon hexUpRight = ConstantValues.hexList.get(newRow).get(newCol);
                        hexUpRight.setFill(Color.PURPLE);
                        hexUpRight.atomPlacement = ConstantValues.atomPlacement.UPRIGHT;
                        hexUpRight.hasBorderingAtom = true;
                        hexUpRight.BorderingAtoms++;
                    }

                    //upLeft
                    if(row<=4) {
                        newRow = row - 1;
                        newCol = col - 1;
                    }else{
                        newRow = row - 1;
                        newCol = col;
                    }
                    if(isHexThere(newRow,newCol) && !ConstantValues.hexList.get(newRow).get(newCol).hasAtom){
                        Hexagon hexUpLeft = ConstantValues.hexList.get(newRow).get(newCol);
                        hexUpLeft.setFill(Color.PURPLE);
                        hexUpLeft.atomPlacement = ConstantValues.atomPlacement.UPLEFT;
                        hexUpLeft.hasBorderingAtom = true;
                        hexUpLeft.BorderingAtoms++;
                    }
                }
            }
        }
    }

    public boolean isHexThere(int newRow, int newCol){
        return newRow >= 0 && newRow < ConstantValues.hexList.size() &&
                newCol >= 0 && newCol < ConstantValues.hexList.get(newRow).size();
    }

    //Elvis make a better initializeHexagonNearAtom() using the atomPlacement enum



}


