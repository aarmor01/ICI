package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class PacManNotInDanger implements Transition {

	public PacManNotInDanger() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput)in;
		// TODO Auto-generated method stub
		return !input.GhostTooCloseToPacMan();
	}
	
	@Override
	public String toString() {
		return "MsPacman is not in danger";
	}

}
