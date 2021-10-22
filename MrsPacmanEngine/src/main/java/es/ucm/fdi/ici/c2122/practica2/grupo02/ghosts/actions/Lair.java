package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Lair implements Action {

	public Lair() {
	}

	@Override
	public String getActionId() {
		return "Ghost in Lair";
	}

	@Override
	public MOVE execute(Game game) {
		return MOVE.NEUTRAL;
	}
}
