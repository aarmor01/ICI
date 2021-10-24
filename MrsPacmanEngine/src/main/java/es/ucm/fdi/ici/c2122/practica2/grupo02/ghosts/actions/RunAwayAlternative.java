package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class RunAwayAlternative implements Action {
	
	GHOST ghostType;
	
	public RunAwayAlternative(GHOST ghostType) {
		super();
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
			boolean[] ghostInTheWay = {false, false, false};
			int[] destinationNodes = {0,0,0};
			int contador = 0;
			while(!intersectionFound) {
				contador = 0;
				for (int node : neighbouringNodes) {
					MOVE lastMoveMade = MOVE.NEUTRAL;					
					if (game.getNodeYCood(node) - game.getNodeYCood(lastNode) == 1 || game.getNodeYCood(node) - game.getNodeYCood(lastNode) < -10)
						lastMoveMade = MOVE.DOWN;
					else if (game.getNodeYCood(node) - game.getNodeYCood(lastNode) == -1 || game.getNodeYCood(node) - game.getNodeYCood(lastNode) > 10)
						lastMoveMade = MOVE.UP;
					else if(game.getNodeXCood(node) - game.getNodeXCood(lastNode) == 1 || game.getNodeXCood(node) - game.getNodeXCood(lastNode) < -10)
						lastMoveMade = MOVE.RIGHT;
					else if (game.getNodeXCood(node) - game.getNodeXCood(lastNode) == -1 || game.getNodeXCood(node) - game.getNodeXCood(lastNode) > 10)
						lastMoveMade = MOVE.LEFT;
					
					for(GHOST ghost : GHOST.values()) {
						if (game.getGhostCurrentNodeIndex(ghost) == node)
							ghostInTheWay[contador] = true;
					}
					
					if (game.isJunction(node)) {
						intersectionFound = true;
						destinationNodes[contador] = node;
					}else {
						//We move the node to the next position
						lastNode = node;
						node = game.getNeighbouringNodes(node, lastMoveMade)[0];
					}
					contador++;
				}
			}
			
			int destination = 0;
			int numberOfPossiblePaths = 0;
			contador = 0;
			for (boolean possiblePath : ghostInTheWay) {
				if (possiblePath) {
					numberOfPossiblePaths++;
					if (numberOfPossiblePaths == 1)
						destination = destinationNodes[contador];
					else {
						if (numberOfPossiblePaths > 1) {
							if (game.getShortestPathDistance(destinationNodes[contador], pacmanNode) > game.getShortestPathDistance(destination, pacmanNode)) {
								destination = destinationNodes[contador];
							}
						}
					}
				}
				contador++;
			}
			return game.getNextMoveTowardsTarget(ghostNode, destination, game.getGhostLastMoveMade(ghostType), DM.PATH);
		}
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghostType + " runs away by alternate path";
	}
}
