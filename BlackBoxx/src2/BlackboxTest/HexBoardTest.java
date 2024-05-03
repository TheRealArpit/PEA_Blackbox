package BlackboxTest;
import Blackbox.Constant.Constants;
import Blackbox.Model.*;

import  static Blackbox.Constant.Constants.*;
import static org.junit.Assert.*;


import Blackbox.View.ViewBlackbox;
import org.junit.Test;

import java.util.List;


public class HexBoardTest {

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
    @Test
    public void createatomsOnBoard() {
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        hexBoard.createAtomAthexagon(0,0);
        hexBoard.createAtomAthexagon(3,4);
        hexBoard.createAtomAthexagon(4,8);
        assertTrue(hexBoard.getHexagon(0, 0).hasAtom());
        assertTrue(hexBoard.getHexagon(3, 4).hasAtom());
        assertTrue(hexBoard.getHexagon(4, 8).hasAtom());

        assertTrue(hexBoard.getHexagon(3, 4).hasAtom());
        assertTrue(hexBoard.getHexagon(4, 8).hasAtom());

    }
    @Test
    public void deleteatomsOnBoard(){
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        hexBoard.createAtomAthexagon(0,0);
        hexBoard.deleteAtomAthexagon(0,0);
        assertFalse(hexBoard.getHexagon(0, 0).hasAtom());
        hexBoard.createAtomAthexagon(1,5);
        hexBoard.deleteAtomAthexagon(1,5);
        assertFalse(hexBoard.getHexagon(1, 5).hasAtom());
    }
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
    public void setGuessAtoms() {
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        Player player1 = new Player();
        hexBoard.setPlayer(player1);
        hexBoard.setFinishedRound();
        hexBoard.setGuessAtomAt(0,0);
        assertEquals("(0, 0)",hexBoard.getGuessedAtomlist().get(0).toString());
        hexBoard.setGuessAtomAt(2,5);
        assertEquals("(2, 5)",hexBoard.getGuessedAtomlist().get(1).toString());
    }

    @Test
    public void checkSpecificGuessAtoms() {
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        Player player1 = new Player();
        hexBoard.setPlayer(player1);
        hexBoard.setFinishedRound();

        hexBoard.setGuessAtomAt(0,0);
        assertFalse(hexBoard.checkGuessedAtomAt(0,0));

        hexBoard.createAtomAthexagon(4,5);
        hexBoard.setGuessAtomAt(4,5);
        assertTrue(hexBoard.checkGuessedAtomAt(4,5));

        hexBoard.createAtomAthexagon(8,4);
        hexBoard.setGuessAtomAt(8,4);
        assertTrue(hexBoard.checkGuessedAtomAt(8,4));

        assertFalse(hexBoard.checkGuessedAtomAt(4,4));


        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            hexBoard.checkGuessedAtomAt(9,5);
        });

        assertEquals("Row or col not in the coordinates",exception.getMessage());
        //checking all atoms uses this but for every atom in the guessedatom list. there is no need to have a seperate test
    }


    @Test
    public void checkNumofRayMarkers() {
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        Player player1 = new Player();
        hexBoard.setPlayer(player1);
       // hexBoard.setRound2();
        hexBoard.sendRayat(1);
        assertEquals(2, player1.getNumofMarkers());
        hexBoard.sendRayat(2);
        assertEquals(4, player1.getNumofMarkers());
        hexBoard.createAtomAthexagon(4,4);
        hexBoard.initializeHexagonsNearAtom();
        hexBoard.sendRayat(10); //direct hit so markers is only 1 added
        assertEquals(5, player1.getNumofMarkers());
        hexBoard.sendRayat(53);//deflects
        assertEquals(7,player1.getNumofMarkers());

        //complex path
        hexBoard.createAtomAthexagon(4,7);
        hexBoard.initializeHexagonsNearAtom();
        hexBoard.sendRayat(53);
        assertEquals(9,player1.getNumofMarkers());


    }

    @Test
    public void checkEndingScoreforPlayer() {
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        Player player1 = new Player();
        hexBoard.setPlayer(player1);
        hexBoard.createAtomAthexagon(4,4);
        hexBoard.initializeHexagonsNearAtom();
        hexBoard.sendRayat(19);
        hexBoard.sendRayat(17);
        hexBoard.sendRayat(21);
        hexBoard.setFinishedRound();
        hexBoard.setGuessAtomAt(3,3);
        hexBoard.checkGuessedAtoms();

        //System.out.println(hexBoard.getPlayer().getNumofMarkers() + " " + hexBoard.getPlayer().getNumofCorrectGuesses()+" " + hexBoard.getPlayer().getNumofWrongGuesses());
        //System.out.println(hexBoard.getPlayer().calculateScore());
        assertEquals(10, hexBoard.getPlayer().calculateScore());
    }
    @Test
    public void checkWinner() {
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        Player player1 = new Player();
        hexBoard.setPlayer(player1);
        hexBoard.createAtomAthexagon(4,4);
        hexBoard.initializeHexagonsNearAtom();
        hexBoard.sendRayat(19);
        hexBoard.sendRayat(17);
        hexBoard.sendRayat(21);
        hexBoard.setFinishedRound();
        hexBoard.setGuessAtomAt(3,3);
        hexBoard.checkGuessedAtoms();


        TESTING = true;
        HexBoard hexb = new HexBoard();
        ViewBlackbox view = new ViewBlackbox();
        hexBoard.setViewBlackbox(view);
        hexb.createHexagonalBoard();
        Player player2 = new Player();
        hexb.setPlayer(player2);
        hexb.createAtomAthexagon(4,4);
        hexb.initializeHexagonsNearAtom();
        hexb.sendRayat(19);
        hexb.sendRayat(17);
        hexb.sendRayat(21);
        hexb.sendRayat(23);
        hexb.setFinishedRound();
        hexb.setGuessAtomAt(3,3);
        hexb.checkGuessedAtoms();

        //System.out.println(hexBoard.getPlayer().getNumofMarkers() + " " + hexBoard.getPlayer().getNumofCorrectGuesses()+" " + hexBoard.getPlayer().getNumofWrongGuesses());
        //System.out.println(hexBoard.getPlayer().calculateScore());
        //assertEquals(10, hexb.getPlayer().calculateScore());
        //System.out.println(hexb.getPlayer().calculateScore());
        //System.out.println(hexBoard.getPlayer().calculateScore());
        //System.out.println( hexBoard.getViewBlackbox().getWinner(player1,player2)); //get winner returns string of the winner
        assertEquals("Winner: Player 1", hexBoard.getViewBlackbox().getWinner(player1,player2));

    }

    @Test
    public void checkPlayerHistory() {
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        Player player1 = new Player();
        hexBoard.setPlayer(player1);
        hexBoard.createAtomAthexagon(4,4);
        hexBoard.initializeHexagonsNearAtom();
        hexBoard.sendRayat(19);
        hexBoard.sendRayat(17);
        hexBoard.sendRayat(21);
        hexBoard.setFinishedRound();
        hexBoard.setGuessAtomAt(3,3);
        hexBoard.checkGuessedAtoms();

        //System.out.println(hexBoard.getPlayer().getNumofMarkers() + " " + hexBoard.getPlayer().getNumofCorrectGuesses()+" " + hexBoard.getPlayer().getNumofWrongGuesses());
        //System.out.println(hexBoard.getPlayer().calculateScore());
        assertEquals(10, hexBoard.getPlayer().calculateScore());

    }
}