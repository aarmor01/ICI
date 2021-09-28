package es.ucm.fdi.ici.c2122.practica0.grupoIndividual;

import java.awt.Color;
import java.util.Random;

import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Constants.GHOST;
import pacman.game.Game;
import pacman.game.GameView;

public final class MsPacManRunAway extends PacmanController {
	private Random rnd = new Random();
	private MOVE[] allMoves = MOVE.values();
	private Color[] colours = { Color.RED, Color.PINK, Color.CYAN, Color.YELLOW };
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		int pacmanNode = game.getPacmanCurrentNodeIndex();
		int shortestDistance = -1;
		int nearestGhostNode = -1;

		for (GHOST ghostType : GHOST.values()) {
			if (game.getGhostLairTime(ghostType) <= 0) {
				int ghostNode = game.getGhostCurrentNodeIndex(ghostType);
				
				MOVE lMov = game.getPacmanLastMoveMade();
				// pathdistance envia numero de nodos que hay de distancia en el camino
				// path envia los indices de los nodos 
				int distance = game.getShortestPathDistance(pacmanNode, ghostNode, lMov);

				if (shortestDistance == -1 || shortestDistance > distance) {
					shortestDistance = distance;
					nearestGhostNode = ghostNode;
				}
			}
		}

		printGameInfo(game, false, true);
		
		if (nearestGhostNode != -1)
			return game.getApproximateNextMoveAwayFromTarget(pacmanNode, nearestGhostNode,
					game.getPacmanLastMoveMade(), DM.PATH);
		else 
			return allMoves[rnd.nextInt(allMoves.length)];
	}
	
	private void printGameInfo(Game game, boolean pills, boolean paths) {
		if (pills) { // if selected, prints lines towards power pills
			// show lines to pills
			int[] activePowerPills = game.getActivePowerPillsIndices();
			
			for (int i = 0; i < activePowerPills.length; i++)
				GameView.addLines(game, Color.CYAN, game.getPacmanCurrentNodeIndex(), activePowerPills[i]);			
		}

		if (paths) { // if selected, prints ghost to pacman shortest paths
			for (GHOST g : GHOST.values()) {
				int ghost = game.getGhostCurrentNodeIndex(g);
				int mspacman = game.getPacmanCurrentNodeIndex();
				
				// shows shortests direction of ghosts
				if (game.getGhostLairTime(g) <= 0)
					GameView.addPoints(game, colours[g.ordinal()], game.getShortestPath(ghost, mspacman));
			}
		}
	}
}
