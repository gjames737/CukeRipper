package northwoods.cukeripper.tests.unit.helpers;

import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.GWTStatement.StatementType;

public class FullTexts {

	public static CukeScenario[] FEATURE_0_SCENARIOS = new CukeScenario[] {
			new CukeScenario("Verifying the default add case screen appearance"),
			new CukeScenario("Saving a case") };

	public static void initFeature0Scenarios() {
		FEATURE_0_SCENARIOS = new CukeScenario[] {
				new CukeScenario(
						"Verifying the default add case screen appearance"),
				new CukeScenario("Saving a case") };
		FEATURE_0_SCENARIOS[0].createStatement(new GWTStatement(
				StatementType.GIVEN, "I am looking at the cases for an event"));
		FEATURE_0_SCENARIOS[0].createStatement(new GWTStatement(
				StatementType.WHEN, "I choose to add a new case"));
		FEATURE_0_SCENARIOS[0].createStatement(new GWTStatement(
				StatementType.THEN,
				"I am presented with the default add new case screen"));
		//
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(
				StatementType.GIVEN, "I am looking at the cases for an event"));
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(
				StatementType.GIVEN, "I choose to add a new case"));
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(
				StatementType.GIVEN, "I have filled in required case details"));
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(
				StatementType.WHEN, "I choose to save the case"));
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(
				StatementType.THEN, "I am not able to save the case"));
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(
				StatementType.THEN, "the tabs are populated"));
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(
				StatementType.THEN,
				"the case number is displayed in the breadcrumb"));
		FEATURE_0_SCENARIOS[1].createStatement(new GWTStatement(
				StatementType.THEN, "the message 'case saved' is displayed"));

	}

	public static final int FEATURE_0_NUMBER_OF_SCENARIOS = 2;
	public static final String FEATURE_0_NAME = "Adding new case items";
	public static final String FEATURE_0 = "Feature:  Adding new case items"
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

	public static final String STEP_DEF_0 = ""
			+ "Given /^I am looking at the cases for an event$/ do"
			+ "\n  on(LoginPage).login_successfully"
			+ "\n on(EventTypesPage).first_event"
			+ "\n end"

			+ "\n\n When /^I choose to add a new case$/ do"
			+ "\n on(CasesPage).add_case"
			+ "\n end"

			+ "\n\n Then /^I am presented with the default add new case screen$/ do"
			+ "\n on(AddCasePage) do |screen|"
			+ "\n screen.should be_active"

			+ "\n\n     screen.should_not be_able_to_delete"
			+ "\n screen.should_not be_able_to_save"

			+ "\n\n screen.case_worker.should eq('Co Pilot')"
			+ "\n  screen.should_not be_able_to_select_a_case_worker"

			+ "\n\n  screen.program_type.should eq('Cucumber')"
			+ "\n   screen.should_not be_able_to_select_a_program_type"

			+ "\n\n  screen.case_head.should match 'Select Case Head\\s*'"
			+ "\n  screen.should be_able_to_select_a_case_head"

			+ "\n\n   screen.should_not have_text 'Members'"
			+ "\n   screen.should_not have_text 'Narratives'"
			+ "\n  end"

			+ "\n\n end"

			+ "\n\n When /^I enter \"(.*?)\" for the State Case Number$/ do |state_case_number|"
			+ "\n   on(AddCasePage).state_case_number = state_case_number"
			+ "\n end"

			+ "\n\n Then /^I am then able to save the new case$/ do"
			+ "\n   on(AddCasePage) do |screen|"
			+ "\n    screen.should be_able_to_save"
			+ "\n    screen.should_not be_able_to_delete"

			+ "\n\n   screen.should_not be_able_to_select_a_case_worker"
			+ "\n    screen.should_not be_able_to_select_a_program_type"

			+ "\n\n   screen.should be_able_to_select_a_case_head"
			+ "\n  end"
			+ "\n end"

			+ "\n\n Given /^I have a saved case$/ do"
			+ "\n  on(LoginPage).login_successfully"
			+ "\n  on(EventTypesPage).first_event"
			+ "\n   on(CasesPage).add_case"
			+ "\n  @case_number = \"Northwoods98765\""
			+ "\n  on(AddCasePage).local_case_number = @case_number"
			+ "\n  on(AddCasePage).save"
			+ "\n end"

			+ "\n\n When /^I select the back button$/ do"
			+ "\n   on(AddCasePage).back"
			+ "\n end"

			+ "\n\n Then /^the case screen will be displayed$/ do"
			+ "\n  #on(AddCasePage) do |screen|"
			+ "\n   #  screen.should have_text @case_number"
			+ "\n   #  screen.should have_text \"Cucumber (Case1)\""
			+ "\n   # screen.should_not have_text \"Narratives\""
			+ "\n   #end"
			+ "\n end"

			+ "\n\n Then /^the saved case will be displayed$/ do"
			+ "\n  pending # express the regexp above with the code you wish you had"
			+ "\n end"

			+ "\n\n When /^I enter \"(.*?)\" for the Local Case Number$/ do |case_number|"
			+ "\n   on(AddCasePage).local_case_number = case_number" + "\n end"

			+ "\n\n Given /^I have filled in required case details$/ do"
			+ "\n   @state_case_number = \"StateCaseNumber123456\""
			+ "\n   on(AddCasePage).state_case_number = @state_case_number"
			+ "\n end"

			+ "\n\n When /^I choose to save the case$/ do"
			+ "\n  on(AddCasePage).save" + "\n end"

			+ "\n\n Then /^I am not able to save the case$/ do"
			+ "\n   on(AddCasePage) do |screen|"
			+ "\n    screen.should_not be_able_to_save"
			+ "\n    screen.should be_able_to_delete" + "\n   end" + "\n end"

			+ "\n\n Then /^the tabs are populated$/ do"
			+ "\n  on(AddCasePage) do |screen|"
			+ "\n 	screen.should have_text 'Members'"
			+ "\n    screen.should have_text 'Narratives'"
			+ "\n 	screen.should have_text 'Case Details'" + "\n   end"
			+ "\n end"

			+ "\n\n Then /^the case number is displayed in the breadcrumb$/ do"
			+ "\n   on(AddCasePage).should have_text @state_case_number"
			+ "\n end"

			+ "\n\n Then /^the message 'case saved' is displayed$/ do"
			+ "\n  pending"
			+ "\n  #on(AddCasePage).should have_text 'Case saved'" + "\n end";

}
