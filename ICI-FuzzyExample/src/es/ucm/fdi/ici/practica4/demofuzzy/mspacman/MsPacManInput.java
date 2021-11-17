package es.ucm.fdi.ici.practica4.demofuzzy.mspacman;

import java.util.HashMap;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Game;
import es.ucm.fdi.ici.fuzzy.FuzzyInput;

public class MsPacManInput extends FuzzyInput {

	double[] distance = {50,50,50,50};
	double[] confidence = {100,100,100,100};
	
	public MsPacManInput(Game game) {
		super(game);

	}
	
	@Override
	public void parseInput() {
		if(distance == null)
		{
			distance = new double[]{50,50,50,50};
			confidence = new double[]{100,100,100,100};
		}
		
		for(GHOST g: GHOST.values()) {
			int index = g.ordinal();
			int pos = game.getGhostCurrentNodeIndex(g);
			if(pos != -1) {
				distance[index] = game.getDistance(game.getPacmanCurrentNodeIndex(), pos, DM.PATH);
				confidence[index] = 100;
			} else if (confidence[index] > 0)
				confidence[index]-=.5;
		}
	}

	@Override
	public HashMap<String, Double> getFuzzyValues() {
		HashMap<String,Double> vars = new HashMap<String,Double>();
		for(GHOST g: GHOST.values()) {
			vars.put(g.name()+"distance",   distance[g.ordinal()]);
			vars.put(g.name()+"confidence", confidence[g.ordinal()]);			
		}
		return vars;
	}

}
