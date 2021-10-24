package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class Ambush implements Action {
	GHOST ghostType;
	public Ambush(GHOST ghostType) {
		this.ghostType = ghostType;
	}

	@Override
	public MOVE execute(Game game) {
		if (game.doesGhostRequireAction(ghostType)) {
			int pacmanNode = game.getPacmanCurrentNodeIndex();
			int ghostNode = game.getGhostCurrentNodeIndex(ghostType);
			
			int[] neighbouringNodes;
			neighbouringNodes = game.getNeighbouringNodes(pacmanNode, game.getPacmanLastMoveMade());
			
			int lastNode = pacmanNode;
			boolean intersectionFound = false;
			int intersectionNodeFound = 0;
			int actualNode = 0;
			while(!intersectionFound) {
				actualNode = 0;
				for (int node : neighbouringNodes) {
					MOVE lastMoveMade = MOVE.NEUTRAL;					
					if (game.getNodeYCood(node) - game.getNodeYCood(lastNode) == 1 || game.getNodeYCood(node) - game.getNodeYCood(lastNode) < -20)
						lastMoveMade = MOVE.DOWN;
					else if (game.getNodeYCood(node) - game.getNodeYCood(lastNode) == -1 || game.getNodeYCood(node) - game.getNodeYCood(lastNode) > 20)
						lastMoveMade = MOVE.UP;
					else if(game.getNodeXCood(node) - game.getNodeXCood(lastNode) == 1 || game.getNodeXCood(node) - game.getNodeXCood(lastNode) < -20)
						lastMoveMade = MOVE.RIGHT;
					else if (game.getNodeXCood(node) - game.getNodeXCood(lastNode) == -1 || game.getNodeXCood(node) - game.getNodeXCood(lastNode) > 20)
						lastMoveMade = MOVE.LEFT;
					
					if (game.isJunction(node)) {
						intersectionFound = true;
						intersectionNodeFound = node;
					}
					else {
						//We move the node to the next position
						lastNode = node;
						neighbouringNodes[actualNode] = game.getNeighbouringNodes(node, lastMoveMade)[0];
					}
					actualNode++;
				}
			}
			return game.getNextMoveTowardsTarget(ghostNode, intersectionNodeFound, game.getGhostLastMoveMade(ghostType), DM.PATH);
		}
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghostType + " goes towards next possible junction";
	}
}
