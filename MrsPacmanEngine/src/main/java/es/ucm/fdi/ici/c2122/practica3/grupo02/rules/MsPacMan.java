package es.ucm.fdi.ici.c2122.practica3.grupo02.rules;

import java.util.HashMap;

import es.ucm.fdi.ici.c2122.practica3.grupo02.Utils;
import es.ucm.fdi.ici.c2122.practica3.grupo02.GameConstants;
import es.ucm.fdi.ici.c2122.practica3.grupo02.rules.pacman.MsPacManInput;
import es.ucm.fdi.ici.c2122.practica3.grupo02.rules.pacman.actions.*;
import es.ucm.fdi.ici.rules.RuleEngine;
import es.ucm.fdi.ici.rules.RulesAction;
import es.ucm.fdi.ici.rules.RulesInput;
import es.ucm.fdi.ici.rules.observers.ConsoleRuleEngineObserver;

import pacman.game.Game;
import pacman.game.Constants.MOVE;
import pacman.controllers.PacmanController;

public class MsPacMan extends PacmanController {
	
	private static final String PACMAN_RULES_PATH = GameConstants.RULES_PATH + "mspacmanrules.clp";
	private static final String PACMAN_NAME = "MsPacMan";
	
	HashMap<String,RulesAction> actionsMap;
	RuleEngine mspacmanRuleEngine;
	
	public MsPacMan() {
		setName("Definitely Not MsPacman");
		setTeam("G2_ICIsports");
		
		actionsMap = new HashMap<String, RulesAction>();
    	
		//ACTIONS------------------------
		RulesAction PCcleansBottom = new CleanBottomAction();  
		actionsMap.put(PCcleansBottom.getActionId(), PCcleansBottom);
		
		RulesAction runaway = new RunawayFromClosestGhost();  
		actionsMap.put(runaway.getActionId(), runaway);
		
		//-------------------------------
		String fileRule = PACMAN_RULES_PATH;
		mspacmanRuleEngine = new RuleEngine(PACMAN_NAME, fileRule, actionsMap);
		
		ConsoleRuleEngineObserver observer = new ConsoleRuleEngineObserver(PACMAN_NAME, true);
		mspacmanRuleEngine.addObserver(observer);
	}
	
