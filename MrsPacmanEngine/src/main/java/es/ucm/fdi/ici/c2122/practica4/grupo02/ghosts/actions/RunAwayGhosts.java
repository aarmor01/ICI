package es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RunAwayGhosts implements Action {
    
    GHOST ghost;
	public RunAwayGhosts(GHOST g) {
		this.ghost = g;
	}
	
	@Override
	public MOVE execute(Game game) {
		int pcNode = game.getPacmanCurrentNodeIndex();
		
		return game.getApproximateNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghost), 
															 pcNode, 
															 game.getGhostLastMoveMade(ghost), 
															 DM.PATH);
    }
	
	@Override
	public String getActionId() {
		return "Runaway " + ghost;
	}
            
}