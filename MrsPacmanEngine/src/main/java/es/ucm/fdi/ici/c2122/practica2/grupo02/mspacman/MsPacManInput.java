package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman;

import es.ucm.fdi.ici.Input;
import pacman.game.Game;
import pacman.game.Constants.GHOST;

public class MsPacManInput extends Input{
	private boolean canEatGhost;  
	
	public MsPacManInput(Game game) {
		super(game);
		
	}

	@Override
	public void parseInput() {
		pMCanEat();
	}
	
	public boolean canEat() {
		return canEatGhost;
	}
	
	private void pMCanEat() {
		canEatGhost = false;
		for (GHOST ghostType : GHOST.values())
			if(game.isGhostEdible(ghostType)) {
				canEatGhost = true;
				return;
			}
	}
}
