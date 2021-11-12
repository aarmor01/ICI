package es.ucm.fdi.ici.c2122.practica3.grupo02.rules.ghosts.actions;

import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Lair implements RulesAction {

	public Lair() {
		super();
	}	
	
	@Override
	public String getActionId() {
		return "Lair";
	}

	@Override
	public MOVE execute(Game game) {
		return MOVE.NEUTRAL;
	}

	@Override
	public void parseFact(Fact actionFact) {
		// TODO Auto-generated method stub
		
	}
}
