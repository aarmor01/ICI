package es.ucm.fdi.ici.c2122.practica1.grupo02;

import java.util.EnumMap;
import java.util.Random;

import pacman.controllers.GhostController;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class Ghosts extends GhostController {
	private EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);
	private MOVE[] allMoves = MOVE.values();
	private Random rnd = new Random();

	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		moves.clear();
		
		int pacmanNode = game.getPacmanCurrentNodeIndex();

		for (GHOST ghostType : GHOST.values()) {
			if (!game.doesGhostRequireAction(ghostType)) {
				moves.put(ghostType, MOVE.NEUTRAL);		
			}
			else {
				int ghostNode = game.getGhostCurrentNodeIndex(ghostType);
				
				if (isGhostEdible(game, ghostType) || pacmanCloseToPill(game, pacmanNode, 25)) {
					moves.put(ghostType, game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode,
							game.getGhostLastMoveMade(ghostType), DM.PATH));
//					System.out.print("RUN\n");
				}
				else if (rnd.nextInt(100) < 90) {
					moves.put(ghostType, game.getApproximateNextMoveTowardsTarget(ghostNode, pacmanNode,
							game.getGhostLastMoveMade(ghostType), DM.PATH));
//					System.out.print("CHASE\n");
				}
				else {
					moves.put(ghostType, allMoves[rnd.nextInt(allMoves.length)]);
//					System.out.print("RAND\n");
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

	private boolean isGhostEdible(Game game, GHOST g) {
		return game.getGhostEdibleTime(g) > 0;
	}
}
