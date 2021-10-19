package es.ucm.fdi.ici.c2122.practica2.grupo02;

import pacman.Executor;

import pacman.controllers.GhostController;
import pacman.controllers.PacmanController;

public class ExecutorTest {
	public static void main(String[] args) {
		Executor executor = new Executor.Builder()
				.setTickLimit(4000)
//				.setPacmanPO(false)
//				.setSightLimit(30)
//				.setPOType(RADIUS)
				.setVisual(true)
				.setScaleFactor(2.0)
				.build();
		
//		PacmanController pacMan = new HumanController(new KeyBoardInput());
		PacmanController pacMan = new MsPacMan();
		GhostController ghosts = new Ghosts();
		
		System.out.println(executor.runGame(pacMan, ghosts, 50));
	}
}