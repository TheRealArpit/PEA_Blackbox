package BlackboxTest;

import Blackbox.Model.HexBoard;
import Blackbox.Model.Player;
import Blackbox.View.ViewBlackbox;
import org.junit.Test;

import static Blackbox.Constant.Constants.TESTING;
import static org.junit.Assert.assertEquals;

public class EndGameTest {
    @Test
    public void checkEndingScoreforPlayer() {
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        Player player1 = new Player();
        hexBoard.setPlayer(player1);
        hexBoard.createAtomAthexagon(4, 4);
        hexBoard.initializeHexagonsNearAtom();
        hexBoard.sendRayat(19);
        hexBoard.sendRayat(17);
        hexBoard.sendRayat(21);
        hexBoard.setFinishedRound();
        hexBoard.setGuessAtomAt(3, 3);
        hexBoard.checkGuessedAtoms();

        //System.out.println(hexBoard.getPlayer().getNumofMarkers() + " " + hexBoard.getPlayer().getNumofCorrectGuesses()+" " + hexBoard.getPlayer().getNumofWrongGuesses());
        //System.out.println(hexBoard.getPlayer().calculateScore());
        assertEquals(10, hexBoard.getPlayer().calculateScore());


        HexBoard HexBoard = new HexBoard();
        HexBoard.createHexagonalBoard();
        Player player2 = new Player();
        HexBoard.setPlayer(player2);
        HexBoard.createAtomAthexagon(5, 5);
        HexBoard.createAtomAthexagon(2, 1);
        HexBoard.createAtomAthexagon(3, 4);
        HexBoard.createAtomAthexagon(8, 2);
        HexBoard.initializeHexagonsNearAtom();

        HexBoard.sendRayat(22);
        HexBoard.sendRayat(15);
        HexBoard.sendRayat(25);
        HexBoard.sendRayat(2);
        HexBoard.setFinishedRound();

        //making guesses for player2
        HexBoard.setGuessAtomAt(5, 5);
        HexBoard.setGuessAtomAt(2, 1);
        HexBoard.setGuessAtomAt(1, 2);
        HexBoard.setGuessAtomAt(3, 4);
        HexBoard.checkGuessedAtoms();
        assertEquals(12, player2.calculateScore());

        HexBoard HexBoard_ = new HexBoard();
        HexBoard_.createHexagonalBoard();
        Player player3 = new Player();
        HexBoard_.setPlayer(player3);
        HexBoard_.createAtomAthexagon(3, 1);
        HexBoard_.createAtomAthexagon(2, 2);
        HexBoard_.initializeHexagonsNearAtom();

        HexBoard_.sendRayat(22);
        HexBoard_.sendRayat(15);
        HexBoard_.setFinishedRound();

        //making guesses for player3
        HexBoard_.setGuessAtomAt(1, 5);
        HexBoard_.setGuessAtomAt(3, 4);
        HexBoard_.setGuessAtomAt(4, 6);
        HexBoard_.setGuessAtomAt(6, 2);
        HexBoard_.checkGuessedAtoms();
        assertEquals(24, player3.calculateScore());

    }

    @Test
    public void checkWinner() {
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        Player player1 = new Player();
        hexBoard.setPlayer(player1);
        hexBoard.createAtomAthexagon(4, 4);
        hexBoard.initializeHexagonsNearAtom();
        hexBoard.sendRayat(19);
        hexBoard.sendRayat(17);
        hexBoard.sendRayat(21);
        hexBoard.setFinishedRound();
        hexBoard.setGuessAtomAt(4, 3);
        hexBoard.checkGuessedAtoms();
        hexBoard.getPlayer().calculateScore();


        HexBoard hexBoard2 = new HexBoard();
        ViewBlackbox view = new ViewBlackbox();
        hexBoard2.setViewBlackbox(view);  // Setting view to hexBoard2
        hexBoard2.createHexagonalBoard();
        Player player2 = new Player();
        hexBoard2.setPlayer(player2);
        hexBoard2.createAtomAthexagon(4, 4);
        hexBoard2.initializeHexagonsNearAtom();
        hexBoard2.sendRayat(19);
        hexBoard2.sendRayat(17);
        hexBoard2.sendRayat(21);
        hexBoard2.sendRayat(23);
        hexBoard2.setFinishedRound();
        hexBoard2.setGuessAtomAt(3, 3);
        hexBoard2.checkGuessedAtoms();
        hexBoard2.getPlayer().calculateScore();

        assertEquals("Winner: Player 1", view.getWinner(player1, player2));

        HexBoard hexboard3 = new HexBoard();
        hexboard3.createHexagonalBoard();
        Player Player11 = new Player();
        hexboard3.setPlayer(Player11);
        hexboard3.createAtomAthexagon(2,2);
        hexboard3.createAtomAthexagon(2,1);
        hexboard3.createAtomAthexagon(3,4);
        hexboard3.createAtomAthexagon(8,2);
        hexboard3.initializeHexagonsNearAtom();
        hexboard3.sendRayat(13);
        hexboard3.sendRayat(42);
        hexboard3.sendRayat(53);
        hexboard3.sendRayat(46);
        hexboard3.setFinishedRound();
        hexboard3.setGuessAtomAt(2,2);
        hexboard3.setGuessAtomAt(2,1);
        hexboard3.setGuessAtomAt(1,2);
        hexboard3.setGuessAtomAt(3,4);
        hexboard3.checkGuessedAtoms();
        hexboard3.getPlayer().calculateScore();


        HexBoard HexBoard2 = new HexBoard();
        ViewBlackbox View = new ViewBlackbox();
        HexBoard2.setViewBlackbox(View);
        HexBoard2.createHexagonalBoard();
        Player Player22 = new Player();
        HexBoard2.setPlayer(Player22);
        HexBoard2.createAtomAthexagon(5,5);
        HexBoard2.createAtomAthexagon(2,1);
        HexBoard2.createAtomAthexagon(3,4);
        HexBoard2.createAtomAthexagon(8,2);
        HexBoard2.initializeHexagonsNearAtom();
        HexBoard2.sendRayat(22);
        HexBoard2.sendRayat(12);
        HexBoard2.sendRayat(46);
        HexBoard2.sendRayat(23);
        HexBoard2.setFinishedRound();
        HexBoard2.setGuessAtomAt(4,8);
        HexBoard2.setGuessAtomAt(2,1);
        HexBoard2.setGuessAtomAt(2,2);
        HexBoard2.setGuessAtomAt(3,4);
        HexBoard2.checkGuessedAtoms();
        HexBoard2.getPlayer().calculateScore();

        assertEquals("Winner: Player 1", View.getWinner(hexboard3.getPlayer(),HexBoard2.getPlayer()));
    }

    @Test
    public void checkPlayerHistory() {
        TESTING = true;
        HexBoard hexBoard = new HexBoard();
        hexBoard.createHexagonalBoard();
        Player player1 = new Player();
        hexBoard.setPlayer(player1);
        hexBoard.createAtomAthexagon(4, 4);
        hexBoard.initializeHexagonsNearAtom();
        hexBoard.sendRayat(19);
        hexBoard.sendRayat(17);
        hexBoard.sendRayat(21);

        assertEquals("Entered at: 19\tType:HIT", hexBoard.getPlayer().getHistory().get(0));
        assertEquals("Entered at: 17\tLeft at: 3\n" + "Type: REFLECTED", hexBoard.getPlayer().getHistory().get(1));
        assertEquals("Entered at: 21\tLeft at: 35\n" + "Type: REFLECTED", hexBoard.getPlayer().getHistory().get(2));
    }
}
