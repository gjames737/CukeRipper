package northwoods.cukeripper.utils.parsing.string;

import java.io.File;
import java.util.List;

import northwoods.cukeripper.utils.CommonRips;
import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.parsing.CukeConsole;
import northwoods.cukeripper.utils.parsing.CukeParser;
import northwoods.cukeripper.utils.parsing.FeatureFileParser;

public class FeatureFileStringParser {

	public CukeFeature parseOutFeature(File file, CukeFileReader reader,
			CukeParser parser) throws Exception {
		CukeConsole.println(
				FeatureFileParser.CONSOLE_STR_PARSING_FEATURE_FROM_FILE + " "
						+ file.getAbsolutePath(), false);
		String featureContents = reader.readFullFileContents(file);
		featureContents = cleanFileContents(featureContents)
				+ FeatureFileParser.END_OF_CONTENTS_PADDING;
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
			if (CukeFileReader.isAllEventsCanceled())
				return null;
			CukeScenario scenario = parser.parseScenario(file, featureContents,
					scenarioTag, indicesOfScenarioTags, i);
			GWTStatement[] statements = scenario.getStatementsArray();
			for (int j = 0; j < statements.length; j++) {
				if (CukeFileReader.isAllEventsCanceled())
					return null;
				GWTStatement gwtStatement = statements[j];
				if (gwtStatement.getStepFile() == null) {
					CukeConsole.println(gwtStatement.slashToSlashStatement(),
							false);
					if (!CukeFileReader.isJIT()) {
						File stepFile = FeatureFileParser.findFileForStatement(
								gwtStatement, reader, parser);
						gwtStatement.setStepFile(stepFile);
					}
				} else {
					CukeConsole.println("it already has a step file ["
							+ gwtStatement.slashToSlashStatement() + "]", true);
				}
			}
			feature.addScenario(scenario);
		}

		return feature;
	}

	private String cleanFileContents(String stringContents) {
		try {
			String firstChar = stringContents.substring(0, 1);
			while (firstChar.equals("\n") || firstChar.equals(" ")) {
				if (CukeFileReader.isAllEventsCanceled())
					return CommonRips.MSG_NO_PARSABLE_CONTENT;
				stringContents = stringContents.substring(1);
				firstChar = stringContents.substring(0, 1);
			}
			return stringContents;
		} catch (Exception e) {
			CukeConsole.println(
					FeatureFileParser.CONSOLE_STR_ERROR_CLEANING_CONTENTS
							+ ": " + CommonRips.MSG_NO_PARSABLE_CONTENT, true);
			e.printStackTrace();
		}
		return CommonRips.MSG_NO_PARSABLE_CONTENT;
	}

}
