package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class NoEdibleTimeAndNotInDanger implements Transition {

	public NoEdibleTimeAndNotInDanger() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput)in;
		return !input.canEat() && !input.PacManIsInDanger();
	}
	
	@Override
	public String toString() {
		return "PacMan cant eat Ghost and isn't in danger";
	}
}
