package es.ucm.fdi.ici.c2122.practica5.grupo02.ghosts;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;

import es.ucm.fdi.ici.cbr.CBRInput;
import es.ucm.fdi.ici.c2122.practica5.grupo02.ghosts.GhostsDescription;

import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class GhostsInput extends CBRInput {

	
	Integer time;
	Integer distanceNearestGhost;
	Integer nearestNodeGhost;
	Boolean edible;
	Integer livesLeft;
	
	//New variables
	Integer score;
	Integer pillsLeft;
	Integer powerPillsLeft;
	Integer edibleGhosts;
	
	Integer[] distancesToPacMan;
	Integer[] ghostsPositions;
	boolean[] inLair;
	Integer distanceNearestPPill;
	MOVE[] lastGhostsMoves;
	Integer[] edibleTimes;

	public GhostsInput(Game game) {
		super(game);
	}

	@Override
	public void parseInput() {
		computeNearestGhost(game);
		computeNearestPPill(game);

		score = game.getScore();
		time = game.getTotalTime();
		livesLeft = game.getPacmanNumberOfLivesRemaining();
		
		pillsLeft = game.getNumberOfActivePills();
		powerPillsLeft = game.getNumberOfActivePowerPills();
		edibleGhosts = 0;
		for (GHOST ghost : GHOST.values()) {
			if(game.isGhostEdible(ghost)) {
				edibleGhosts++;
			}
		}
		
		distancesToPacMan = new Integer[]{0,0,0,0};
		ghostsPositions = new Integer[]{0,0,0,0};
		inLair = new boolean[]{false, false, false, false};
		lastGhostsMoves = new MOVE[]{MOVE.NEUTRAL, MOVE.NEUTRAL, MOVE.NEUTRAL, MOVE.NEUTRAL};
		edibleTimes = new Integer[]{0,0,0,0};
		
		Integer nodePacman = game.getPacmanCurrentNodeIndex();
		
		distancesToPacMan[0] = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(GHOST.BLINKY), nodePacman, game.getGhostLastMoveMade(GHOST.BLINKY));
		distancesToPacMan[1] = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(GHOST.PINKY), nodePacman, game.getGhostLastMoveMade(GHOST.PINKY));
		distancesToPacMan[2] = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(GHOST.INKY), nodePacman, game.getGhostLastMoveMade(GHOST.INKY));
		distancesToPacMan[3] = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(GHOST.SUE), nodePacman, game.getGhostLastMoveMade(GHOST.SUE));
		
		Integer contadorFantasmas = 0;
		for (GHOST ghost : GHOST.values()) {
			ghostsPositions[contadorFantasmas] = game.getGhostCurrentNodeIndex(ghost);
			contadorFantasmas++;
		}
		
		contadorFantasmas = 0;
		for (GHOST ghost : GHOST.values()) {
			inLair[contadorFantasmas] = game.getGhostLairTime(ghost) > 0;
			contadorFantasmas++;
		}
		
		contadorFantasmas = 0;
		for (GHOST ghost : GHOST.values()) {
			lastGhostsMoves[contadorFantasmas] = game.getGhostLastMoveMade(ghost);
			contadorFantasmas++;
		}
		
		contadorFantasmas = 0;
		for (GHOST ghost : GHOST.values()) {
			edibleTimes[contadorFantasmas] = game.getGhostEdibleTime(ghost);
			contadorFantasmas++;
		}
	}

	@Override
	public CBRQuery getQuery() {
		GhostsDescription description = new GhostsDescription();

		description.setEdibleGhost(edible);
		description.setNearestNodeGhost(nearestNodeGhost);
		description.setDistanceNearestGhost(distanceNearestGhost);
		description.setDistanceNearestPPill(distanceNearestPPill);
		description.setScore(score);
		description.setTime(time);
		description.setLivesLeft(livesLeft);

		CBRQuery query = new CBRQuery();
		query.setDescription(description);

		return query;
	}

	private void computeNearestGhost(Game game) {
		nearestNodeGhost = -1;
		distanceNearestGhost = Integer.MAX_VALUE;
		edible = false;
		GHOST nearest = null;
		for (GHOST g : GHOST.values()) {
			int pos = game.getGhostCurrentNodeIndex(g);
			int distance;
			if (pos != -1)
				distance = (int) game.getDistance(game.getPacmanCurrentNodeIndex(), pos, DM.PATH);
			else
				distance = Integer.MAX_VALUE;
			if (distance < nearestNodeGhost) {

				nearestNodeGhost = game.getGhostCurrentNodeIndex(g);
				distanceNearestGhost = distance;
				nearest = g;
			}
		}
		if (nearest != null)
			edible = game.isGhostEdible(nearest);
	}

	private void computeNearestPPill(Game game) {
		distanceNearestPPill = Integer.MAX_VALUE;
		for (int pos : game.getPowerPillIndices()) {
			int distance = (int) game.getDistance(game.getPacmanCurrentNodeIndex(), pos, DM.PATH);
			if (distance < nearestNodeGhost && game.isPowerPillStillAvailable(pos))
				distanceNearestPPill = distance;
		}
	}
}
