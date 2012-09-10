package northwoods.cukeripper.tests.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.FeatureBuilder;
import northwoods.cukeripper.utils.CukeScenario;

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

	// ~~::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ~~::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

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
