package northwoods.cukeripper.tests.unit;

import static northwoods.cukeripper.tests.unit.helpers.TestHelper.featureFile;
import static northwoods.cukeripper.tests.unit.helpers.TestHelper.stepFile;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import northwoods.cukeripper.tests.unit.helpers.TestHelper;
import northwoods.cukeripper.utils.CukeScreen;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.GWTStatement.StatementType;
import northwoods.cukeripper.utils.ScreenMethod;
import northwoods.cukeripper.utils.StepAction;

import org.junit.Before;
import org.junit.Test;

public class TestGWTStatement {
	private GWTStatement gwt;
	private CukeScreen screen;
	private ScreenMethod screenMethod;

	@Before
	public void Setup() {
		TestHelper.initiate();

		gwt = new GWTStatement(stepFile(), featureFile(), StatementType.GIVEN,
				TestHelper.TEST_GIVEN_STATEMENTS[0]);
		screenMethod = new ScreenMethod("method_name", "#todo method body");
		screen = new CukeScreen("screen_1");
		screen.addMethod(screenMethod);
	}

	@Test
	public void itDoesntHaveAFilesWhenGivenNone() {
		GWTStatement gwt_statement = new GWTStatement(null, null,
				StatementType.GIVEN, "the statement string");
		assertThat(gwt_statement.getStepFile(), is(nullValue()));
		assertThat(gwt_statement.getFeatureFile(), is(nullValue()));
	}

	@Test
	public void itHasAStepFileWhenGivenButNotAFeatureFile() {
		File step_file = new File("step_file.rb");
		GWTStatement gwt_statement = new GWTStatement(step_file, null,
				StatementType.GIVEN, "the statement string");
		assertThat(gwt_statement.getStepFile(), is(step_file));
		assertThat(gwt_statement.getFeatureFile(), is(nullValue()));
	}

	@Test
	public void itHasAFeatureFileWhenGivenButNotAstepFile() {
		File feature_file = new File("foo.feature");
		GWTStatement gwt_statement = new GWTStatement(null, feature_file,
				StatementType.GIVEN, "the statement string");
		assertThat(gwt_statement.getStepFile(), is(nullValue()));
		assertThat(gwt_statement.getFeatureFile(), is(feature_file));
	}

	@Test
	public void itHasABothFilesWhenGiven() {
		File step_file = new File("step_file.rb");
		File feature_file = new File("foo.feature");
		GWTStatement gwt_statement = new GWTStatement(step_file, feature_file,
				StatementType.GIVEN, "the statement string");
		assertThat(gwt_statement.getStepFile(), is(notNullValue()));
		assertThat(gwt_statement.getFeatureFile(), is(notNullValue()));
		assertThat(gwt_statement.getStepFile(), is(step_file));
		assertThat(gwt_statement.getFeatureFile(), is(feature_file));
	}

	@Test
	public void itCreatesTheCorrectTypePrefix_Given() {
		String expected = "Given";
		String actual = gwt.getTypePrefix();

		assertThat(actual, is(expected));
	}

	@Test
	public void itCreatesTheCorrectTypePrefix_When() {
		gwt = new GWTStatement(stepFile(), featureFile(), StatementType.WHEN,
				TestHelper.TEST_GIVEN_STATEMENTS[0]);

		String expected = "When";
		String actual = gwt.getTypePrefix();

		assertThat(actual, is(expected));
	}

	@Test
	public void itCreatesTheCorrectTypePrefix_Then() {

		gwt = new GWTStatement(stepFile(), featureFile(), StatementType.THEN,
				TestHelper.TEST_GIVEN_STATEMENTS[0]);

		String expected = "Then";
		String actual = gwt.getTypePrefix();

		assertThat(actual, is(expected));
	}

	@Test
	public void itToStrings() {

		gwt = new GWTStatement(stepFile(), featureFile(), StatementType.THEN,
				TestHelper.TEST_GIVEN_STATEMENTS[0]);

		String expected = gwt.getStatement();
		String actual = gwt.toString();

		assertThat(actual, is(expected));
	}

	@Test
	public void itGivesAListofStepActions() {

		gwt = new GWTStatement(stepFile(), featureFile(), StatementType.THEN,
				TestHelper.TEST_GIVEN_STATEMENTS[0]);
		List<StepAction> actions = new ArrayList<StepAction>();
		actions.add(new StepAction(screen.getName(), 0));
		actions.add(new StepAction(screen.getName(), 0));
		actions.add(new StepAction(screen.getName(), 0));

		theStepActionsAre(actions.get(0), actions.get(1), actions.get(2));

		List<StepAction> actualActions = gwt.getAllActions();

		assertThat(actualActions.size(), is(actions.size()));
		assertThat(actualActions, is(actions));
	}

	private void theStepActionsAre(StepAction... stepActions) {
		for (StepAction stepAction : stepActions) {
			gwt.addStepAction(stepAction);
		}
	}

}
