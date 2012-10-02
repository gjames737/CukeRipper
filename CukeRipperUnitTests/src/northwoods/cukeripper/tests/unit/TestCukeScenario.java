package northwoods.cukeripper.tests.unit;

import static northwoods.cukeripper.tests.unit.helpers.TestHelper.GIVEN_STATEMENT_INDEX_MULTI_ACTIONS;
import static northwoods.cukeripper.tests.unit.helpers.TestHelper.GIVEN_STATEMENT_INDEX_NO_ACTIONS;
import static northwoods.cukeripper.tests.unit.helpers.TestHelper.GIVEN_STATEMENT_INDEX_ONE_ACTION;
import static northwoods.cukeripper.tests.unit.helpers.TestHelper.TEST_GIVEN_STATEMENTS;
import static northwoods.cukeripper.utils.CommonRips.BREAKLINE;
import static northwoods.cukeripper.utils.CommonRips.DO;
import static northwoods.cukeripper.utils.CommonRips.END;
import static northwoods.cukeripper.utils.CommonRips.GIVEN;
import static northwoods.cukeripper.utils.CommonRips.TODO;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import northwoods.cukeripper.tests.unit.helpers.TestHelper;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement.StatementType;

import org.junit.Before;
import org.junit.Test;

public class TestCukeScenario {

	private CukeScenario scenario;

	@Before
	public void Setup() {
		TestHelper.initiate();
		scenario = new CukeScenario("Scenario Foo", null);
	}

	@Test
	public void itHasTheRightName() {
		assertThat(scenario.getName(), is("Scenario Foo"));
	}

	@Test
	public void itCreatesAGivenStatementWithTheCorrectStatement() {
		createGivensByStatements();

		String expected = TEST_GIVEN_STATEMENTS[0];
		scenario.createStatement(TestHelper.getTestGWTStatement(
				StatementType.GIVEN,
				TestHelper.GIVEN_STATEMENT_INDEX_NO_ACTIONS));
		String actual = scenario.getStatement(0).getStatement();
		assertThat(actual, is(expected));
	}

	@Test
	public void itCreatesAGivenStatementRubyWith_NO_StepActions() {
		createGivensByStatements();

		String actionsString = BREAKLINE + TODO;
		String expected = GIVEN + " /^"
				+ TEST_GIVEN_STATEMENTS[GIVEN_STATEMENT_INDEX_NO_ACTIONS]
				+ "$/ " + DO + actionsString + BREAKLINE + END;

		String actual = scenario.getStatement(GIVEN_STATEMENT_INDEX_NO_ACTIONS)
				.toRuby();
		System.out.println(expected);
		assertThat(actual, is(expected));
	}

	@Test
	public void itCreatesAGivenStatementRubyWith_One_StepAction() {
		createGivensByStatements();

		String actionsString = TestHelper
				.getTestScreenActionsString(GIVEN_STATEMENT_INDEX_ONE_ACTION);
		String expected = GIVEN + " /^"
				+ TEST_GIVEN_STATEMENTS[GIVEN_STATEMENT_INDEX_ONE_ACTION]
				+ "$/ " + DO + actionsString + BREAKLINE + END;
		String actual = scenario.getStatement(GIVEN_STATEMENT_INDEX_ONE_ACTION)
				.toRuby();
		System.out.println(expected);
		assertThat(actual, is(expected));
	}

	@Test
	public void itCreatesAGivenStatementRubyWith_Multiple_StepAction() {
		createGivensByStatements();

		String actionsString = TestHelper
				.getTestScreenActionsString(GIVEN_STATEMENT_INDEX_MULTI_ACTIONS);
		String expected = GIVEN + " /^"
				+ TEST_GIVEN_STATEMENTS[GIVEN_STATEMENT_INDEX_MULTI_ACTIONS]
				+ "$/ " + DO + actionsString + BREAKLINE + END;
		String actual = scenario.getStatement(
				GIVEN_STATEMENT_INDEX_MULTI_ACTIONS).toRuby();
		System.out.println(expected);
		assertThat(actual, is(expected));
	}

	@Test
	public void itToStringsCorrectly() {
		String lineNumberString = scenario.getLineNumber() + ": ";
		String expected = lineNumberString + scenario.getName();
		assertThat(scenario.toString(), is(expected));
	}

	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	private void createGivensByStatements() {
		scenario.createStatement(TestHelper.getTestGWTStatement(
				StatementType.GIVEN,
				TestHelper.GIVEN_STATEMENT_INDEX_NO_ACTIONS));
		scenario.createStatement(TestHelper.getTestGWTStatement(
				StatementType.GIVEN,
				TestHelper.GIVEN_STATEMENT_INDEX_ONE_ACTION));
		scenario.createStatement(TestHelper.getTestGWTStatement(
				StatementType.GIVEN,
				TestHelper.GIVEN_STATEMENT_INDEX_MULTI_ACTIONS));
	}

}
