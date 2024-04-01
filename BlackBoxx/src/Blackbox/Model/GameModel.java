package Blackbox.Model;

import java.util.ArrayList;

public class GameModel {
    // Define constants for game states
    public static final int PLAYER_ONE = 1;
    public static final int PLAYER_TWO = 2;
    public static final int EMPTY = 0;

    // Define game board dimensions
    private static final int BOARD_SIZE = 8;

    // Define game board and current player
    private int[][] board;
    private int currentPlayer;
    private ArrayList<String> history;

    public GameModel() {
        history = new ArrayList<String>();
        board = new int[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = PLAYER_ONE; // Assuming two players
        initializeBoard();
    }

    private void initializeBoard() {
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }
    public void setHistory(String st){
        history.add(st);
    }
    public ArrayList<String> getHistory(){
        return history;
    }
    // Add methods to update the game state, move pieces, check for win conditions, etc.
}
