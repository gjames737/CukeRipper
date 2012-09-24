package northwoods.cukeripper.tests.unit.helpers;

import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement.StatementType;

public class TestScenarios {

	public static final CukeScenario[] SCENARIOS = new CukeScenario[] {
			new CukeScenario("s0", null), new CukeScenario("s1", null),
			new CukeScenario("s2", null) };

	public static void initiate() {
		TestHelper.initiate();
	}

	public static void createSingularStatements() {
		int scenarioIndex = 0;

		// 0
		scenarioIndex = 0;
		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.GIVEN,
						TestHelper.GIVEN_STATEMENT_INDEX_NO_ACTIONS));

		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.WHEN, 0));

		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.THEN, 0));

		// 1
		scenarioIndex = 1;
		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.GIVEN,
						TestHelper.GIVEN_STATEMENT_INDEX_ONE_ACTION));

		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.WHEN, 0));

		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.THEN, 0));

		// 2
		scenarioIndex = 2;
		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.GIVEN,
						TestHelper.GIVEN_STATEMENT_INDEX_MULTI_ACTIONS));

		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.WHEN, 0));

		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.THEN, 0));

	}

	public static void createDoubleGWTStatements() {
		int scenarioIndex = 0;
		// 0
		scenarioIndex = 0;
		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.GIVEN,
						TestHelper.GIVEN_STATEMENT_INDEX_NO_ACTIONS));
		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.GIVEN,
						TestHelper.GIVEN_STATEMENT_INDEX_NO_ACTIONS));

		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.WHEN, 0));

		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.THEN, 0));

		// 1
		scenarioIndex = 1;
		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.GIVEN,
						TestHelper.GIVEN_STATEMENT_INDEX_ONE_ACTION));

		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.WHEN, 0));

		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.WHEN, 0));

		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.THEN, 0));

		// 2
		scenarioIndex = 2;
		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.GIVEN,
						TestHelper.GIVEN_STATEMENT_INDEX_MULTI_ACTIONS));

		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.WHEN, 0));

		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.THEN, 0));

		SCENARIOS[scenarioIndex].createStatement(TestHelper
				.getTestGWTStatement(StatementType.THEN, 0));
	}
}
