package northwoods.cukeripper.utils.parsing;

import java.io.File;
import java.util.List;

import northwoods.cukeripper.utils.CommonRips;
import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.CukeScenario;

public class FeatureFileParser {

	private CukeFileReader reader;
	private CukeParser parser;

	public FeatureFileParser(CukeFileReader reader) {
		this.reader = reader;
		this.parser = new CukeParser();
	}

	public CukeFileReader getReader() {
		return reader;
	}

	public CukeFeature getFeatureFromFile(File file) {

		String featureContents = reader.readFullFileContents(file);
		String featureName = parser.getObjectNameFromContents(0,
				CommonRips.FEATURE + ":", featureContents);
		CukeFeature feature = new CukeFeature(featureName);

		String scenarioTag = CommonRips.SCENARIO + ":";
		List<Integer> indicesOfScenarioTags = parser.indicesOfOccurances(
				featureContents, scenarioTag);
		int numberOfScenarios = indicesOfScenarioTags.size();

		for (int i = 0; i < numberOfScenarios; i++) {
			CukeScenario scenario = parser.parseScenario(featureContents,
					scenarioTag, indicesOfScenarioTags, i);
			feature.addScenario(scenario);
		}

		return feature;
	}

}
