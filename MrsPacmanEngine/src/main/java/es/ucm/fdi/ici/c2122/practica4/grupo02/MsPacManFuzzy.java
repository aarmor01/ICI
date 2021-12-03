package es.ucm.fdi.ici.c2122.practica4.grupo02;

import java.io.File;
import java.util.HashMap;

import es.ucm.fdi.ici.Action;

import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.*;
import es.ucm.fdi.ici.c2122.practica4.grupo02.mspacman.actions.*;

import es.ucm.fdi.ici.fuzzy.FuzzyEngine;
import es.ucm.fdi.ici.fuzzy.ActionSelector;
import es.ucm.fdi.ici.fuzzy.observers.ConsoleFuzzyEngineObserver;

import pacman.game.Game;
import pacman.game.Constants.MOVE;
import pacman.controllers.PacmanController;

public class MsPacManFuzzy extends PacmanController {

	FuzzyEngine fuzzyEngine;
	MsPacManFuzzyMemory fuzzyMemory;

	public MsPacManFuzzy() {
		setName("If you don't see it");
		setTeam("G2_ICIsports");
		
		fuzzyMemory = new MsPacManFuzzyMemory();

		Action[] actions = { new GoToPillAction(fuzzyMemory), new RunAwayAction() };

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
