package es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman;

import java.util.HashMap;
import java.util.Vector;

import pacman.game.Constants.GHOST;

public class MsPacManFuzzyMemory {
	
	public class PillState{
	    public int x; 
	    public int y;  
	    public boolean avail; 
	 };
	 
	 public class PowerPillState {
	    public int x; 
	    public int y;  
	    public boolean avail; 
	 };
	
	HashMap<String,Double> mem;
	
	double[] confidence = {100,100,100,100};
	
	Vector<PillState> pills = new Vector<PillState>(); 
	Vector powerPills = new Vector();
	
	
	
	public MsPacManFuzzyMemory() {
		mem = new HashMap<String,Double>();
	}
	
	public void searchForPills() {
//		int[] adjacentPathsNode = game.getNeighbouringNodes(assumedPM_LastPos, lastMoveMade);
	}
	
	public void getInput(MsPacManInput input){
		
		PillState p = new PillState();
		p.x = 2;
		p.y = 2;
		p.avail = true;
		pills.addElement(p);
		
		
		
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
