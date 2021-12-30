package es.ucm.fdi.ici.c2122.practica5.grupo02;

import java.io.File;
import java.awt.Color;

public class GameConstants {

	public static final String RULES_PATH = "es" + File.separator + "ucm" + File.separator + "fdi" + File.separator
			+ "ici" + File.separator + "c2122" + File.separator + "practica3" + File.separator + "grupo02"
			+ File.separator + "rules" + File.separator;

	public static final String FUZZY_PATH = "src" + File.separator + "main" + File.separator + "java" + File.separator
			+ "es" + File.separator + "ucm" + File.separator + "fdi" + File.separator + "ici" + File.separator + "c2122"
			+ File.separator + "practica4" + File.separator + "grupo02" + File.separator;

	public final static String CONNECTOR_FILE_PATH = "es" + File.separator + "ucm" + File.separator + "fdi" + File.separator
			+ "ici" + File.separator + "c2122" + File.separator + "practica5" + File.separator + "grupo02" + File.separator + "mspacman"
			 + File.separator + "plaintextconfig.xml";

	public final static String CASE_BASE_PATH = "src" + File.separator + "main" + File.separator + "java" + File.separator
			+ "es" + File.separator + "ucm" + File.separator + "fdi" + File.separator + "ici" + File.separator + "c2122"
			+ File.separator + "practica5" + File.separator + "grupo02" + File.separator + "cbrdata" + File.separator
			+ "mspacman" + File.separator;

	public static Color[] colours = { Color.RED, Color.PINK, Color.CYAN, Color.YELLOW };

	public static boolean DEBUG = false;
}