package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.GhostInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class SeerSpecialChaseStart implements Transition {
	
	GHOST ghost;
	
	public SeerSpecialChaseStart(GHOST ghost) {
		super();
		this.ghost = ghost;
	}
	
	@Override
	public boolean evaluate(Input in) {
		GhostInput input = (GhostInput)in;
		
		switch(ghost) {
			case BLINKY:
				return input.getBLINKYposition() == input.getNextPillPacManBySeer();
			default:
				return false;
		}
	}

	@Override
	public String toString() {
		return "Seer reached predicted pill and started chasing MsPacMan";
	}
}
