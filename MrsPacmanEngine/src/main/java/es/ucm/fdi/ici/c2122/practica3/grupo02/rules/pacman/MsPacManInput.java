package es.ucm.fdi.ici.c2122.practica3.grupo02.rules.pacman;

import java.util.Collection;
import java.util.Random;
import java.util.Vector;

import es.ucm.fdi.ici.c2122.practica3.grupo02.GameConstants;
import es.ucm.fdi.ici.c2122.practica3.grupo02.Utils;
import es.ucm.fdi.ici.rules.RulesInput;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacManInput extends RulesInput {
	
	
	private boolean isFirstMatch;
	
	private boolean canEatGhost;
	
	private boolean ghostOutsideLair;

	private int nearestGhostDistance;
	
	private boolean existenPillsAbajo;
	
	private boolean caminoBloqueado;
	
	private Random rnd = new Random();
	
	public MsPacManInput(Game game) {
		super(game);	
	}

	@Override
	public void parseInput() {
		
		isFirstMatch = game.getPacmanNumberOfLivesRemaining() == 3;
		
		existenPillsAbajo = ExistingBottomPills();
		
		caminoBloqueado = PathMayBeObstructed();
		
		canEatGhost = game.isGhostEdible(GHOST.BLINKY) || game.isGhostEdible(GHOST.PINKY)
				|| game.isGhostEdible(GHOST.INKY) || game.isGhostEdible(GHOST.SUE);
				
//		
//		ghostOutsideLair = !(game.getGhostLairTime(GHOST.BLINKY) > 0) || !(game.getGhostLairTime(GHOST.PINKY) > 0)
//				|| !(game.getGhostLairTime(GHOST.INKY) > 0) || !(game.getGhostLairTime(GHOST.SUE) > 0);
//		
//		GHOST g = Tools.nearestGhost(game); 
//		
//		if(g != null) {
//			nearestGhostDistance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), 
//					game.getGhostCurrentNodeIndex(g), game.getPacmanLastMoveMade());
//		}
		
	}

	public boolean canEat() {
		return canEatGhost;
	}
	
	public boolean anyGhostOutsideLair() {
		return ghostOutsideLair;
	}
	
	public int closestGhostDistance() {
		return nearestGhostDistance;
	}

	public boolean ExistingBottomPills() {
		int[] activePills = game.getActivePillsIndices();
		for (int activePill : activePills)
			if(game.getNodeYCood(activePill) <= game.getNodeYCood(game.getPacManInitialNodeIndex()))
				return true;
		
		return false;
	}
	
	public boolean pillsInRange() {
		int pcNode = game.getPacmanCurrentNodeIndex();
		MOVE[] possibleMoves = game.getPossibleMoves(pcNode);
		for(MOVE moves : possibleMoves){
			int nodeDir = game.getNeighbour(pcNode, moves);
			while(nodeDir != -1 && !game.isJunction(nodeDir)) {
				if(game.isPillStillAvailable(nodeDir)) return true;
				nodeDir = game.getNeighbour(nodeDir, moves);
			}	
		}
		return false;
	}
	
	public boolean GhostTooCloseToPacMan() {
		return Utils.nearestGhostInRange(game, GameConstants.pacmanChaseDistance) != null;
	}
	
	public boolean PacManIsInDanger() {
		GHOST g = Utils.nearestGhostInRange(game, GameConstants.pacmanChaseDistance);
		if(g!= null)
			return !game.isGhostEdible(g);
		else return false;
	}
	
	public boolean MoreThanOnePillsInRange() {
		int pcNode = game.getPacmanCurrentNodeIndex();
		MOVE[] possibleMoves = game.getPossibleMoves(pcNode);
		int minPills = 1;
		for(MOVE moves : possibleMoves){
			int nPills = 0;
			int nodeDir = game.getNeighbour(pcNode, moves);
			while(nodeDir != -1 && !game.isJunction(nodeDir)) {
				if(game.isPillStillAvailable(nodeDir)) {
					nPills++;
					if(nPills > minPills) return true;
				}
				nodeDir = game.getNeighbour(nodeDir, moves);
			}	
		}
		return false;
	}
	
	public boolean PathMayBeObstructed() {
		int pcNode = game.getPacmanCurrentNodeIndex();
		int nodeTarget = Utils.nodeTarget;
		
		if(nodeTarget != -1) {
			//We get all the nodes that take MsPacMan to the pill
			int[] path = game.getShortestPath(pcNode, nodeTarget);
			
			//For each Ghost we check if block up the MsPacMan path or may block it up.
			
			//Min distance to predict if the nearest Ghost can block up MsPacMan's path 
			int obstructionLimit = 40;
			
			for(int pathNode : path) {			
				for(GHOST ghostType: GHOST.values()) {
					if(game.getGhostLairTime(ghostType)<=0) {
						if(pathNode == game.getGhostCurrentNodeIndex(ghostType))
							return true;
						
						int ghostIndex = game.getGhostCurrentNodeIndex(ghostType);
						
						int distance = game.getShortestPathDistance(ghostIndex, pathNode, game.getGhostLastMoveMade(ghostType));
						if (distance < obstructionLimit) 
							return true;
					}
				}
			}
		}
		
		return false;
	}
		
	public boolean PathBlockedByGhost() {
		int pcNode = game.getPacmanCurrentNodeIndex();
		
		int nodeTarget = Utils.getPathWithMorePills(game, pcNode);
		
		
		//Min distance to predict if the nearest Ghost can block up MsPacMan's path 
		//We get all the nodes that take MsPacMan to the pill
		int[] path = game.getShortestPath(pcNode, nodeTarget);
		
		//int ghostIndex = game.getGhostCurrentNodeIndex(nearestGhostType_);
		
		//For each Ghost we check if block up the MsPacMan path or may block it up.
		//For each node we check if there's a Ghost, or if it is a junction, we check if it could be a Ghost in the 
		//surroundings
		int maxNodesDistance = 4;
		int maxSurroundingsDistance = maxNodesDistance * 10;
		
		for(int pathNode : path) {			
			for(GHOST ghostType: GHOST.values()) {
				if(pathNode == game.getGhostCurrentNodeIndex(ghostType))
					return false;	
			}
			
			if(game.isJunction(pathNode)) {
				MOVE[] possibleMoves = game.getPossibleMoves(pathNode);
				for(MOVE move : possibleMoves) {
					int[] neighbourNodes = game.getNeighbouringNodes(pathNode, move);
					if(neighbourNodes != null) {
						int nextNode = neighbourNodes[0];
						MOVE actualMove = move;
						for(int i = 0; i < maxNodesDistance; i++) {
							for(GHOST ghostType: GHOST.values()) {
								int distance = game.getShortestPathDistance(nextNode, pathNode, game.getGhostLastMoveMade(ghostType));
								if(nextNode == game.getGhostCurrentNodeIndex(ghostType) && distance < maxSurroundingsDistance)
									return true;	
							}
							
							int[] possibleNextNodes = game.getNeighbouringNodes(nextNode, actualMove);
							//If the array is null, is because that node is a wall. 
							if(possibleNextNodes != null) {
								actualMove = game.getMoveToMakeToReachDirectNeighbour(nextNode, possibleNextNodes[0]);
								nextNode = possibleNextNodes[0];
							}else break;
						}
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public Collection<String> getFacts() {
		Vector<String> facts = new Vector<String>();
		facts.add(String.format("(MSPACMAN (existenPillsAbajo %s))", this.existenPillsAbajo));
		facts.add(String.format("(MSPACMAN (caminoBloqueado %s))", this.caminoBloqueado));
		
//		facts.add(String.format("(INKY (edible %s))", this.INKYedible));
//		facts.add(String.format("(PINKY (edible %s))", this.PINKYedible));
//		facts.add(String.format("(SUE (edible %s))", this.SUEedible));
//		facts.add(String.format("(MSPACMAN (mindistancePPill %d))", 
//								(int)this.minPacmanDistancePPill));
		return facts;
	}
}

