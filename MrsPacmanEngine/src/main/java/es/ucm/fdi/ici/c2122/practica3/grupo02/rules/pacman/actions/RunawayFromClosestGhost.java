package es.ucm.fdi.ici.c2122.practica3.grupo02.rules.pacman.actions;

import es.ucm.fdi.ici.c2122.practica2.grupo02.GameConstants;
import es.ucm.fdi.ici.c2122.practica2.grupo02.Tools;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class RunawayFromClosestGhost implements RulesAction {
	
	int runawayDistance = 50;
	public RunawayFromClosestGhost() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getActionId() {
		// TODO Auto-generated method stub
		return "RunawayFromClosestGhost";
	}

	@Override
	public MOVE execute(Game game) {
		int pacmanNode = game.getPacmanCurrentNodeIndex();
		GHOST nearestGhostType = Tools.nearestGhostInRange(game, runawayDistance);
		
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
	
	@Override
	public void parseFact(Fact actionFact) {
		// TODO Auto-generated method stub
		
	}
}
