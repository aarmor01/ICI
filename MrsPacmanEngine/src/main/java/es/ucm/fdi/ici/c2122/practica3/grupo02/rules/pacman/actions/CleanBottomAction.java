package es.ucm.fdi.ici.c2122.practica3.grupo02.rules.pacman.actions;

import java.awt.Color;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2122.practica3.grupo02.GameConstants;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class CleanBottomAction implements RulesAction {
	
	int initYCoordPM = 0;
	public CleanBottomAction() {
	
	}
	
	@Override
	public String getActionId() {
		// TODO Auto-generated method stub
		return "PCcleansBottom";
	}

	@Override
	public MOVE execute(Game game) {
		int pcNode = game.getPacmanCurrentNodeIndex();
		int[] activePills = game.getActivePillsIndices();
		int nearestPillNode = -1;
		int shortestDistance = -1;
		
		//For each active Pill we check which one is the nearest
		for (int activePill : activePills) {
			if(game.getNodeYCood(activePill) >= game.getNodeYCood(game.getPacManInitialNodeIndex())) {
				int distance = game.getShortestPathDistance(pcNode, activePill, game.getPacmanLastMoveMade());
				if (shortestDistance == -1 || distance < shortestDistance) {
					nearestPillNode = activePill;
					shortestDistance = distance;
				}
			}
		}

		//if there's active pill we draw it's euclidean path
		if (nearestPillNode != -1 && GameConstants.DEBUG)
			GameView.addLines(game, Color.CYAN, pcNode, nearestPillNode);
		
		return game.getApproximateNextMoveTowardsTarget(pcNode, nearestPillNode, game.getPacmanLastMoveMade(), DM.PATH);
	}

	@Override
	public void parseFact(Fact actionFact) {
		// TODO Auto-generated method stub
		
	}

}
