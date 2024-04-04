package Blackbox.View;

//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;

import  static Blackbox.Constant.Constants.*;
import static org.junit.Assert.assertEquals;

import Blackbox.View.Shapes.Arrow;
import Blackbox.View.Shapes.Hexagon;
import javafx.scene.layout.Pane;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class HexBoardTest {

    @Test
    public void createHexagonalBoard() {
        Pane display = new Pane();
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        for(int i=0; i<hexList.size(); i++){
            List<Hexagon> curr = hexList.get(i);
            for(int j=0; j<curr.size() ; j++){
                System.out.print("(" + i + ", "+ j+ ")\t");

            }
            System.out.println("");
        }

    }

    @Test
    public void atomsOnBoard() {
        Pane display = new Pane();
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        hexBoard.createAtomAthexagon(0,0);
        hexBoard.createAtomAthexagon(3,4);
        hexBoard.createAtomAthexagon(4,8);
        assertEquals(true, hexBoard.getHexagon(0,0).hasAtom());
        assertEquals(true, hexBoard.getHexagon(3,4).hasAtom());
        assertEquals(true, hexBoard.getHexagon(4,8).hasAtom());

    }
    @Test
    public void hideAtomsOnBoard() {
    }

    @Test
    public void showAtomsOnBoard() {
    }

    @Test
    public void initializeHexagonsNearAtom() {
    }
}