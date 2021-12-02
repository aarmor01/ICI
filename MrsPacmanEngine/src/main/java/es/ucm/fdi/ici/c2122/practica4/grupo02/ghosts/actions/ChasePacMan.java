package es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts.actions;

import pacman.game.Game;
import es.ucm.fdi.ici.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class ChasePacMan implements Action{

    GHOST ghost;
	public ChasePacMan(GHOST g) {
		this.ghost = g;
	}
	
	@Override
	public MOVE execute(Game game) {
		int pcNode = game.getPacmanCurrentNodeIndex();
		
		return game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost), 
															 pcNode, 
															 game.getGhostLastMoveMade(ghost), 
															 DM.PATH);
    }
	
	@Override
	public String getActionId() {
		return "Chase " + ghost;
	}
}
