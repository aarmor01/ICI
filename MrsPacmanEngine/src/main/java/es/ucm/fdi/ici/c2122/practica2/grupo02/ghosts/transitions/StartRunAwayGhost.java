package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.GhostInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class StartRunAwayGhost implements Transition {

	GHOST ghost;

	public StartRunAwayGhost(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {
		GhostInput input = (GhostInput) in;
		switch (ghost) {
		case BLINKY:
			return input.isBLINKYedible();
		case INKY:
			return input.isINKYedible();
		case PINKY:
			return input.isPINKYedible();
		case SUE:
			return input.isSUEedible();
		default:
			return false;
		}
	}

	@Override
	public String toString() {
		return ghost + " starts running away";
	}
}
