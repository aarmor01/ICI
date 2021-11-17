package es.ucm.fdi.ici.c2122.practica4.grupo02;

import java.io.File;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.MaxActionSelector;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.MsPacManInput;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.actions.GoToPPillAction;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.actions.RunAwayAction;
import es.ucm.fdi.ici.fuzzy.ActionSelector;
import es.ucm.fdi.ici.fuzzy.FuzzyEngine;
import es.ucm.fdi.ici.fuzzy.observers.ConsoleFuzzyEngineObserver;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacManFuzzy extends PacmanController {

	private static final String RULES_PATH = "src"+File.separator+"main"+File.separator+"java"+File.separator+"es"+File.separator+"ucm"+File.separator+"fdi"+File.separator+"ici"+File.separator+"c2122"+File.separator+"practica4"+File.separator+"grupo02"+File.separator+"mspacman"+File.separator;
	FuzzyEngine fuzzyEngine;
	
	public MsPacManFuzzy() {
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
