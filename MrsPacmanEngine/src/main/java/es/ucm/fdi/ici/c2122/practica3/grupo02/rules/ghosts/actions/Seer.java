package es.ucm.fdi.ici.c2122.practica3.grupo02.rules.ghosts.actions;

import java.awt.Color;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2122.practica2.grupo02.GameConstants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class Seer implements Action {
	
	GHOST ghostType;
	
	public Seer(GHOST ghostType) {
		super();
		this.ghostType = ghostType;
	}

	@Override
	public MOVE execute(Game game) {
		if (game.doesGhostRequireAction(ghostType)) {
			int pacmanNode = game.getPacmanCurrentNodeIndex();
			int ghostNode = game.getGhostCurrentNodeIndex(ghostType);
			
			int[] activePills = game.getActivePillsIndices();
			int nearestPillNode = -1;
			int shortestDistance = Integer.MAX_VALUE;
			
			// We get the next possible destination of the Pacman
			for (int activePill : activePills) {
				int distance = game.getShortestPathDistance(pacmanNode, 
						activePill, game.getPacmanLastMoveMade());
				if (distance > GameConstants.minPredictionDistance && distance < shortestDistance) {
					nearestPillNode = activePill;
					shortestDistance = distance;
				}
			}
			
			if (GameConstants.DEBUG)
				GameView.addLines(game, Color.RED, ghostNode, nearestPillNode);
			
			return game.getApproximateNextMoveTowardsTarget(ghostNode, nearestPillNode,
					game.getGhostLastMoveMade(ghostType), DM.PATH);
		}
		
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghostType + " seer";
	}
}
