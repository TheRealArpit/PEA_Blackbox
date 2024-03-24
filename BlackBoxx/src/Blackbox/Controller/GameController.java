package Blackbox.Controller;

import Blackbox.Model.GameModel;
import Blackbox.View.GameView;


public class GameController {
    private GameModel model;
    private GameView view;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }

    public void startGame() throws Exception {
        view.start();
    }
}