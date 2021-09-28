package es.ucm.fdi.ici.c2122.practica0.grupoIndividual;

import java.awt.Color;
import java.util.Random;

import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Constants;
import pacman.game.GameView;
import pacman.game.Game;

public final class MsPacManRandom extends PacmanController {
	private Random rnd = new Random();
	private MOVE[] allMoves = MOVE.values();
	private Color[] colours = { Color.RED, Color.PINK, Color.CYAN, Color.YELLOW };

	@Override
	public MOVE getMove(Game game, long timeDue) {
		printGameInfo(game);
		return allMoves[rnd.nextInt(allMoves.length)];
	}

	private void printGameInfo(Game game) {
		// show lines to pills
		int[] activePowerPills = game.getActivePowerPillsIndices();

		for (int i = 0; i < activePowerPills.length; i++)
			GameView.addLines(game, Color.CYAN, game.getPacmanCurrentNodeIndex(), activePowerPills[i]);

		for (Constants.GHOST g : Constants.GHOST.values()) {
			int ghost = game.getGhostCurrentNodeIndex(g);
			int mspacman = game.getPacmanCurrentNodeIndex();

			// shows shortests direction of ghosts
			if (game.getGhostLairTime(g) <= 0)
				GameView.addPoints(game, colours[g.ordinal()], game.getShortestPath(ghost, mspacman));
		}
	}
}