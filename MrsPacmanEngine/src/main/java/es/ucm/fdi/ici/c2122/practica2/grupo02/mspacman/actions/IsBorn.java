package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class IsBorn implements Action {

	public IsBorn() {
	}

	@Override
	public String getActionId() {
		return "Is Born";
	}

	@Override
	public MOVE execute(Game game) {
		return MOVE.NEUTRAL;
	}

}
