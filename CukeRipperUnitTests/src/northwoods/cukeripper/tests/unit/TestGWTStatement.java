package northwoods.cukeripper.tests.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
		gwt = new GWTStatement(StatementType.GIVEN,
				TestHelper.TEST_GIVEN_STATEMENTS[0]);
		screenMethod = new ScreenMethod("method_name", "#todo method body");
		screen = new CukeScreen("screen_1");
		screen.addMethod(screenMethod);
	}

	@Test
	public void itCreatesTheCorrectTypePrefix_Given() {
		String expected = "Given";
		String actual = gwt.getTypePrefix();

		assertThat(actual, is(expected));
	}

	@Test
	public void itCreatesTheCorrectTypePrefix_When() {
		gwt = new GWTStatement(StatementType.WHEN,
				TestHelper.TEST_GIVEN_STATEMENTS[0]);

		String expected = "When";
		String actual = gwt.getTypePrefix();

		assertThat(actual, is(expected));
	}

	@Test
	public void itCreatesTheCorrectTypePrefix_Then() {

		gwt = new GWTStatement(StatementType.THEN,
				TestHelper.TEST_GIVEN_STATEMENTS[0]);

		String expected = "Then";
		String actual = gwt.getTypePrefix();

		assertThat(actual, is(expected));
	}

	@Test
	public void itGivesAListofStepActions() {

		gwt = new GWTStatement(StatementType.THEN,
				TestHelper.TEST_GIVEN_STATEMENTS[0]);
		List<StepAction> actions = new ArrayList<StepAction>();
		actions.add(new StepAction(screen, 0));
		actions.add(new StepAction(screen, 0));
		actions.add(new StepAction(screen, 0));

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
