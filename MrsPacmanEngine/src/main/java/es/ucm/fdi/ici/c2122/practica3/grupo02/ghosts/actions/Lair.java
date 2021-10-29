package es.ucm.fdi.ici.c2122.practica3.grupo02.ghosts.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Lair implements Action {

	public Lair() {
		super();
	}

	@Override
	public MOVE execute(Game game) {
		return MOVE.NEUTRAL;
	}
	
	@Override
	public String getActionId() {
		return "Lair";
	}
}
