package es.ucm.fdi.ici.c2122.practica5.grupo02.ghosts;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import pacman.game.Constants.MOVE;

public class GhostsDescription implements CaseComponent {

	Integer id;
	
	Integer score;
	Integer time;
	Integer distanceNearestPPill; 
	Integer distanceNearestGhost;//
	Integer nearestNodeGhost;
	Boolean edibleGhost;
	Integer livesLeft; //
//	MOVE lastMoveMade;//NEW
//	MOVE currMoveMade;//NEW
//	Integer distanceNearestPill;//NEW
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Integer getDistanceNearestPPill() {
		return distanceNearestPPill;
	}

	public void setDistanceNearestPPill(Integer nearestPPill) {
		this.distanceNearestPPill = nearestPPill;
	}

	public void setDistanceNearestGhost(Integer d) {
		this.distanceNearestGhost = d;
	}

	public Integer getDistanceNearestGhost() {
		return distanceNearestGhost;
	}

	public Integer getNearestNodeGhost() {
		return nearestNodeGhost;
	}
	
	public void setNearestNodeGhost(Integer nearestGhost) {
		this.nearestNodeGhost = nearestGhost;
	}

	public Boolean getEdibleGhost() {
		return edibleGhost;
	}

	public void setEdibleGhost(Boolean edibleGhost) {
		this.edibleGhost = edibleGhost;
	}
	
	public Integer getLivesLeft() {
		return livesLeft;
	}
	
	public void setLivesLeft(Integer l) {
		livesLeft = l;
	}
	
	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", GhostsDescription.class);
	}

	@Override
	public String toString() {
		return "GhostsDescription [id=" + id + ", score=" + score + ", time=" + time + ", distanceNearestPPill="
				+ distanceNearestPPill + ", distanceNearestGhost=" + distanceNearestGhost + ", nearestNodeGhost=" 
				+ nearestNodeGhost + ", edibleGhost=" + edibleGhost + ", livesLeft =" + livesLeft + "]";
	}


	
	

}
