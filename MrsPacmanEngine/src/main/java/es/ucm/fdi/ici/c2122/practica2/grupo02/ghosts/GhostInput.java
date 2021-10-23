package es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts;

import es.ucm.fdi.ici.Input;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;

public class GhostInput extends Input{

	private boolean BLINKYedible;
	private boolean INKYedible;
	private boolean PINKYedible;
	private boolean SUEedible;
	
	private int BLINKYedTimeLeft;
	private int INKYedTimeLeft;
	private int PINKYedTimeLeft;
	private int SUEedTimeLeft;
	
	private boolean BLINKYoutOfLair;
	private boolean INKYoutOfLair;
	private boolean PINKYoutOfLair;
	private boolean SUEoutOfLair;
	
	private int BLINKYposition;
	private int INKYposition;
	private int PINKYposition;
	private int SUEposition;
	
	private double pacmanDistancePowerPill;
	
	private int distanceBlinkyToPacman;
	private int distancePinkyToPacman;
	private int distanceInkyToPacman;
	private int distanceSueToPacman;
	
	private int nextPillPacManBySeer;
	
	private int[] chaseCountsGhosts = {0,0,0,0};
	
	public GhostInput(Game game) {
		super(game);
	}

	@Override
	public void parseInput() {
		this.BLINKYedible = game.isGhostEdible(GHOST.BLINKY);
		this.PINKYedible = game.isGhostEdible(GHOST.PINKY);
		this.INKYedible = game.isGhostEdible(GHOST.INKY);
		this.SUEedible = game.isGhostEdible(GHOST.SUE);
		
		this.BLINKYedTimeLeft = game.getGhostEdibleTime(GHOST.BLINKY);
		this.INKYedTimeLeft = game.getGhostEdibleTime(GHOST.INKY);
		this.PINKYedTimeLeft = game.getGhostEdibleTime(GHOST.PINKY);
		this.SUEedTimeLeft = game.getGhostEdibleTime(GHOST.SUE);
	
		this.BLINKYoutOfLair = !(game.getGhostLairTime(GHOST.BLINKY) > 0);
		this.PINKYedible = !(game.getGhostLairTime(GHOST.PINKY) > 0);
		this.INKYedible = !(game.getGhostLairTime(GHOST.INKY) > 0);
		this.SUEedible = !(game.getGhostLairTime(GHOST.SUE) > 0);
		
		int pacmanNode = game.getPacmanCurrentNodeIndex();
		this.pacmanDistancePowerPill = Double.MAX_VALUE;
		for(int ppill: game.getPowerPillIndices()) {
			double distance = game.getDistance(pacmanNode, ppill, DM.PATH);
			this.pacmanDistancePowerPill = Math.min(distance, this.pacmanDistancePowerPill);
		}
		
		BLINKYposition = game.getGhostCurrentNodeIndex(GHOST.BLINKY);
		distanceBlinkyToPacman = game.getShortestPathDistance(pacmanNode, BLINKYposition);
		PINKYposition = game.getGhostCurrentNodeIndex(GHOST.PINKY);
		distancePinkyToPacman = game.getShortestPathDistance(pacmanNode, PINKYposition);
		INKYposition = game.getGhostCurrentNodeIndex(GHOST.INKY);
		distanceInkyToPacman = game.getShortestPathDistance(pacmanNode, INKYposition);
		SUEposition = game.getGhostCurrentNodeIndex(GHOST.SUE);
		distanceSueToPacman = game.getShortestPathDistance(pacmanNode, SUEposition);
		
		if (game.doesGhostRequireAction(GHOST.BLINKY)) {
			//Seer prediction
			int[] activePills = game.getActivePillsIndices();
			int nearestPillNode = -1;
			int shortestDistance = -1;
			
			int minPredictionDistance = 15;
			// We get the next possible destination of the Pacman
			for (int activePill : activePills) {
				int distance = game.getShortestPathDistance(pacmanNode, activePill, game.getPacmanLastMoveMade());
				if (shortestDistance == -1 || distance < shortestDistance && distance > minPredictionDistance) {
					nearestPillNode = activePill;
					shortestDistance = distance;
				}
			}
			
			nextPillPacManBySeer = nearestPillNode;
		}
		
		int distanceToStartChase = 30;
		int contadorGhosts = 0;
		for (GHOST ghost : GHOST.values()) {
			if (game.doesGhostRequireAction(ghost)) {
				if (!game.isGhostEdible(ghost) && game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghost), pacmanNode) < distanceToStartChase)
					chaseCountsGhosts[contadorGhosts]++;
			}
			contadorGhosts++;
		}
	}

	public boolean isBLINKYedible() {
		return BLINKYedible;
	}

	public boolean isINKYedible() {
		return INKYedible;
	}

	public boolean isPINKYedible() {
		return PINKYedible;
	}

	public boolean isSUEedible() {
		return SUEedible;
	}
	
	public int getBLINKYedibleTimeLeft() {
		return BLINKYedTimeLeft;
	}
	
	public int getINKYedibleTimeLeft() {
		return INKYedTimeLeft;
	}
	
	public int getPINKYedibleTimeLeft() {
		return PINKYedTimeLeft;
	}
	
	public int getSUEedibleTimeLeft() {
		return SUEedTimeLeft;
	}
	
	public boolean isBLINKYoutOfLair() {
		return BLINKYoutOfLair;
	}

	public boolean isPINKYoutOfLair() {
		return PINKYoutOfLair;
	}

	public boolean isINKYoutOfLair() {
		return INKYoutOfLair;
	}

	public boolean isSUEoutOfLair() {
		return SUEoutOfLair;
	}
	
	public int getBLINKYposition() {
		return BLINKYposition;
	}
	
	public int getINKYposition() {
		return INKYposition;
	}
	
	public int getPINKYposition() {
		return PINKYposition;
	}
	
	public int getSUEposition() {
		return SUEposition;
	}
	
	public int getNextPillPacManBySeer() {
		return nextPillPacManBySeer;
	}
	
	public int getGhostChaseCount(GHOST ghost) {
		switch(ghost) {
		case BLINKY:
			return chaseCountsGhosts[0];
		case INKY:
			return chaseCountsGhosts[1];
		case PINKY:
			return chaseCountsGhosts[2];
		case SUE:
			return chaseCountsGhosts[3];
		default:
			return -1;
		}
	}
	
	public void resetCount(GHOST ghost) {
		switch(ghost) {
		case BLINKY:
			chaseCountsGhosts[0] = 0;
		case INKY:
			chaseCountsGhosts[1] = 0;
		case PINKY:
			chaseCountsGhosts[2] = 0;
		case SUE:
			chaseCountsGhosts[3] = 0;
		}
	}

	public double getMinPacmanDistancePPill() {
		return pacmanDistancePowerPill;
	}
	
	public int getBlinkyDistancePacman() {
		return distanceBlinkyToPacman;
	}
	
	public int getPinkyDistancePacman() {
		return distancePinkyToPacman;
	}
	
	public int getInkyDistancePacman() {
		return distanceInkyToPacman;
	}
	
	public int getSueDistancePacman() {
		return distanceSueToPacman;
	}
}
