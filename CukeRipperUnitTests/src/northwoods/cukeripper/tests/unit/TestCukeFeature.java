package northwoods.cukeripper.tests.unit;

import static northwoods.cukeripper.utils.CommonRips.BREAKLINE;
import static northwoods.cukeripper.utils.CommonRips.FEATURE;
import static northwoods.cukeripper.utils.CommonRips.SCENARIO;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
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

	@Test
	public void itCreatesTheCorrectRubyWithNOScenarios() {
		String expectedRubyWithNoScenarios = getExpectedRubyFeatureWithNOScenarios();
		System.out.println(expectedRubyWithNoScenarios);
		assertThat(theRuby(), is(notNullValue()));
		assertThat(theRuby(), is(expectedRubyWithNoScenarios));
	}

	@Test
	public void itCreatesTheCorrectRubyWithScenarios() {
		addScenarios("foos", "bars", "Bizzes");
		String expectedRubyWithScenarios = getExpectedRubyFeatureWithScenarios();
		System.out.println(expectedRubyWithScenarios);
		System.out.println("\nActual Ruby\n\n" + theRuby());
		assertThat(theRuby(), is(notNullValue()));
		assertThat(theRuby(), is(expectedRubyWithScenarios));
		fail();
	}

	// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	private String theRuby() {
		return feature.toRuby();
	}

	private String getExpectedRubyFeatureWithNOScenarios() {
		String ruby = "";
		ruby += FEATURE + ": " + feature.getName() + BREAKLINE;
		return ruby;
	}

	private String getExpectedRubyFeatureWithScenarios() {
		String ruby = "";
		ruby += FEATURE + ": " + feature.getName() + BREAKLINE;
		ruby += BREAKLINE + SCENARIO + ": " + "foos" + BREAKLINE;
		ruby += BREAKLINE + SCENARIO + ": " + "bars" + BREAKLINE;
		ruby += BREAKLINE + SCENARIO + ": " + "Bizzes" + BREAKLINE;
		return ruby;
	}

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
