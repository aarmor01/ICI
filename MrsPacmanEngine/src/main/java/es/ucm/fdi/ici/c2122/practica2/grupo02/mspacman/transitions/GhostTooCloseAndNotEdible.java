package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class GhostTooCloseAndNotEdible implements Transition {

	public GhostTooCloseAndNotEdible() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput)in;
		// TODO Auto-generated method stub
		boolean danger = input.PacManIsInDanger(); 
		
		return danger;
	}
	
	@Override
	public String toString() {
		return "Ghost not edible and too close to PacMan";
	}

}
