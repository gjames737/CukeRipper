package northwoods.cukeripper.tests.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeScenario;

import org.junit.Before;
import org.junit.Test;

public class TestCukeFeature {

	private CukeFeature feature;

	@Before
	public void Setup() {
		feature = new CukeFeature("featureName");
	}

	@Test
	public void itCanAddAScenario() {
		addScenarios("foos", "bars", "Bizzes");
		assertThat(theScenarioNameAtIndex(0), is("foos"));
		assertThat(theScenarioNameAtIndex(1), is("bars"));
		assertThat(theScenarioNameAtIndex(2), is("Bizzes"));
	}

	// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	private String theScenarioNameAtIndex(int index) {
		return theScenarioAtIndex(index).getName();
	}

	private CukeScenario theScenarioAtIndex(int index) {
		return feature.getScenarios().get(index);
	}

	private void addScenarios(String... cukeScenarioNames) {
		for (String name : cukeScenarioNames) {
			feature.addScenario(new CukeScenario(name));
		}
	}
}
