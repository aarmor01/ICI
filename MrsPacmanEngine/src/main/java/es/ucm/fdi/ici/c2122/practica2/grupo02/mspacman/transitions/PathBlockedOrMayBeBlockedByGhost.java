package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class PathBlockedOrMayBeBlockedByGhost implements Transition {

	String id = "";
	public PathBlockedOrMayBeBlockedByGhost(String id_) {
		id = id_;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput)in;
		
		// TODO Auto-generated method stub
		return input.PathBlockedByGhost();
	}
	
	@Override
	public String toString() {
		return "Path Blocked Or May Be Blocked By Ghost " + id;
	}

}
