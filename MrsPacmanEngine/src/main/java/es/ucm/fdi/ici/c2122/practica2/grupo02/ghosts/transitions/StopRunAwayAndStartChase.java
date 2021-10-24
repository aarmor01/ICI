package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.GameConstants;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.GhostInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class StopRunAwayAndStartChase implements Transition {
	
	GHOST ghost;
	
	public StopRunAwayAndStartChase(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {
		GhostInput input = (GhostInput)in;
		switch(ghost) {
		case BLINKY:
			return !input.isBLINKYedible() && input.isBLINKYoutOfLair() 
					&& input.getBlinkyDistancePacman() <= GameConstants.ghostChaseDistance;
		case PINKY:
			return !input.isPINKYedible() && input.isPINKYoutOfLair() 
					&& input.getPinkyDistancePacman() <= GameConstants.ghostChaseDistance;
		case INKY:
			return !input.isINKYedible() && input.isINKYoutOfLair() 
					&& input.getInkyDistancePacman() <= GameConstants.ghostChaseDistance;
		case SUE:
			return !input.isSUEedible() && input.isSUEoutOfLair() 
					&& input.getSueDistancePacman() <= GameConstants.ghostChaseDistance;
		default:
			return false;
		}
	}

	public String toString() {
		return ghost + " is no longer edible and starts chasing";
	}
}
