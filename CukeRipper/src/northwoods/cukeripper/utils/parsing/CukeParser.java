package northwoods.cukeripper.utils.parsing;

import static northwoods.cukeripper.utils.CommonRips.DOLLAR_SLASH;
import static northwoods.cukeripper.utils.CommonRips.GIVEN;
import static northwoods.cukeripper.utils.CommonRips.SLASH_POINT;
import static northwoods.cukeripper.utils.CommonRips.THEN;
import static northwoods.cukeripper.utils.CommonRips.WHEN;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import northwoods.cukeripper.utils.CommonRips;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.GWTStatement.StatementType;
import northwoods.cukeripper.utils.StepAction;

public class CukeParser {

	public String CONSOLE_STR_ERROR_PARSING_SCENARIO_IN_FILE;
	public static boolean THROW_ERRORS = false;

	CukeScenario parseScenario(File file, String featureContents,
			String scenarioTag, List<Integer> indicesOfScenarioTags, int index)
			throws Exception {
		try {
			int thisCharIndex = indicesOfScenarioTags.get(index);

			String scenarioName = getObjectNameFromContents(thisCharIndex,
					scenarioTag, featureContents);
			scenarioName = scenarioName.trim();
			CukeScenario scenario = new CukeScenario(scenarioName, file);
			//
			String fullScenarioString = getFullScenarioString(featureContents,
					indicesOfScenarioTags, index, thisCharIndex);

			List<Integer> indicesOfStatements = getIndicesOfAllStatementsInScenario(fullScenarioString);

			List<GWTStatement> GWTStatements = getStatementTypes(file,
					fullScenarioString, indicesOfStatements);

			for (GWTStatement gs : GWTStatements) {
				scenario.createStatement(gs);
			}
			return scenario;
		} catch (Exception e) {
			CukeConsole.println(CONSOLE_STR_ERROR_PARSING_SCENARIO_IN_FILE
					+ " " + file.getAbsolutePath(), true);
			CukeConsole.println(
					"parseScenario: on file " + file.getAbsolutePath(), true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return null;
	}

	List<Integer> indicesOfOccurances(String haystack, String subStr)
			throws Exception {
		try {
			int lastIndex = 0;
			List<Integer> indices = new ArrayList<Integer>();
			if (haystack == null) {
				System.err.println("haystack is null");
			}

			while (lastIndex != -1) {
				lastIndex = haystack.indexOf(subStr, lastIndex);
				if (lastIndex != -1) {
					indices.add(lastIndex);
					lastIndex += subStr.length();
				}
			}

			return indices;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("indicesOfOccurances", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return new ArrayList<Integer>();
	}

	String getObjectNameFromContents(int offset, String tag,
			String stringContents) throws Exception {
		try {
			int indexOfNameStart = offset + tag.length();
			int indexOfNextEndOfLineChar = indexOfNameStart
					+ stringContents.substring(indexOfNameStart).indexOf("\n");
			String featureName = stringContents.substring(indexOfNameStart,
					indexOfNextEndOfLineChar);
			featureName = featureName.trim();
			return featureName;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("getObjectNameFromContents", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return "";
	}

	List<GWTStatement> parseStatementsFromStepFile(File file,
			String fullContents) throws Exception {
		List<GWTStatement> statements = new ArrayList<GWTStatement>();
		try {
			List<Integer> indicesOfSlashPt = indicesOfOccurances(fullContents,
					SLASH_POINT);

			List<Integer> indicesOfDollarSlash = indicesOfOccurances(
					fullContents, DOLLAR_SLASH);

			extractTypeAndStatementsToList(file, fullContents, statements,
					indicesOfSlashPt, indicesOfDollarSlash);
			return statements;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println(
					"parseStatementsFromStepFile on file "
							+ file.getAbsolutePath(), true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return statements;
	}

	private List<Integer> getIndicesOfAllStatementsInScenario(
			String fullScenarioString) throws Exception {
		try {
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
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("getIndicesOfAllStatementsInScenario", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return new ArrayList<Integer>();
	}

	private List<GWTStatement> getStatementTypes(File file,
			String fullScenarioString, List<Integer> indicesOfStatements)
			throws Exception {
		try {
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

				// System.err.println(thisType.name() + "[  " + statement +
				// " ]");

				File stepFile = isFeatureFile(file) ? null : file;
				File featureFile = isFeatureFile(file) ? file : null;

				gwts.add(new GWTStatement(stepFile, featureFile, thisType,
						statement));
				lastType = thisType;
			}
			return gwts;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole
					.println(
							"getStatementTypes on file:"
									+ file.getAbsolutePath(), true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return new ArrayList<GWTStatement>();
	}

	private boolean isFeatureFile(File file) throws Exception {
		try {
			if (file == null)
				return false;
			return file.getName().toLowerCase()
					.contains("." + CommonRips.FEATURE.toLowerCase());
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println(
					"isFeatureFile on file:" + file.getAbsolutePath(), true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return false;
	}

	private String cleanStatementString(String statement) throws Exception {
		try {
			statement = statement.trim();
			statement = statement.replace("\n", "");
			return statement;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("cleanStatementString", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return CommonRips.MSG_NO_PARSABLE_CONTENT;
	}

	private String getSnippetOfStatement(String fullScenarioString,
			int statementIndex, int snippetLength) throws Exception {
		try {
			String snippetOfStatement = fullScenarioString.substring(
					statementIndex, statementIndex + snippetLength);
			return snippetOfStatement;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("getSnippetOfStatement", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return CommonRips.MSG_NO_PARSABLE_CONTENT;
	}

	private int calculateTypeLength(boolean isAnd, StatementType thisType)
			throws Exception {
		try {
			int typeLength;
			typeLength = isAnd ? "And".length() : thisType.name().length();
			return typeLength;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("calculateTypeLength", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return 0;
	}

	private StatementType parseStatementType(StatementType lastType,
			String snippetOfStatement) throws Exception {
		try {
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
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("parseStatementType", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return null;
	}

	private String parseStatementString(String fullScenarioString,
			List<Integer> indicesOfStatements, int i, int statementIndex,
			StatementType thisType, boolean isAnd) throws Exception {
		try {
			int typeLength;
			typeLength = calculateTypeLength(isAnd, thisType);

			String statement = parseStatementString(fullScenarioString,
					indicesOfStatements, i, statementIndex, typeLength);
			return statement;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("parseStatementString 1", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return CommonRips.MSG_NO_PARSABLE_CONTENT;
	}

	private String parseStatementString(String fullScenarioString,
			List<Integer> indicesOfStatements, int indexIndex,
			int statementIndex, int typeLength) throws Exception {
		try {
			int nextIndexpfStatement = indexIndex + 1 < indicesOfStatements
					.size() ? indicesOfStatements.get(indexIndex + 1)
					: fullScenarioString.length() - 1;

			int indexStartStatement = statementIndex + typeLength;
			int indexStopStatement = nextIndexpfStatement;
			String statement = fullScenarioString.substring(
					indexStartStatement, indexStopStatement);
			statement = cleanStatementString(statement);
			return statement;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("parseStatementString 2", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return CommonRips.MSG_NO_PARSABLE_CONTENT;
	}

	private String getFullScenarioString(String featureContents,
			List<Integer> indicesOfScenarioTags, int index, int thisCharIndex)
			throws Exception {
		try {
			String fullScenarioString = "";
			if (index + 1 == indicesOfScenarioTags.size()) {
				fullScenarioString = featureContents.substring(thisCharIndex);
			} else {
				int nextCharIndex = indicesOfScenarioTags.get(index + 1);
				fullScenarioString = featureContents.substring(thisCharIndex,
						nextCharIndex);
			}
			return fullScenarioString;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("getFullScenarioString", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return CommonRips.MSG_NO_PARSABLE_CONTENT;
	}

	private void extractTypeAndStatementsToList(File file, String fullContents,
			List<GWTStatement> outStatements, List<Integer> indicesOfSlashPt,
			List<Integer> indicesOfDollarSlash) throws Exception {
		try {
			int numberOfStepDefs = indicesOfSlashPt.size();
			for (int i = 0; i < numberOfStepDefs; i++) {

				String substringOfType = getSubStringForStepDefAtStatementIndex(
						fullContents, indicesOfSlashPt.get(i));

				StatementType type = determineStatementTypeFromSubstring(substringOfType);

				int lengthOfSlashPt = SLASH_POINT.length();
				int beginIndextatement = indicesOfSlashPt.get(i)
						+ lengthOfSlashPt;
				int endIndexStatement = indicesOfDollarSlash.get(i);

				String statementString = fullContents.substring(
						beginIndextatement, endIndexStatement);
				File stepFile = isFeatureFile(file) ? null : file;
				File featureFile = isFeatureFile(file) ? file : null;
				GWTStatement gwtStatement = new GWTStatement(stepFile,
						featureFile, type, statementString);

				// Add StepActios
				List<StepAction> stepActionsInStatement = parseOutStepActionsForStatement(
						fullContents, indicesOfSlashPt, i, endIndexStatement);
				gwtStatement.setStepActions(stepActionsInStatement);
				//

				outStatements.add(gwtStatement);
			}
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("extractTypeAndStatementsToList on file:"
					+ file.getAbsolutePath(), true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
	}

	private List<StepAction> parseOutStepActionsForStatement(
			String fullContents, List<Integer> indicesOfSlashPt,
			int statementIndex, int endIndexLastStatement) throws Exception {
		try {
			List<StepAction> stepActionsInStatement = new ArrayList<StepAction>();
			int startStatementSearchBound = endIndexLastStatement;
			int endStatementSearchBound = statementIndex < indicesOfSlashPt
					.size() - 1 ? indicesOfSlashPt.get(statementIndex + 1)
					: fullContents.length();

			String statementsParagraph = fullContents.substring(
					startStatementSearchBound, endStatementSearchBound);

			if (isASpanishStatementsParagraph(statementsParagraph)) {
				statementsParagraph = convertSpanishStatementsParagraphToEnglish(statementsParagraph);
			}
			stepActionsInStatement = getStatementsFromStatementsParagraph(statementsParagraph);
			return stepActionsInStatement;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("parseOutStepActionsForStatement", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return new ArrayList<StepAction>();
	}

	private String convertSpanishStatementsParagraphToEnglish(
			String statementsParagraph) throws Exception {
		try {
			List<Integer> bar_indices = indicesOfOccurances(
					statementsParagraph, "|");

			String screenSubstitute = statementsParagraph.substring(
					bar_indices.get(0) + 1, bar_indices.get(1));

			String thisStatementsScreenname = getScreenNameFromStatementText(statementsParagraph);

			String englishParagraph = "";
			String removal = CommonRips.DO + CommonRips.ON + "("
					+ thisStatementsScreenname + ")" + CommonRips.DO + "|"
					+ screenSubstitute + "|";
			englishParagraph = statementsParagraph.replaceAll("\\s+", "");
			englishParagraph = englishParagraph.replace("\n", "");
			englishParagraph = englishParagraph.replace(removal, "");

			englishParagraph = englishParagraph.replace(screenSubstitute,
					CommonRips.ON + "(" + thisStatementsScreenname + ")");
			return englishParagraph;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("convertSpanishStatementsParagraphToEnglish",
					true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return CommonRips.MSG_NO_PARSABLE_CONTENT;
	}

	private String getScreenNameFromStatementText(String thisStatement)
			throws Exception {
		try {
			List<Integer> indicesOfParamsLeft = indicesOfOccurances(
					thisStatement, "(");
			List<Integer> indicesOfParamsRight = indicesOfOccurances(
					thisStatement, ")");

			int beginIndex = indicesOfParamsLeft.get(0) + 1;
			int endIndex = indicesOfParamsRight.get(0);
			String screenName = thisStatement.substring(beginIndex, endIndex);
			return screenName;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("getScreenNameFromStatementText", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return CommonRips.MSG_NO_PARSABLE_CONTENT;
	}

	private boolean isASpanishStatementsParagraph(String statementsParagraph)
			throws Exception {
		try {
			boolean isSpanish = numberOfOccurences(statementsParagraph, "|") == 2;
			return isSpanish;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("isASpanishStatementsParagraph", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return false;
	}

	private List<StepAction> getStatementsFromStatementsParagraph(
			String statementsParagraph) throws Exception {
		try {
			List<StepAction> allActionsForStep = new ArrayList<StepAction>();

			String on_subStr = CommonRips.ON + "(";
			if (statementsParagraph == null) {
				System.err.println("statementsParagraph is null");
			}

			List<Integer> indicesOfOn = indicesOfOccurances(
					statementsParagraph, on_subStr);
			for (int index : indicesOfOn) {
				int startScreenNameIndex = on_subStr.length() + index;
				String screenNameIsInThisString = statementsParagraph
						.substring(startScreenNameIndex);

				int endScreenNameIndex = startScreenNameIndex
						+ screenNameIsInThisString.indexOf(")");

				String screenName = statementsParagraph.substring(
						startScreenNameIndex, endScreenNameIndex);

				StepAction thisAction = new StepAction(screenName, -1);

				allActionsForStep.add(thisAction);

			}

			return allActionsForStep;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("getStatementsFromStatementsParagraph", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return new ArrayList<StepAction>();
	}

	private StatementType determineStatementTypeFromSubstring(
			String substringOfType) throws Exception {
		try {
			StatementType type = null;
			if (substringOfType.contains(GIVEN)) {
				type = StatementType.GIVEN;
			} else if (substringOfType.contains(WHEN)) {
				type = StatementType.WHEN;
			} else if (substringOfType.contains(THEN)) {
				type = StatementType.THEN;
			}
			return type;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("determineStatementTypeFromSubstring", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return null;
	}

	private String getSubStringForStepDefAtStatementIndex(String fullContents,
			int indexEnd) throws Exception {
		try {
			int maxBackTrack = 20;
			int backTrackLength = indexEnd < maxBackTrack ? indexEnd
					: maxBackTrack;
			int indexStart = indexEnd - backTrackLength;
			String substringOfType = fullContents.substring(indexStart,
					indexEnd);
			return substringOfType;
		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("getSubStringForStepDefAtStatementIndex", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return CommonRips.MSG_NO_PARSABLE_CONTENT;
	}

	private static int numberOfOccurences(String str, String findStr)
			throws Exception {
		try {
			int lastIndex = 0;
			int count = 0;

			while (lastIndex != -1) {

				lastIndex = str.indexOf(findStr, lastIndex);

				if (lastIndex != -1) {
					count++;
					lastIndex += findStr.length();
				}
			}
			return count;
		} catch (Exception e) {

			CukeConsole.println(CukeParser.class.getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("numberOfOccurences", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return 0;
	}

	public File findStepFileForStatement(CukeFileReader reader,
			GWTStatement gwtStatement) throws Exception {
		try {
			File[] stepFiles = reader.getAllStepDefinitionFiles();
			String theStepString = gwtStatement.slashToSlashStatement();
			for (int i = 0; i < stepFiles.length; i++) {
				String contents = reader.readFullFileContents(stepFiles[i]);

				if (contents.contains(theStepString)) {
					System.out.println("Found step file for: "
							+ gwtStatement.slashToSlashStatement());
					return stepFiles[i];
				}
			}
			CukeConsole.println(this.getClass().getCanonicalName(), false);
			CukeConsole.println("     "
					+ CommonRips.MSG_NO_STEP_FILE_FOUND_FOR_STATEMENT + ": ",
					false);
			CukeConsole.println("     " + theStepString, false);
			return null;

		} catch (Exception e) {

			CukeConsole.println(this.getClass().getCanonicalName() + " "
					+ CommonRips.CONSOLE_STR_ERROR_GENERAL_STRING, true);
			CukeConsole.println("findStepFileForStatement", true);
			e.printStackTrace();
			if (THROW_ERRORS)
				throw e;
		}
		return null;
	}
}
