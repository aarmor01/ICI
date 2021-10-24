package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class ChaseAlternatePathGhost implements Action {
	GHOST ghostType;
	public ChaseAlternatePathGhost(GHOST ghostType) {
		this.ghostType = ghostType;
	}

	@Override
	public MOVE execute(Game game) {
		if (game.doesGhostRequireAction(ghostType)) {
			int pacmanNode = game.getPacmanCurrentNodeIndex();
			int ghostNode = game.getGhostCurrentNodeIndex(ghostType);
			MOVE[] possibleMoves = game.getPossibleMoves(ghostNode);
			if (possibleMoves.length == 2) {
				if (possibleMoves[0] == game.getNextMoveTowardsTarget(ghostNode, pacmanNode, game.getGhostLastMoveMade(ghostType), DM.PATH)) {
					return possibleMoves[1];
				}else
					return possibleMoves[0];
			}else if (possibleMoves.length == 3) {
				if (possibleMoves[0] == game.getNextMoveTowardsTarget(ghostNode, pacmanNode, game.getGhostLastMoveMade(ghostType), DM.PATH)) {
					if (possibleMoves[1] == game.getNextMoveAwayFromTarget(ghostNode, pacmanNode, game.getGhostLastMoveMade(ghostType), DM.PATH))
						return possibleMoves[2];
					else
						return possibleMoves[1];
				}
				if (possibleMoves[1] == game.getNextMoveTowardsTarget(ghostNode, pacmanNode, game.getGhostLastMoveMade(ghostType), DM.PATH)) {
					if (possibleMoves[0] == game.getNextMoveAwayFromTarget(ghostNode, pacmanNode, game.getGhostLastMoveMade(ghostType), DM.PATH))
						return possibleMoves[2];
					else
						return possibleMoves[0];
				}
				if (possibleMoves[2] == game.getNextMoveTowardsTarget(ghostNode, pacmanNode, game.getGhostLastMoveMade(ghostType), DM.PATH)) {
					if (possibleMoves[1] == game.getNextMoveAwayFromTarget(ghostNode, pacmanNode, game.getGhostLastMoveMade(ghostType), DM.PATH))
						return possibleMoves[0];
					else
						return possibleMoves[1];
				}
			}
			return game.getNextMoveTowardsTarget(ghostNode, pacmanNode, game.getGhostLastMoveMade(ghostType), DM.PATH);
		}
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghostType + " chases by alternate path";
	}
}
