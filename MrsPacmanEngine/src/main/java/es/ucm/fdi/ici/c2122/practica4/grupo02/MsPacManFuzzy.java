package es.ucm.fdi.ici.c2122.practica4.grupo02;

import java.io.File;
import java.util.HashMap;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.fuzzy.ActionSelector;
import es.ucm.fdi.ici.fuzzy.FuzzyEngine;
import es.ucm.fdi.ici.fuzzy.observers.ConsoleFuzzyEngineObserver;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.*;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.actions.*;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacManFuzzy extends PacmanController {

	private static final String RULES_PATH = "src"+File.separator+"main"+File.separator+"java"+File.separator+"es"+File.separator+"ucm"+File.separator+"fdi"+File.separator+"ici"+File.separator+"c2122"+File.separator+"practica4"+File.separator+"grupo02"+File.separator+"mspacman"+File.separator;
	FuzzyEngine fuzzyEngine;
	MsPacManFuzzyMemory fuzzyMemory;
	
	
	public MsPacManFuzzy(){
		setName("If you don't see it, ");
		setTeam("G2_ICIsports");
		fuzzyMemory = new MsPacManFuzzyMemory();
		
		Action[] actions = {new GoToPillAction(fuzzyMemory), new RunAwayAction()};
		
		ActionSelector actionSelector = new MaxActionSelector(actions);
		 
		ConsoleFuzzyEngineObserver observer = new ConsoleFuzzyEngineObserver("MsPacMan","MsPacManRules");
		fuzzyEngine = new FuzzyEngine("MsPacMan",RULES_PATH+"mspacman.fcl","FuzzyMsPacMan",actionSelector);
		fuzzyEngine.addObserver(observer);
		
	}
	
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		MsPacManInput input = new MsPacManInput(game);
		input.parseInput();
		fuzzyMemory.getInput(input);
		
		HashMap<String, Double> fvars = input.getFuzzyValues();
		fvars.putAll(fuzzyMemory.getFuzzyValues());
		
		return fuzzyEngine.run(fvars,game);
	}

}
