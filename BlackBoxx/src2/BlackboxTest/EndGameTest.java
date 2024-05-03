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

        //System.out.println(hexBoard.getPlayer().getNumofMarkers() + " " + hexBoard.getPlayer().getNumofCorrectGuesses()+" " + hexBoard.getPlayer().getNumofWrongGuesses());
        //System.out.println(hexBoard.getPlayer().calculateScore());
        //assertEquals(10, hexb.getPlayer().calculateScore());
        //System.out.println(hexb.getPlayer().calculateScore());
        //System.out.println(hexBoard.getPlayer().calculateScore());
        //System.out.println( hexBoard.getViewBlackbox().getWinner(player1,player2)); //get winner returns string of the winner
        assertEquals("It's a tie!", view.getWinner(player1, player2));

          /*  HexBoard HexBoard = new HexBoard();
            HexBoard.createHexagonalBoard();
            Player Player1 = new Player();
        HexBoard.setPlayer(Player1);
        HexBoard.createAtomAthexagon(2,2);
        HexBoard.createAtomAthexagon(2,1);
        HexBoard.createAtomAthexagon(3,4);
        HexBoard.createAtomAthexagon(8,2);
        HexBoard.initializeHexagonsNearAtom();
        HexBoard.sendRayat(13);
        HexBoard.sendRayat(42);
        HexBoard.sendRayat(53);
        HexBoard.sendRayat(46);
        HexBoard.setFinishedRound();
        HexBoard.setGuessAtomAt(2,2);
        HexBoard.setGuessAtomAt(2,1);
        HexBoard.setGuessAtomAt(1,2);
        HexBoard.setGuessAtomAt(3,4);
        HexBoard.checkGuessedAtoms();


            HexBoard HexBoard2 = new HexBoard();
            ViewBlackbox View = new ViewBlackbox();
        HexBoard2.setViewBlackbox(View);
        HexBoard2.createHexagonalBoard();
            Player Player2 = new Player();
        HexBoard2.setPlayer(Player2);
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
        assertEquals("It's a tie!", View.getWinner(Player1,Player2));

           */
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
        hexBoard.setFinishedRound();
        hexBoard.setGuessAtomAt(3, 3);
        hexBoard.checkGuessedAtoms();

        //System.out.println(hexBoard.getPlayer().getNumofMarkers() + " " + hexBoard.getPlayer().getNumofCorrectGuesses()+" " + hexBoard.getPlayer().getNumofWrongGuesses());
        //System.out.println(hexBoard.getPlayer().calculateScore());
        assertEquals(10, hexBoard.getPlayer().calculateScore());

/*
        HexBoard HexBoard = new HexBoard();
        HexBoard.createHexagonalBoard();
        Player player2 = new Player();
        hexBoard.setPlayer(player2);
        hexBoard.createAtomAthexagon(5,5);
        hexBoard.createAtomAthexagon(2,1);
        hexBoard.createAtomAthexagon(3,4);
        hexBoard.createAtomAthexagon(8,2);
        hexBoard.initializeHexagonsNearAtom();

        hexBoard.sendRayat(22);
        hexBoard.sendRayat(15);
        hexBoard.sendRayat(25);
        hexBoard.setFinishedRound();

        //making guesses for player2
        hexBoard.setGuessAtomAt(5, 5);
        hexBoard.setGuessAtomAt(2, 1);
        hexBoard.setGuessAtomAt(1, 2);
        hexBoard.checkGuessedAtoms();
        assertEquals(11, player2.calculateScore());
    }*/

    }
}
