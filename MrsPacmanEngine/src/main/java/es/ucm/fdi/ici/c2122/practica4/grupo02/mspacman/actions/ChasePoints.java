package es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.actions;

import java.util.HashMap;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2122.practica2.grupo02.GameConstants;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.MsPacManFuzzyMemory;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.MsPacManFuzzyMemory.GhostState;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.MsPacManFuzzyMemory.PillState;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.MsPacManFuzzyMemory.PowerPillState;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class ChasePoints implements Action {
	MsPacManFuzzyMemory mem;
	public ChasePoints(MsPacManFuzzyMemory mem_) {
		mem = mem_;
	}

	int searchNearestPowerPillSaved(Game game) {
		int target = -1;
		int pcNode = game.getPacmanCurrentNodeIndex();
		int distance = Integer.MAX_VALUE;
		
		for (HashMap.Entry<Integer, PowerPillState> entry : mem.pPillsSeen.entrySet()) {
			if(!entry.getValue().eaten) {
				int auxDis = game.getShortestPathDistance(pcNode, entry.getKey().intValue(), game.getPacmanLastMoveMade());
				//Si es mas pequeña su distancia y si la pill está activa
				if(auxDis < distance) {
					distance = auxDis;
					target = entry.getKey().intValue();
				}
			}
		}
		
		return target;
	}
	
	//El objetivo es cazar fantasmas y coger PowerPills
	@Override
	public MOVE execute(Game game) {
		MOVE move = MOVE.NEUTRAL;
		
		int target = -1;
		int pcNode = game.getPacmanCurrentNodeIndex();
		boolean gotEdible = false;
		int distance = Integer.MAX_VALUE;
		
		for (HashMap.Entry<GHOST,GhostState> entry : mem.ghostsSeen.entrySet()) {
			boolean isEdible = entry.getValue().edible;
			if(isEdible) {
				target = entry.getValue().ghostNode;
			}
			
			gotEdible = gotEdible || isEdible;
		}
		
		if(!gotEdible) target = searchNearestPowerPillSaved(game);
		
		if(target != -1) {
			if(GameConstants.DEBUG)
			GameView.addPoints(game, java.awt.Color.YELLOW, target);	
			move = game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
					target,
					game.getPacmanLastMoveMade(), 
					DM.PATH);
		}
//		return allMoves[rnd.nextInt(allMoves.length)];
		return move;
	}
	
	@Override
	public String getActionId() {
		// TODO Auto-generated method stub
		return "ChasePoints";
	}
}
