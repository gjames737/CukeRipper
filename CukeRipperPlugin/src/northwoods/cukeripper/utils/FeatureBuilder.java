package northwoods.cukeripper.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FeatureBuilder {

	private List<CukeFeature> features;

	public FeatureBuilder() {
		features = new ArrayList<CukeFeature>();
	}

	public void makeNewFeature(String featureName, File f) {
		features.add(new CukeFeature(featureName, f));
	}

	public List<CukeFeature> getFeatures() {
		return features;
	}

	public List<CukeScenario> listAllScenarios() {
		List<CukeScenario> allScenarios = new ArrayList<CukeScenario>();
		for (CukeFeature feature : features) {
			List<CukeScenario> scenarios = feature.getScenarios();
			for (CukeScenario scen : scenarios) {
				if (!allScenarios.contains(scen))
					allScenarios.add(scen);
			}
		}

		return allScenarios;
	}

	public void addParsedFeature(CukeFeature parsedfeature) {
		features.add(parsedfeature);
	}

	public CukeScenario[] listAllScenariosAsArray() {
		List<CukeScenario> scensList = listAllScenarios();
		CukeScenario[] scens = new CukeScenario[scensList.size()];
		for (int i = 0; i < scens.length; i++) {
			scens[i] = scensList.get(i);
		}
		return scens;
	}

	public List<GWTStatement> listStatements() {
		List<GWTStatement> statementList = new ArrayList<GWTStatement>();

		for (CukeFeature feature : features) {
			List<CukeScenario> scens = feature.getScenarios();
			for (CukeScenario cukeScenario : scens) {
				List<GWTStatement> states = cukeScenario.getStatements();
				for (GWTStatement gwtStatement : states) {
					statementList.add(gwtStatement);
				}
			}
		}
		return statementList;
	}

	public GWTStatement[] listStatementsAsArray() {
		List<GWTStatement> statementList = listStatements();
		GWTStatement[] statements = new GWTStatement[statementList.size()];
		for (int i = 0; i < statementList.size(); i++) {
			statements[i] = statementList.get(i);
		}
		return statements;
	}
}
