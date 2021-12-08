package es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.MsPacManFuzzyMemory;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class ChaseGhostAction implements Action {

	MsPacManFuzzyMemory mem;
	public ChaseGhostAction(MsPacManFuzzyMemory mem_) {
		// TODO Auto-generated constructor stub
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
			GameView.addLines(game, java.awt.Color.ORANGE, game.getPacmanCurrentNodeIndex() ,nodeGhost);	
			return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
															 nodeGhost, 
															 game.getPacmanLastMoveMade(), 
															 DM.PATH);
		}
		// TODO Auto-generated method stub
		return MOVE.NEUTRAL;
	}
	
	@Override
	public String getActionId() {
		// TODO Auto-generated method stub
		return "Chase";
	}

}
