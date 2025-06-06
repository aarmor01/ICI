package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.fsm.Transition;

public class GoToMainChase implements Transition {
	
	public GoToMainChase() {
		super();
	}
	
	@Override
	public boolean evaluate(Input in) {
		return true;
	}

	@Override
	public String toString() {
		return "Direct transition towards main chase action";
	}
}
