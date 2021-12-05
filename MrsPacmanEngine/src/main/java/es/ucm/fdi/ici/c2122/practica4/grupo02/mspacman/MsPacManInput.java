package es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman;

import java.util.HashMap;

import es.ucm.fdi.ici.c2122.practica4.grupo02.GameConstants;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.MsPacManFuzzyMemory.PillState;
import es.ucm.fdi.ici.fuzzy.FuzzyInput;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
public class MsPacManInput extends FuzzyInput {

	private double[] distance;
	
	/* TODO: Cazar a los fantasmas con su tiempo edible
	 * TODO: Que no vaya a por las powerPills a no ser que sea necesario
	 * TODO: guardar las powerPills
	 * TODO: Jugar con el tiempo de juego
	 * TODO: huir de los fantasmas en funcion de su ultimo movimiento visto
	 */
	
	public MsPacManInput(Game game) {
		super(game);
	}
	
	public void savePills(HashMap<Integer,PillState> pills) {
		int pcNode = game.getPacmanCurrentNodeIndex();
		int[] adjacentPathsNode = game.getNeighbouringNodes(pcNode);
		MOVE move;
		for(int i = 0; i < adjacentPathsNode.length; ++i) {
			move = game.getMoveToMakeToReachDirectNeighbour(pcNode, adjacentPathsNode[i]);
			int k = 0;
			boolean noEnd = false;
			int assumedPos = pcNode;
			while(k < GameConstants.sightLimit && !noEnd) {
				int node = game.getNeighbour(assumedPos, move);
				if(node != -1) {
					//Comparar si en este nodo hay una Pill
					int indexPill = game.getPillIndex(node);
					if( indexPill != -1) {
						if(!pills.containsKey(node)) { //Si no está la pill en el vector, la metemos
							PillState p = new PillState();
							p.indexPill = indexPill;
							p.x = game.getNodeXCood(node);
							p.y = game.getNodeXCood(node);
							p.eaten = false; // Como es la primera vez que se mete, está activa
							pills.put(node, p);
						}
					}
					assumedPos = node;
					++k;
				}else noEnd = true; //No hay mas visibilidad
				
			}
		}
	}
	
	void updatePillsState(HashMap<Integer,PillState> pills) {
		int pcNode = game.getPacmanCurrentNodeIndex();
		
		int indexPill = game.getPillIndex(pcNode);
		if(indexPill != -1) {
			// Si existe una pill en el nodo actual del pacman, es que se ha comido
			PillState p = pills.get(Integer.valueOf(pcNode));
			if(p != null) //No contiene aun esa pill en el inventario
				p.eaten = true;
		}
	}
	
	@Override
	public void parseInput() {
		distance = new double[] {-1,-1,-1,-1};
		
		int time = game.getCurrentLevelTime();
		int timeLimit = Constants.LEVEL_LIMIT;	
		for(GHOST g: GHOST.values()) {
			int index = g.ordinal();
			int pos = game.getGhostCurrentNodeIndex(g); //returns its positions if it's visible
			if(pos != -1) {
				distance[index] = game.getDistance(game.getPacmanCurrentNodeIndex(), pos, DM.PATH);
			}
			else
				distance[index] = -1;
		}
	}
	
	public boolean isVisible(GHOST ghost) {
		return distance[ghost.ordinal()] != -1;
	}

	@Override
	public HashMap<String, Double> getFuzzyValues() {
		HashMap<String,Double> vars = new HashMap<String,Double>();
		for(GHOST g: GHOST.values()) {
			vars.put(g.name()+"distance",   distance[g.ordinal()]);
		}
		return vars;
	}

}
