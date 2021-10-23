package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.fsm.Transition;
import es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.MsPacManInput;

public class StopsClearingBottomHalf implements Transition {

	public StopsClearingBottomHalf() {
		super();
	}
	
	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput) in;

		return input.anyGhostOutsideLair();
	}

	public String toString() {
		return "A ghost has entered the maze";
	}
}
