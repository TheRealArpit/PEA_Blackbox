package Blackbox.Model;
import Blackbox.Model.HexBoard;

import java.util.ArrayList;
/**
 * Class to keep track of the score
 */
public class Player {

    private int Totalscore;

    private int numOfRays;
    private int numOfGuesses;
    private int numofCorrectGuesses;
    private int numofWrongGuesses;
    private int numOfMarkers;
    private ArrayList<String> history;

    public Player() {
        this.numOfRays=0;
        this.numOfMarkers = 0;
        this.numOfRays = 0;
        this.numOfGuesses = 0;
        this.numofCorrectGuesses = 0;
        this.numofWrongGuesses = 0;
        this.history = new ArrayList<String>();
    }

    //--------Getters
    public int getNumOfGuesses() {
        return numOfGuesses;
    }

    public int getNumofCorrectGuesses() {
        return numofCorrectGuesses;
    }

    public int getNumofWrongGuesses() {
        return numofWrongGuesses;
    }
    public int getNumofMarkers() {
        return numOfMarkers;
    }
    public int getScore() {
        return Totalscore;
    }
    public ArrayList<String> getHistory() {
        return history;
    }


    //--------Setters / adders
    public void setNumOfWrongGuesses(int i){
        numofWrongGuesses = i;
    }
    public void addNumOfRays() {
        this.numOfRays++;
    }
    public void addNumMarkers(int i) {
        this.numOfMarkers+= i;
    }
    public void setHistory(ArrayList<String> history){
        this.history = history;
    }
    public void addNumOfGuesses() {
        numOfGuesses++;
    }

    public void addNumofCorrectGuesses() {
        numofCorrectGuesses++;
    }
    public void subNumofCorrectGuesses() {
        numofCorrectGuesses--;
    }
    //calculate score based on game rules
    public int calculateScore() {
        Totalscore += numOfMarkers + 5*numofWrongGuesses;
        return Totalscore;
    }




}
