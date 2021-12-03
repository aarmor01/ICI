package es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts;

import es.ucm.fdi.ici.c2122.practica4.grupo02.GameConstants;
import es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts.GhostsFuzzyMemory.PowerPillState;
import es.ucm.fdi.ici.fuzzy.FuzzyInput;

import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

import java.util.HashMap;

public class GhostsInput extends FuzzyInput {

	private double[] distance;

	public GhostsInput(Game game) {
		super(game);
	}

	public void savePills(HashMap<Integer,PowerPillState> pills, GHOST ghost) {
		int pcNode = game.getGhostCurrentNodeIndex(ghost);
		int[] adjacentPathsNode = game.getNeighbouringNodes(pcNode);
		MOVE move;
		for(int i = 0; i < adjacentPathsNode.length; ++i) {
			move = game.getMoveToMakeToReachDirectNeighbour(pcNode, adjacentPathsNode[i]);
			int k = 0;
			boolean noEnd = false;
			int assumedPos = pcNode;
			while(k < GameConstants.sightLimit && !noEnd) {
				int node = game.getNeighbour(assumedPos, move);
				if(node != -1) {
					//Comparar si en este nodo hay una Pill
					int indexPill = game.getPowerPillIndex(node);
					if( indexPill != -1) {
						if(!pills.containsKey(node)) { //Si no está la pill en el vector, la metemos
							PowerPillState p = new PowerPillState();
							p.indexPPill = indexPill;
							p.x = game.getNodeXCood(node);
							p.y = game.getNodeXCood(node);
							p.eaten = false; // Como es la primera vez que se mete, está activa
							pills.put(node, p);
						}
					}
					assumedPos = node;
					++k;
				}else noEnd = true; //No hay mas visibilidad
				
			}
		}
	}
	
	@Override
	public void parseInput() {
		distance = new double[] { -1, -1, -1, -1 };

		for (GHOST g : GHOST.values()) {
			int index = g.ordinal();
			int pos = game.getGhostCurrentNodeIndex(g);
			int pacManPos = game.getPacmanCurrentNodeIndex();

			if (pacManPos != -1)
				distance[index] = game.getDistance(pos, pacManPos, DM.PATH);
		}
	}

	public boolean isVisible(GHOST ghost) {
		return distance[ghost.ordinal()] != -1;
	}

	@Override
	public HashMap<String, Double> getFuzzyValues() {
		// puts the values on the fcl file input values
		HashMap<String, Double> vars = new HashMap<String, Double>();

		for (GHOST g : GHOST.values())
			vars.put(g.name() + "distanceToPacMan", distance[g.ordinal()]);

		return vars;
	}
}