package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.fsm.Transition;
import es.ucm.fdi.ici.c2122.practica2.grupo02.GameConstants;
import es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.MsPacManInput;

public class CanChaseGhost implements Transition {

	public CanChaseGhost() {
		super();
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput)in;
		
		return input.canEat() && input.closestGhostDistance() < GameConstants.pacmanChaseDistance;
	}
	
	@Override
	public String toString() {
		return "Pacman has eaten PP and ghost nearby";
	}

}
