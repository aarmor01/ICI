package es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.actions;


import java.util.HashMap;
import java.util.Random;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2122.practica2.grupo02.GameConstants;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.MsPacManFuzzyMemory;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.MsPacManFuzzyMemory.GhostState;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class RunAwayAction implements Action {
    
	private Random rnd = new Random();
    private MOVE[] allMoves = MOVE.values();
    MsPacManFuzzyMemory mem;
	public RunAwayAction(MsPacManFuzzyMemory mem_) {
		mem = mem_;
	}
	
	GHOST nearestGhost(Game game) {
		int pcNode = game.getPacmanCurrentNodeIndex();
		double distance = Integer.MAX_VALUE;
		GHOST ghost = null;
		for(GHOST g: GHOST.values()) {
			int pos = game.getGhostCurrentNodeIndex(g);
			if(pos != -1) {
				double aux = game.getDistance(game.getPacmanCurrentNodeIndex(), pos, DM.PATH);
				if(aux < distance) {
					distance = aux;
					ghost = g;
				}
			}
		}
		
		return ghost;
	}
	
	int lastMoveNearestGhost(Game game) {
		int target = -1;
		int pcNode = game.getPacmanCurrentNodeIndex();
		double distance = Double.MAX_VALUE;
		for (HashMap.Entry<GHOST,GhostState> entry : mem.ghostsSeen.entrySet()) {
			
			int auxDis = game.getShortestPathDistance(pcNode, entry.getValue().ghostNode, game.getPacmanLastMoveMade());
			//Si es mas pequeña su distancia y si la pill está activa
			if(auxDis < distance) {
				distance = auxDis;
				target = game.getNeighbour(entry.getValue().ghostNode, entry.getValue().move); 
				
				if(target == -1) target = entry.getValue().ghostNode;
			}
			
		}
		
		return target;
	}
	
	@Override
	public MOVE execute(Game game) {
		GHOST g = nearestGhost(game);
		 //If this actions has been triggered, then there must be a ghost in the actual sight
		int nodeGhost = -1;
		
		if(g == null) nodeGhost = lastMoveNearestGhost(game);
		if(g != null || nodeGhost != -1) {
			if(g!= null) nodeGhost = game.getGhostCurrentNodeIndex(g);
			
			if(GameConstants.DEBUG)
			GameView.addPoints(game, java.awt.Color.BLUE, game.getShortestPath(game.getPacmanCurrentNodeIndex(), nodeGhost));	
			return game.getApproximateNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), 
															 nodeGhost, 
															 game.getPacmanLastMoveMade(), 
															 DM.PATH);
		}
		
		//return allMoves[rnd.nextInt(allMoves.length)];
		return allMoves[rnd.nextInt(allMoves.length)]; // si no hay fantasma, lo cual no es posible, pero se va en random
    }
	
	@Override
	public String getActionId() {
		return "Runaway";
	}
            
}
