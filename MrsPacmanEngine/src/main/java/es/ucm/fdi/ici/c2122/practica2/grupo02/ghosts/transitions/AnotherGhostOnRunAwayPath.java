package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.fsm.Transition;

public class AnotherGhostOnRunAwayPath implements Transition {

	public AnotherGhostOnRunAwayPath() {
		super();
	}
	
	@Override
	public boolean evaluate(Input in) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return ghost + " has been chasing MsPacMan for too long on the same path";
	}
}
