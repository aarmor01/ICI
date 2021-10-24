package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2122.practica2.grupo02.GameConstants;
import es.ucm.fdi.ici.c2122.practica2.grupo02.Tools;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class RunawayFromClosestGhost implements Action {
	
	int chaseDistance = 50;
	public RunawayFromClosestGhost() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getActionId() {
		// TODO Auto-generated method stub
		return "Runaway From Closest Ghost";
	}

	@Override
	public MOVE execute(Game game) {
		int pacmanNode = game.getPacmanCurrentNodeIndex();
		GHOST nearestGhostType = Tools.nearestGhostInRange(game, chaseDistance);
		
		if (nearestGhostType != null) {
			if(GameConstants.DEBUG) {
				GameView.addPoints(game, GameConstants.colours[nearestGhostType.ordinal()],
						game.getShortestPath(game.getGhostCurrentNodeIndex(nearestGhostType), pacmanNode));				
			}
			
			return game.getApproximateNextMoveAwayFromTarget(pacmanNode,
					game.getGhostCurrentNodeIndex(nearestGhostType), game.getPacmanLastMoveMade(), DM.PATH);
		}
		
		MOVE[] possibleMoves = game.getPossibleMoves(pacmanNode, game.getPacmanLastMoveMade());
		return possibleMoves[Tools.rnd.nextInt(possibleMoves.length)];
	}
}
