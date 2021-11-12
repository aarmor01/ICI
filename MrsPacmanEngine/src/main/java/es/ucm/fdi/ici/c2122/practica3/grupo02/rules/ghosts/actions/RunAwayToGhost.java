package es.ucm.fdi.ici.c2122.practica3.grupo02.rules.ghosts.actions;

import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class RunAwayToGhost implements RulesAction {

	GHOST ghostType;

	public RunAwayToGhost(GHOST ghostType) {
		super();
		this.ghostType = ghostType;
	}
	
	@Override
	public String getActionId() {
		return ghostType + "runstoghost";
	}

	@Override
	public MOVE execute(Game game) {
		if (game.doesGhostRequireAction(ghostType)) {
			int pacmanNode = game.getPacmanCurrentNodeIndex();
			int ghostNode = game.getGhostCurrentNodeIndex(ghostType);

			int closestGhostNode = -1;
			int closestDistance = Integer.MAX_VALUE;

			for (GHOST otherGhost : GHOST.values()) {
				int otherGhostNode = game.getGhostCurrentNodeIndex(otherGhost);
				if (otherGhostNode != ghostNode && !game.isGhostEdible(otherGhost)
						&& game.getGhostLairTime(otherGhost) == 0) {
					int distanceToOtherGhost = game.getShortestPathDistance(ghostNode, otherGhostNode);
					if (distanceToOtherGhost < closestDistance) {
						closestDistance = distanceToOtherGhost;
						closestGhostNode = otherGhostNode;
					}
				}
			}

			if (closestGhostNode != -1)
				return game.getNextMoveTowardsTarget(ghostNode, closestGhostNode, 
						game.getGhostLastMoveMade(ghostType), DM.PATH);
			else
				return game.getNextMoveAwayFromTarget(ghostNode, pacmanNode, 
						game.getGhostLastMoveMade(ghostType), DM.PATH);
		}

		return MOVE.NEUTRAL;
	}
	
	@Override
	public void parseFact(Fact actionFact) {
		// TODO Auto-generated method stub
		
	}
}