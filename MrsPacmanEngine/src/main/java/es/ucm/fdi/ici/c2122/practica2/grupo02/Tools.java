package es.ucm.fdi.ici.c2122.practica2.grupo02;

import pacman.game.Constants.GHOST;
import pacman.game.Game;

public final class Tools {
	
	public static GHOST nearestGhostInRange(Game game, int chaseLimit) {
		int pcNode = game.getPacmanCurrentNodeIndex();
		GHOST ghost = null;

		int nearestGhostDistance = Integer.MAX_VALUE;
		for (GHOST ghostType : GHOST.values()) {
			int ghostIndex = game.getGhostCurrentNodeIndex(ghostType);
			
			//If the ghost is not into jail, we check if it's near to MsPacMan 
			if (game.getGhostLairTime(ghostType) <= 0) {
				int distance = game.getShortestPathDistance(pcNode, ghostIndex, game.getPacmanLastMoveMade());

				if (distance <= chaseLimit && nearestGhostDistance > distance) {
					ghost = ghostType;
					nearestGhostDistance = distance;
				}
			}
		}

		return ghost;
	}
	
	public static GHOST nearestGhost(Game game) {
		int pcNode = game.getPacmanCurrentNodeIndex();
		GHOST ghost = null;

		int nearestGhostDistance = Integer.MAX_VALUE;
		for (GHOST ghostType : GHOST.values()) {
			int ghostIndex = game.getGhostCurrentNodeIndex(ghostType);
			
			//If the ghost is not into jail, we check if it's near to MsPacMan 
			if (game.getGhostLairTime(ghostType) <= 0) {
				int distance = game.getShortestPathDistance(pcNode, ghostIndex, game.getPacmanLastMoveMade());

				if (nearestGhostDistance > distance) {
					ghost = ghostType;
					nearestGhostDistance = distance;
				}
			}
		}

		return ghost;
	}
	
}
