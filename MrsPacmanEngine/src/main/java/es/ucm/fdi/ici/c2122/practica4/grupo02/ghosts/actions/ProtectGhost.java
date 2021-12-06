package es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts.actions;

import java.util.Random;
import java.util.Vector;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts.GhostsFuzzyMemory;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class ProtectGhost implements Action {

	private GHOST ghost;
	private GhostsFuzzyMemory mem;

	private Random rnd = new Random();
    private MOVE[] allMoves = MOVE.values();
	
	public ProtectGhost(GHOST g, GhostsFuzzyMemory mem_) {
		this.ghost = g;
		this.mem = mem_;
	}

	@Override
	public MOVE execute(Game game) {
		int pcNode = game.getPacmanCurrentNodeIndex();
		
		Vector<GHOST> edibleGhosts = new Vector<GHOST>(1,1);
		
		for (GHOST g : GHOST.values()) {
			if(game.isGhostEdible(g))
				edibleGhosts.add(g);
		}
		
		if (pcNode != -1) {
			GHOST closestGhost = GHOST.BLINKY;
			double closestDistance = 10000;
			for (GHOST g : edibleGhosts) {
				if (game.getGhostCurrentNodeIndex(g) != -1) {
					if (closestDistance > game.getDistance(pcNode, game.getGhostCurrentNodeIndex(g), DM.PATH)) {
						closestDistance = game.getDistance(pcNode, game.getGhostCurrentNodeIndex(g), DM.PATH);
						closestGhost = g;
					}
				}
			}
			if (closestDistance != 10000) {
				return game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost),
						game.getGhostCurrentNodeIndex(closestGhost), game.getGhostLastMoveMade(ghost), DM.PATH);
			}else {
				return game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost), pcNode,
						game.getGhostLastMoveMade(ghost), DM.PATH);
			}
		}
			
		
		return allMoves[rnd.nextInt(allMoves.length)];
	}

	@Override
	public String getActionId() {
		return "Protect " + ghost;
	}
}
