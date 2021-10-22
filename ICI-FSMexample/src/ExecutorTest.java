

import pacman.Executor;
import pacman.controllers.GhostController;
import pacman.controllers.HumanController;
import pacman.controllers.PacmanController;


public class ExecutorTest {

    public static void main(String[] args) {
        Executor executor = new Executor.Builder()
                .setTickLimit(4000)
                .setGhostPO(false)
                .setPacmanPO(false)
                .setVisual(true)
                .setScaleFactor(3.0)
                .build();
        
        PacmanController pacMan = new es.ucm.fdi.ici.practica2.demofsm.MsPacMan();
        GhostController ghosts = new es.ucm.fdi.ici.practica2.demofsm.Ghosts();
        
        pacMan.setName("Hola"); pacMan.setTeam("G2_ICIsports");
        ghosts.setName("Mundo"); ghosts.setTeam("G2_ICIsports");
        
        System.out.println( 
        		executor.runGame(pacMan, ghosts, 40)
        );
        
    }
}
