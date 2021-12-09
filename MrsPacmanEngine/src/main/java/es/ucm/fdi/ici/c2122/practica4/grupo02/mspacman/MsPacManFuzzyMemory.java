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
	double[] edibleTime = {0,0,0,0};
	
	
	//NodeIndex, PillState
	public HashMap<Integer,PillState> pillsSeen = new HashMap<Integer,PillState>(); 
	public HashMap<Integer,PowerPillState> pPillsSeen = new HashMap<Integer,PowerPillState>(); 
	//GhostType, GhostState
	public HashMap<GHOST,GhostState> ghostsSeen = new HashMap<GHOST,GhostState>(); 
	
	public MsPacManFuzzyMemory() {
		mem = new HashMap<String,Double>();
	}
		
	public void getInput(MsPacManInput input){
		
		input.savePills(pillsSeen, pPillsSeen);			// Guardamos las pills y powerPills que vayamos viendo
		input.updateGhosts(ghostsSeen);					//Actualizamos a los fantasmas
		input.updatePillsState(pillsSeen, pPillsSeen);	//actualizamos el estado de las pills vistas
		
		for(GHOST g: GHOST.values()) {
			double conf = confidence[g.ordinal()];
			double edibleT = edibleTime[g.ordinal()];
			if(input.isVisible(g)) {
				conf = confidence[g.ordinal()] = 100;
				edibleT = edibleTime[g.ordinal()] = input.getGame().getGhostEdibleTime(g); 
			}
			else {
				edibleT = edibleTime[g.ordinal()] = Double.max(0, edibleT - 1); //Se va restando el tiempo si no es visible, cada tick se resta en 1 
				conf = confidence[g.ordinal()] = Double.max(0, conf - 1);
			}
			mem.put(g.name()+"confidence", conf);			
			mem.put(g.name()+"timeEdible", edibleT);			
		}

	}
	
	public HashMap<String, Double> getFuzzyValues() {
		return mem;
	}
	
}
