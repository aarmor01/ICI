package es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.actions;

import es.ucm.fdi.ici.c2122.practica2.grupo02.Tools;
import es.ucm.fdi.ici.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ChaseGhost implements Action {
	
	int chaseLimit = 50;
	public ChaseGhost() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getActionId() {
		// TODO Auto-generated method stub
		return "Chase Ghost";
	}

	@Override
	public MOVE execute(Game game) {
		int pacmanNode = game.getPacmanCurrentNodeIndex();
		GHOST nearestGhostType = Tools.nearestGhost(game, pacmanNode, chaseLimit);
		//Perseguir
		return game.getApproximateNextMoveTowardsTarget(pacmanNode,
				game.getGhostCurrentNodeIndex( 	nearestGhostType), game.getPacmanLastMoveMade(), DM.PATH);
	}
}
