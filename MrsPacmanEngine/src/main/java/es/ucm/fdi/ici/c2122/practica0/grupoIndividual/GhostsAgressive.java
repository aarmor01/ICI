package es.ucm.fdi.ici.c2122.practica0.grupoIndividual;

import java.util.EnumMap;

import pacman.controllers.GhostController;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Constants.GHOST;

public final class GhostsAgressive extends GhostController {
	private EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);

	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		moves.clear();
		
		for (GHOST ghostType : GHOST.values()) {
			if (game.doesGhostRequireAction(ghostType)) 
				moves.put(ghostType,
						game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType),
						game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghostType), DM.PATH));
			else
				moves.put(ghostType, MOVE.NEUTRAL);
		}
		
		return moves;
	}
}
