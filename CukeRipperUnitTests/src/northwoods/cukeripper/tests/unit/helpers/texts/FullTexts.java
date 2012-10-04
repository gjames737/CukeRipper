package northwoods.cukeripper.tests.unit.helpers.texts;

import static northwoods.cukeripper.tests.unit.helpers.TestHelper.featureFile;
import static northwoods.cukeripper.tests.unit.helpers.TestHelper.stepFile;
import northwoods.cukeripper.tests.unit.helpers.TestHelper;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.CukeScreen;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.GWTStatement.StatementType;
import northwoods.cukeripper.utils.ScreenMethod;
import northwoods.cukeripper.utils.StepAction;

public class FullTexts {
	public static GWTStatement[] STEP_DEF_0_STATEMENTS;

	public static CukeScenario[] FEATURE_0_SCENARIOS;
	public static CukeScenario[] FEATURE_1_SCENARIOS;

	public static void initFeatureScenarios() {
		TestHelper.initiate();
		FEATURE_0_SCENARIOS = new CukeScenario[] {
				new CukeScenario(
						"Verifying the default add case screen appearance",
						null), new CukeScenario("Saving a case", null) };
		FEATURE_0_SCENARIOS[0].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.GIVEN,
				"I am looking at the cases for an event"));
		FEATURE_0_SCENARIOS[0]
				.createStatement(new GWTStatement(stepFile(), featureFile(),
						StatementType.WHEN, "I choose to add a new case"));
		FEATURE_0_SCENARIOS[0].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.THEN,
				"I am presented with the default add new case screen"));
		//
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.GIVEN,
				"I am looking at the cases for an event"));
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.GIVEN,
				"I choose to add a new case"));
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.GIVEN,
				"I have filled in required case details"));
		FEATURE_0_SCENARIOS[1]
				.createStatement(new GWTStatement(stepFile(), featureFile(),
						StatementType.WHEN, "I choose to save the case"));
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.THEN,
				"I am not able to save the case"));
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.THEN, "the tabs are populated"));
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.THEN,
				"the case number is displayed in the breadcrumb"));
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.THEN,
				"the message 'case saved' is displayed"));

		//
		FEATURE_1_SCENARIOS = new CukeScenario[] { new CukeScenario(
				"blah blah", null) };
		FEATURE_1_SCENARIOS[0].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.GIVEN, "The given statement"));
		FEATURE_1_SCENARIOS[0].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.WHEN, "The when statement"));
		FEATURE_1_SCENARIOS[0].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.THEN, "The then statement"));
	}

	public static final int FEATURE_0_NUMBER_OF_SCENARIOS = 2;
	public static final String FEATURE_0_NAME = "Adding new case items";
	public static final String FEATURE_0 = "@wip   \n\n\n Feature:     Adding new case items"
			+ "\n\nScenario: Verifying the default add case screen appearance"
			+ "\nGiven I am looking at the cases for an event"
			+ "\n When I choose to add a new case"
			+ "\n Then I am presented with the default add new case screen"

			+ "\n\n Scenario: Saving a case"
			+ "\n	Given I am looking at the cases for an event"
			+ "\n	And I choose to add a new case"
			+ "\n	And I have filled in required case details"
			+ "\n	When I choose to save the case"
			+ "\n	Then I am not able to save the case"
			+ "\n	And the tabs are populated"
			+ "\n	And the case number is displayed in the breadcrumb"
			+ "\n	And the message 'case saved' is displayed ";
	public static final String SCREEN_0 = ""
			+ "class screen1"
			+ "\ninclude Gametel"

			+ "\n\nbutton(:delete, :id => 'delete_case_id')"
			+ "\n spinner(:case_worker, :id => 'case_case_worker_spinner')"
			+ "\n spinner(:program_type, :id => 'case_program_type_spinner')"
			+ "\n  spinner(:case_head, :id => 'case_case_head_spin')"
			+ "\n  text(:state_case_number, :id => 'case_state_case_number_edit_text')"
			+ "\n  text(:local_case_number, :id => 'case_local_case_number_edit_text')"
			+ "\n  button(:save, :id => 'save_case_id')"

			+ "\n\n  def active?" + "\n    has_text? 'Case Details'"
			+ "\n  end"

			+ "\n\n  def able_to_delete?" + "\n    enabled? 'delete_case_id'"
			+ "\n  end"

			+ "\n\n  def able_to_save?" + "\n    enabled? 'save_case_id'"
			+ "\n  end"

			+ "\n\n  def able_to_select_a_case_worker?"
			+ "\n    enabled? 'case_case_worker_spinner'" + "\n  end"

			+ "\n\n  def able_to_select_a_program_type?"
			+ "\n    enabled? 'case_program_type_spinner'" + "\n  end"

			+ "\n\n  def able_to_select_a_case_head?"
			+ "\n    enabled? 'case_case_head_spin'" + "\n  end" + "\n	end";

	public static void initStepDefs() {
		TestHelper.initiate();
		GWTStatement g_statement = new GWTStatement(stepFile(), featureFile(),
				StatementType.GIVEN, "The given statement");
		GWTStatement w_statement = new GWTStatement(stepFile(), featureFile(),
				StatementType.WHEN, "The when statement");
		GWTStatement t_statement = new GWTStatement(stepFile(), featureFile(),
				StatementType.THEN, "The then statement");
		GWTStatement g_statement_spanish = new GWTStatement(stepFile(),
				featureFile(), StatementType.GIVEN,
				"The given statement spanish");

		CukeScreen screen1 = new CukeScreen("screen1");
		screen1.addMethod(new ScreenMethod("method0", ""));
		CukeScreen screen2 = new CukeScreen("screen2");
		screen1.addMethod(new ScreenMethod("method0", ""));
		screen1.addMethod(new ScreenMethod("method1", ""));

		g_statement.addStepAction(new StepAction(screen1.getName(), 0));
		g_statement.addStepAction(new StepAction(screen2.getName(), 1));

		w_statement.addStepAction(new StepAction(screen1.getName(), 0));
		w_statement.addStepAction(new StepAction(screen1.getName(), 0));

		t_statement.addStepAction(new StepAction(screen1.getName(), 0));
		t_statement.addStepAction(new StepAction(screen2.getName(), 0));

		g_statement_spanish.addStepAction(new StepAction(screen1.getName(), 0));
		g_statement_spanish.addStepAction(new StepAction(screen1.getName(), 0));

		STEP_DEF_0_STATEMENTS = new GWTStatement[] { g_statement, w_statement,
				t_statement, g_statement_spanish };

	}

	public static final String STEP_DEF_0 = ""
			+ "Given /^The given statement$/ do"
			+ "\n  on(screen1).method0"
			+ "\n on(screen2).method0"
			+ "\n end"
			+ "\nWhen /^The when statement$/ do"
			+ "\n  on(screen1).method0"
			+ "\n on(screen1).method0"
			+ "\n end"
			+ "\nThen /^The then statement$/ do"
			+ "\n  on(screen1).method0"
			+ "\n on(screen2).method0"
			+ "\n end"
			+ "Given /^The given statement spanish$/ do\n on(screen1) do |scrn|"
			+ "  scrn.method0" + "  \n scrn.method0" + "       \nend     \nend";

	public static final String FEATURE_1 = "@wip   \n\n\n Feature:     foo zi bar"
			+ "\n\nScenario: blah blah"
			+ "\nGiven The given statement"
			+ "\n When The when statement" + "\n Then The then statement";

}
