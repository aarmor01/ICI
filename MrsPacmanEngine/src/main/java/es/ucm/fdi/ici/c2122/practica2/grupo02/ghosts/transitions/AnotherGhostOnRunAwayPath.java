package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.GhostInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class AnotherGhostOnRunAwayPath implements Transition {
	GHOST ghost;
	
	public AnotherGhostOnRunAwayPath(GHOST ghost) {
		super();
		this.ghost = ghost;
	}
	
	@Override
	public boolean evaluate(Input in) {
		GhostInput input = (GhostInput)in;
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return ghost + " changes direction to not use the same path as another ghost";
	}
}
