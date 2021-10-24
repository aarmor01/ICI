package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class NotPillsInRange implements Transition {

	String id = "";
	public NotPillsInRange(String id_) {
		id = id_;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput)in;
		// TODO Auto-generated method stub
		return !input.pillsInRange();
	}
	
	@Override
	public String toString() {
		return "Not Pills In Range " + id;
	}

}
