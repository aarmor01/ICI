package es.ucm.fdi.ici.c2122.practica4.grupo02;

import java.io.File;
import java.util.HashMap;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.MsPacManFuzzyMemory;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.MsPacManInput;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.actions.ChaseGhostAction;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.actions.ChasePoints;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.actions.GoToPillAction;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.actions.RunAwayAction;
import es.ucm.fdi.ici.fuzzy.ActionSelector;
import es.ucm.fdi.ici.fuzzy.FuzzyEngine;
import es.ucm.fdi.ici.fuzzy.observers.ConsoleFuzzyEngineObserver;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacMan extends PacmanController {

	FuzzyEngine fuzzyEngine;
	MsPacManFuzzyMemory fuzzyMemory;

	public MsPacMan() {
		setName("If you don't see it");
		setTeam("G2_ICIsports");
		
		fuzzyMemory = new MsPacManFuzzyMemory();

		Action[] actions = { new GoToPillAction(fuzzyMemory) , new RunAwayAction(fuzzyMemory), new ChasePoints(fuzzyMemory), new ChaseGhostAction(fuzzyMemory) };

		ActionSelector actionSelector = new MaxActionSelector(actions);

		fuzzyEngine = new FuzzyEngine("MsPacMan", GameConstants.FUZZY_PATH + "mspacman" + File.separator + "mspacman.fcl", "FuzzyMsPacMan",
				actionSelector);

		if (GameConstants.DEBUG) {
			ConsoleFuzzyEngineObserver observer = new ConsoleFuzzyEngineObserver("MsPacMan", "MsPacManRules");
			fuzzyEngine.addObserver(observer);
		}
	}

	@Override
	public MOVE getMove(Game game, long timeDue) {
		MsPacManInput input = new MsPacManInput(game);
		input.parseInput();
		fuzzyMemory.getInput(input);

		HashMap<String, Double> fvars = input.getFuzzyValues();
		fvars.putAll(fuzzyMemory.getFuzzyValues());

		return fuzzyEngine.run(fvars, game);
	}
}
