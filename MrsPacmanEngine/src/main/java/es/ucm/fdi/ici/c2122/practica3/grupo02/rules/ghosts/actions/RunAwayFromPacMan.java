package es.ucm.fdi.ici.c2122.practica3.grupo02.rules.ghosts.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class RunAwayFromPacMan implements Action {
	
	GHOST ghostType;
	
	public RunAwayFromPacMan(GHOST ghostType) {
		super();
		this.ghostType = ghostType;
	}

	@Override
	public String getActionId() {
		return ghostType + " runs away";
	}
	
	@Override
	public MOVE execute(Game game) {
		if (game.doesGhostRequireAction(ghostType)) {
			int pacmanNode = game.getPacmanCurrentNodeIndex();
			int ghostNode = game.getGhostCurrentNodeIndex(ghostType);
			return game.getNextMoveAwayFromTarget(ghostNode, pacmanNode, 
					game.getGhostLastMoveMade(ghostType), DM.PATH);
		}
		
		return MOVE.NEUTRAL;
	}
}
