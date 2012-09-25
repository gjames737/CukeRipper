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

}
