package es.ucm.fdi.ici.c2122.practica3.grupo02.rules.ghosts;

import es.ucm.fdi.ici.rules.RulesInput;

import java.util.Collection;
import java.util.Vector;

import es.ucm.fdi.ici.c2122.practica2.grupo02.GameConstants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GhostInput extends RulesInput {

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

	private int distanceBLINKYToPacman;
	private int distancePINKYToPacman;
	private int distanceINKYToPacman;
	private int distanceSUEToPacman;

	private double pacmanDistancePowerPill;

	private int nextPillPacManBySeer;

	private boolean BLINKYanotherGhostInPath;
	private boolean INKYanotherGhostInPath;
	private boolean PINKYanotherGhostInPath;
	private boolean SUEanotherGhostInPath;

	private int chaseCountBLINKY;
	private int chaseCountINKY;
	private int chaseCountPINKY;
	private int chaseCountSUE;

	public GhostInput(Game game) {
		super(game);
	}

	@Override
	public void parseInput() {
		int pacmanNode = game.getPacmanCurrentNodeIndex();

		this.BLINKYedible = game.isGhostEdible(GHOST.BLINKY);
		this.PINKYedible = game.isGhostEdible(GHOST.PINKY);
		this.INKYedible = game.isGhostEdible(GHOST.INKY);
		this.SUEedible = game.isGhostEdible(GHOST.SUE);

		this.BLINKYedTimeLeft = game.getGhostEdibleTime(GHOST.BLINKY);
		this.INKYedTimeLeft = game.getGhostEdibleTime(GHOST.INKY);
		this.PINKYedTimeLeft = game.getGhostEdibleTime(GHOST.PINKY);
		this.SUEedTimeLeft = game.getGhostEdibleTime(GHOST.SUE);

		this.BLINKYoutOfLair = !(game.getGhostLairTime(GHOST.BLINKY) > 0);
		this.PINKYoutOfLair = !(game.getGhostLairTime(GHOST.PINKY) > 0);
		this.INKYoutOfLair = !(game.getGhostLairTime(GHOST.INKY) > 0);
		this.SUEoutOfLair = !(game.getGhostLairTime(GHOST.SUE) > 0);

		this.BLINKYposition = game.getGhostCurrentNodeIndex(GHOST.BLINKY);
		this.PINKYposition = game.getGhostCurrentNodeIndex(GHOST.PINKY);
		this.INKYposition = game.getGhostCurrentNodeIndex(GHOST.INKY);
		this.SUEposition = game.getGhostCurrentNodeIndex(GHOST.SUE);

		this.distanceBLINKYToPacman = game.getShortestPathDistance(pacmanNode, BLINKYposition);
		this.distanceINKYToPacman = game.getShortestPathDistance(pacmanNode, INKYposition);
		this.distancePINKYToPacman = game.getShortestPathDistance(pacmanNode, PINKYposition);
		this.distanceSUEToPacman = game.getShortestPathDistance(pacmanNode, SUEposition);

		this.pacmanDistancePowerPill = Double.MAX_VALUE;
		for (int ppill : game.getPowerPillIndices()) {
			double distance = game.getDistance(pacmanNode, ppill, DM.PATH);
			this.pacmanDistancePowerPill = Math.min(distance, this.pacmanDistancePowerPill);
		}

		// Seer prediction
		if (game.doesGhostRequireAction(GHOST.BLINKY)) {

		}

		for (GHOST ghost : GHOST.values()) {

			if (game.doesGhostRequireAction(ghost)) {
				if (ghost == GHOST.BLINKY)
					seerPill(pacmanNode);

				if (!game.isGhostEdible(ghost) && game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghost),
						pacmanNode) < GameConstants.ghostChaseDistance) {
					switch (ghost) {
					case BLINKY:
						chaseCountBLINKY++;
						break;
					case INKY:
						chaseCountINKY++;
						break;
					case PINKY:
						chaseCountPINKY++;
						break;
					case SUE:
						chaseCountSUE++;
						break;
					}
				}

				checkGhostsInPath(ghost);
			}
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
		switch (ghost) {
		case BLINKY:
			return chaseCountBLINKY;
		case INKY:
			return chaseCountINKY;
		case PINKY:
			return chaseCountPINKY;
		case SUE:
			return chaseCountSUE;
		default:
			return -1;
		}
	}

	public void resetCount(GHOST ghost) {
		switch (ghost) {
		case BLINKY:
			chaseCountBLINKY = 0;
		case INKY:
			chaseCountINKY = 0;
		case PINKY:
			chaseCountPINKY = 0;
		case SUE:
			chaseCountSUE = 0;
		}
	}

	public void resetAllCounts() {
		chaseCountBLINKY = 0;
		chaseCountINKY = 0;
		chaseCountPINKY = 0;
		chaseCountSUE = 0;
	}

	public double getMinPacmanDistancePPill() {
		return pacmanDistancePowerPill;
	}

	public int getBlinkyDistancePacman() {
		return distanceBLINKYToPacman;
	}

	public int getPinkyDistancePacman() {
		return distancePINKYToPacman;
	}

	public int getInkyDistancePacman() {
		return distanceINKYToPacman;
	}

	public int getSueDistancePacman() {
		return distanceSUEToPacman;
	}

	public boolean anotherGhostInRunAwayPath(GHOST ghost) {
		switch(ghost) {
		case BLINKY:
			return BLINKYanotherGhostInPath;
		case PINKY:
			return PINKYanotherGhostInPath;
		case INKY:
			return INKYanotherGhostInPath;
		case SUE:
			return SUEanotherGhostInPath;
		default:
			return false;
		}
	}

	private void seerPill(int pacmanNode) {
		int[] activePills = game.getActivePillsIndices();
		int nearestPillNode = -1;
		int shortestDistance = Integer.MAX_VALUE;

		// We get the next possible destination of the Pacman
		for (int activePill : activePills) {
			int distance = game.getShortestPathDistance(pacmanNode, activePill, game.getPacmanLastMoveMade());
			if (distance < shortestDistance && distance > GameConstants.minPredictionDistance) {
				nearestPillNode = activePill;
				shortestDistance = distance;
			}
		}

		nextPillPacManBySeer = nearestPillNode;
	}

	private void checkGhostsInPath(GHOST ghost) {
		int ghostNode = game.getGhostCurrentNodeIndex(ghost);

		boolean auxAnotherGhostInPath;
		switch(ghost) {
		case BLINKY:
			auxAnotherGhostInPath = BLINKYanotherGhostInPath;
			BLINKYanotherGhostInPath = false;
			break;
		case PINKY:
			auxAnotherGhostInPath = PINKYanotherGhostInPath;
			PINKYanotherGhostInPath = false;
			break;
		case INKY:
			auxAnotherGhostInPath = INKYanotherGhostInPath;
			INKYanotherGhostInPath = false;
			break;
		case SUE:
			auxAnotherGhostInPath = SUEanotherGhostInPath;
			SUEanotherGhostInPath = false;
			break;
		default:
			auxAnotherGhostInPath = false;
			break;
		}

		for (GHOST ghostToEvade : GHOST.values()) {
			if (auxAnotherGhostInPath)
				return;

			if (ghostToEvade == ghost || game.getGhostLairTime(ghostToEvade) > 0)
				continue;

			int ghostEvNode = game.getGhostCurrentNodeIndex(ghostToEvade);
			int[] path = game.getShortestPath(ghostNode, ghostEvNode, game.getGhostLastMoveMade(ghost));

			int node = 0;
			for (; node < path.length; node++) 
				if (game.isJunction(node)) 
					break;
				
			if(node == path.length) {
				switch(ghost) {
				case BLINKY:
					BLINKYanotherGhostInPath = true;
					break;
				case PINKY:
					PINKYanotherGhostInPath = true;
					break;
				case INKY:
					INKYanotherGhostInPath = true;
					break;
				case SUE:
					SUEanotherGhostInPath = true;
					break;
				}
			}
		}
	}
	
	@Override
	public Collection<String> getFacts() {
		Vector<String> facts = new Vector<String>();
		
		//BLINKY
		facts.add(String.format("(BLINKY (edible %s))", this.BLINKYedible));
		facts.add(String.format("(BLINKY (edTimeLeft %d))", (int)this.BLINKYedTimeLeft));
		facts.add(String.format("(BLINKY (outOfLair %s))", this.BLINKYoutOfLair));
		facts.add(String.format("(BLINKY (position %d))", (int)this.BLINKYposition));
		facts.add(String.format("(BLINKY (distanceToPacman %d))", (int)this.distanceBLINKYToPacman));
		facts.add(String.format("(BLINKY (anotherGhostInPath %s))", this.BLINKYanotherGhostInPath));
		facts.add(String.format("(BLINKY (chaseCount %d))", (int)this.chaseCountBLINKY));
		
		//INKY
		facts.add(String.format("(INKY (edible %s))", this.INKYedible));
		facts.add(String.format("(INKY (edTimeLeft %d))", (int)this.INKYedTimeLeft));
		facts.add(String.format("(INKY (outOfLair %s))", this.INKYoutOfLair));
		facts.add(String.format("(INKY (position %d))", (int)this.INKYposition));
		facts.add(String.format("(INKY (distanceToPacman %d))", (int)this.distanceINKYToPacman));
		facts.add(String.format("(INKY (anotherGhostInPath %s))", this.INKYanotherGhostInPath));
		facts.add(String.format("(INKY (chaseCount %d))", (int)this.chaseCountINKY));
		
		//PINKY
		facts.add(String.format("(PINKY (edible %s))", this.PINKYedible));
		facts.add(String.format("(PINKY (edTimeLeft %d))", (int)this.PINKYedTimeLeft));
		facts.add(String.format("(PINKY (outOfLair %s))", this.PINKYoutOfLair));
		facts.add(String.format("(PINKY (position %d))", (int)this.PINKYposition));
		facts.add(String.format("(PINKY (distanceToPacman %d))", (int)this.distancePINKYToPacman));
		facts.add(String.format("(PINKY (anotherGhostInPath %s))", this.PINKYanotherGhostInPath));
		facts.add(String.format("(PINKY (chaseCount %d))", (int)this.chaseCountPINKY));
		
		//SUE
		facts.add(String.format("(SUE (edible %s))", this.SUEedible));
		facts.add(String.format("(SUE (edTimeLeft %d))", (int)this.SUEedTimeLeft));
		facts.add(String.format("(SUE (outOfLair %s))", this.SUEoutOfLair));
		facts.add(String.format("(SUE (position %d))", (int)this.SUEposition));
		facts.add(String.format("(SUE (distanceToPacman %d))", (int)this.distanceSUEToPacman));
		facts.add(String.format("(SUE (anotherGhostInPath %s))", this.SUEanotherGhostInPath));
		facts.add(String.format("(SUE (chaseCount %d))", (int)this.chaseCountSUE));
		
		//MSPACMAN
		facts.add(String.format("(MSPACMAN (mindistancePPill %d))", (int)30)); //Temporal <-----------------------------------
		facts.add(String.format("(MSPACMAN (pacmanDistancePowerPill %d))", (int)this.pacmanDistancePowerPill));
		facts.add(String.format("(MSPACMAN (nextPillPacManBySeer %d))", (int)this.nextPillPacManBySeer));
		
//		facts.add(String.format("(INKY (edible %s))", this.INKYedible));
//		facts.add(String.format("(PINKY (edible %s))", this.PINKYedible));
//		facts.add(String.format("(SUE (edible %s))", this.SUEedible));
//		facts.add(String.format("(MSPACMAN (mindistancePPill %d))", 
//								(int)this.minPacmanDistancePPill));
		return facts;
	}
}
