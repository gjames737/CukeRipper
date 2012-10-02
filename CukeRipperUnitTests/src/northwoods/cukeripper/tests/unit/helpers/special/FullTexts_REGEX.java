package northwoods.cukeripper.tests.unit.helpers.special;

import static northwoods.cukeripper.tests.unit.helpers.TestHelper.featureFile;
import static northwoods.cukeripper.tests.unit.helpers.TestHelper.stepFile;
import northwoods.cukeripper.tests.unit.helpers.TestHelper;
import northwoods.cukeripper.utils.CommonRips;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.CukeScreen;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.GWTStatement.StatementType;
import northwoods.cukeripper.utils.ScreenMethod;
import northwoods.cukeripper.utils.StepAction;

public class FullTexts_REGEX {
	public static GWTStatement[] STEP_DEF_0_STATEMENTS;

	public static CukeScenario[] FEATURE_0_SCENARIOS;

	public static void initFeatureScenarios() {
		TestHelper.initiate();
		FEATURE_0_SCENARIOS = new CukeScenario[] {
				new CukeScenario("Regex scenario 0", null),
				new CukeScenario("Regex scenario 1", null) };
		FEATURE_0_SCENARIOS[0].setLineNumber(6);

		FEATURE_0_SCENARIOS[0].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.GIVEN, "I am doing something"));
		FEATURE_0_SCENARIOS[0].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.WHEN, "I do this"));
		FEATURE_0_SCENARIOS[0].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.THEN, "I get that"));
		//
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.GIVEN,
				"I am doing something extra wording present"));
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.WHEN, "I do this"));
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(stepFile(),
				featureFile(), StatementType.THEN, "I get that"));
		FEATURE_0_SCENARIOS[1].setLineNumber(11);
	}

	public static final int FEATURE_0_NUMBER_OF_SCENARIOS = 2;
	public static final String FEATURE_0_NAME = "Regex feature 0";
	public static final String FEATURE_0 = "@wip   \n\n\n Feature:     Regex feature 0"
			+ "\n\nScenario: Regex scenario 0"
			+ "\nGiven I am doing something \"reg insert\""
			+ "\n When I do this\nThen I get that"

			+ "\n\n Scenario: Regex scenario 1"
			+ "\nGiven I am doing something extra wording present"
			+ "\n When I do this\n Then I get that";

	public static final String SCREEN_0 = ""
			+ "class AddCasePage"
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
				StatementType.GIVEN,
				"Given I am doing something \"reg insert\"");
		GWTStatement w_statement = new GWTStatement(stepFile(), featureFile(),
				StatementType.WHEN, "I do this");
		GWTStatement t_statement = new GWTStatement(stepFile(), featureFile(),
				StatementType.THEN, "I get that");
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
			+ "Given /^Given I am doing something "
			+ CommonRips.REGEX_INSERT
			+ "$/ do"
			+ "\n  on(screen1).method0"
			+ "\n on(screen2).method0"
			+ "\n end"
			+ "\nWhen /^I do this$/ do"
			+ "\n  on(screen1).method0"
			+ "\n on(screen1).method0"
			+ "\n end"
			+ "\nThen /^I get that$/ do"
			+ "\n  on(screen1).method0"
			+ "\n on(screen2).method0"
			+ "\n end"
			+ "Given /^The given statement spanish$/ do\n on(screen1) do |scrn|"
			+ "  scrn.method0" + "  \n scrn.method0" + "       \nend     \nend";

}
