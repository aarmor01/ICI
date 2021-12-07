package es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts;

import es.ucm.fdi.ici.c2122.practica4.grupo02.GameConstants;
import es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts.GhostsFuzzyMemory.PowerPillState;
import es.ucm.fdi.ici.fuzzy.FuzzyInput;

import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

import java.util.HashMap;

public class GhostsInput extends FuzzyInput {

	private double[] distances;
	private double[] edibleTimes;
	private double[] lairTimes;

	private double time;
	private double pills;

	public GhostsInput(Game game) {
		super(game);
	}

	@Override
	public void parseInput() {
		distances = new double[] { -1, -1, -1, -1 };
		edibleTimes = new double[] { 0, 0, 0, 0 };
		lairTimes = new double[] { 0, 0, 0, 0 };

		
		
		for (GHOST ghost : GHOST.values()) {
			getEdibleTimes(ghost);
			getLairTimes(ghost);
			getPacmanDistance(ghost);
			time = game.getCurrentLevelTime();
			pills = game.getNumberOfActivePills();
		}
	}

	private void getEdibleTimes(GHOST ghost) {
		edibleTimes[ghost.ordinal()] = game.getGhostEdibleTime(ghost);
	}

	private void getLairTimes(GHOST ghost) {
		lairTimes[ghost.ordinal()] = game.getGhostLairTime(ghost);
	}

	private void getPacmanDistance(GHOST ghost) {
		int index = ghost.ordinal();
		int pos = game.getGhostCurrentNodeIndex(ghost);
		int pacManPos = game.getPacmanCurrentNodeIndex();

		if (pacManPos != -1)
			distances[index] = game.getDistance(pos, pacManPos, DM.PATH);
	}

	public double getPacmanDistanceToNearestPowerPill() {
		int pacManPos = game.getPacmanCurrentNodeIndex();

		if (pacManPos == -1)
			return -1;

		int[] pills = game.getActivePowerPillsIndices();
		int distance = Integer.MAX_VALUE;

		for (int pill : pills) {
			int dst = game.getShortestPathDistance(pacManPos, pill);
			if (dst > distance)
				distance = dst;
		}

		return distance;
	}

	public void ghostPosition() {
		game.getGhostCurrentNodeIndex(GHOST.BLINKY);
	}

	public void savePills(HashMap<Integer, PowerPillState> pills, GHOST ghost) {
		int pcNode = game.getGhostCurrentNodeIndex(ghost);
		int[] adjacentPathsNode = game.getNeighbouringNodes(pcNode);
		MOVE move;

		for (int i = 0; i < adjacentPathsNode.length; ++i) {
			move = game.getMoveToMakeToReachDirectNeighbour(pcNode, adjacentPathsNode[i]);
			int k = 0;
			boolean noEnd = false;
			int assumedPos = pcNode;
			while (k < GameConstants.sightLimit && !noEnd) {
				int node = game.getNeighbour(assumedPos, move);
				if (node != -1) {
					// Comparar si en este nodo hay una Pill
					int indexPill = game.getPowerPillIndex(node);
					if (indexPill != -1) {
						if (!pills.containsKey(node)) { // Si no está la pill en el vector, la metemos
							PowerPillState p = new PowerPillState();
							p.indexPPill = indexPill;
							p.x = game.getNodeXCood(node);
							p.y = game.getNodeXCood(node);
							p.eaten = false; // Como es la primera vez que se mete, está activa
							pills.put(node, p);
						}
					}
					assumedPos = node;
					++k;
				} else
					noEnd = true; // No hay mas visibilidad
			}
		}
	}

	public boolean isVisible(GHOST ghost) {
		return distances[ghost.ordinal()] != -1;
	}

	@Override
	public HashMap<String, Double> getFuzzyValues() {
		// puts the values on the fcl file input values
		HashMap<String, Double> vars = new HashMap<String, Double>();

		for (GHOST ghost : GHOST.values()) {
			vars.put(ghost.name() + "distanceToPacMan", distances[ghost.ordinal()]);
			vars.put(ghost.name() + "edibleTime", edibleTimes[ghost.ordinal()]);
			vars.put(ghost.name() + "lairTime", lairTimes[ghost.ordinal()]);
		}

		vars.put("numPills", pills);
		vars.put("currentTime", time);

		return vars;
	}
}