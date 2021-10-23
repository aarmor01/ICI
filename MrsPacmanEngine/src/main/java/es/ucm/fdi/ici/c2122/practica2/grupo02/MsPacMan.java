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
    	FSM collectPills_fsm1 = new FSM("Collect Pills");
    	GraphFSMObserver c1observer = new GraphFSMObserver(collectPills_fsm1.toString());
    	collectPills_fsm1.addObserver(c1observer);
    	
    	CompoundState collectPills_s2 = new CompoundState("Collect Pills", collectPills_fsm1);
   
    	
    	SimpleState ReachClosestPill_s3 = new SimpleState("ReachClosestPill", new ReachClosestPill());
    	SimpleState TakePathWithMorePills_s4 = new SimpleState("Take Path With More Pills", new TakePathWithMorePills());
    	Transition NotPillsInRange_s4 = new NotPillsInRange();
    	
    	collectPills_fsm1.add(TakePathWithMorePills_s4, NotPillsInRange_s4, ReachClosestPill_s3);
    	collectPills_fsm1.add(ReachClosestPill_s3 , NotPillsInRange_s4, TakePathWithMorePills_s4);
    	//Collect Pills State---------------------------------
    	
    	//Base State
    	collectPills_fsm1.ready(TakePathWithMorePills_s4);
    	
    	Transition NotExistingBottomPills_t2 = new WhileNotExistingBottomPills();
    	fsm.add(isBorn_s0, NotExistingBottomPills_t2, collectPills_s2);
    	
    	Transition ExistsActiveGhost_t3 = new StopsClearingBottomHalf();
    	fsm.add(cleanBM_s1, ExistsActiveGhost_t3, collectPills_s2);
    	
    	
    	SimpleState chaseGhost_s3 = new SimpleState("ChaseGhost", new ChaseGhost());
    	Transition CanChaseGhost_t1 = new CanChaseGhost();
    	//fsm.add(ReachClosestPill_s2, CanChaseGhost_t1, chaseGhost_s3);
    	
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
