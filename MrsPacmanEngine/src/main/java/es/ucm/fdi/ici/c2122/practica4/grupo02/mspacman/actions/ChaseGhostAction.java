package es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.MsPacManFuzzyMemory;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ChaseGhostAction implements Action {

	MsPacManFuzzyMemory mem;
	public ChaseGhostAction(MsPacManFuzzyMemory mem_) {
		// TODO Auto-generated constructor stub
		mem = mem_;
	}

	@Override
	public String getActionId() {
		// TODO Auto-generated method stub
		return "Chase";
	}

	@Override
	public MOVE execute(Game game) {
		// TODO Auto-generated method stub
		return null;
	}

}
