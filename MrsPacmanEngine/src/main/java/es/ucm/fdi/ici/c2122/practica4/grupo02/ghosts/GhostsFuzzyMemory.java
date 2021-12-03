package es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts;

import pacman.game.Constants.GHOST;

import java.util.HashMap;

public class GhostsFuzzyMemory {

	static public class GhostState {
		public GHOST ghost;
		public int x;
		public int y;
	}

	static public class PowerPillState {
		public int indexPPill;
		public int x;
		public int y;
		public boolean eaten;
	}

	HashMap<String, Double> mem;

	double[] confidence = { 100, 100, 100, 100 };
	
	public HashMap<Integer,PowerPillState> powerPillsSeen = new HashMap<Integer,PowerPillState>(); 

	public GhostsFuzzyMemory() {
		mem = new HashMap<String, Double>();
	}

	public void getInput(GhostsInput input) {
		for (GHOST ghost : GHOST.values()) {
			input.savePills(powerPillsSeen, ghost);
		}
	}
	
	public HashMap<String, Double> getFuzzyValues() {
		return mem;
	}
}