package BlackboxTest;

import Blackbox.Model.HexBoard;
import Blackbox.Model.Player;
import org.junit.Test;

import static Blackbox.Constant.Constants.TESTING;
import static org.junit.Assert.assertEquals;

public class RayTest {

    @Test
    public void SixtyDegreeReflection() {
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        Player player1 = new Player();
        hexBoard.setPlayer(player1);
        hexBoard.createAtomAthexagon(4,4);
        hexBoard.initializeHexagonsNearAtom();

        //testing all the posibilites ray hits the middle atom on the board
        assertEquals("17->3",hexBoard.sendRayat(17));
        assertEquals("19->Hit",hexBoard.sendRayat(19));        //19 is direct hit. Hits the hexagon on the circle of influence. buttom left of 4,4
        assertEquals("21->35",hexBoard.sendRayat(21));

        assertEquals("28->Hit",hexBoard.sendRayat(28));
        assertEquals("26->12",hexBoard.sendRayat(26));
        assertEquals("30->44",hexBoard.sendRayat(30));

        assertEquals("48->8",hexBoard.sendRayat(48));
        assertEquals("46->Hit",hexBoard.sendRayat(46));
        assertEquals("44->30",hexBoard.sendRayat(44));

        assertEquals("3->17",hexBoard.sendRayat(3));
        assertEquals("1->Hit",hexBoard.sendRayat(1));
        assertEquals("53->39",hexBoard.sendRayat(53));

        assertEquals("39->53",hexBoard.sendRayat(39));
        assertEquals("37->Hit",hexBoard.sendRayat(37));
        assertEquals("35->21",hexBoard.sendRayat(35));

        assertEquals("8->48",hexBoard.sendRayat(8));
        assertEquals("10->Hit",hexBoard.sendRayat(10));
        assertEquals("12->26",hexBoard.sendRayat(12));
    }
    @Test
    public void OneTwentyDegreeReflection() {
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        Player player = new Player();
        hexBoard.setPlayer(player);
        hexBoard.createHexagonalBoard();
        hexBoard.createAtomAthexagon(4, 4);
        hexBoard.createAtomAthexagon(4, 5);
        hexBoard.createAtomAthexagon(4, 3);
        hexBoard.createAtomAthexagon(4, 2);
        hexBoard.initializeHexagonsNearAtom();
        //testing all some possibilities ray hits between two atoms (causing a 120 degree reflection)
        assertEquals("28->21", hexBoard.sendRayat(28));
        assertEquals("26->19", hexBoard.sendRayat(26));
        assertEquals("24->17", hexBoard.sendRayat(24));

        assertEquals("53->46", hexBoard.sendRayat(53));
        assertEquals("1->48", hexBoard.sendRayat(1));
        assertEquals("50->3", hexBoard.sendRayat(50));
    }
    @Test
    public void EquallyDistantReflection() {
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        Player player = new Player();
        hexBoard.setPlayer(player);
        hexBoard.createAtomAthexagon(2, 1);
        hexBoard.createAtomAthexagon(4, 2);
        hexBoard.createAtomAthexagon(6, 1);
        hexBoard.createAtomAthexagon(7, 2);
        hexBoard.createAtomAthexagon(7, 3);
        hexBoard.createAtomAthexagon(6, 5);


        hexBoard.initializeHexagonsNearAtom();

        //testing all the possibilities ray hits equally distant atoms (causing a 180 degree reflection)
        assertEquals("8->8", hexBoard.sendRayat(8));
        assertEquals("39->39", hexBoard.sendRayat(39));

        assertEquals("12->12", hexBoard.sendRayat(12));

        assertEquals("19->19", hexBoard.sendRayat(19));
        assertEquals("46->46", hexBoard.sendRayat(46));

        assertEquals("28->28", hexBoard.sendRayat(28));

    }
    @Test
    public void complexPath() {
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        Player player = new Player();
        hexBoard.setPlayer(player);
        hexBoard.createAtomAthexagon(3, 1);
        hexBoard.createAtomAthexagon(6, 2);
        hexBoard.createAtomAthexagon(6, 5);
        hexBoard.createAtomAthexagon(3, 7);
        hexBoard.initializeHexagonsNearAtom();

        assertEquals("50->49", hexBoard.sendRayat(50));

        HexBoard HexBoard1 = new HexBoard();
        Player player11 = new Player();
        HexBoard1.setPlayer(player11);
        HexBoard1.createHexagonalBoard();
        HexBoard1.createAtomAthexagon(1, 3);
        HexBoard1.createAtomAthexagon(4, 6);
        HexBoard1.createAtomAthexagon(5, 5);
        HexBoard1.initializeHexagonsNearAtom();

        assertEquals("6->10", HexBoard1.sendRayat(6));

        HexBoard HexBoard_ = new HexBoard();
        Player player1 = new Player();
        HexBoard_.setPlayer(player1);
        HexBoard_.createHexagonalBoard();
        HexBoard_.initializeHexagonsNearAtom();

        HexBoard_.createAtomAthexagon(7, 0);
        HexBoard_.createAtomAthexagon(5, 4);
        HexBoard_.createAtomAthexagon(4, 2);
        HexBoard_.createAtomAthexagon(2, 4);
        HexBoard_.initializeHexagonsNearAtom();
        assertEquals("22->53", HexBoard_.sendRayat(22));
    }
    @Test
    public void internalReflection() {
        TESTING = true;
        HexBoard HexBoard = new HexBoard();
        HexBoard.createHexagonalBoard();
        Player player1 = new Player();
        HexBoard.setPlayer(player1);
        HexBoard.createAtomAthexagon(4, 8);
        HexBoard.createAtomAthexagon(7, 5);
        HexBoard.createAtomAthexagon(8, 1);
        HexBoard.initializeHexagonsNearAtom();
        assertEquals("39->39", HexBoard.sendRayat(39));
        assertEquals("29->29", HexBoard.sendRayat(29));
        assertEquals("23->23", HexBoard.sendRayat(23));
    }

    @Test
    public void checkNumofRayMarkers() {
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        Player player1 = new Player();
        hexBoard.setPlayer(player1);

        //initial ray
        hexBoard.sendRayat(1);
        assertEquals(2, player1.getNumofMarkers());
        hexBoard.sendRayat(2);
        assertEquals(4, player1.getNumofMarkers());

        //add atom and check marker count after a direct hit
        hexBoard.createAtomAthexagon(4, 4);
        hexBoard.initializeHexagonsNearAtom();
        hexBoard.sendRayat(10); //direct hit so markers is only 1 added
        assertEquals(5, player1.getNumofMarkers());

        //check for deflection
        hexBoard.sendRayat(53); //deflects
        assertEquals(7, player1.getNumofMarkers());

        //complex path
        hexBoard.createAtomAthexagon(4, 7);
        hexBoard.initializeHexagonsNearAtom();
        hexBoard.sendRayat(53);
        assertEquals(9, player1.getNumofMarkers());

        //ddditional test cases with new atom placements
        hexBoard.createAtomAthexagon(3, 5);
        hexBoard.initializeHexagonsNearAtom();
        hexBoard.sendRayat(20);
        assertEquals(11, player1.getNumofMarkers());

        hexBoard.createAtomAthexagon(5, 3);
        hexBoard.initializeHexagonsNearAtom();
        hexBoard.sendRayat(15); //multiple deflections
        assertEquals(13, player1.getNumofMarkers());



    }

}

