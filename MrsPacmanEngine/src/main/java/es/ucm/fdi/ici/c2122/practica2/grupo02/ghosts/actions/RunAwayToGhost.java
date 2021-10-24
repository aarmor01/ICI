package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class RunAwayToGhost implements Action {
	GHOST ghostType;
	public RunAwayToGhost(GHOST ghostType) {
		this.ghostType = ghostType;
	}

	@Override
	public MOVE execute(Game game) {
		if (game.doesGhostRequireAction(ghostType)) {
			int pacmanNode = game.getPacmanCurrentNodeIndex();
			int ghostNode = game.getGhostCurrentNodeIndex(ghostType);
			int closestGhostNode = -1;
			int closestDistance = 10000;
			for (GHOST otherGhost : GHOST.values()) {
				int otherGhostNode = game.getGhostCurrentNodeIndex(otherGhost);
				if (otherGhostNode != ghostNode) {
					if (game.getGhostEdibleTime(otherGhost) != 0) {
						int distanceToOtherGhost = game.getShortestPathDistance(ghostNode, otherGhostNode);
						if (distanceToOtherGhost < closestDistance) {
							closestDistance = distanceToOtherGhost;
							closestGhostNode = otherGhostNode;
						}
					}
				}
			}
			
			if (closestGhostNode != -1)
				return game.getNextMoveTowardsTarget(ghostNode, closestGhostNode, game.getGhostLastMoveMade(ghostType), DM.PATH);
			else
				return game.getNextMoveAwayFromTarget(ghostNode, pacmanNode, game.getGhostLastMoveMade(ghostType), DM.PATH);
		}
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghostType + " runs away to closest ghost";
	}
}