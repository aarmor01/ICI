package es.ucm.fdi.ici.practica4.demofuzzy;

import java.io.File;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.fuzzy.ActionSelector;
import es.ucm.fdi.ici.fuzzy.FuzzyEngine;
import es.ucm.fdi.ici.fuzzy.observers.ConsoleFuzzyEngineObserver;
import es.ucm.fdi.ici.practica4.demofuzzy.mspacman.MaxActionSelector;
import es.ucm.fdi.ici.practica4.demofuzzy.mspacman.MsPacManInput;
import es.ucm.fdi.ici.practica4.demofuzzy.mspacman.actions.GoToPPillAction;
import es.ucm.fdi.ici.practica4.demofuzzy.mspacman.actions.RunAwayAction;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacMan extends PacmanController {

	private static final String RULES_PATH = "src"+File.separator+"es"+File.separator+"ucm"+File.separator+"fdi"+File.separator+"ici"+File.separator+"practica4"+File.separator+"demofuzzy"+File.separator+"mspacman"+File.separator;
	FuzzyEngine fuzzyEngine;
	
	public MsPacMan()
	{
		setName("MsPacMan XX");

		Action[] actions = {new GoToPPillAction(), new RunAwayAction()};
		
		ActionSelector actionSelector = new MaxActionSelector(actions);
		 
		ConsoleFuzzyEngineObserver observer = new ConsoleFuzzyEngineObserver("MsPacMan","MsPacManRules");
		fuzzyEngine = new FuzzyEngine("MsPacMan",RULES_PATH+"mspacman.fcl","FuzzyMsPacMan",actionSelector);
		fuzzyEngine.addObserver(observer);
	}
	
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		MsPacManInput input = new MsPacManInput(game);
		input.parseInput();
		return fuzzyEngine.run(input.getFuzzyValues(),game);
	}

}
