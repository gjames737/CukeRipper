package northwoods.cukeripper.utils.parsing;

import static northwoods.cukeripper.utils.CommonRips.DOLLAR_SLASH;
import static northwoods.cukeripper.utils.CommonRips.GIVEN;
import static northwoods.cukeripper.utils.CommonRips.SLASH_POINT;
import static northwoods.cukeripper.utils.CommonRips.THEN;
import static northwoods.cukeripper.utils.CommonRips.WHEN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.GWTStatement.StatementType;

public class CukeParser {

	CukeScenario parseScenario(String featureContents, String scenarioTag,
			List<Integer> indicesOfScenarioTags, int index) {

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

	List<Integer> indicesOfOccurances(String str, String subStr) {
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

	String getObjectNameFromContents(int offset, String tag,
			String stringContents) {

		int indexOfNameStart = offset + tag.length();
		int indexOfNextEndOfLineChar = indexOfNameStart
				+ stringContents.substring(indexOfNameStart).indexOf("\n");
		String featureName = stringContents.substring(indexOfNameStart,
				indexOfNextEndOfLineChar);
		featureName = featureName.trim();
		return featureName;
	}

	List<GWTStatement> parseStatementsFromStepFile(String fullContents) {
		List<GWTStatement> statements = new ArrayList<GWTStatement>();

		List<Integer> indicesOfSlashPt = indicesOfOccurances(fullContents,
				SLASH_POINT);

		List<Integer> indicesOfDollarSlash = indicesOfOccurances(fullContents,
				DOLLAR_SLASH);

		int numberOfStepDefs = indicesOfSlashPt.size();
		for (int i = 0; i < numberOfStepDefs; i++) {

			String substringOfType = getSubStringForStepDefAtStatementIndex(
					fullContents, indicesOfSlashPt.get(i));

			StatementType type = determineStatementTypeFromSubstring(substringOfType);

			int lengthOfSlashPt = SLASH_POINT.length();
			int beginIndextatement = indicesOfSlashPt.get(i) + lengthOfSlashPt;
			int endIndexStatement = indicesOfDollarSlash.get(i);

			String statementString = fullContents.substring(beginIndextatement,
					endIndexStatement);

			statements.add(new GWTStatement(type, statementString));
		}
		return statements;
	}

	private StatementType determineStatementTypeFromSubstring(
			String substringOfType) {
		StatementType type = null;
		if (substringOfType.contains(GIVEN)) {
			type = StatementType.GIVEN;
		} else if (substringOfType.contains(WHEN)) {
			type = StatementType.WHEN;
		} else if (substringOfType.contains(THEN)) {
			type = StatementType.THEN;
		}
		return type;
	}

	private String getSubStringForStepDefAtStatementIndex(String fullContents,
			int indexEnd) {
		int maxBackTrack = 20;
		int backTrackLength = indexEnd < maxBackTrack ? indexEnd : maxBackTrack;
		int indexStart = indexEnd - backTrackLength;
		String substringOfType = fullContents.substring(indexStart, indexEnd);
		return substringOfType;
	}

}
