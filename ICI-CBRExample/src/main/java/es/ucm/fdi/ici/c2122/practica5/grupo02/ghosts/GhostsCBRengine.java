package es.ucm.fdi.ici.c2122.practica5.grupo02.ghosts;

import java.io.File;
import java.util.Iterator;
import java.util.Collection;

import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.RetrievalResult;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import es.ucm.fdi.gaia.jcolibri.util.FileIO;

import es.ucm.fdi.ici.c2122.practica5.grupo02.GameConstants;
import es.ucm.fdi.ici.c2122.practica5.grupo02.CBRengine.Average;
import es.ucm.fdi.ici.c2122.practica5.grupo02.CBRengine.CachedLinearCaseBase;
import es.ucm.fdi.ici.c2122.practica5.grupo02.CBRengine.CustomPlainTextConnector;

import pacman.game.Constants.MOVE;

public class GhostsCBRengine implements StandardCBRApplication {

	// our variables
	private MOVE action;
	private String opponent;
	private GhostsStorageManager storageManager;

	// colibri variables
	NNConfig simConfig;
	CBRCaseBase caseBase;
	CustomPlainTextConnector connector;	
	
	public final static String GHOSTS_FOLDER = "ghosts" + File.separator;

	public GhostsCBRengine(GhostsStorageManager storageManager) {
		this.storageManager = storageManager;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent.replace("es.ucm.fdi.ici.c2122.practica5.grupo02.", "") ;
	}

	@Override
	public void configure() throws ExecutionException {
		caseBase = new CachedLinearCaseBase();
		connector = new CustomPlainTextConnector();

		connector.initFromXMLfile(FileIO.findFile(GameConstants.CONNECTOR_FILE_PATH + GHOSTS_FOLDER + "plaintextconfig.xml"));
		connector.setCaseBaseFile(GameConstants.CASE_BASE_PATH + "ghosts" + File.separator, opponent + ".csv");

		this.storageManager.setCaseBase(caseBase);

		simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new Average());
		
		simConfig.addMapping(new Attribute("score", GhostsDescription.class), new Interval(15000));
		simConfig.addMapping(new Attribute("time", GhostsDescription.class), new Interval(4000));
		simConfig.addMapping(new Attribute("distanceNearestPPill", GhostsDescription.class), new Interval(650));
		simConfig.addMapping(new Attribute("distanceNearestGhost", GhostsDescription.class), new Interval(650));
		simConfig.addMapping(new Attribute("nearestNodeGhost", GhostsDescription.class), new Interval(650));
		simConfig.addMapping(new Attribute("edibleGhost", GhostsDescription.class), new Equal());
		simConfig.addMapping(new Attribute("livesLeft", GhostsDescription.class), new Interval(650));
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		caseBase.init(connector);
		return caseBase;
	}

	@Override
	public void postCycle() throws ExecutionException {
		this.storageManager.close();
		this.caseBase.close();
	}

	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		if (caseBase.getCases().isEmpty()) {
			this.action = MOVE.NEUTRAL;
		} else {
			// AQUI SE OBTIENEN LOS CASOS DE LA BASE DE DATOS MAS SIMILARES A LA QUERY
			// Compute retrieve
			Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(caseBase.getCases(), query, simConfig);
			// Compute reuse
			this.action = reuse(eval);
		}

		// Compute revise & retain
		CBRCase newCase = createNewCase(query);
		this.storageManager.reviseAndRetain(newCase);
	}

	private MOVE reuse(Collection<RetrievalResult> eval) {
		// This simple implementation only uses 1NN
		// Consider using kNNs with majority voting
		int nCases = 5;
		
		// Selecciona el/los mas prioritarios/CAMBIAR
		Collection<RetrievalResult> collec = SelectCases.selectTopKRR(eval, nCases);

		Iterator<RetrievalResult> it = collec.iterator();

		double similarity = 0.0;
		GhostsSolution solution = null;
		GhostsResult result = null;

		for (int i = 0; i < collec.size(); i++) {
			RetrievalResult cases = it.next();
			CBRCase mostSimilarCase = cases.get_case();
			similarity = cases.getEval();

			result = (GhostsResult) mostSimilarCase.getResult();
			solution = (GhostsSolution) mostSimilarCase.getSolution();
		}

		// Now compute a solution for the query

		// Here, it simply takes the action of the 1NN
		MOVE action = solution.getAction();

		// But if not enough similarity or bad case, choose another move randomly
		// //CAMBIAR
		if ((similarity < 0.7) || (result.getScore() < 100)) {
			int index = (int) Math.floor(Math.random() * 4);
			if (MOVE.values()[index] == action)
				index = (index + 1) % 4;
			action = MOVE.values()[index];
		}
		return action;
	}

	/**
	 * Creates a new case using the query as description, storing the action into
	 * the solution and setting the proper id number
	 */
	private CBRCase createNewCase(CBRQuery query) {
		CBRCase newCase = new CBRCase();
		
		GhostsDescription newDescription = (GhostsDescription)query.getDescription();
		GhostsResult newResult = new GhostsResult();
		GhostsSolution newSolution = new GhostsSolution();
		
		int newId = this.caseBase.getCases().size();
		newId += storageManager.getPendingCases();
		
		newDescription.setId(newId);
		newResult.setId(newId);
		newSolution.setId(newId);
		newSolution.setAction(this.action);
		
		newCase.setDescription(newDescription);
		newCase.setResult(newResult);
		newCase.setSolution(newSolution);
		
		return newCase;
	}

	public MOVE getSolution() {
		return this.action;
	}

}
