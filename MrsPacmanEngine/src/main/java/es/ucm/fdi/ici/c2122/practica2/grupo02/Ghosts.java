package es.ucm.fdi.ici.c2122.practica2.grupo02;

import java.awt.Color;
import java.awt.Dimension;
import java.util.EnumMap;

import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.actions.Agressive;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.actions.ChasePrimaryPath;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.actions.ChaseSecondaryPath;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.actions.Lair;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.actions.Seer;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions.GoToMainRunAway;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions.IsMsPacManNear;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions.LairTimeOver;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions.StartRunAwayGhost;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions.StopRunAwayGhost;
import es.ucm.fdi.ici.fsm.CompoundState;
import es.ucm.fdi.ici.fsm.FSM;
import es.ucm.fdi.ici.fsm.SimpleState;
import es.ucm.fdi.ici.fsm.Transition;
import es.ucm.fdi.ici.fsm.observers.ConsoleFSMObserver;
import es.ucm.fdi.ici.fsm.observers.GraphFSMObserver;
import pacman.controllers.GhostController;
import pacman.game.Game;
import pacman.game.GameView;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class Ghosts extends GhostController {
	

	EnumMap<GHOST,FSM> fsms;
	public Ghosts()
	{
		setName("Ghosts XX");

		fsms = new EnumMap<GHOST,FSM>(GHOST.class);
		for(GHOST ghost: GHOST.values()) {
			FSM fsm = new FSM(ghost.name());
			fsm.addObserver(new ConsoleFSMObserver(ghost.name()));
			GraphFSMObserver graphObserver = new GraphFSMObserver(ghost.name());
			fsm.addObserver(graphObserver);

			
			FSM fsmChase = new FSM("Chase compound");
			
			SimpleState chasePrimaryPath = new SimpleState(new ChasePrimaryPath(ghost));
			SimpleState chaseSecondaryPath = new SimpleState(new ChaseSecondaryPath(ghost));
			SimpleState lair = new SimpleState(new Lair());
			
			Transition mainRunAwayTransition = new GoToMainRunAway();
			Transition isMsPacManNearTransition = new IsMsPacManNear(ghost);
			Transition lairTimeOverTransition = new LairTimeOver(ghost);
			Transition startRunAwayGhostTransition = new StartRunAwayGhost(ghost);
			Transition stopRunAwayGhostTransition = new StopRunAwayGhost(ghost);
			
			switch(ghost) {
			case BLINKY:
				SimpleState seer = new SimpleState(new Seer(ghost));
				fsm.add(lair, lairTimeOverTransition, seer);
				//fsm.add(seer, startRunAwayGhostTransition, seer);
				break;
			case INKY:
				//Cosas especificas de INKY
				//fsm.add(lair, lairTimeOverTransition, seer);
				break;
			case PINKY:
				//Cosas especificas de PINKY
				//fsm.add(lair, lairTimeOverTransition, seer);
				break;
			case SUE:
				SimpleState agressive = new SimpleState(new Agressive(ghost));
				fsm.add(lair, lairTimeOverTransition, agressive);
				break;
			}
			
//			fsm.add(chase, near, runAway);
//			fsm.add(runAway, toChaseTransition, chase);
//			
//			fsm.ready(chase);
			
			CompoundState chase = new CompoundState(ghost + "starts chasing", fsmChase);
			
			graphObserver.showInFrame(new Dimension(800,600));
			
			fsms.put(ghost, fsm);
		}
	}
	
	public void preCompute(String opponent) {
    	for(FSM fsm: fsms.values())
    		fsm.reset();
    }
	
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		EnumMap<GHOST,MOVE> result = new EnumMap<GHOST,MOVE>(GHOST.class);
		
		GhostsInput in = new GhostsInput(game);
		
		for(GHOST ghost: GHOST.values())
		{
			FSM fsm = fsms.get(ghost);
			MOVE move = fsm.run(in);
			result.put(ghost, move);
		}
		
		return result;
		
	
		
	}
	
	
//	boolean ghostNotOnSamePath(Game game, int ghostNode, int destination) {
//		//All the nodes in the path we want to check
//		int[] path = game.getShortestPath(ghostNode, destination);
//		
//		//For each Ghost we check if there is another ghost in the path
//		for(int pathNode : path) {
//			for(GHOST ghostType: GHOST.values()) {
//				int ghostInPath = game.getGhostCurrentNodeIndex(ghostType);
//				if (ghostInPath != ghostNode && pathNode == ghostInPath)
//					return false;
//			}	
//		}
//		
//		return true;
//	}
}
