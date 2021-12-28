package es.ucm.fdi.ici.c2122.practica5.grupo02.mspacman;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.ici.cbr.CBRInput;
import es.ucm.fdi.ici.c2122.practica5.grupo02.mspacman.MsPacManDescription;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class MsPacManInput extends CBRInput {

	public MsPacManInput(Game game) {
		super(game);
		
	}

	Integer score;
	Integer time;
	Integer distanceNearestPPill;
	Integer distanceNearestGhost;
	Integer nearestNodeGhost;
	Boolean edible;
	Integer livesLeft;
	
	@Override
	public void parseInput() {
		computeNearestGhost(game);
		computeNearestPPill(game);
		time = game.getTotalTime();
		score = game.getScore();
		livesLeft = game.getPacmanNumberOfLivesRemaining();
	}

	@Override
	public CBRQuery getQuery() {
		MsPacManDescription description = new MsPacManDescription();
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
		nearestNodeGhost = Integer.MAX_VALUE;
		edible = false;
		GHOST nearest = null;
		for(GHOST g: GHOST.values()) {
			int pos = game.getGhostCurrentNodeIndex(g);
			int distance; 
			if(pos != -1) 
				distance = (int)game.getDistance(game.getPacmanCurrentNodeIndex(), pos, DM.PATH);
			else
				distance = Integer.MAX_VALUE;
			if(distance < nearestNodeGhost){
				
				nearestNodeGhost = game.getGhostCurrentNodeIndex(g);
				distanceNearestGhost = distance;
				nearest = g;
			}
		}
		if(nearest!=null)
			edible = game.isGhostEdible(nearest);
	}
	
	private void computeNearestPPill(Game game) {
		distanceNearestPPill = Integer.MAX_VALUE;
		for(int pos: game.getPowerPillIndices()) {
			int distance = (int)game.getDistance(game.getPacmanCurrentNodeIndex(), pos, DM.PATH);
			if(distance < nearestNodeGhost)
				distanceNearestPPill = distance;
		}
	}
}
