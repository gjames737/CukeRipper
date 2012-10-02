package com.cukeripper.plugin.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.cukeripper.plugin.views.FeatureTreeContentProvider;
import com.cukeripper.plugin.views.ICukeParsingListener;

public class TestFeatureTreeContentProvider extends BaseParseTesting {

	private FeatureTreeContentProvider feature_treeContentProvider;

	@Before
	public void Setup() {
		super.Setup();

		feature_treeContentProvider = new FeatureTreeContentProvider(
				Mockito.mock(ICukeParsingListener.class), new File[] {
						TEST_FEATURE_FILE_0, TEST_FEATURE_FILE_1 },
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

		for (int i = 0; i < testFeatures.length; i++) {
			assertThat(feature_treeContentProvider.hasChildren(testFeatures[0]
					.getScenarios().get(0)), is(true));
		}

	}

	@Test
	public void itHasChildElementsForScenario() {

		for (int i = 0; i < testFeatures.length; i++) {
			List<CukeScenario> scenarios = testFeatures[i].getScenarios();
			for (CukeScenario cukeScenario : scenarios) {
				assertThat(
						feature_treeContentProvider.hasChildren(cukeScenario),
						is(cukeScenario.getStatements().size() > 0));
			}

		}

	}

	@Test
	public void itDoesNotHaveChildElementsForStatements() {

		for (int i = 0; i < testFeatures.length; i++) {
			List<CukeScenario> scenarios = testFeatures[i].getScenarios();
			for (CukeScenario cukeScenario : scenarios) {
				List<GWTStatement> statements = cukeScenario.getStatements();
				for (GWTStatement gwtStatement : statements) {
					assertThat(
							feature_treeContentProvider
									.hasChildren(gwtStatement),
							is(false));
				}
			}

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

	@Test
	public void itGetsTheCorrectChildElementsForScenarios() {
		Object[] actualChildren = feature_treeContentProvider
				.getChildren(testFeatures[0].getScenarios().get(0));

		for (int i = 0; i < testFeatures[0].getScenarios().size(); i++) {
			GWTStatement actual_statement = (GWTStatement) actualChildren[i];
			GWTStatement expected_statement = testFeatures[0].getScenarios()
					.get(i).getStatement(i);
			assertThat(actual_statement.getStatement(),
					is(expected_statement.getStatement()));
			assertThat(actual_statement, is(expected_statement));
		}

	}

	@Test
	public void itGetsTheCorrectChildElementsForStatements() {
		Object[] actualChildren = feature_treeContentProvider
				.getChildren(testFeatures[0].getScenarios().get(0)
						.getStatements().get(0));

		assertThat(actualChildren, is(nullValue()));

	}

	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

}
