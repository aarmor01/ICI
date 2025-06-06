package es.ucm.fdi.ici.c2122.practica4.grupo02;

import java.awt.Color;
import java.io.File;

public class GameConstants {

	public static final String RULES_PATH = "es" + File.separator + "ucm" + File.separator + "fdi" + File.separator
			+ "ici" + File.separator + "c2122" + File.separator + "practica3" + File.separator + "grupo02"
			+ File.separator + "rules" + File.separator;

	public static final String FUZZY_PATH = "src" + File.separator + "main" + File.separator + "java" + File.separator
			+ "es" + File.separator + "ucm" + File.separator + "fdi" + File.separator + "ici" + File.separator + "c2122"
			+ File.separator + "practica4" + File.separator + "grupo02" + File.separator;
	
	public static Color[] colours = { Color.RED, Color.PINK, Color.CYAN, Color.YELLOW };
	
	public static int maxEdibleTime = 200;

	public static int pacmanChaseDistance = 40;
	
	public static int pacmanRunawayDistance = 40;
	
	public static int ghostChaseDistance = 50;

	public static int minPredictionDistance = 15;

	public static int numIntersectionsBeforeChange = 2;
	
	public static int minRangePacmanPowerPill = 10;
	
	public static int sightLimit = 50;

	public static boolean DEBUG = false;
}