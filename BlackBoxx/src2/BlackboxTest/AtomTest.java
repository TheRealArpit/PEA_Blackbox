package BlackboxTest;

import Blackbox.Model.HexBoard;
import Blackbox.Model.Player;
import org.junit.Test;

import static Blackbox.Constant.Constants.TESTING;
import static org.junit.Assert.*;

public class AtomTest {
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
        hexBoard.setGuessAtomAt(8,2);
        assertEquals("(8, 2)",hexBoard.getGuessedAtomlist().get(2).toString());
        hexBoard.setGuessAtomAt(1,3);
        assertEquals("(1, 3)",hexBoard.getGuessedAtomlist().get(3).toString());
        hexBoard.setGuessAtomAt(1,1);
        assertEquals("(1, 1)",hexBoard.getGuessedAtomlist().get(4).toString());
    }

    @Test
    public void checkSpecificGuessAtoms() {
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        Player player1 = new Player();
        hexBoard.setPlayer(player1);
        hexBoard.setFinishedRound();

        hexBoard.setGuessAtomAt(0, 0);
        assertFalse(hexBoard.checkGuessedAtomAt(0, 0));

        hexBoard.createAtomAthexagon(4, 5);
        hexBoard.setGuessAtomAt(4, 5);
        assertTrue(hexBoard.checkGuessedAtomAt(4, 5));

        hexBoard.createAtomAthexagon(8, 4);
        hexBoard.setGuessAtomAt(8, 4);
        assertTrue(hexBoard.checkGuessedAtomAt(8, 4));

        assertFalse(hexBoard.checkGuessedAtomAt(4, 4));


        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            hexBoard.checkGuessedAtomAt(9,5);
        });

        assertEquals("Row or col not in the coordinates",exception.getMessage());
        //checking all atoms uses this but for every atom in the guessedatom list. there is no need to have a seperate test
        hexBoard.createAtomAthexagon(1, 1);
        hexBoard.setGuessAtomAt(1, 1);
        assertTrue(hexBoard.checkGuessedAtomAt(1, 1));

        hexBoard.createAtomAthexagon(2, 2);
        hexBoard.setGuessAtomAt(2, 2);
        assertTrue(hexBoard.checkGuessedAtomAt(2, 2));

        assertFalse(hexBoard.checkGuessedAtomAt(3, 3));
        assertFalse(hexBoard.checkGuessedAtomAt(6, 6));

        exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            hexBoard.checkGuessedAtomAt(-1, -1);
        });
        assertEquals("Row or col not in the coordinates", exception.getMessage());

        exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            hexBoard.checkGuessedAtomAt(10, 10);
        });
        assertEquals("Row or col not in the coordinates", exception.getMessage());
    }

}
