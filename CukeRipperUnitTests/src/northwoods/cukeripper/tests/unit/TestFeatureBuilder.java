package northwoods.cukeripper.tests.unit;

import static northwoods.cukeripper.tests.unit.helpers.TestScenarios.SCENARIOS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import northwoods.cukeripper.tests.unit.helpers.TestScenarios;
import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.FeatureBuilder;

import org.junit.Before;
import org.junit.Test;

public class TestFeatureBuilder {

	private FeatureBuilder featureBuilder;

	@Before
	public void Setup() {
		featureBuilder = new FeatureBuilder();
	}

	@Test
	public void itCreatesAFeatureWithTheCorrectName() {
		makeFeaturesWithNames("foo", "bar", "bizz");
		assertThat(theFeatureNameAtIndex(0), is("foo"));
		assertThat(theFeatureNameAtIndex(1), is("bar"));
		assertThat(theFeatureNameAtIndex(2), is("bizz"));
	}

	@Test
	public void itCreatesAFeatureWithAnEmptyListOfScenarios() {
		makeFeaturesWithNames("foo", "bar", "bizz");
		assertThat(theFeaturesScenarioListAtIndex(0), is(notNullValue()));
		assertThat(theFeaturesScenarioListAtIndex(1), is(notNullValue()));
		assertThat(theFeaturesScenarioListAtIndex(2), is(notNullValue()));
	}

	@Test
	public void itCanListAllUniqueScenarios() {
		setupTestFeaturesWithScenarios();

		assertThat(featureBuilder.listAllScenarios().size(),
				is(SCENARIOS.length));

	}

	// ~~::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ~~::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	private void setupTestFeaturesWithScenarios() {
		makeFeaturesWithNames("foo", "bar", "bizz");
		addScenariosToFeature(0, 0);
		addScenariosToFeature(1, SCENARIOS.length);
		addScenariosToFeature(2, SCENARIOS.length - 1);

		assertThat(theFeaturesScenarioListAtIndex(0).size(), is(0));
		assertThat(theFeaturesScenarioListAtIndex(1).size(),
				is(SCENARIOS.length));
		assertThat(theFeaturesScenarioListAtIndex(2).size(),
				is(SCENARIOS.length - 1));
	}

	private void addScenariosToFeature(int index, int numberOfScenarios) {
		TestScenarios.initiate();
		TestScenarios.createDoubleGWTStatements();
		CukeFeature cukeFeature = featureBuilder.getFeatures().get(index);
		for (int i = 0; i < numberOfScenarios; i++) {
			cukeFeature.addScenario(SCENARIOS[i]);
		}

	}

	private void makeFeaturesWithNames(String... names) {
		for (String name : names) {
			featureBuilder.makeNewFeature(name);
		}

	}

	private List<CukeScenario> theFeaturesScenarioListAtIndex(int index) {
		return theFeatureAtIndex(index).getScenarios();
	}

	private String theFeatureNameAtIndex(int index) {
		return theFeatureAtIndex(index).getName();
	}

	private CukeFeature theFeatureAtIndex(int index) {
		return featureBuilder.getFeatures().get(index);
	}

}
