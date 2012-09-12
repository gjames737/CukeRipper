
Feature:  Adding new case items
    
  Scenario: Verifying the default add case screen appearance
    Given I am looking at the cases for an event
    When I choose to add a new case
    Then I am presented with the default add new case screen

  Scenario:  Adding new case information
    Given I am looking at the cases for an event
    And I choose to add a new case
    When I enter "123456" for the State Case Number
    Then I am then able to save the new case

  Scenario: Adding new case information for Local Case
	Given I am looking at the cases for an event
	And I choose to add a new case
	When I enter "123456" for the Local Case Number
	Then I am then able to save the new case
		
  Scenario: Saving a case
	Given I am looking at the cases for an event
	And I choose to add a new case
	And I have filled in required case details
	When I choose to save the case
	Then I am not able to save the case
	And the tabs are populated
	And the case number is displayed in the breadcrumb
	And the message 'case saved' is displayed 

 Scenario: Back button behavior
	Given I have a saved case
	When I select the back button
	Then the case screen will be displayed
	And the saved case will be displayed
	