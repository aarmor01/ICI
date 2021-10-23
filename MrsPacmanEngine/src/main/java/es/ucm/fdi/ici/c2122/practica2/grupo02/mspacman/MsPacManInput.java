package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.Tools;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class MsPacManInput extends Input {
	
	private boolean canEatGhost;
	
	private boolean ghostOutsideLair;

	private int nearestGhostDistance;
	
	public MsPacManInput(Game game) {
		super(game);
	}

	@Override
	public void parseInput() {
		canEatGhost = game.isGhostEdible(GHOST.BLINKY) || game.isGhostEdible(GHOST.PINKY)
				|| game.isGhostEdible(GHOST.INKY) || game.isGhostEdible(GHOST.SUE);
		
		ghostOutsideLair = !(game.getGhostLairTime(GHOST.BLINKY) > 0) || !(game.getGhostLairTime(GHOST.PINKY) > 0)
				|| !(game.getGhostLairTime(GHOST.INKY) > 0) || !(game.getGhostLairTime(GHOST.SUE) > 0);
		
		nearestGhostDistance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), 
				game.getGhostCurrentNodeIndex(Tools.nearestGhost(game)), game.getPacmanLastMoveMade());
	}

	public boolean canEat() {
		return canEatGhost;
	}
	
	public boolean anyGhostOutsideLair() {
		return ghostOutsideLair;
	}
	
	public int closestGhostDistance() {
		return nearestGhostDistance;
	}

	public boolean ExistingBottomPills() {
		int[] activePills = game.getActivePillsIndices();
		for (int activePill : activePills)
			if(game.getNodeYCood(activePill) <= game.getNodeYCood(game.getPacManInitialNodeIndex()))
				return true;
		
		return false;
	}	
}
