package es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts.actions;

import java.util.Random;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts.GhostsFuzzyMemory;

import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Constants.GHOST;

public class Runaway implements Action {

	GHOST ghost;
	GhostsFuzzyMemory mem;

	private Random rnd = new Random();
    private MOVE[] allMoves = MOVE.values();
    
	public Runaway(GHOST g, GhostsFuzzyMemory mem_) {
		this.ghost = g;
		this.mem = mem_;
	}

	@Override
	public MOVE execute(Game game) {
		int pcNode = game.getPacmanCurrentNodeIndex();

		if (pcNode != -1)
			return game.getApproximateNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghost), pcNode,
					game.getGhostLastMoveMade(ghost), DM.PATH);
		
		return allMoves[rnd.nextInt(allMoves.length)];
	}

	@Override
	public String getActionId() {
		return "Runaway";
	}

}