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
	
		int pacman = game.getPacmanCurrentNodeIndex();
		this.pacmanDistancePowerPill = Double.MAX_VALUE;
		for(int ppill: game.getPowerPillIndices()) {
			double distance = game.getDistance(pacman, ppill, DM.PATH);
			this.pacmanDistancePowerPill = Math.min(distance, this.pacmanDistancePowerPill);
		}
		
		int blinkyNode = game.getGhostCurrentNodeIndex(GHOST.BLINKY);
		distanceBlinkyToPacman = game.getShortestPathDistance(pacman, blinkyNode);
		int pinkyNode = game.getGhostCurrentNodeIndex(GHOST.PINKY);
		distancePinkyToPacman = game.getShortestPathDistance(pacman, pinkyNode);
		int inkyNode = game.getGhostCurrentNodeIndex(GHOST.INKY);
		distanceInkyToPacman = game.getShortestPathDistance(pacman, inkyNode);
		int sueNode = game.getGhostCurrentNodeIndex(GHOST.SUE);
		distanceSueToPacman = game.getShortestPathDistance(pacman, sueNode);
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
