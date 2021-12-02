package es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts;

import java.util.HashMap;

public class GhostsFuzzyMemory {
	static public class PacManState{
		public int x;
		public int y;
	}
	
	static public class PowerPillState {
		public int indexPPill;
	    public int x; 
	    public int y;  
	    public boolean eaten;
	}
	
	HashMap<String,Double> mem;
	
	double[] confidence = {100,100,100,100};
	
	public GhostsFuzzyMemory() {
		mem = new HashMap<String,Double>();
	}
	
	public void getInput(GhostsInput input) {
		
	}
}