package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class MoreThanOnePill_InRange implements Transition {

	public MoreThanOnePill_InRange() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput)in;
		
		return input.MoreThanOnePillsInRange();
	}
	
	@Override
	public String toString() {
		return "More Than 1 Pill In Range";
	}

}
