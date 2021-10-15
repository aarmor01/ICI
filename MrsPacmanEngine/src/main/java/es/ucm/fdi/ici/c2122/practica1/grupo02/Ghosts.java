package es.ucm.fdi.ici.c2122.practica1.grupo02;

import java.awt.Color;
import java.util.EnumMap;
import java.util.Random;

import pacman.controllers.GhostController;
import pacman.game.Game;
import pacman.game.GameView;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class Ghosts extends GhostController {
	private EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);
	private MOVE[] allMoves = MOVE.values();

	// Aggresive behaviour data
	private int chaseTime = 2; // Number of junctions max before changing ghosts' path
	private int[] countGhost = { 0, 0, 0, 0 }; // Junctions' counter for every ghost

	// Lurker behaviour data
	private int awayFromPacmanDistance = 40; // Distance that changes to aggresive behaviour
	private int minPredictionDistance = 15; // Range to check pills that are further than this distance

	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		moves.clear();
		
		// We get Pacman current node
		int pacmanNode = game.getPacmanCurrentNodeIndex();
		
		// We get the next possible destination of the Pacman
		int[] activePills = game.getActivePillsIndices();
		int nearestPillNode = -1;
		int shortestDistance = -1;

		for (int activePill : activePills) {
			int distance = game.getShortestPathDistance(pacmanNode, activePill, game.getPacmanLastMoveMade());
			if (shortestDistance == -1 || distance < shortestDistance && distance > minPredictionDistance) {
				nearestPillNode = activePill;
				shortestDistance = distance;
			}
		}

		for (GHOST ghostType : GHOST.values()) {
			if (!game.doesGhostRequireAction(ghostType)) 
				moves.put(ghostType, MOVE.NEUTRAL);
			else {
				int ghostNode = game.getGhostCurrentNodeIndex(ghostType);

				// If Pacman has been powered up, or it's close to eat a power pill, the ghost runs away from her
				if (isGhostEdible(game, ghostType) || pacmanCloseToPowerPill(game, pacmanNode, 25))
					moves.put(ghostType, game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode,
							game.getGhostLastMoveMade(ghostType), DM.PATH));
				else {
					int ghostDistance; // distance between the ghost and Pacman
					ghostDistance = game.getShortestPathDistance(pacmanNode, ghostNode);
					
					// Go to next pill if it's not next to the Pacman
					if (ghostDistance > awayFromPacmanDistance && game.getShortestPathDistance(ghostNode, nearestPillNode) > 1) {
						moves.put(ghostType, game.getApproximateNextMoveTowardsTarget(ghostNode, nearestPillNode,
								game.getGhostLastMoveMade(ghostType), DM.PATH));

						GameView.addLines(game, Color.RED, ghostNode, nearestPillNode);
					}
					else // if the ghost is close enough, it chases Pacman
						nextMoveAgressive(game, ghostNode, pacmanNode, ghostType, ghostType.ordinal());
				}
			}
		}

		return moves;
	}
	
	private void SeerBehaviour() {
		
	}
	
	private void AntBehaviour() {
		
	}
	
	private void AggressiveBehaviour() {
		
	}
	
	private void AmbushBehaviour() {
		
	}

	private boolean pacmanCloseToPowerPill(Game game, int pcNode, int limit) {
		MOVE lMov = game.getPacmanLastMoveMade();
		int[] powerPills = game.getActivePowerPillsIndices();

		int i = 0;
		while (i < powerPills.length) { // if close, true
			if (game.getShortestPathDistance(pcNode, powerPills[i], lMov) < limit)
				return true;
			i++;
		}

		return false;
	}

	private void nextMoveAgressive(Game game, int ghostNode, int pacmanNode, GHOST ghostType, int numGhost) {
		int ghostDistance;

		ghostDistance = game.getShortestPathDistance(pacmanNode, ghostNode);
		if (ghostDistance <= awayFromPacmanDistance)
			countGhost[numGhost]++;
		else // if it has stopped being close to the Pacman, we reset the counter
			countGhost[numGhost] = 0;

		// to evade a situation where the ghosts chases indefinitely, we change to a secondary path
		if (countGhost[numGhost] >= chaseTime) {
			moves.put(ghostType, getSecondaryPath(game, game.getPossibleMoves(ghostNode, game.getGhostLastMoveMade(ghostType)),
							ghostNode, pacmanNode, ghostType));
			countGhost[numGhost] = 0; // reset the counter
		}
		else // if it has chased for a little time, he can keep using the optimal path
			moves.put(ghostType, game.getApproximateNextMoveTowardsTarget(ghostNode, pacmanNode,
					game.getGhostLastMoveMade(ghostType), DM.PATH));
	}

	private MOVE getSecondaryPath(Game game, MOVE[] possibleMoves, int ghostNode, int pacmanNode, GHOST ghostType) {
		int numChoices = possibleMoves.length;

		if (numChoices == 2) // if two possible moves, we go the opposite way (away instead of towards)
			return game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode,
					game.getGhostLastMoveMade(ghostType), DM.PATH);
		else { // if three possible moves, we go to the one that isn't the furthest nor the closest to the Pacman
			MOVE optimalMove = game.getApproximateNextMoveTowardsTarget(ghostNode, pacmanNode,
					game.getGhostLastMoveMade(ghostType), DM.PATH);

			MOVE awayMove = game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode,
					game.getGhostLastMoveMade(ghostType), DM.PATH);

			// we select the one that isn't the furthest nor the closest
			if (possibleMoves[0] != optimalMove && possibleMoves[0] != awayMove)
				return possibleMoves[0];
			else if (possibleMoves[1] != optimalMove && possibleMoves[1] != awayMove)
				return possibleMoves[1];
			else
				return possibleMoves[2];
		}
	}

	private boolean isGhostEdible(Game game, GHOST g) {
		return game.getGhostEdibleTime(g) > 0;
	}
}
