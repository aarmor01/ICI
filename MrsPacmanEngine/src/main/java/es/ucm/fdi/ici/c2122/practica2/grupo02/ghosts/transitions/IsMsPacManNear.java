package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.GameConstants;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.GhostInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class IsMsPacManNear implements Transition {

	GHOST ghost;

	public IsMsPacManNear(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {
		GhostInput input = (GhostInput) in;

		switch (ghost) {
		case BLINKY:
			if (input.getBlinkyDistancePacman() <= GameConstants.ghostChaseDistance) {
				input.resetCount(ghost);
				return true;
			}
			break;
		case PINKY:
			if (input.getPinkyDistancePacman() <= GameConstants.ghostChaseDistance) {
				input.resetCount(ghost);
				return true;
			}
			break;
		case INKY:
			if (input.getInkyDistancePacman() <= GameConstants.ghostChaseDistance) {
				input.resetCount(ghost);
				return true;

			}
			break;
		case SUE:
			if (input.getSueDistancePacman() <= GameConstants.ghostChaseDistance) {
				input.resetCount(ghost);
				return true;
			}
			break;
		default:
			return false;
		}
		return false;
	}

	@Override
	public String toString() {
		return ghost + " starts chasing MsPacMan";
	}
}
