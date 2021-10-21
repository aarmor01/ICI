package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.fsm.Transition;

public class PathNotBlocked implements Transition {

	public PathNotBlocked() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean evaluate(Input in) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return "Path Not Blocked";
	}
}
