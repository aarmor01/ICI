package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.GhostInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class AnotherGhostIsntEdible implements Transition {
	GHOST ghost;

	public AnotherGhostIsntEdible(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {
		GhostInput input = (GhostInput) in;

		switch(ghost) {
		case BLINKY:
			return (!input.isPINKYedible() && input.isPINKYoutOfLair()) || 
					(!input.isINKYedible() && input.isINKYoutOfLair()) ||
					(!input.isSUEedible() && input.isSUEoutOfLair());
		case PINKY:
			return (!input.isBLINKYedible() && input.isBLINKYoutOfLair()) || 
					(!input.isINKYedible() && input.isINKYoutOfLair()) ||
					(!input.isSUEedible() && input.isSUEoutOfLair());
		case INKY:
			return (!input.isBLINKYedible() && input.isBLINKYoutOfLair()) || 
					(!input.isPINKYedible() && input.isPINKYoutOfLair()) ||
					(!input.isSUEedible() && input.isSUEoutOfLair());
		case SUE:
			return (!input.isBLINKYedible() && input.isBLINKYoutOfLair()) || 
					(!input.isPINKYedible() && input.isPINKYoutOfLair()) ||
					(!input.isINKYedible() && input.isINKYoutOfLair());
		default:
			return false;
		}
	}

	@Override
	public String toString() {
		return ghost + " goes towards a non-edible ghost";
	}
}
