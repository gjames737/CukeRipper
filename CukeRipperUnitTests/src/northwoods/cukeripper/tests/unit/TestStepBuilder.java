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
import northwoods.cukeripper.utils.GWTStatement.StatementType;
import northwoods.cukeripper.utils.StepBuilder;

import org.junit.Before;
import org.junit.Test;

public class TestStepBuilder {

	private StepBuilder stepBuilder;

	@Before
	public void Setup() {
		TestHelper.initiate();
		stepBuilder = new StepBuilder();

	}

	@Test
	public void itCreatesAGivenStatementWithTheCorrectStatement() {
		createGivensByStatements();

		String expected = TEST_GIVEN_STATEMENTS[0];
		stepBuilder.createGivenStatement(TestHelper.getTestGWTStatement(
				StatementType.GIVEN,
				TestHelper.GIVEN_STATEMENT_INDEX_NO_ACTIONS));
		String actual = stepBuilder.getGivenStatement(0).getStatement();
		assertThat(actual, is(expected));
	}

	@Test
	public void itCreatesAGivenStatementRuby_NO_StepActions() {
		createGivensByStatements();

		String actionsString = BREAKLINE + TODO;
		String expected = GIVEN + " /^"
				+ TEST_GIVEN_STATEMENTS[GIVEN_STATEMENT_INDEX_NO_ACTIONS]
				+ "$/ " + DO + actionsString + BREAKLINE + END;

		String actual = stepBuilder.getGivenStatement(
				GIVEN_STATEMENT_INDEX_NO_ACTIONS).getRubyDef();
		System.out.println(expected);
		assertThat(actual, is(expected));
	}

	@Test
	public void itCreatesAGivenStatementRuby_One_StepAction() {
		createGivensByStatements();

		String actionsString = TestHelper
				.getTestScreenActionsString(GIVEN_STATEMENT_INDEX_ONE_ACTION);
		String expected = GIVEN + " /^"
				+ TEST_GIVEN_STATEMENTS[GIVEN_STATEMENT_INDEX_ONE_ACTION]
				+ "$/ " + DO + actionsString + BREAKLINE + END;
		String actual = stepBuilder.getGivenStatement(
				GIVEN_STATEMENT_INDEX_ONE_ACTION).getRubyDef();
		System.out.println(expected);
		assertThat(actual, is(expected));
	}

	@Test
	public void itCreatesAGivenStatementRuby_Multiple_StepAction() {
		createGivensByStatements();

		String actionsString = TestHelper
				.getTestScreenActionsString(GIVEN_STATEMENT_INDEX_MULTI_ACTIONS);
		String expected = GIVEN + " /^"
				+ TEST_GIVEN_STATEMENTS[GIVEN_STATEMENT_INDEX_MULTI_ACTIONS]
				+ "$/ " + DO + actionsString + BREAKLINE + END;
		String actual = stepBuilder.getGivenStatement(
				GIVEN_STATEMENT_INDEX_MULTI_ACTIONS).getRubyDef();
		System.out.println(expected);
		assertThat(actual, is(expected));
	}

	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	private void createGivensByStatements() {
		stepBuilder.createGivenStatement(TestHelper.getTestGWTStatement(
				StatementType.GIVEN,
				TestHelper.GIVEN_STATEMENT_INDEX_NO_ACTIONS));
		stepBuilder.createGivenStatement(TestHelper.getTestGWTStatement(
				StatementType.GIVEN,
				TestHelper.GIVEN_STATEMENT_INDEX_ONE_ACTION));
		stepBuilder.createGivenStatement(TestHelper.getTestGWTStatement(
				StatementType.GIVEN,
				TestHelper.GIVEN_STATEMENT_INDEX_MULTI_ACTIONS));
	}

}
