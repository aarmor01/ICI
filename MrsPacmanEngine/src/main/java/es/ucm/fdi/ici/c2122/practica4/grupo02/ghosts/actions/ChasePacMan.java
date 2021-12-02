package es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts.actions;

import pacman.game.Game;
import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts.GhostsFuzzyMemory;
import es.ucm.fdi.ici.c2122.practica4.grupo02.GameConstants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class ChasePacMan implements Action{

    GHOST ghost;
    GhostsFuzzyMemory mem;
	public ChasePacMan(GHOST g, GhostsFuzzyMemory mem_) {
		this.ghost = g;
		this.mem = mem_;
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
