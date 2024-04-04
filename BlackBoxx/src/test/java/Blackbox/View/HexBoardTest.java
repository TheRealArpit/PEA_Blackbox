package Blackbox.View;

//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;

import  static Blackbox.Constant.Constants.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import Blackbox.View.Shapes.Atom;
import Blackbox.View.Shapes.Hexagon;
import javafx.scene.layout.Pane;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class HexBoardTest {
    @Test
    public void createHexagonalBoard() {
        String coor =
                "(0, 0)\t(0, 1)\t(0, 2)\t(0, 3)\t(0, 4)\t\n" +
                        "(1, 0)\t(1, 1)\t(1, 2)\t(1, 3)\t(1, 4)\t(1, 5)\t\n" +
                        "(2, 0)\t(2, 1)\t(2, 2)\t(2, 3)\t(2, 4)\t(2, 5)\t(2, 6)\t\n" +
                        "(3, 0)\t(3, 1)\t(3, 2)\t(3, 3)\t(3, 4)\t(3, 5)\t(3, 6)\t(3, 7)\t\n" +
                        "(4, 0)\t(4, 1)\t(4, 2)\t(4, 3)\t(4, 4)\t(4, 5)\t(4, 6)\t(4, 7)\t(4, 8)\t\n" +
                        "(5, 0)\t(5, 1)\t(5, 2)\t(5, 3)\t(5, 4)\t(5, 5)\t(5, 6)\t(5, 7)\t\n" +
                        "(6, 0)\t(6, 1)\t(6, 2)\t(6, 3)\t(6, 4)\t(6, 5)\t(6, 6)\t\n" +
                        "(7, 0)\t(7, 1)\t(7, 2)\t(7, 3)\t(7, 4)\t(7, 5)\t\n" +
                        "(8, 0)\t(8, 1)\t(8, 2)\t(8, 3)\t(8, 4)\t\n";
        StringBuilder add = new StringBuilder();

        Pane display = new Pane();
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        for (int i = 0; i < hexList.size(); i++) {
            List<Hexagon> curr = hexList.get(i);
            for (int j = 0; j < curr.size(); j++) {
                add.append("(" + i + ", " + j + ")\t");
            }
            add.append("\n");
            System.out.println();
        }

        assertEquals(coor, add.toString());

    }
    @Test
    public void createatomsOnBoard() {
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
    public void SixtyDegreeReflection() {
        Pane display = new Pane();
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        hexBoard.createAtomAthexagon(4,4);
        hexBoard.initializeHexagonsNearAtom();

        //testing all the posibilites ray hits the middle atom on the board
        assertEquals("1,0",hexBoard.sendRayat(17));
        assertEquals("5,3:Hit",hexBoard.sendRayat(19));        //19 is direct hit. Hits the hexagon on the circle of influence. buttom left of 4,4
        assertEquals("5,7",hexBoard.sendRayat(21));

        assertEquals("5,4:Hit",hexBoard.sendRayat(28));
        assertEquals("5,0",hexBoard.sendRayat(26));
        assertEquals("1,5",hexBoard.sendRayat(30));

        assertEquals("3,0",hexBoard.sendRayat(48));
        assertEquals("3,4:Hit",hexBoard.sendRayat(46));
        assertEquals("7,5",hexBoard.sendRayat(44));

        assertEquals("7,0",hexBoard.sendRayat(3));
        assertEquals("3,3:Hit",hexBoard.sendRayat(1));
        assertEquals("3,7",hexBoard.sendRayat(53));

        assertEquals("0,1",hexBoard.sendRayat(39));
        assertEquals("4,5:Hit",hexBoard.sendRayat(37));
        assertEquals("8,1",hexBoard.sendRayat(35));

        assertEquals("0,3",hexBoard.sendRayat(8));
        assertEquals("4,3:Hit",hexBoard.sendRayat(10));
        assertEquals("8,3",hexBoard.sendRayat(12));
}
    @Test
    public void OneTwentyDegreeReflection() {
        Pane display = new Pane();
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        hexBoard.createAtomAthexagon(4, 4);
        hexBoard.createAtomAthexagon(4, 5);
        hexBoard.createAtomAthexagon(4, 3);
        hexBoard.createAtomAthexagon(4, 2);

        hexBoard.initializeHexagonsNearAtom();

        //testing all the possibilities ray hits between two atoms (causing a 120 degree reflection)
        assertEquals("8,1", hexBoard.sendRayat(28));
        assertEquals("8,0", hexBoard.sendRayat(26));
        assertEquals("7,0", hexBoard.sendRayat(24));


        assertEquals("0,4", hexBoard.sendRayat(53));
        assertEquals("0,3", hexBoard.sendRayat(1));
        assertEquals("1,0", hexBoard.sendRayat(50));

    }
    @Test
    public void EquallyDistantReflection() {
        Pane display = new Pane();
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        hexBoard.createAtomAthexagon(2, 1);
        hexBoard.createAtomAthexagon(4, 2);
        hexBoard.createAtomAthexagon(6, 1);
        hexBoard.createAtomAthexagon(7, 2);
        hexBoard.createAtomAthexagon(7, 3);
        hexBoard.createAtomAthexagon(6, 5);


        hexBoard.initializeHexagonsNearAtom();

        //testing all the possibilities ray hits equally distant atoms (causing a 180 degree reflection)
        assertEquals("3,0", hexBoard.sendRayat(8));
        assertEquals("3,7", hexBoard.sendRayat(39));

        assertEquals("5,0", hexBoard.sendRayat(12));

        assertEquals("8,0", hexBoard.sendRayat(19));
        assertEquals("0,4", hexBoard.sendRayat(46));

        assertEquals("8,4", hexBoard.sendRayat(28));

    }


}

