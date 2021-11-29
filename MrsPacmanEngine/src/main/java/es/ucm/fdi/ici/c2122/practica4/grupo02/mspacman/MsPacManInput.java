package es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman;

import java.util.HashMap;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import es.ucm.fdi.ici.fuzzy.FuzzyInput;
import es.ucm.fdi.ici.c2122.practica4.grupo02.GameConstants;
public class MsPacManInput extends FuzzyInput {

	private double[] distance;
	
	
	public MsPacManInput(Game game) {
		super(game);
	}
	
	public void searchForPills() {
		int pcNode = game.getPacmanCurrentNodeIndex();
		int[] adjacentPathsNode = game.getNeighbouringNodes(pcNode, game.getPacmanLastMoveMade());
		MOVE move;
		for(int i = 0; i < adjacentPathsNode.length; ++i) {
			move = game.getMoveToMakeToReachDirectNeighbour(pcNode, i);
			int k = 0;
			boolean noEnd = false;
			while(k < GameConstants.sightLimit && !noEnd) {
				
				int node = game.getNeighbour(pcNode, move);
				if(node != -1) {
					//Comparar si en este nodo hay una powerPill
					boolean avail = game.isPowerPillStillAvailable(i);
					
					//determinar si ya esta en el vector, de lo contrario, meterla
				}else noEnd = true; //No hay mas visibilidad
				++k;
			}
		}
	}
	
	
	@Override
	public void parseInput() {		
		distance = new double[] {-1,-1,-1,-1};

		for(GHOST g: GHOST.values()) {
			int index = g.ordinal();
			int pos = game.getGhostCurrentNodeIndex(g);
			if(pos != -1) {
				distance[index] = game.getDistance(game.getPacmanCurrentNodeIndex(), pos, DM.PATH);
			}
			else
				distance[index] = -1;
		}
	}
	
	public boolean isVisible(GHOST ghost) {
		return distance[ghost.ordinal()]!=-1;
	}
	
	

	@Override
	public HashMap<String, Double> getFuzzyValues() {
		HashMap<String,Double> vars = new HashMap<String,Double>();
		for(GHOST g: GHOST.values()) {
			vars.put(g.name()+"distance",   distance[g.ordinal()]);
		}
		return vars;
	}

}
