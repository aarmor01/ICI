package es.ucm.fdi.ici.c2122.practica1.grupo02;

import java.awt.Color;

import pacman.controllers.PacmanController;
import pacman.game.Game;
import pacman.game.GameView;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class MsPacMan extends PacmanController {
	private Color[] colours = { Color.RED, Color.PINK, Color.CYAN, Color.YELLOW };
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		int chaseDistance = 35;
		int pacmanNode = game.getPacmanCurrentNodeIndex();
		
		GHOST nearestGhostType = nearestGhost(game, pacmanNode, chaseDistance);
		
		if (nearestGhostType != null) {
			GameView.addPoints(game, colours[nearestGhostType.ordinal()],
					game.getShortestPath(game.getGhostCurrentNodeIndex(nearestGhostType), pacmanNode));
			
			if (game.getGhostEdibleTime(nearestGhostType) > 0)
				return game.getApproximateNextMoveTowardsTarget(pacmanNode, game.getGhostCurrentNodeIndex(nearestGhostType), 
						game.getPacmanLastMoveMade(), DM.PATH);
			else
				return game.getApproximateNextMoveAwayFromTarget(pacmanNode, game.getGhostCurrentNodeIndex(nearestGhostType), 
						game.getPacmanLastMoveMade(), DM.PATH);
		}
		
		return moveTowardsPills(game, pacmanNode);
	}
	
	GHOST nearestGhost(Game game, int pcNode, int chaseLimit) {
		GHOST ghost = null;
		int nGhost = -1;
		
		for(GHOST ghostType : GHOST.values()) {
			int ghostIndex = game.getGhostCurrentNodeIndex(ghostType);
			
			if (game.getGhostLairTime(ghostType) <= 0) {
				int distance = game.getShortestPathDistance(pcNode, ghostIndex, game.getPacmanLastMoveMade());
				
				if(distance <= chaseLimit && (nGhost == -1 || nGhost > distance)) {
					ghost = ghostType;
					nGhost = distance;
				}
			}
		}
		
		return ghost;
	}
	
	MOVE moveTowardsPills(Game game, int pcNode) {
		int [] activePills = game.getActivePillsIndices();
		int nearestPillNode = -1;
		int shortestDistance = -1;
		
		for (int activePill : activePills) {
			int distance = game.getShortestPathDistance(pcNode, activePill,
					game.getPacmanLastMoveMade());
			if(shortestDistance == -1 || game.getShortestPathDistance(pcNode, activePill,
					game.getPacmanLastMoveMade()) < shortestDistance) {
				nearestPillNode = activePill;
				shortestDistance = distance;
//				System.out.print("PILL\n");
			}
		}
	
		if (nearestPillNode != -1)
            GameView.addLines(game, Color.CYAN, pcNode, nearestPillNode);
		
		return game.getApproximateNextMoveTowardsTarget(pcNode, nearestPillNode, 
				game.getPacmanLastMoveMade(), DM.PATH);
	}
}
