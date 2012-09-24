package com.cukeripper.plugin.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.GWTStatement.StatementType;
import northwoods.cukeripper.utils.StepAction;
import northwoods.cukeripper.utils.parsing.FeatureFileParser;

import org.junit.Before;
import org.junit.Test;

import com.cukeripper.plugin.views.FeatureTreeContentProvider;

public class TestFeatureTreeContentProvider {

	private static final String[] FEATURE_NAMES = new String[] { "feature_0",
			"feature_1" };
	private static final String[] SCENARIO_NAMES = new String[] { "scenario_0" };
	private static File TEST_FEATURE_FILE_0;
	private static File TEST_FEATURE_FILE_1;
	private FeatureFileParser featureFileParser;

	private CukeFeature[] testFeatures;
	private CukeScenario[] cukeScenarios;
	private GWTStatement g_statement;
	private GWTStatement t_statement;
	private GWTStatement w_statement;
	private StepAction[] stepActions;
	private FeatureTreeContentProvider feature_treeContentProvider;

	@Before
	public void Setup() {
		setupTestFeatureFiles();
		setupTestCukeFeatures();
		setupParser();
		when(featureFileParser.getFeatureFromFile(TEST_FEATURE_FILE_0))
				.thenReturn(testFeatures[0]);

		feature_treeContentProvider = new FeatureTreeContentProvider(
				new File[] { TEST_FEATURE_FILE_0, TEST_FEATURE_FILE_1 },
				featureFileParser);
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	@Test
	public void itGetsTheCorrectElements() {
		Object[] actualFeatures = feature_treeContentProvider.getElements(null);

		assertThat(actualFeatures.length, is(testFeatures.length));

		for (int i = 0; i < testFeatures.length; i++) {
			assertThat((CukeFeature) actualFeatures[i], is(testFeatures[i]));
		}

	}

	@Test
	public void itHasChildElementsForFeatures() {
		Object[] actualChildren = feature_treeContentProvider
				.getChildren(testFeatures[0]);

		for (int i = 0; i < testFeatures.length; i++) {
			assertThat(
					feature_treeContentProvider.hasChildren(testFeatures[i]),
					is(true));
		}

	}

	@Test
	public void itGetsTheCorrectChildElementsForFeatures() {
		Object[] actualChildren = feature_treeContentProvider
				.getChildren(testFeatures[0]);

		for (int i = 0; i < testFeatures[0].getScenarios().size(); i++) {
			assertThat((CukeScenario) actualChildren[i], is(testFeatures[0]
					.getScenarios().get(i)));
		}

	}

	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	private void setupTestFeatureFiles() {
		TEST_FEATURE_FILE_0 = mock(File.class);
		TEST_FEATURE_FILE_1 = mock(File.class);
		when(TEST_FEATURE_FILE_0.getAbsolutePath()).thenReturn(
				"test" + File.separator + "path" + File.separator + "fileName_"
						+ 0 + ".feature");
		when(TEST_FEATURE_FILE_1.getAbsolutePath()).thenReturn(
				"test" + File.separator + "path" + File.separator + "fileName_"
						+ 1 + ".feature");

	}

	private void setupParser() {
		featureFileParser = mock(FeatureFileParser.class);

		when(featureFileParser.getFeatureFromFile(TEST_FEATURE_FILE_0))
				.thenReturn(testFeatures[0]);
		when(featureFileParser.getFeatureFromFile(TEST_FEATURE_FILE_1))
				.thenReturn(testFeatures[1]);
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
		g_statement = new GWTStatement(StatementType.GIVEN, "this is the given");
		w_statement = new GWTStatement(StatementType.WHEN, "this is the when");
		t_statement = new GWTStatement(StatementType.THEN, "this is the then");

		g_statement.addStepAction(stepActions[0]);
		g_statement.addStepAction(stepActions[1]);
		g_statement.addStepAction(stepActions[2]);
	}

	private void setupStepActions() {
		stepActions = new StepAction[3];
		stepActions[0] = new StepAction("Screen_0", 0);
		stepActions[1] = new StepAction("Screen_1", 0);
		stepActions[2] = new StepAction("Screen_1", 1);
	}
}
