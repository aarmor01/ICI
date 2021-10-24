package es.ucm.fdi.ici.c2122.practica2.grupo02;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.EnumMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.GhostInput;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.actions.*;
import es.ucm.fdi.ici.c2122.practica2.grupo02.ghosts.transitions.*;
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
		setName("Definitely Not Ghosts");
		setTeam("G2_ICIsports");

		fsms = new EnumMap<GHOST,FSM>(GHOST.class);
		for(GHOST ghost: GHOST.values()) {
			FSM fsm = new FSM(ghost.name());
			fsm.addObserver(new ConsoleFSMObserver(ghost.name()));
			GraphFSMObserver graphObserver = new GraphFSMObserver(ghost.name());
			fsm.addObserver(graphObserver);

			
			FSM fsmChase = new FSM("Chase compound");
			GraphFSMObserver c1observer = new GraphFSMObserver(fsmChase.toString());
	    	fsmChase.addObserver(c1observer);
	    	
	    	FSM fsmRun = new FSM("Run compound");
			GraphFSMObserver anotherObserver = new GraphFSMObserver(fsmRun.toString());
	    	fsmRun.addObserver(anotherObserver);
			
			SimpleState chasePrimaryPath = new SimpleState(new ChasePrimaryPath(ghost));
			SimpleState chaseSecondaryPath = new SimpleState(new ChaseSecondaryPath(ghost));
			SimpleState runAwayFromPacMan = new SimpleState(new RunAwayFromPacMan(ghost));
			SimpleState runTowardsGhost = new SimpleState(new RunAwayToGhost(ghost));
			SimpleState runAlternative = new SimpleState(new RunAwayAlternative(ghost));
			SimpleState lair = new SimpleState(new Lair());
			
			Transition mainRunAwayTransition = new GoToMainRunAway();
			Transition mainChaseTransition = new GoToMainChase();
			Transition isMsPacManNearTransition = new IsMsPacManNear(ghost);
			Transition lairTimeOverTransition = new LairTimeOver(ghost);
			Transition anotherGhostOnRunAway = new AnotherGhostOnRunAwayPath(ghost);
			Transition ghostNotEdibleTransition = new AnotherGhostIsntEdible(ghost);
			Transition startRunAwayGhostTransition = new StartRunAwayGhost(ghost);
			Transition startRunAwayGhostFromSpecificBehaviourTransition = new RunAwayFromSpecificBehaviourTransition(ghost);
			Transition stopRunAwayGhostTransition = new StopRunAwayGhost(ghost);
			Transition stopRunAwayAndChaseTransition = new StopRunAwayAndStartChase(ghost);
			Transition chaseSecondaryTransition = new ChaseSecondaryPathTransition(ghost);
			
			fsmChase.add(chasePrimaryPath, chaseSecondaryTransition, chaseSecondaryPath);
			fsmChase.add(chaseSecondaryPath, mainChaseTransition, chasePrimaryPath);
			
			fsmChase.ready(chasePrimaryPath);
			
			CompoundState chase = new CompoundState(ghost + " starts chasing", fsmChase);
			
			fsmRun.add(runAwayFromPacMan, ghostNotEdibleTransition, runTowardsGhost);
			fsmRun.add(runAwayFromPacMan, anotherGhostOnRunAway, runAlternative);
			fsmRun.add(runAlternative, mainRunAwayTransition, runAwayFromPacMan);
			
			fsmRun.ready(runAwayFromPacMan);
			
			CompoundState run = new CompoundState(ghost + " starts running", fsmRun);
			
			fsm.ready(lair);
			
			switch(ghost) {
			case BLINKY:
				SimpleState seer = new SimpleState(new Seer(ghost));
				Transition seerChaseSpecificTransition = new SeerSpecialChaseStart(ghost);
				fsm.add(lair, lairTimeOverTransition, seer);
				fsm.add(seer, isMsPacManNearTransition, chase);
				fsm.add(seer, seerChaseSpecificTransition, chase);
				fsm.add(seer, startRunAwayGhostFromSpecificBehaviourTransition, run);
				fsm.add(chase, startRunAwayGhostTransition, run);
				fsm.add(run, stopRunAwayGhostTransition, seer);
				fsm.add(run, stopRunAwayAndChaseTransition, chase);
				break;
			case PINKY:
			case INKY:
				SimpleState mole = new SimpleState(new Mole(ghost));
				fsm.add(lair, lairTimeOverTransition, mole);
				fsm.add(mole, isMsPacManNearTransition, chase);
				fsm.add(mole, startRunAwayGhostFromSpecificBehaviourTransition, run);
				fsm.add(chase, startRunAwayGhostTransition, run);
				fsm.add(run, stopRunAwayGhostTransition, mole);
				fsm.add(run, stopRunAwayAndChaseTransition, chase);
				break;
//			case INKY:
//				SimpleState moleambush = new SimpleState(new Ambush(ghost));
//				fsm.add(lair, lairTimeOverTransition, ambush);
//				fsm.add(ambush, isMsPacManNearTransition, chase);
//				fsm.add(ambush, startRunAwayGhostFromSpecificBehaviourTransition, run);
//				fsm.add(chase, startRunAwayGhostTransition, run);
//				fsm.add(run, stopRunAwayGhostTransition, ambush);
//				fsm.add(run, stopRunAwayAndChaseTransition, chase);
//				break;
			case SUE:
				SimpleState agressive = new SimpleState(new Agressive(ghost));
				fsm.add(lair, lairTimeOverTransition, agressive);
				fsm.add(agressive, isMsPacManNearTransition, chase);
				fsm.add(agressive, startRunAwayGhostFromSpecificBehaviourTransition, run);
				fsm.add(chase, startRunAwayGhostTransition, run);
				fsm.add(run, stopRunAwayGhostTransition, agressive);
				fsm.add(run, stopRunAwayAndChaseTransition, chase);
				break;
			}
			
			fsms.put(ghost, fsm);
			
			JFrame frame = new JFrame();
	    	JPanel main = new JPanel();
	    	main.setLayout(new BorderLayout());
	    	main.add(graphObserver.getAsPanel(true, null), BorderLayout.NORTH);
	    	main.add(c1observer.getAsPanel(true, null), BorderLayout.CENTER);
	    	main.add(anotherObserver.getAsPanel(true, null), BorderLayout.SOUTH);
	    	frame.getContentPane().add(main);
	    	frame.pack();
	    	frame.setVisible(true);
		}
	}
	
	public void preCompute(String opponent) {
    	for(FSM fsm: fsms.values())
    		fsm.reset();
    }
	
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		EnumMap<GHOST,MOVE> result = new EnumMap<GHOST,MOVE>(GHOST.class);
		
		GhostInput in = new GhostInput(game);
		
		for(GHOST ghost: GHOST.values())
		{
			FSM fsm = fsms.get(ghost);
			MOVE move = fsm.run(in);
			result.put(ghost, move);
		}
		
		return result;
	}
}
