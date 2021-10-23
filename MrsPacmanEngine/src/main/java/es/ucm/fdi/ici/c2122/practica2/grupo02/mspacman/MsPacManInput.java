package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.Tools;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class MsPacManInput extends Input {
	
	private boolean canEatGhost;

	private int chaseDistance = 40;
	private int nearestGhostDistance;
	
	
	public MsPacManInput(Game game) {
		super(game);
	}

	@Override
	public void parseInput() {
		canEatGhost = game.isGhostEdible(GHOST.BLINKY) || game.isGhostEdible(GHOST.PINKY)
				|| game.isGhostEdible(GHOST.INKY) || game.isGhostEdible(GHOST.SUE);
		
		nearestGhostDistance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), 
				game.getGhostCurrentNodeIndex(Tools.nearestGhost(game)), game.getPacmanLastMoveMade());
	}

	public boolean canEat() {
		return canEatGhost;
	}
	
	public int closestGhostDistance() {
		return nearestGhostDistance;
	}
}
