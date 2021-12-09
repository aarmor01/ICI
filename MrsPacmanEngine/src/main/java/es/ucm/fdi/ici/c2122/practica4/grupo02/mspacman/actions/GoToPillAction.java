package es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.actions;


import java.util.HashMap;
import java.util.Random;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2122.practica4.grupo02.GameConstants;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.MsPacManFuzzyMemory;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.MsPacManFuzzyMemory.PillState;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class GoToPillAction implements Action {
    
	private Random rnd = new Random();
    private MOVE[] allMoves = MOVE.values();
    MsPacManFuzzyMemory mem;
	public GoToPillAction(MsPacManFuzzyMemory mem_) { //se le puede pasar el el fuzzy Memory a la accion?
		mem = mem_;
	}
	
	//Buscamos la pill mas cercana en el rango de vision, si hay
	int getNextPillInRange(Game game) {
		//Nodo a la pill mas cercana
		int nodeTarget = -1;
		//Distancia minima a un nodo
		int minDistance = Integer.MAX_VALUE;
		//Nodo del pacMan
		int pcNode = game.getPacmanCurrentNodeIndex();
		MOVE move, pcLastMoveMade = game.getPacmanLastMoveMade();
		//Nodos contiguos al pacMan
		int[] adjacentPathsNode = game.getNeighbouringNodes(pcNode, pcLastMoveMade);
		for(int i = 0; i < adjacentPathsNode.length; ++i) {
			//Posicion auxiliar para la busqueda
			int assumedPos = pcNode;
			//Movimiento que lleva al nodo contiguo
			move = game.getMoveToMakeToReachDirectNeighbour(pcNode, adjacentPathsNode[i]);
			//Iterador de vision limite
			int k = 0;
			//Flag por si ha llegado a un muro
			boolean noEnd = false;
			while(k < GameConstants.sightLimit && !noEnd) {
				//Siguiente nodo en el camino del nodo contiguo
				int node = game.getNeighbour(assumedPos, move);
				if(node != -1) {
					int indexPill = game.getPillIndex(node); //indice en el array de pills
					if(indexPill != -1 && (game.isPillStillAvailable(indexPill) != null && game.isPillStillAvailable(indexPill) != false)) { //Existe pill en ese nodo y es comestible
						int aux = game.getShortestPathDistance(pcNode, node, pcLastMoveMade);
						if(aux < minDistance) {
							nodeTarget = node;
							minDistance = aux;
						}
					}
					assumedPos = node;
					++k;
				}else noEnd = true; //No hay mas camino
			}
		}
		
		return nodeTarget;
	}
	
	//Busca en el inventario de las pills, la mas cercana con respecto a su posicion
	int searchNearestPillSaved(Game game) {
		int target = -1;
		int pcNode = game.getPacmanCurrentNodeIndex();
		int distance = Integer.MAX_VALUE;
		
		for (HashMap.Entry<Integer, PillState> entry : mem.pillsSeen.entrySet()) {
			if(!entry.getValue().eaten) {
				int auxDis = game.getShortestPathDistance(pcNode, entry.getKey().intValue(), game.getPacmanLastMoveMade());
				//Si es mas pequeña su distancia y si la pill está activa
				if(auxDis < distance) {
					distance = auxDis;
					target = entry.getKey().intValue();
				}
			}
		}
		
		return target;
	}
	
	@Override
	public MOVE execute(Game game) {
		MOVE move = MOVE.NEUTRAL;
		int target = getNextPillInRange(game);
		
		if(target == -1) {
			target = searchNearestPillSaved(game);
		}
		
		if(target != -1) {
			if(GameConstants.DEBUG)
			GameView.addPoints(game, java.awt.Color.RED, target);	
			move = game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
					target,
					game.getPacmanLastMoveMade(), 
					DM.PATH);
		}
		
//		return allMoves[rnd.nextInt(allMoves.length)];
		return move;
    }

	@Override
	public String getActionId() {
		return "GoToPill";
	}
            
}
