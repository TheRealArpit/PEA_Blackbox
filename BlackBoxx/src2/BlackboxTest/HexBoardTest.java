package BlackboxTest;
import Blackbox.Constant.Constants;
import Blackbox.Model.*;

import  static Blackbox.Constant.Constants.*;
import static org.junit.Assert.*;


import Blackbox.View.ViewBlackbox;
import org.junit.Test;

import java.util.List;


public class HexBoardTest {
    /**
     * Test to check if the coordinates are set up properly by the hexboard class
     */

    @Test
    public void createHexagonalBoard() {
        TESTING = true;
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

        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        for (int i = 0; i < hexBoard.getHexList().size(); i++) {
            List<Hexagon> curr = hexBoard.getHexList().get(i);
            for (int j = 0; j < curr.size(); j++) {
                add.append("(" + i + ", " + j + ")\t");
            }
            add.append("\n");
        }
        assertEquals(coor, add.toString());

    }
}