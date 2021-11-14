package es.ucm.fdi.ici.c2122.practica3.grupo02.rules.ghosts.actions;

import es.ucm.fdi.ici.rules.RulesAction;
import es.ucm.fdi.ici.c2122.practica2.grupo02.GameConstants;

import java.awt.Color;

import jess.Fact;

import pacman.game.Game;
import pacman.game.GameView;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Constants.GHOST;

public class Seer implements RulesAction {
	
	GHOST ghostType;
	
	public Seer(GHOST ghostType) {
		super();
		this.ghostType = ghostType;
	}

	@Override
	public String getActionId() {
		return ghostType + "Seer";
	}

	@Override
	public MOVE execute(Game game) {
		if (game.doesGhostRequireAction(ghostType)) {
			int pacmanNode = game.getPacmanCurrentNodeIndex();
			int ghostNode = game.getGhostCurrentNodeIndex(ghostType);
			MOVE lastMove = game.getGhostLastMoveMade(ghostType);
			
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
				GameView.addLines(game, Color.CYAN, ghostNode, nearestPillNode);
			
			return game.getApproximateNextMoveTowardsTarget(ghostNode, nearestPillNode, lastMove, DM.PATH);
		}
		
		return MOVE.NEUTRAL;
	}
	
	@Override
	public void parseFact(Fact actionFact) {
		
	}
}
