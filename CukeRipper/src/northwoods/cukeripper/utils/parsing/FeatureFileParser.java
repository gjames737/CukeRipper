package northwoods.cukeripper.utils.parsing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import northwoods.cukeripper.utils.CommonRips;
import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.CukeScenario;

public class FeatureFileParser {

	private CukeFileReader reader;

	public FeatureFileParser(CukeFileReader reader) {
		this.reader = reader;
	}

	public CukeFileReader getReader() {
		return reader;
	}

	public CukeFeature getFeatureFromFile(File file) {

		String featureContents = reader.readFullFileContents(file);
		String featureName = getObjectNameFromContents(0, CommonRips.FEATURE
				+ ":", featureContents);
		CukeFeature feature = new CukeFeature(featureName);

		String scenarioTag = CommonRips.SCENARIO + ":";
		List<Integer> indicesOfScenarioTags = indicesOfOccurances(
				featureContents, scenarioTag);
		int numberOfScenarios = indicesOfScenarioTags.size();

		for (int i = 0; i < numberOfScenarios; i++) {
			CukeScenario scenario = parseScenario(featureContents, scenarioTag,
					indicesOfScenarioTags, i);
			feature.addScenario(scenario);
		}

		return feature;
	}

	private CukeScenario parseScenario(String featureContents,
			String scenarioTag, List<Integer> indicesOfScenarioTags, int i) {
		String scenarioName = getObjectNameFromContents(
				indicesOfScenarioTags.get(i), scenarioTag, featureContents);
		scenarioName = scenarioName.trim();
		CukeScenario scenario = new CukeScenario(scenarioName);
		//

		//
		return scenario;
	}

	private List<Integer> indicesOfOccurances(String str, String subStr) {
		int lastIndex = 0;
		List<Integer> indices = new ArrayList<Integer>();
		while (lastIndex != -1) {
			lastIndex = str.indexOf(subStr, lastIndex);
			if (lastIndex != -1) {
				indices.add(lastIndex);
				lastIndex += subStr.length();
			}
		}

		return indices;
	}

	private String getObjectNameFromContents(int offset, String tag,
			String stringContents) {

		int indexOfNameStart = offset + tag.length();
		int indexOfNextEndOfLineChar = indexOfNameStart
				+ stringContents.substring(indexOfNameStart).indexOf("\n");
		String featureName = stringContents.substring(indexOfNameStart,
				indexOfNextEndOfLineChar);
		featureName = featureName.trim();
		return featureName;
	}
}
