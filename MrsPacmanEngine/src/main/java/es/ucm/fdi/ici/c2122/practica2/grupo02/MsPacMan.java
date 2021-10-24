package es.ucm.fdi.ici.c2122.practica2.grupo02;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.MsPacManInput;
import es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.actions.*;
import es.ucm.fdi.ici.c2122.practica2.grupo02.mspacman.transitions.*;
import es.ucm.fdi.ici.fsm.CompoundState;
import es.ucm.fdi.ici.fsm.FSM;
import es.ucm.fdi.ici.fsm.SimpleState;
import es.ucm.fdi.ici.fsm.Transition;
import es.ucm.fdi.ici.fsm.observers.GraphFSMObserver;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacMan extends PacmanController {

	FSM fsm;
	public MsPacMan() {
		setName("MsPacMan XX");
		
    	fsm = new FSM("MsPacMan");
    	
    	GraphFSMObserver observer = new GraphFSMObserver(fsm.toString());
    	fsm.addObserver(observer);
    	//Clean All Pills From the Bottom Map
    	SimpleState isBorn_s0 = new SimpleState("Is Born", new IsBorn());
    	Transition ExistingBottomPills_t1 = new WhileExistingBottomPills();
    	SimpleState cleanBM_s1 = new SimpleState("Clean Bottom Map", new CleanBottomMap());
    	fsm.add(isBorn_s0, ExistingBottomPills_t1, cleanBM_s1);
    	    	
    	//Collect Pills State---------------------------------
    	//Create new StateMachine
    	FSM collectPills_fsm1 = new FSM("Collect Pills");
    	GraphFSMObserver c1observer = new GraphFSMObserver(collectPills_fsm1.toString());
    	collectPills_fsm1.addObserver(c1observer);
    	CompoundState collectPills_s2 = new CompoundState("Collect Pills", collectPills_fsm1);
    	
    	//Path With More Pills -> (  Alternative Path | Closest Pill)
    	SimpleState TakePathWithMorePills_s3 = new SimpleState("Take Path With More Pills", new TakePathWithMorePills());
    	SimpleState ReachClosestPill_s4 = new SimpleState("ReachClosestPill", new ReachClosestPill());
    	Transition NotPillsInRange_t4 = new NotPillsInRange("1");
    	Transition MoreThanOnePillsInRange_t5 = new MoreThanOnePill_InRange();
    	collectPills_fsm1.add(TakePathWithMorePills_s3, NotPillsInRange_t4, ReachClosestPill_s4);
    	collectPills_fsm1.add(ReachClosestPill_s4 , MoreThanOnePillsInRange_t5, TakePathWithMorePills_s3);
    	
    	//Alternative Path -> ( Path With More Pills | Alternative Path | Closest Pill)
    	Transition PathBlockedByGhost_t6 = new PathBlockedOrMayBeBlockedByGhost("1");
    	Transition PathNotBlocked_t7 = new PathNotBlockedByGhost();
    	SimpleState TakeAlternativePathToClosetsPill_s5 = new SimpleState("Take Alternative Path To Closest Pill", new TakeAlternativePathToClosestPill());
    	collectPills_fsm1.add(TakePathWithMorePills_s3, PathBlockedByGhost_t6, TakeAlternativePathToClosetsPill_s5);
    	collectPills_fsm1.add(TakeAlternativePathToClosetsPill_s5, PathNotBlocked_t7, TakePathWithMorePills_s3);
    	
    	Transition NotPillsInRange_t41 = new NotPillsInRange("2");
    	Transition PathBlockedByGhost_t61 = new PathBlockedOrMayBeBlockedByGhost("2");
    	collectPills_fsm1.add(TakeAlternativePathToClosetsPill_s5, PathBlockedByGhost_t61, TakeAlternativePathToClosetsPill_s5);
    	collectPills_fsm1.add(TakeAlternativePathToClosetsPill_s5, NotPillsInRange_t41, ReachClosestPill_s4);
    	
    	//Base State
    	collectPills_fsm1.ready(TakePathWithMorePills_s3);
    	//---------------------------------
    	//Is Born -> Collect Pills
    	Transition NotExistingBottomPills_t2 = new WhileNotExistingBottomPills();
    	fsm.add(isBorn_s0, NotExistingBottomPills_t2, collectPills_s2);
    	//Clean Bottom -> Collect Pills
    	Transition ExistsActiveGhost_t3 = new StopsClearingBottomHalf();
    	fsm.add(cleanBM_s1, ExistsActiveGhost_t3, collectPills_s2);
    	
    	//Collect Pills ->Runaway
    	//Runaway -> Collect Pills 
    	SimpleState runawayFromClosestGhost_s6 = new SimpleState("Runaway From Closest Ghost", new RunawayFromClosestGhost());
    	Transition ghostTooCloseAndNotEdible_t8 = new GhostTooCloseAndNotEdible();
    	Transition pacManNotInDanger_s7 = new PacManNotInDanger();
    	fsm.add(collectPills_s2, ghostTooCloseAndNotEdible_t8, runawayFromClosestGhost_s6);
    	fsm.add(runawayFromClosestGhost_s6, pacManNotInDanger_s7, collectPills_s2);
    	
    	//Runaway -> Chase Ghost
    	SimpleState chaseGhost_s7 = new SimpleState("ChaseGhost", new ChaseGhost());
    	Transition canChaseGhost_t9 = new CanChaseGhost();
    	fsm.add(runawayFromClosestGhost_s6, canChaseGhost_t9, chaseGhost_s7);
    	
    	//Runaway -> Is Born
    	Transition eatenByGhost_t10 = new IsEatenByGhost();
    	fsm.add(runawayFromClosestGhost_s6, eatenByGhost_t10, isBorn_s0);
    	
    	//ChaseGhost -> Collect Pills
    	Transition NoEdibleTimeAndNotInDanger_t11 = new NoEdibleTimeAndNotInDanger();
    	fsm.add(chaseGhost_s7, NoEdibleTimeAndNotInDanger_t11, collectPills_s2);
    	
    	
    	//Base State
    	fsm.ready(isBorn_s0);
    	
    	JFrame frame = new JFrame();
    	JPanel main = new JPanel();
    	main.setLayout(new BorderLayout());
    	main.add(observer.getAsPanel(true, null), BorderLayout.CENTER);
    	frame.getContentPane().add(main);
    	frame.pack();
    	frame.setVisible(true);
	}
	
	
	public void preCompute(String opponent) {
    		fsm.reset();
    }
	
	
	
    /* (non-Javadoc)
     * @see pacman.controllers.Controller#getMove(pacman.game.Game, long)
     */
    @Override
    public MOVE getMove(Game game, long timeDue) {
       	Input in = new MsPacManInput(game); 
    	return fsm.run(in);
    }
}
