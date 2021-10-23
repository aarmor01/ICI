package es.ucm.fdi.ici.c2122.practica2.grupo02;

import java.awt.Color;

import pacman.game.Game;
import pacman.game.Constants.GHOST;

public final class Tools {
	
	public static Color[] colours = { Color.RED, Color.PINK, Color.CYAN, Color.YELLOW };
	
	public static GHOST nearestGhostInRange(Game game, int chaseLimit) {
		GHOST ghost = null;
		int nGhost = -1;
		int pcNode = game.getPacmanCurrentNodeIndex();

		for (GHOST ghostType : GHOST.values()) {
			int ghostIndex = game.getGhostCurrentNodeIndex(ghostType);
			//If the ghost is not into jail, we check if it's near to MsPacMan 
			if (game.getGhostLairTime(ghostType) <= 0) {
				int distance = game.getShortestPathDistance(pcNode, ghostIndex, game.getPacmanLastMoveMade());

				if (distance <= chaseLimit && (nGhost == -1 || nGhost > distance)) {
					ghost = ghostType;
					nGhost = distance;
				}
			}
		}

		return ghost;
	}
}
