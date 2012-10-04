package com.cukeripper.plugin.tests;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.GWTStatement.StatementType;
import northwoods.cukeripper.utils.StepAction;
import northwoods.cukeripper.utils.parsing.FeatureFileParser;

public class BaseParseTesting {
	protected static final String[] FEATURE_NAMES = new String[] { "feature_0",
			"feature_1" };
	protected static final String[] SCENARIO_NAMES = new String[] { "scenario_0" };
	protected static File TEST_FEATURE_FILE_0;
	protected static File TEST_FEATURE_FILE_1;
	protected static File TEST_STEP_FILE_0;
	protected CukeFileReader reader;
	protected FeatureFileParser featureFileParser;
	protected static final String[] screenNames = new String[] { "screen_0",
			"screen_1" };

	protected CukeFeature[] testFeatures;
	protected CukeScenario[] cukeScenarios;
	protected GWTStatement g_statement;
	protected GWTStatement t_statement;
	protected GWTStatement w_statement;
	protected StepAction[] stepActions;

	public void Setup() {
		setupReader();
		setupTestFeatureFiles();
		setupTestCukeFeatures();
		setupParser();
		try {
			when(featureFileParser.getFeatureFromFile(TEST_FEATURE_FILE_0))
					.thenReturn(testFeatures[0]);
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	private void setupReader() {
		this.reader = mock(CukeFileReader.class);
	}

	private void setupTestFeatureFiles() {
		TEST_FEATURE_FILE_0 = mock(File.class);
		TEST_FEATURE_FILE_1 = mock(File.class);
		TEST_STEP_FILE_0 = mock(File.class);
		when(TEST_FEATURE_FILE_0.getAbsolutePath()).thenReturn(
				"test" + File.separator + "path" + File.separator + "fileName_"
						+ 0 + ".feature");
		when(TEST_FEATURE_FILE_0.getName()).thenReturn(
				"fileName_" + 0 + ".feature");
		when(TEST_FEATURE_FILE_1.getAbsolutePath()).thenReturn(
				"test" + File.separator + "path" + File.separator + "fileName_"
						+ 1 + ".feature");
		when(TEST_FEATURE_FILE_1.getName()).thenReturn(
				"fileName_" + 1 + ".feature");
		when(TEST_STEP_FILE_0.getAbsolutePath()).thenReturn(
				"test" + File.separator + "path" + File.separator + "step_file"
						+ 1 + ".rb");
		when(TEST_STEP_FILE_0.getName()).thenReturn("step_file" + 1 + ".rb");
	}

	private void setupParser() {
		featureFileParser = mock(FeatureFileParser.class);

		try {
			when(featureFileParser.getFeatureFromFile(TEST_FEATURE_FILE_0))
					.thenReturn(testFeatures[0]);

			when(featureFileParser.getFeatureFromFile(TEST_FEATURE_FILE_1))
					.thenReturn(testFeatures[1]);
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	private void setupTestCukeFeatures() {
		setupTestCukeScenarios();
		testFeatures = new CukeFeature[2];

		testFeatures[0] = new CukeFeature(FEATURE_NAMES[0], TEST_FEATURE_FILE_0);
		testFeatures[0].addScenario(cukeScenarios[0]);
		testFeatures[1] = new CukeFeature(FEATURE_NAMES[1], TEST_FEATURE_FILE_1);
		testFeatures[1].addScenario(cukeScenarios[0]);

	}

	private void setupTestCukeScenarios() {
		setupStatements();
		cukeScenarios = new CukeScenario[1];
		cukeScenarios[0] = new CukeScenario(SCENARIO_NAMES[0],
				TEST_FEATURE_FILE_0);
		cukeScenarios[0].createStatement(g_statement);
		cukeScenarios[0].createStatement(t_statement);
		cukeScenarios[0].createStatement(w_statement);
	}

	private void setupStatements() {
		setupStepActions();
		g_statement = new GWTStatement(TEST_STEP_FILE_0, TEST_FEATURE_FILE_0,
				StatementType.GIVEN, "this is the given");
		w_statement = new GWTStatement(TEST_STEP_FILE_0, TEST_FEATURE_FILE_0,
				StatementType.WHEN, "this is the when");
		t_statement = new GWTStatement(TEST_STEP_FILE_0, TEST_FEATURE_FILE_0,
				StatementType.THEN, "this is the then");

		g_statement.addStepAction(stepActions[0]);
		g_statement.addStepAction(stepActions[1]);
		g_statement.addStepAction(stepActions[2]);
	}

	private void setupStepActions() {
		stepActions = new StepAction[3];
		stepActions[0] = new StepAction(screenNames[0], 0);
		stepActions[1] = new StepAction(screenNames[1], 0);
		stepActions[2] = new StepAction(screenNames[1], 1);
	}
}
