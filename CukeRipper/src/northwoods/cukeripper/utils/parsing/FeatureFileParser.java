package northwoods.cukeripper.utils.parsing;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import northwoods.cukeripper.utils.CommonRips;
import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.GWTStatement.StatementType;

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
			String scenarioTag, List<Integer> indicesOfScenarioTags, int index) {

		int thisCharIndex = indicesOfScenarioTags.get(index);

		String scenarioName = getObjectNameFromContents(thisCharIndex,
				scenarioTag, featureContents);
		scenarioName = scenarioName.trim();
		CukeScenario scenario = new CukeScenario(scenarioName);
		//
		String fullScenarioString = getFullScenarioString(featureContents,
				indicesOfScenarioTags, index, thisCharIndex);

		List<Integer> indicesOfStatements = getIndicesOfAllStatementsInScenario(fullScenarioString);

		List<GWTStatement> GWTStatements = getStatementTypes(
				fullScenarioString, indicesOfStatements);

		for (GWTStatement gs : GWTStatements) {
			scenario.createStatement(gs);
		}
		//
		//
		return scenario;
	}

	private List<Integer> getIndicesOfAllStatementsInScenario(
			String fullScenarioString) {
		List<Integer> indicesOfStatements = indicesOfOccurances(
				fullScenarioString, "Given");
		indicesOfStatements.addAll(indicesOfOccurances(fullScenarioString,
				"When"));
		indicesOfStatements.addAll(indicesOfOccurances(fullScenarioString,
				"Then"));
		indicesOfStatements.addAll(indicesOfOccurances(fullScenarioString,
				"And"));
		Collections.sort(indicesOfStatements);
		return indicesOfStatements;
	}

	private List<GWTStatement> getStatementTypes(String fullScenarioString,
			List<Integer> indicesOfStatements) {
		StatementType lastType = StatementType.GIVEN;
		List<GWTStatement> gwts = new ArrayList<GWTStatement>();
		for (int i = 0; i < indicesOfStatements.size(); i++) {

			int statementIndex = indicesOfStatements.get(i);
			String snippetOfStatement = getSnippetOfStatement(
					fullScenarioString, statementIndex, "And".length());

			StatementType thisType = parseStatementType(lastType,
					snippetOfStatement);

			boolean isAnd = snippetOfStatement.equals("And");
			String statement = parseStatementString(fullScenarioString,
					indicesOfStatements, i, statementIndex, thisType, isAnd);

			// System.err.println(thisType.name() + "[  " + statement + " ]");
			gwts.add(new GWTStatement(thisType, statement));
			lastType = thisType;
		}
		return gwts;
	}

	private String cleanStatementString(String statement) {
		statement = statement.trim();
		statement = statement.replace("\n", "");
		return statement;
	}

	private String getSnippetOfStatement(String fullScenarioString,
			int statementIndex, int snippetLength) {
		String snippetOfStatement = fullScenarioString.substring(
				statementIndex, statementIndex + snippetLength);
		return snippetOfStatement;
	}

	private int calculateTypeLength(boolean isAnd, StatementType thisType) {
		int typeLength;
		typeLength = isAnd ? "And".length() : thisType.name().length();
		return typeLength;
	}

	private StatementType parseStatementType(StatementType lastType,
			String snippetOfStatement) {
		StatementType thisType = null;

		if (snippetOfStatement.equals("Giv")) {
			thisType = StatementType.GIVEN;
		} else if (snippetOfStatement.equals("Whe")) {
			thisType = StatementType.WHEN;
		} else if (snippetOfStatement.equals("The")) {
			thisType = StatementType.THEN;
		} else if (snippetOfStatement.equals("And")) {
			thisType = lastType;
		}
		return thisType;
	}

	private String parseStatementString(String fullScenarioString,
			List<Integer> indicesOfStatements, int i, int statementIndex,
			StatementType thisType, boolean isAnd) {
		int typeLength;
		typeLength = calculateTypeLength(isAnd, thisType);

		String statement = parseStatementString(fullScenarioString,
				indicesOfStatements, i, statementIndex, typeLength);
		return statement;
	}

	private String parseStatementString(String fullScenarioString,
			List<Integer> indicesOfStatements, int indexIndex,
			int statementIndex, int typeLength) {
		int nextIndexpfStatement = indexIndex + 1 < indicesOfStatements.size() ? indicesOfStatements
				.get(indexIndex + 1) : fullScenarioString.length() - 1;

		int indexStartStatement = statementIndex + typeLength;
		int indexStopStatement = nextIndexpfStatement;
		String statement = fullScenarioString.substring(indexStartStatement,
				indexStopStatement);
		statement = cleanStatementString(statement);
		return statement;
	}

	private String getFullScenarioString(String featureContents,
			List<Integer> indicesOfScenarioTags, int index, int thisCharIndex) {
		String fullScenarioString = "";
		if (index + 1 == indicesOfScenarioTags.size()) {
			fullScenarioString = featureContents.substring(thisCharIndex);
		} else {
			int nextCharIndex = indicesOfScenarioTags.get(index + 1);
			fullScenarioString = featureContents.substring(thisCharIndex,
					nextCharIndex);
		}
		return fullScenarioString;
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
