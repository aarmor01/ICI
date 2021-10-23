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
	
	int BLINKYposition;
	int INKYposition;
	int PINKYposition;
	int SUEposition;
	
	private double pacmanDistancePowerPill;
	
	private int distanceBlinkyToPacman;
	private int distancePinkyToPacman;
	private int distanceInkyToPacman;
	private int distanceSueToPacman;
	
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
		
		int pacman = game.getPacmanCurrentNodeIndex();
		this.pacmanDistancePowerPill = Double.MAX_VALUE;
		for(int ppill: game.getPowerPillIndices()) {
			double distance = game.getDistance(pacman, ppill, DM.PATH);
			this.pacmanDistancePowerPill = Math.min(distance, this.pacmanDistancePowerPill);
		}
		
		BLINKYposition = game.getGhostCurrentNodeIndex(GHOST.BLINKY);
		distanceBlinkyToPacman = game.getShortestPathDistance(pacman, BLINKYposition);
		PINKYposition = game.getGhostCurrentNodeIndex(GHOST.PINKY);
		distancePinkyToPacman = game.getShortestPathDistance(pacman, PINKYposition);
		INKYposition = game.getGhostCurrentNodeIndex(GHOST.INKY);
		distanceInkyToPacman = game.getShortestPathDistance(pacman, INKYposition);
		SUEposition = game.getGhostCurrentNodeIndex(GHOST.SUE);
		distanceSueToPacman = game.getShortestPathDistance(pacman, SUEposition);
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
