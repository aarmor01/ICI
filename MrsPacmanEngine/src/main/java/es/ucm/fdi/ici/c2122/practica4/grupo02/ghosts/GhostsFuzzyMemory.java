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

	double distToPacmanConfidence = 100;
	double pacmanDistToPowerPillConfidence = 100;

	public HashMap<Integer, PowerPillState> powerPillsSeen = new HashMap<Integer, PowerPillState>();

	public GhostsFuzzyMemory() {
		mem = new HashMap<String, Double>();
	}

	public void getInput(GhostsInput input) {
		boolean pacmanVisible = false;
		
		for (GHOST ghost : GHOST.values()) {
			input.savePills(powerPillsSeen, ghost);
			
			pacmanVisible = (pacmanVisible || input.isVisible(ghost));
		}
		
		if (pacmanVisible)
			distToPacmanConfidence = Double.max(0, distToPacmanConfidence - 0.5);
		else 
			distToPacmanConfidence = 100;
		
		mem.put("distanceToPacmanConfidence", distToPacmanConfidence);
	}

	public HashMap<String, Double> getFuzzyValues() {
		return mem;
	}
}