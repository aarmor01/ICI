package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.actions;

import java.awt.Color;

import es.ucm.fdi.ici.Action;
import pacman.game.Game;
import pacman.game.GameView;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class Seer implements Action {
	GHOST ghostType;
	int minPredictionDistance = 15;
	boolean DEBUG = true;
	public Seer(GHOST ghostType) {
		this.ghostType = ghostType;
	}

	@Override
	public MOVE execute(Game game) {
		if (game.doesGhostRequireAction(ghostType)) {
			int pacmanNode = game.getPacmanCurrentNodeIndex();
			int ghostNode = game.getGhostCurrentNodeIndex(ghostType);
			
			int[] activePills = game.getActivePillsIndices();
			int nearestPillNode = -1;
			int shortestDistance = -1;
			
			// We get the next possible destination of the Pacman
			for (int activePill : activePills) {
				int distance = game.getShortestPathDistance(pacmanNode, activePill, game.getPacmanLastMoveMade());
				if (shortestDistance == -1 || distance < shortestDistance && distance > minPredictionDistance) {
					nearestPillNode = activePill;
					shortestDistance = distance;
				}
			}
			
			if (DEBUG)
				GameView.addLines(game, Color.RED, ghostNode, nearestPillNode);
			
			return game.getApproximateNextMoveTowardsTarget(ghostNode, nearestPillNode,
					game.getGhostLastMoveMade(ghostType), DM.PATH);
		}
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghostType + "chases when far away";
	}
}
