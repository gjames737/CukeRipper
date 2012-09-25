package northwoods.cukeripper.tests.unit.helpers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import northwoods.cukeripper.utils.CommonRips;
import northwoods.cukeripper.utils.CukeScreen;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.GWTStatement.StatementType;
import northwoods.cukeripper.utils.ScreenMethod;
import northwoods.cukeripper.utils.StepAction;

public class TestHelper {
	private static File featureFile;
	private static File stepFile;

	public static final String[] TEST_GIVEN_STATEMENTS = new String[] {
			"It is something 0", "It is something 1", "It is something 2" };
	public static final String[] TEST_WHEN_STATEMENTS = new String[] {
			"I did something 0", "I did something 1", "I did something 2" };
	public static final String[] TEST_THEN_STATEMENTS = new String[] {
			"It should be 0", "It should be 1", "It should be 2" };

	public static final String TEST_SCREENNAME = "screenname";

	public static final int NUMBER_OF_METHODS = 10;

	public static final int GIVEN_STATEMENT_INDEX_NO_ACTIONS = 0;

	public static final int GIVEN_STATEMENT_INDEX_ONE_ACTION = 1;

	public static final int GIVEN_STATEMENT_INDEX_MULTI_ACTIONS = 2;

	private static List<GWTStatement> givenStatements;
	private static List<GWTStatement> whenStatements;
	private static List<GWTStatement> thenStatements;

	private static boolean isInitiated = false;

	public static void initiate() {
		if (!isInitiated) {
			TestHelper.createGWTStatements();
			isInitiated = true;
		}
		featureFile = mock(File.class);
		when(featureFile.getName()).thenReturn("asds.feature");
		stepFile = mock(File.class);
		when(stepFile.getName()).thenReturn("adsdsa.rb");
	}

	public static CukeScreen getTestScreen() {
		CukeScreen cukeScreen = new CukeScreen(TEST_SCREENNAME);
		for (int i = 0; i < NUMBER_OF_METHODS; i++) {
			cukeScreen.addMethod(new ScreenMethod(getTestScreenMethodNames()
					.get(i), "body"));
		}
		return cukeScreen;
	}

	public static String getTestScreenActionsString(int index) {
		List<StepAction> actionsList = getTestActionsList(index);
		String actionStr = "";
		for (StepAction stepAction : actionsList) {
			actionStr += CommonRips.BREAKLINE + stepAction.toRuby();
		}

		return actionStr;
	}

	public static List<StepAction> getTestActionsList(int index) {
		List<StepAction> actionsList = new ArrayList<StepAction>();
		switch (index) {
		case GIVEN_STATEMENT_INDEX_NO_ACTIONS:
			break;
		case GIVEN_STATEMENT_INDEX_ONE_ACTION:
			actionsList.add(new StepAction(getTestScreen().getName(), 0));
			break;
		case GIVEN_STATEMENT_INDEX_MULTI_ACTIONS:
		default:
			actionsList.add(new StepAction(getTestScreen().getName(), 0));
			actionsList.add(new StepAction(getTestScreen().getName(), 1));
			actionsList.add(new StepAction(getTestScreen().getName(), 2));
			break;
		}

		return actionsList;
	}

	public static List<String> getTestScreenMethodNames() {
		List<String> names = new ArrayList<String>();
		for (int i = 0; i < NUMBER_OF_METHODS; i++) {
			names.add("method_" + i);
		}
		return names;
	}

	public static GWTStatement getTestGWTStatement(StatementType type, int index) {
		switch (type) {
		case WHEN:
			return whenStatements.get(index);
		case THEN:
			return thenStatements.get(index);
		default:
		case GIVEN:
			return givenStatements.get(index);
		}
	}

	private static List<GWTStatement> createGWTStatements() {
		givenStatements = new ArrayList<GWTStatement>();
		whenStatements = new ArrayList<GWTStatement>();
		thenStatements = new ArrayList<GWTStatement>();
		List<StepAction> actionsList;
		for (int i = 0; i < TEST_GIVEN_STATEMENTS.length; i++) {
			actionsList = getTestActionsList(i);
			GWTStatement statement = new GWTStatement(stepFile, featureFile,
					StatementType.GIVEN, TEST_GIVEN_STATEMENTS[i]);
			statement.setStepActions(actionsList);
			givenStatements.add(statement);
		}
		for (int i = 0; i < TEST_WHEN_STATEMENTS.length; i++) {
			actionsList = getTestActionsList(i);
			GWTStatement statement = new GWTStatement(stepFile, featureFile,
					StatementType.WHEN, TEST_WHEN_STATEMENTS[i]);
			statement.setStepActions(actionsList);
			whenStatements.add(statement);
		}
		for (int i = 0; i < TEST_WHEN_STATEMENTS.length; i++) {
			actionsList = getTestActionsList(i);
			GWTStatement statement = new GWTStatement(stepFile, featureFile,
					StatementType.THEN, TEST_WHEN_STATEMENTS[i]);
			statement.setStepActions(actionsList);
			thenStatements.add(statement);
		}
		return givenStatements;
	}

	public static File featureFile() {
		return featureFile;
	}

	public static File stepFile() {
		return stepFile;
	}
}
