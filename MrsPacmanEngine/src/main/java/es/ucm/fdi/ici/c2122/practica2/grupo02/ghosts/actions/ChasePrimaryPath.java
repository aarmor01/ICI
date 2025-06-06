package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class ChasePrimaryPath implements Action {
	
	GHOST ghostType;
	
	public ChasePrimaryPath(GHOST ghostType) {
		super();
		this.ghostType = ghostType;
	}

	@Override
	public MOVE execute(Game game) {
		if (game.doesGhostRequireAction(ghostType)) {
			int pacmanNode = game.getPacmanCurrentNodeIndex();
			int ghostNode = game.getGhostCurrentNodeIndex(ghostType);
//			int ghostNodeX = game.getNodeXCood(ghostNode);
//			int ghostNodeY = game.getNodeYCood(ghostNode);
//			int pacmanNodeX = game.getNodeXCood(pacmanNode);
//			int pacmanNodeY = game.getNodeYCood(pacmanNode);
			MOVE movimiento = game.getNextMoveTowardsTarget(ghostNode, pacmanNode, game.getGhostLastMoveMade(ghostType), DM.PATH);
			if (movimiento == MOVE.UP)
				movimiento = MOVE.DOWN;
			else if (movimiento == MOVE.DOWN)
				movimiento = MOVE.UP;
			else if (movimiento == MOVE.RIGHT)
				movimiento = MOVE.LEFT;
			else if (movimiento == MOVE.LEFT)
				movimiento = MOVE.RIGHT;
			return movimiento;
		}else
			return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghostType + " chase 1";
	}
}
