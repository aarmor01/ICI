package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.fsm.Transition;
import es.ucm.fdi.ici.c2122.practica2.grupo02.GameConstants;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.GhostInput;
import pacman.game.Constants.GHOST;

public class ChaseSecondaryPathTransition implements Transition {
	
	GHOST ghost;
	
	public ChaseSecondaryPathTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {
		GhostInput input = (GhostInput)in;
		
		if (input.getGhostChaseCount(ghost) >= GameConstants.numIntersectionsBeforeChange) {
			input.resetCount(ghost);
			return true;
		}
		else return false;
	}

	@Override
	public String toString() {
		return ghost + " has been chasing MsPacMan for too long on the same path";
	}
}
