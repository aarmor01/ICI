package es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman;

import java.util.HashMap;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class MsPacManFuzzyMemory {
	
	static public class PillState{
		public int indexPill; //Index in the array
	    public int x; 
	    public int y;  
	    public boolean eaten; 
	 };
	 
	 static public class PowerPillState {
		public int indexPPill;
	    public int x; 
	    public int y;  
	    public boolean eaten; 
	 };
	 
	 static public class GhostState {
			public int ghostNode;
		    public int x; 
		    public int y;  
		    public MOVE move;
		    public boolean edible;
		 }; 
	
	HashMap<String,Double> mem;
	
	double[] confidence = {100,100,100,100};
	
	//NodeIndex, PillState
	public HashMap<Integer,PillState> pillsSeen = new HashMap<Integer,PillState>(); 
	public HashMap<Integer,PowerPillState> pPillsSeen = new HashMap<Integer,PowerPillState>(); 
	
	public HashMap<GHOST,GhostState> ghostsSeen = new HashMap<GHOST,GhostState>(); 
	
	public MsPacManFuzzyMemory() {
		mem = new HashMap<String,Double>();
	}
		
	public void getInput(MsPacManInput input){
		
		input.savePills_N_Ghosts(pillsSeen, pPillsSeen, ghostsSeen);
		input.updatePillsState_N_Ghosts(pillsSeen, pPillsSeen);	//actualizamos el estado de las pills vistas
		
		for(GHOST g: GHOST.values()) {
			double conf = confidence[g.ordinal()];
			if(input.isVisible(g))
				conf = 100;
			else
				conf = Double.max(0, conf - 0.5);
			mem.put(g.name()+"confidence", conf);			
		}

	}
	
	public HashMap<String, Double> getFuzzyValues() {
		return mem;
	}
	
}
