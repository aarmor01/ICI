package es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts.actions;

import java.util.Random;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts.GhostsFuzzyMemory;

import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class ChasePacMan implements Action {

	private GHOST ghost;
	private GhostsFuzzyMemory mem;

	private Random rnd = new Random();
    private MOVE[] allMoves = MOVE.values();
    
    int contador = 0;
	
	public ChasePacMan(GHOST g, GhostsFuzzyMemory mem_) {
		this.ghost = g;
		this.mem = mem_;
	}

	private MOVE getSecondaryPath(Game game, MOVE[] possibleMoves, int ghostNode, int pacmanNode, GHOST ghostType) {
		int numChoices = possibleMoves.length;

		if (numChoices == 2) // if two possible moves, we go the opposite way (away instead of towards)
			return game.getNextMoveAwayFromTarget(ghostNode, pacmanNode,
					game.getGhostLastMoveMade(ghostType), DM.PATH);
		else { // if three possible moves, we go to the one that isn't the furthest nor the closest to the Pacman
			MOVE optimalMove = game.getNextMoveTowardsTarget(ghostNode, pacmanNode,
					game.getGhostLastMoveMade(ghostType), DM.PATH);

			MOVE awayMove = game.getNextMoveAwayFromTarget(ghostNode, pacmanNode,
					game.getGhostLastMoveMade(ghostType), DM.PATH);

			// we select the one that isn't the furthest nor the closest
			if (possibleMoves[0] != optimalMove && possibleMoves[0] != awayMove)
				return possibleMoves[0];
			else if (possibleMoves[1] != optimalMove && possibleMoves[1] != awayMove)
				return possibleMoves[1];
			else
				return possibleMoves[2];
		}
	}
	
	@Override
	public MOVE execute(Game game) {
		int pcNode = game.getPacmanCurrentNodeIndex();

		if (pcNode != -1 && game.getGhostLairTime(ghost) <= 0) {
			if (contador > 2) {
				contador = 0;
				return getSecondaryPath(game, game.getPossibleMoves(game.getGhostCurrentNodeIndex(ghost), game.getGhostLastMoveMade(ghost)),
						game.getGhostCurrentNodeIndex(ghost), pcNode, ghost);
				
			}else {
				if (game.getDistance(game.getGhostCurrentNodeIndex(ghost), pcNode, game.getGhostLastMoveMade(ghost), DM.PATH) < 20)
					contador++;
				return game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost), pcNode,
						game.getGhostLastMoveMade(ghost), DM.PATH);
			}
		}
		
		return allMoves[rnd.nextInt(allMoves.length)];
	}

	@Override
	public String getActionId() {
		return "Chase";
	}
}
