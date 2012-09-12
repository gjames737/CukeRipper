package northwoods.cukeripper.utils.parsing;

import java.io.File;

import northwoods.cukeripper.utils.CommonRips;
import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;

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

		String featureName = getFeatureNameFromContents(featureContents);
		CukeFeature feature = new CukeFeature(featureName);
		return feature;
	}

	private String getFeatureNameFromContents(String stringContents) {
		String featureTag = CommonRips.FEATURE + ":";
		int indexOfNameStart = featureTag.length();
		int indexOfNextEndOfLineChar = indexOfNameStart
				+ stringContents.substring(indexOfNameStart).indexOf("\n");
		String featureName = stringContents.substring(indexOfNameStart,
				indexOfNextEndOfLineChar);
		featureName = featureName.trim();
		return featureName;
	}
}
