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
		int chaseDistance = 50;
		int pacmanNode = game.getPacmanCurrentNodeIndex();

		GHOST nearestGhostType = nearestGhost(game, pacmanNode, chaseDistance);
		//If there's near ghost
		if (nearestGhostType != null) {
			GameView.addPoints(game, colours[nearestGhostType.ordinal()],
					game.getShortestPath(game.getGhostCurrentNodeIndex(nearestGhostType), pacmanNode));
			//If the ghost is edible, we go for it
			if (game.getGhostEdibleTime(nearestGhostType) > 0)
				return game.getApproximateNextMoveTowardsTarget(pacmanNode,
						game.getGhostCurrentNodeIndex(nearestGhostType), game.getPacmanLastMoveMade(), DM.PATH);
			else {
				MOVE nextMove = getNextPossibleMoveToPowerPill(game, pacmanNode, nearestGhostType);
				//Si no hay PP o si los caminos hacia ellas estan bloqueados, huye
				if (nextMove == MOVE.NEUTRAL)
					return game.getApproximateNextMoveAwayFromTarget(pacmanNode,
							game.getGhostCurrentNodeIndex(nearestGhostType), game.getPacmanLastMoveMade(), DM.PATH);
				else //De lo contario, se dirige a la PP mas cercana no obstruida
					return nextMove;
			}
		}
		//Si no hay fantasmas cercanos, persigue las pills normales
		return moveTowardsPills(game, pacmanNode);
	}
	
	MOVE getNextPossibleMoveToPowerPill(Game game, int pcNode, GHOST ghostType) {
		int[] activePowerPills = game.getActivePowerPillsIndices();
		int nearestPP = -1, shortestDist = -1;

		for (int activePill : activePowerPills) {
			int distance = game.getShortestPathDistance(pcNode, activePill, game.getPacmanLastMoveMade());
			if (nearestPP == -1 || game.getShortestPathDistance(pcNode, activePill,
					game.getPacmanLastMoveMade()) < shortestDist) {
				if(pathNotBlocked(game, pcNode, activePill, ghostType)) {
					nearestPP = activePill;
					shortestDist = distance;
				}
			}
		}
		
		if (nearestPP == -1)
			return MOVE.NEUTRAL;
		else
			return game.getApproximateNextMoveTowardsTarget(pcNode,	nearestPP, game.getPacmanLastMoveMade(), DM.PATH);
	}

	GHOST nearestGhost(Game game, int pcNode, int chaseLimit) {
		GHOST ghost = null;
		int nGhost = -1;

		for (GHOST ghostType : GHOST.values()) {
			int ghostIndex = game.getGhostCurrentNodeIndex(ghostType);
			//Si el fantasma no esta en la jaula
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

	MOVE moveTowardsPills(Game game, int pcNode) {
		int[] activePills = game.getActivePillsIndices();
		int nearestPillNode = -1;
		int shortestDistance = -1;

		for (int activePill : activePills) {
			int distance = game.getShortestPathDistance(pcNode, activePill, game.getPacmanLastMoveMade());
			if (shortestDistance == -1 || distance < shortestDistance) {
				nearestPillNode = activePill;
				shortestDistance = distance;
//				System.out.print("PILL\n");
			}
		}

		//Si existe Pill cercana, dibujamos su camino euclideo
		if (nearestPillNode != -1)
			GameView.addLines(game, Color.CYAN, pcNode, nearestPillNode);
		
		return game.getApproximateNextMoveTowardsTarget(pcNode, nearestPillNode, game.getPacmanLastMoveMade(), DM.PATH);
	}

	boolean pathNotBlocked(Game game, int pcNode, int activePillNode, GHOST nearestGhostType_) {
		int obstructionLimit = 40;
		
		int[] path = game.getShortestPath(pcNode, activePillNode);
		
		int ghostIndex = game.getGhostCurrentNodeIndex(nearestGhostType_);
		
		
		for(int pathNode : path) {
			for(GHOST ghostType: GHOST.values()) {
				if(pathNode == game.getGhostCurrentNodeIndex(ghostType))
					return false;
			}
			
			//We check if the nearest Ghost is close to our nodes path, if so, we ran away
			int distance = game.getShortestPathDistance(ghostIndex, pathNode, game.getGhostLastMoveMade(nearestGhostType_));
			if (distance < obstructionLimit) 
				return false;			
		}
		
		return true;
	}
}