	public void oldBehaviour() {
//		fsm = new FSM("MsPacMan");
//    	
//    	GraphFSMObserver observer = new GraphFSMObserver(fsm.toString());
//    	fsm.addObserver(observer);
//    	//Clean All Pills From the Bottom Map
//    	SimpleState isBorn_s0 = new SimpleState("Is Born", new IsBorn());
//    	Transition ExistingBottomPills_t1 = new WhileExistingBottomPills();
//    	SimpleState cleanBM_s1 = new SimpleState("Clean Bottom Map", new CleanBottomMap());
//    	fsm.add(isBorn_s0, ExistingBottomPills_t1, cleanBM_s1);
//    	    	
//    	//Collect Pills State---------------------------------
//    	//Create new StateMachine
//    	FSM collectPills_fsm1 = new FSM("Collect Pills");
//    	GraphFSMObserver c1observer = new GraphFSMObserver(collectPills_fsm1.toString());
//    	collectPills_fsm1.addObserver(c1observer);
//    	CompoundState collectPills_s2 = new CompoundState("Collect Pills", collectPills_fsm1);
//    	
//    	//Path With More Pills -> (  Alternative Path | Closest Pill)
//    	SimpleState TakePathWithMorePills_s3 = new SimpleState("Take Path With More Pills", new TakePathWithMorePills());
//    	SimpleState ReachClosestPill_s4 = new SimpleState("ReachClosestPill", new ReachClosestPill());
//    	Transition NotPillsInRange_t4 = new NotPillsInRange("1");
//    	Transition MoreThanOnePillsInRange_t5 = new MoreThanOnePill_InRange();
//    	collectPills_fsm1.add(TakePathWithMorePills_s3, NotPillsInRange_t4, ReachClosestPill_s4);
//    	collectPills_fsm1.add(ReachClosestPill_s4 , MoreThanOnePillsInRange_t5, TakePathWithMorePills_s3);
//    	
//    	//Alternative Path -> ( Path With More Pills | Alternative Path | Closest Pill)
//    	Transition PathBlockedByGhost_t6 = new PathBlockedOrMayBeBlockedByGhost("1");
//    	Transition PathNotBlocked_t7 = new PathNotBlockedByGhost();
//    	SimpleState TakeAlternativePathToClosetsPill_s5 = new SimpleState("Take Alternative Path To Closest Pill", new TakeAlternativePathToClosestPill());
//    	collectPills_fsm1.add(TakePathWithMorePills_s3, PathBlockedByGhost_t6, TakeAlternativePathToClosetsPill_s5);
//    	collectPills_fsm1.add(TakeAlternativePathToClosetsPill_s5, PathNotBlocked_t7, TakePathWithMorePills_s3);
//    	
//    	Transition NotPillsInRange_t41 = new NotPillsInRange("2");
//    	Transition PathBlockedByGhost_t61 = new PathBlockedOrMayBeBlockedByGhost("2");
//    	collectPills_fsm1.add(TakeAlternativePathToClosetsPill_s5, PathBlockedByGhost_t61, TakeAlternativePathToClosetsPill_s5);
//    	collectPills_fsm1.add(TakeAlternativePathToClosetsPill_s5, NotPillsInRange_t41, ReachClosestPill_s4);
//    	
//    	//Base State
//    	collectPills_fsm1.ready(TakePathWithMorePills_s3);
//    	//---------------------------------
//    	//Is Born -> Collect Pills
//    	Transition NotExistingBottomPills_t2 = new WhileNotExistingBottomPills();
//    	fsm.add(isBorn_s0, NotExistingBottomPills_t2, collectPills_s2);
//    	//Clean Bottom -> Collect Pills
//    	Transition ExistsActiveGhost_t3 = new StopsClearingBottomHalf();
//    	fsm.add(cleanBM_s1, ExistsActiveGhost_t3, collectPills_s2);
//    	
//    	//Collect Pills ->Runaway
//    	//Runaway -> Collect Pills 
//    	SimpleState runawayFromClosestGhost_s6 = new SimpleState("Runaway From Closest Ghost", new RunawayFromClosestGhost());
//    	Transition ghostTooCloseAndNotEdible_t8 = new GhostTooCloseAndNotEdible();
//    	Transition pacManNotInDanger_s7 = new PacManNotInDanger();
//    	fsm.add(collectPills_s2, ghostTooCloseAndNotEdible_t8, runawayFromClosestGhost_s6);
//    	fsm.add(runawayFromClosestGhost_s6, pacManNotInDanger_s7, collectPills_s2);
//    	
//    	//Runaway -> Chase Ghost
//    	SimpleState chaseGhost_s7 = new SimpleState("ChaseGhost", new ChaseGhost());
//    	Transition canChaseGhost_t9 = new CanChaseGhost();
//    	fsm.add(runawayFromClosestGhost_s6, canChaseGhost_t9, chaseGhost_s7);
//    	
//    	//Runaway -> Is Born
//    	Transition eatenByGhost_t10 = new IsEatenByGhost();
//    	fsm.add(runawayFromClosestGhost_s6, eatenByGhost_t10, isBorn_s0);
//    	
//    	//ChaseGhost -> Collect Pills
//    	Transition NoEdibleTimeAndNotInDanger_t11 = new NoEdibleTimeAndNotInDanger();
//    	fsm.add(chaseGhost_s7, NoEdibleTimeAndNotInDanger_t11, collectPills_s2);
//    	
//    	
//    	//Base State
//    	fsm.ready(isBorn_s0);
//    	
//    	if (GameConstants.DEBUG) {
//	    	JFrame frame = new JFrame();
//	    	JPanel main = new JPanel();
//	    	main.setLayout(new BorderLayout());
//	    	main.add(observer.getAsPanel(true, null), BorderLayout.NORTH);
//	    	main.add(c1observer.getAsPanel(true, null), BorderLayout.CENTER);
//	    	frame.getContentPane().add(main);
//	    	frame.pack();
//	    	frame.setVisible(true);
//    	}
	}
	
	
	public void preCompute(String opponent) {
//    		fsm.reset();
    }
	
    /* (non-Javadoc)
     * @see pacman.controllers.Controller#getMove(pacman.game.Game, long)
     */
    @Override
    public MOVE getMove(Game game, long timeDue) {
    	//Creamos el input
       	RulesInput in = new MsPacManInput(game); 
       	//Reseteamos sus valores a saber por qué
       	mspacmanRuleEngine.reset();
       	//Obtenemos los resultados de cada iteracion
       	mspacmanRuleEngine.assertFacts(in.getFacts());
       	//En funcion de los resultados, habra devuelto un MOVE
       	return mspacmanRuleEngine.run(game);
    }
}
