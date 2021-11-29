package es.ucm.fdi.ici.c2122.practica4.grupo02;

import pacman.Executor;
import pacman.controllers.GhostController;
import pacman.controllers.HumanController;
import pacman.controllers.KeyBoardInput;
import pacman.controllers.PacmanController;


public class ExecutorTest {

    public static void main(String[] args) {
        Executor executor = new Executor.Builder()
                .setTickLimit(4000)
//                .setGhostPO(false)
                .setPacmanPO(true)
                .setPacmanPOvisual(true)
                .setSightLimit(30)
                .setVisual(true)
                .setScaleFactor(2.5)
                .build();
        
//        PacmanController pacMan = new HumanController(new KeyBoardInput());
        PacmanController pacMan = new MsPacManFuzzy();
        GhostController ghosts = new GhostsRandom();
        
        System.out.println(executor.runGame(pacMan, ghosts, 40));
    }
}
