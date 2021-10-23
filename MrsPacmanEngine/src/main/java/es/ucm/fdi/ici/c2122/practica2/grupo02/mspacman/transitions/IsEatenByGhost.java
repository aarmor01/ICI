package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.fsm.Transition;

public class IsEatenByGhost implements Transition {

	public IsEatenByGhost() {
		super();
	}

	@Override
	public boolean evaluate(Input in) {
		return true;
	}
	
	@Override
	public String toString() {
		return "MsPacman was eaten by a ghost";
	}
}
