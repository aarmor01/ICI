package es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.actions;


import java.util.Random;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.MsPacManFuzzyMemory;
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
	
	@Override
	public MOVE execute(Game game) {
		GHOST g = nearestGhost(game);
		 //If this actions has been triggered, then there must be a ghost in the actual sight
		if(g != null) {
			int nodeGhost = game.getGhostCurrentNodeIndex(g);
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
