package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.GhostInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class IsMsPacManNear implements Transition {
	GHOST ghost;
	int minDistanceToStartChasing = 40;
	
	public IsMsPacManNear(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {
		GhostInput input = (GhostInput)in;
		switch(ghost) {
			case BLINKY:
				return input.getBlinkyDistancePacman() <= minDistanceToStartChasing;
			case PINKY:
				return input.getPinkyDistancePacman() <= minDistanceToStartChasing;
			case INKY:
				return input.getInkyDistancePacman() <= minDistanceToStartChasing;
			case SUE:
				return input.getSueDistancePacman() <= minDistanceToStartChasing;
			default:
				return false;
		}
		
	}

	@Override
	public String toString() {
		return ghost + " starts chasing MsPacMan";
	}
}
