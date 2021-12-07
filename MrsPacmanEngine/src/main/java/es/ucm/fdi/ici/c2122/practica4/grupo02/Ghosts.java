package es.ucm.fdi.ici.c2122.practica4.grupo02;

import java.io.File;
import java.util.EnumMap;
import java.util.HashMap;

import es.ucm.fdi.ici.Action;

import es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts.*;
import es.ucm.fdi.ici.c2122.practica4.grupo02.ghosts.actions.*;

import es.ucm.fdi.ici.fuzzy.FuzzyEngine;
import es.ucm.fdi.ici.fuzzy.ActionSelector;
import es.ucm.fdi.ici.fuzzy.observers.ConsoleFuzzyEngineObserver;

import pacman.game.Game;
import pacman.game.Constants.MOVE;
import pacman.game.Constants.GHOST;
import pacman.controllers.GhostController;

public class Ghosts extends GhostController {

	EnumMap<GHOST, FuzzyEngine> ghostsFuzzyEngine;
	EnumMap<GHOST, GhostsFuzzyMemory> ghostsFuzzyMemory;

	public Ghosts() {
		setName("It doesn't exists");
		setTeam("G2_ICIsports");

		ghostsFuzzyEngine = new EnumMap<GHOST, FuzzyEngine>(GHOST.class);
		ghostsFuzzyMemory = new EnumMap<GHOST, GhostsFuzzyMemory>(GHOST.class);

		for (GHOST ghost : GHOST.values()) {
			ghostsFuzzyMemory.put(ghost, new GhostsFuzzyMemory());

			Action[] actions = { new ChasePacMan(ghost, ghostsFuzzyMemory.get(ghost)),
					new RunAwayGhosts(ghost, ghostsFuzzyMemory.get(ghost)), new ProtectGhost(ghost, ghostsFuzzyMemory.get(ghost)) };

			ActionSelector actionSelector = new MaxActionSelector(actions);

			ghostsFuzzyEngine.put(ghost,
					new FuzzyEngine("Ghosts" + ghost.name(),
							GameConstants.FUZZY_PATH + "ghosts" + File.separator + "ghosts.fcl", "FuzzyGhosts",
							actionSelector));

			if (GameConstants.DEBUG) {
				ConsoleFuzzyEngineObserver observer = new ConsoleFuzzyEngineObserver(ghost.name(), "FuzzyGhosts");
				ghostsFuzzyEngine.get(ghost).addObserver(observer);
			}
		}
	}

	public void preCompute(String opponent) {
	}

	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		EnumMap<GHOST, MOVE> result = new EnumMap<GHOST, MOVE>(GHOST.class);

		for (GHOST ghost : GHOST.values()) {
			GhostsInput inGhost = new GhostsInput(game);

			inGhost.parseInput();
			ghostsFuzzyMemory.get(ghost).getInput(inGhost);

			HashMap<String, Double> fvars = inGhost.getFuzzyValues();
			fvars.putAll(ghostsFuzzyMemory.get(ghost).getFuzzyValues());

			result.put(ghost, ghostsFuzzyEngine.get(ghost).run(fvars, game));
		}

		return result;
	}
}
