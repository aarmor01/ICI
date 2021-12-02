package es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts;

import es.ucm.fdi.ici.fuzzy.FuzzyInput;

import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;

import java.util.HashMap;

public class GhostsInput extends FuzzyInput {

	private double[] distance;
	
	
	public GhostsInput(Game game) {
		super(game);
	}
	
	@Override
	public void parseInput() {		
		distance = new double[] {-1,-1,-1,-1};
		
		for(GHOST g: GHOST.values()) {
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
		HashMap<String,Double> vars = new HashMap<String,Double>();
		
		for(GHOST g : GHOST.values()) 
			vars.put(g.name()+"distanceToPacMan",   distance[g.ordinal()]);
		
		return vars;
	}

}