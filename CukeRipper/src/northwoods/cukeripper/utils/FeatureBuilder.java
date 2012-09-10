package northwoods.cukeripper.utils;

import java.util.ArrayList;
import java.util.List;

public class FeatureBuilder {

	private List<CukeFeature> features;

	public FeatureBuilder() {
		features = new ArrayList<CukeFeature>();
	}

	public void makeNewFeature(String featureName) {
		features.add(new CukeFeature(featureName));
	}

	public List<CukeFeature> getFeatures() {
		return features;
	}

}
