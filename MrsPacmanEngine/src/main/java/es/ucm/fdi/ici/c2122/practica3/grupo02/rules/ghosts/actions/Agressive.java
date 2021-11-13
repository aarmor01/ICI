package es.ucm.fdi.ici.c2122.practica3.grupo02.rules.ghosts.actions;

import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class Agressive implements RulesAction {
	
	GHOST ghostType;
	
	public Agressive(GHOST ghostType) {
		super();
		this.ghostType = ghostType;
	}
	
	@Override
	public String getActionId() {
		return ghostType + "Agressive";
	}

	@Override
	public MOVE execute(Game game) {
		if (game.doesGhostRequireAction(ghostType)) {
			int pacmanNode = game.getPacmanCurrentNodeIndex();
			int ghostNode = game.getGhostCurrentNodeIndex(ghostType);
			MOVE move = game.getGhostLastMoveMade(ghostType);
			
			System.out.print(game.getNextMoveTowardsTarget(ghostNode, pacmanNode, move, DM.PATH) + "\n");
			
			return game.getNextMoveTowardsTarget(ghostNode, pacmanNode, move, DM.PATH);
		}
		return MOVE.NEUTRAL;
	}
	
	@Override
	public void parseFact(Fact actionFact) {
		// TODO Auto-generated method stub
		
	}
}
