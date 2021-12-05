package es.ucm.fdi.ici.c2122.practica4.grupo02;

import pacman.Executor;

import pacman.controllers.GhostController;
import pacman.controllers.PacmanController;

//import pacman.controllers.HumanController;
//import pacman.controllers.KeyBoardInput;

public class ExecutorTest {

    public static void main(String[] args) {
        Executor executor = new Executor.Builder()
                .setTickLimit(4000)
                .setPacmanPO(true)
                .setGhostPO(false)
                .setPacmanPOvisual(true)
                .setGhostsPOvisual(true)
                .setSightLimit(30)
                .setVisual(true)
                .setScaleFactor(2.5)
                .build();
        
//      PacmanController pacMan = new HumanController(new KeyBoardInput());
        PacmanController pacMan = new MsPacManFuzzy();
        GhostController ghosts = new es.ucm.fdi.ici.c2122.practica3.grupo02.Ghosts();
        
        System.out.println(executor.runGame(pacMan, ghosts, 40));
    }
}
