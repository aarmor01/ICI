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
	private Random rnd = new Random();
	private int chaseDistance = 35;
	private int chaseTime = 2;
	private int [] countGhost = {0, 0, 0, 0};
	private int awayFromPacmanDistance = 40;
	private int minPredictionDistance = 15;


	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		moves.clear();
		
		int pacmanNode = game.getPacmanCurrentNodeIndex();

		
		//We get the next possible destination of the pacman
		int [] activePills = game.getActivePillsIndices();
		int nearestPillNode = -1;
		int shortestDistance = -1;
		
		for (int activePill : activePills) {
			int distance = game.getShortestPathDistance(pacmanNode, activePill,
					game.getPacmanLastMoveMade());
			if(shortestDistance == -1 || game.getShortestPathDistance(pacmanNode, activePill,
					game.getPacmanLastMoveMade()) < shortestDistance && distance > minPredictionDistance) {
				nearestPillNode = activePill;
				shortestDistance = distance;
			}
		}
		
		for (GHOST ghostType : GHOST.values()) {
			if (!game.doesGhostRequireAction(ghostType)) {
				moves.put(ghostType, MOVE.NEUTRAL);		
			}
			else {
				int ghostNode = game.getGhostCurrentNodeIndex(ghostType);
				
				
				if (isGhostEdible(game, ghostType) || pacmanCloseToPill(game, pacmanNode, 25)) {
					moves.put(ghostType, game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode,
							game.getGhostLastMoveMade(ghostType), DM.PATH));
				}
				else {
					int ghostDistance;
					ghostDistance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), ghostNode);
					if (ghostDistance > awayFromPacmanDistance && game.getShortestPathDistance(ghostNode, nearestPillNode) > 1) {
						//Go to next pill
						moves.put(ghostType, game.getApproximateNextMoveTowardsTarget(ghostNode, nearestPillNode,
							game.getGhostLastMoveMade(ghostType), DM.PATH));
							
						GameView.addLines(game,Color.RED, ghostNode, nearestPillNode);
							
					} else {
						//Chase Pacman
						nextMoveAgressive(game, ghostNode, pacmanNode, ghostType, ghostType.ordinal());
					}
					
					//System.out.print("DECISION\n");
					//System.out.print(countGhost[0] + "\n");
					//System.out.print(countGhost[1] + "\n");
					//System.out.print(countGhost[2] + "\n");
					//System.out.print(countGhost[3] + "\n");
				}
			}
		}

		return moves;
	}

	private boolean pacmanCloseToPill(Game game, int pcNode, int limit) {
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
		
		ghostDistance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), ghostNode);
		if (ghostDistance <= chaseDistance)
			countGhost[numGhost]++;
		else countGhost[numGhost] = 0;
		
		//Secondary path
		if (countGhost[numGhost] >= chaseTime) {
			
			//System.out.print("Secondary Path\n");
			
			moves.put(ghostType, getSecondaryPath(game, game.getPossibleMoves(ghostNode, game.getGhostLastMoveMade(ghostType)),
					ghostNode, pacmanNode, ghostType));
			countGhost[numGhost] = 0;
		//Optimal path
		} else {
			//System.out.print("Optimal Path\n");
			
			moves.put(ghostType, game.getApproximateNextMoveTowardsTarget(ghostNode, pacmanNode,
					game.getGhostLastMoveMade(ghostType), DM.PATH));
		}
	}
	
	
	private MOVE getSecondaryPath(Game game, MOVE[] possibleMoves, int ghostNode, int pacmanNode, GHOST ghostType) {
		int numChoices = possibleMoves.length;
		
		if (numChoices == 2) {
			return game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode,
					game.getGhostLastMoveMade(ghostType), DM.PATH);
		}
		else {
			MOVE optimalMove = game.getApproximateNextMoveTowardsTarget(ghostNode, pacmanNode,
							   game.getGhostLastMoveMade(ghostType), DM.PATH);
			
			MOVE awayMove = game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode,
						    game.getGhostLastMoveMade(ghostType), DM.PATH);
			
			if (possibleMoves[0] != optimalMove && possibleMoves[0] != awayMove) {
				return possibleMoves[0];
			} else if (possibleMoves[1] != optimalMove && possibleMoves[1] != awayMove) {
				return possibleMoves[1];
			} else {
				return possibleMoves[2];
			}
			
		}
	}

	private boolean isGhostEdible(Game game, GHOST g) {
		return game.getGhostEdibleTime(g) > 0;
	}
}
