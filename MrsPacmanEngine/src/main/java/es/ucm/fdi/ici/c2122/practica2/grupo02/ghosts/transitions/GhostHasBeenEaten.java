package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.GhostInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class GhostHasBeenEaten implements Transition {

	GHOST ghost;

	public GhostHasBeenEaten(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {
GhostInput input = (GhostInput) in;
		
		switch (ghost) {
		case BLINKY:
			return !input.isBLINKYoutOfLair();
		case PINKY:
			return !input.isPINKYoutOfLair();
		case INKY:
			return !input.isINKYoutOfLair();
		case SUE:
			return !input.isSUEoutOfLair();
		default:
			return false;
		}
	}

	@Override
	public String toString() {
		return ghost + " has been eaten";
	}
}
