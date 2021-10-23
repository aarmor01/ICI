package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.GhostInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class StartRunAwayGhost implements Transition {

	GHOST ghost;
	int minDistanceGhostIgnoresEdible = 30;
	int minTimeBeforeIgnoresEdible = 1000;
	public StartRunAwayGhost(GHOST ghost) {
		super();
		this.ghost = ghost;
	}
	
	@Override
	public boolean evaluate(Input in) {
		GhostInput input = (GhostInput)in;
		switch(ghost) {
		case BLINKY:
			if(input.isBLINKYedible()) {
				if (!(input.getBlinkyDistancePacman() > minDistanceGhostIgnoresEdible && 
						input.getBLINKYedibleTimeLeft() < minTimeBeforeIgnoresEdible)) {
					return true;
				}
			}
			break;
		case INKY:
			if(input.isINKYedible()) {
				if (!(input.getInkyDistancePacman() > minDistanceGhostIgnoresEdible && 
						input.getINKYedibleTimeLeft() < minTimeBeforeIgnoresEdible)) {
					return true;
				}
			}
			break;
			
		case PINKY:
			if(input.isPINKYedible()) {
				if (!(input.getPinkyDistancePacman() > minDistanceGhostIgnoresEdible && 
						input.getPINKYedibleTimeLeft() < minTimeBeforeIgnoresEdible)) {
					return true;
				}
			}
			break;
		case SUE:
			if(input.isSUEedible()) {
				if (!(input.getSueDistancePacman() > minDistanceGhostIgnoresEdible && 
						input.getSUEedibleTimeLeft() < minTimeBeforeIgnoresEdible)) {
					return true;
				}
			}
			break;
		}
		return false;
	}

	@Override
	public String toString() {
		return ghost + " starts running away";
	}
}
