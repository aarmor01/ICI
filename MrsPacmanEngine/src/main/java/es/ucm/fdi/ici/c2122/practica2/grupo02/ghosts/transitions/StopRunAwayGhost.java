package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.GhostInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class StopRunAwayGhost implements Transition {
	GHOST ghost;
	int minDistanceToStartChasing = 40;
	
	public StopRunAwayGhost(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {
		GhostInput input = (GhostInput)in;
		switch(ghost) {
		case BLINKY:
			return !input.isBLINKYedible() && input.isBLINKYoutOfLair() && input.getBlinkyDistancePacman() > minDistanceToStartChasing;
		case PINKY:
			return !input.isPINKYedible() && input.isPINKYoutOfLair() && input.getPinkyDistancePacman() > minDistanceToStartChasing;
		case INKY:
			return !input.isINKYedible() && input.isINKYoutOfLair() && input.getInkyDistancePacman() > minDistanceToStartChasing;
		case SUE:
			return !input.isSUEedible() && input.isSUEoutOfLair() && input.getSueDistancePacman() > minDistanceToStartChasing;
		default:
			return false;
		}
	}

	public String toString() {
		return ghost + " is no longer edible and returns to specific behaviour";
	}
}
