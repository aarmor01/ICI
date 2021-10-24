package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class Mole implements Action {
	
	GHOST ghostType;
	
	public Mole(GHOST ghostType) {
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
			int [] intersectionNodeFound = {0,0,0};
			int [] numPillsFound = {0,0,0};
			int contador = 0;
			for (int node : neighbouringNodes) {
				while(!intersectionFound) {
					MOVE lastMoveMade = MOVE.NEUTRAL;					
					if (game.getNodeYCood(node) - game.getNodeYCood(lastNode) == 1 ||
							game.getNodeYCood(node) - game.getNodeYCood(lastNode) < -10)
						lastMoveMade = MOVE.DOWN;
					else if (game.getNodeYCood(node) - game.getNodeYCood(lastNode) == -1 || 
							game.getNodeYCood(node) - game.getNodeYCood(lastNode) > 10)
						lastMoveMade = MOVE.UP;
					else if(game.getNodeXCood(node) - game.getNodeXCood(lastNode) == 1 || 
							game.getNodeXCood(node) - game.getNodeXCood(lastNode) < -10)
						lastMoveMade = MOVE.RIGHT;
					else if (game.getNodeXCood(node) - game.getNodeXCood(lastNode) == -1 || 
							game.getNodeXCood(node) - game.getNodeXCood(lastNode) > 10)
						lastMoveMade = MOVE.LEFT;
					
					if (game.isJunction(node)) {
						intersectionFound = true;
						intersectionNodeFound[contador] = node;
					}
					else {
						if (game.isPillStillAvailable(node))
							numPillsFound[contador]++;
						//We move the node to the next position
						lastNode = node;
						node = game.getNeighbouringNodes(node, lastMoveMade)[0];
					}
				}
				contador++;
			}
			
			int optimalNode = 0;
			int maxNumberOfPills = 0;
			contador = 0;
			for (int node : intersectionNodeFound) {
				if (numPillsFound[contador] > maxNumberOfPills) {
					maxNumberOfPills = numPillsFound[contador];
					optimalNode = node;
				}
			}
			if (optimalNode == 0)
				return game.getNextMoveTowardsTarget(ghostNode, pacmanNode, 
						game.getGhostLastMoveMade(ghostType), DM.PATH);
			else
				return game.getNextMoveTowardsTarget(ghostNode, optimalNode, 
						game.getGhostLastMoveMade(ghostType), DM.PATH);
		}
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghostType + " mole";
	}
}
