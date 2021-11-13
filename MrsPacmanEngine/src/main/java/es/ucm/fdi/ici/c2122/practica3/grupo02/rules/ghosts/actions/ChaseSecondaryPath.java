package es.ucm.fdi.ici.c2122.practica3.grupo02.rules.ghosts.actions;

import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class ChaseSecondaryPath implements RulesAction {

	GHOST ghostType;
	
	public ChaseSecondaryPath(GHOST ghost) {
		super();
		this.ghostType = ghost;
	}

	@Override
	public String getActionId() {
		return ghostType + "Chase2";
	}

	@Override
	public MOVE execute(Game game) {
        if (game.doesGhostRequireAction(ghostType))        //if it requires an action
        {
        	int ghostNode = game.getGhostCurrentNodeIndex(ghostType);
        	int pacmanNode = game.getPacmanCurrentNodeIndex();
        	MOVE[] possibleMoves = game.getPossibleMoves(ghostNode);
        	int numChoices = possibleMoves.length;

        	// if two possible moves, we go the opposite way (away instead of towards)
    		if (numChoices == 2) 
    			return game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode,
    					game.getGhostLastMoveMade(ghostType), DM.PATH);
    		else { 
    			// if three possible moves, we go to the one that isn't the furthest nor the closest to the Pacman
    			MOVE optimalMove = game.getApproximateNextMoveTowardsTarget(ghostNode, pacmanNode,
    					game.getGhostLastMoveMade(ghostType), DM.PATH);

    			MOVE awayMove = game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode,
    					game.getGhostLastMoveMade(ghostType), DM.PATH);

    			// we select the one that isn't the furthest nor the closest
    			for(MOVE move : possibleMoves) { // i know at least one of them is going to be returned
    				if (move != optimalMove && move != awayMove)
    					return move;
    			}
    		}
        }
        
        return MOVE.NEUTRAL;
	}
	
	@Override
	public void parseFact(Fact actionFact) {
		// TODO Auto-generated method stub
		
	}
}
