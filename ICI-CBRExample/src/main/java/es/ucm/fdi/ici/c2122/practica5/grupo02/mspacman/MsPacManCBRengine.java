package es.ucm.fdi.ici.c2122.practica5.grupo02.mspacman;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.RetrievalResult;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Threshold;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import es.ucm.fdi.gaia.jcolibri.util.FileIO;
import es.ucm.fdi.ici.c2122.practica5.grupo02.GameConstants;
import es.ucm.fdi.ici.c2122.practica5.grupo02.CBRengine.Average;
import es.ucm.fdi.ici.c2122.practica5.grupo02.CBRengine.CachedLinearCaseBase;
import es.ucm.fdi.ici.c2122.practica5.grupo02.CBRengine.CustomPlainTextConnector;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;

public class MsPacManCBRengine implements StandardCBRApplication {

	
	Game game;
	private MOVE action;
	private String opponent;
	private double similarityCase;
	private MsPacManStorageManager storageManager;

	NNConfig simConfig;
	CBRCaseBase caseBase;
	CustomPlainTextConnector connector;
	
	private final static String MSPACMAN_FOLDER = "mspacman" + File.separator;

	public MsPacManCBRengine(MsPacManStorageManager storageManager) {
		this.storageManager = storageManager;
	}

	public void setGame(Game game_) {
		this.game = game_;
	}
	
	public void setOpponent(String opponent) {
		this.opponent = opponent.replace("es.ucm.fdi.ici.c2122.practica5.grupo02.", "") ;
	}

	@Override
	public void configure() throws ExecutionException {
		connector = new CustomPlainTextConnector();
		caseBase = new CachedLinearCaseBase();

		connector.initFromXMLfile(FileIO.findFile(GameConstants.CONNECTOR_FILE_PATH + MSPACMAN_FOLDER + "plaintextconfig.xml"));
		connector.setCaseBaseFile(GameConstants.CASE_BASE_PATH + "mspacman" + File.separator, opponent + ".csv");

		this.storageManager.setCaseBase(caseBase);

		simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new Average());
		simConfig.addMapping(new Attribute("score", MsPacManDescription.class), new Interval(1500));
		simConfig.addMapping(new Attribute("time", MsPacManDescription.class), new Interval(4000)); //timeLimit 4000
		simConfig.addMapping(new Attribute("distanceNearestPPill", MsPacManDescription.class), new Interval(900));
		simConfig.addMapping(new Attribute("distanceNearestGhost", MsPacManDescription.class), new Interval(500));
		simConfig.addMapping(new Attribute("nearestNodeGhost", MsPacManDescription.class), new Threshold(50)); //Si esta por debajo la diferencia, se consideran similares
		simConfig.addMapping(new Attribute("edibleGhost", MsPacManDescription.class), new Equal());
		simConfig.addMapping(new Attribute("livesLeft", MsPacManDescription.class), new Interval(3));
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		caseBase.init(connector);
		return caseBase;
	}

	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		if (caseBase.getCases().isEmpty()) {
			this.action = MOVE.NEUTRAL;
		} else {
			// Compute retrieve
			Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(caseBase.getCases(), query,
					simConfig);
			// Compute reuse
			this.action = reuse(query, eval);
		}

		// Compute revise & retain
		CBRCase newCase = createNewCase(query);
		if(similarityCase < 0.93) //Si no es lo suficientemente similar, lo guardamos
			this.storageManager.reviseAndRetain(newCase);

	}

	private MOVE reuse(CBRQuery query_, Collection<RetrievalResult> eval) {
		int nCases = 5;
		// Selecciona el/los mas prioritarios
		Collection<RetrievalResult> collec = SelectCases.selectTopKRR(eval, nCases);
		Iterator<RetrievalResult> it = collec.iterator();

		double similarity = 0.0;
		MOVE action = MOVE.NEUTRAL;
		int[] moves = new int [5];
		
		for (int i = 0; i < collec.size(); i++) {
			RetrievalResult cases = it.next();
			CBRCase mostSimilarCase = cases.get_case();
			similarity += cases.getEval();

			MsPacManResult result = (MsPacManResult) mostSimilarCase.getResult();
			MsPacManSolution solution = (MsPacManSolution) mostSimilarCase.getSolution();
			
			moves[solution.getAction().ordinal()]++;
		}
		
		similarity = similarity / nCases; //AVARAGE 
		
		//TODO Pillar un caso que si esta siendo perseguido, y si tiene powerPill cercana
		//Si ha obtenido buena puntuacion 
		
		//VOTACION MAYORITARIA
		if(similarity > 0.55) {
			int index_ = 4; //Neutral
			for(int i = 0 ; i < 5; i++)
				if(moves[i] > moves[index_]) index_ = i; //elegimos por votacion mayoritara
			
			action = MOVE.values()[index_];
			
		}else {
			RetrievalResult case_ = collec.iterator().next();
			MsPacManResult result = (MsPacManResult) case_.get_case().getResult();
			MsPacManSolution sol = (MsPacManSolution) case_.get_case().getSolution();
			similarity = case_.getEval();
			
			if(result.score > 40) {
				action = sol.getAction();
				
			}else {
				MsPacManDescription descriptionReal = (MsPacManDescription) query_.getDescription();
				if(descriptionReal.nearestNodeGhost != -1) {
					int pcNode = game.getPacmanCurrentNodeIndex();
					action = game.getApproximateNextMoveAwayFromTarget(pcNode, descriptionReal.nearestNodeGhost, game.getPacmanLastMoveMade(), DM.PATH);
				}else {
					
//					action = game.getApproximateNextMoveTowardsTarget(pcNode, descriptionReal., action, null)
				}
			}
			
		}
		
		similarityCase = similarity;
		return action;
	}

	/**
	 * Creates a new case using the query as description, storing the action into
	 * the solution and setting the proper id number
	 */
	private CBRCase createNewCase(CBRQuery query) {
		CBRCase newCase = new CBRCase();
		MsPacManDescription newDescription = (MsPacManDescription) query.getDescription();
		MsPacManResult newResult = new MsPacManResult();
		MsPacManSolution newSolution = new MsPacManSolution();
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

	@Override
	public void postCycle() throws ExecutionException {
		this.storageManager.close();
		this.caseBase.close();
	}

}
