package northwoods.cukeripper.utils.parsing;

import java.io.File;
import java.util.List;

import northwoods.cukeripper.utils.CommonRips;
import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement;

public class FeatureFileParser {

	private static final String END_OF_CONTENTS_PADDING = "    ";
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
		featureContents = cleanFileContents(featureContents)
				+ END_OF_CONTENTS_PADDING;
		int indexOfStartOfFeaure = featureContents.indexOf(CommonRips.FEATURE
				+ ":");
		String featureName = parser
				.getObjectNameFromContents(indexOfStartOfFeaure,
						CommonRips.FEATURE + ":", featureContents);
		CukeFeature feature = new CukeFeature(featureName, file);

		String scenarioTag = CommonRips.SCENARIO + ":";
		List<Integer> indicesOfScenarioTags = parser.indicesOfOccurances(
				featureContents, scenarioTag);
		int numberOfScenarios = indicesOfScenarioTags.size();

		for (int i = 0; i < numberOfScenarios; i++) {
			CukeScenario scenario = parser.parseScenario(file, featureContents,
					scenarioTag, indicesOfScenarioTags, i);

			GWTStatement[] statements = scenario.getStatementsArray();
			for (int j = 0; j < statements.length; j++) {
				GWTStatement gwtStatement = statements[j];
				if (gwtStatement.getStepFile() == null) {
					// System.out.println(gwtStatement.slashToSlashStatement());
					File stepFile = parser.findStepFileForStatement(reader,
							gwtStatement);
					// if (stepFile == null) {
					// System.err.println("it is null here");
					// }

					gwtStatement.setStepFile(stepFile);
				} else {
					System.err.println("it already has a step file ["
							+ gwtStatement.slashToSlashStatement() + "]");
				}
			}
			feature.addScenario(scenario);
		}

		return feature;
	}

	private String cleanFileContents(String stringContents) {
		String firstChar = stringContents.substring(0, 1);
		while (firstChar.equals("\n") || firstChar.equals(" ")) {
			stringContents = stringContents.substring(1);
			firstChar = stringContents.substring(0, 1);
		}
		return stringContents;
	}

}
