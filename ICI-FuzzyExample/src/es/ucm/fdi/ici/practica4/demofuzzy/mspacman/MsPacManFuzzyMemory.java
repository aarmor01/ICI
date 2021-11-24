package es.ucm.fdi.ici.practica4.demofuzzy.mspacman;

import java.util.HashMap;

import pacman.game.Constants.GHOST;

public class MsPacManFuzzyMemory {
	HashMap<String,Double> mem;
	
	double[] confidence = {100,100,100,100};

	
	public MsPacManFuzzyMemory() {
		mem = new HashMap<String,Double>();
	}
	
	public void getInput(MsPacManInput input)
	{
		for(GHOST g: GHOST.values()) {
			double conf = confidence[g.ordinal()];
			if(input.isVisible(g))
				conf = 100;
			else
				conf = Double.max(0, conf-5);
			mem.put(g.name()+"confidence", conf);			
		}

	}
	
	public HashMap<String, Double> getFuzzyValues() {
		return mem;
	}
	
}
