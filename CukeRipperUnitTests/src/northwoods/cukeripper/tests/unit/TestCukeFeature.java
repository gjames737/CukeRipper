package northwoods.cukeripper.tests.unit;

import static northwoods.cukeripper.tests.unit.helpers.TestScenarios.SCENARIOS;
import static northwoods.cukeripper.utils.CommonRips.BREAKLINE;
import static northwoods.cukeripper.utils.CommonRips.FEATURE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import northwoods.cukeripper.tests.unit.helpers.TestScenarios;
import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeScenario;

import org.junit.Before;
import org.junit.Test;

public class TestCukeFeature {

	private CukeFeature feature;

	@Before
	public void Setup() {
		feature = new CukeFeature("featureName");
		TestScenarios.initiate();
	}

	@Test
	public void itCanAddAScenario() {
		addScenarios();
		assertThat(theScenarioNameAtIndex(0), is(SCENARIOS[0].getName()));
		assertThat(theScenarioNameAtIndex(1), is(SCENARIOS[1].getName()));
		assertThat(theScenarioNameAtIndex(2), is(SCENARIOS[2].getName()));
	}

	@Test
	public void itCreatesTheCorrectRubyWithNOScenarios() {
		String expectedRubyWithNoScenarios = getExpectedRubyFeatureWithNOScenarios();
		System.out.println(expectedRubyWithNoScenarios);
		assertThat(theRuby(), is(notNullValue()));
		assertThat(theRuby(), is(expectedRubyWithNoScenarios));
	}

	@Test
	public void itCreatesTheCorrectRubyWithScenarios_Singular() {
		addScenarios();
		TestScenarios.createSingularStatements();
		String expectedRubyWithScenarios = getExpectedRubyFeatureWithScenarios_Singular();
		System.out.println(expectedRubyWithScenarios);

		assertThat(theRuby(), is(notNullValue()));
		assertThat(theRuby(), is(expectedRubyWithScenarios));
	}

	@Test
	public void itCreatesTheCorrectRubyWithScenarios_DoubleGWTs() {
		addScenarios();
		TestScenarios.createDoubleGWTStatements();
		String expectedRubyWithScenarios = getExpectedRubyFeatureWithScenarios_DoubleGWTs();
		System.out.println(theRuby());

		assertThat(theRuby(), is(notNullValue()));
		assertThat(theRuby(), is(expectedRubyWithScenarios));
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

	private String getExpectedRubyFeatureWithScenarios_Singular() {
		String ruby = "";
		ruby += FEATURE + ": " + feature.getName() + BREAKLINE;
		for (int i = 0; i < SCENARIOS.length; i++) {
			ruby += BREAKLINE + BREAKLINE + testScenarioToRuby(i);
		}
		return ruby;
	}

	private String getExpectedRubyFeatureWithScenarios_DoubleGWTs() {
		String ruby = "";
		ruby += FEATURE + ": " + feature.getName() + BREAKLINE;

		for (int i = 0; i < SCENARIOS.length; i++) {
			ruby += BREAKLINE + BREAKLINE + SCENARIOS[i].toRuby();
		}
		return ruby;
	}

	private String testScenarioToRuby(int index) {
		return SCENARIOS[index].toRuby();
	}

	private String theScenarioNameAtIndex(int index) {
		return theScenarioAtIndex(index).getName();
	}

	private CukeScenario theScenarioAtIndex(int index) {
		return feature.getScenarios().get(index);
	}

	private void addScenarios() {
		for (int i = 0; i < SCENARIOS.length; i++) {
			feature.addScenario(SCENARIOS[i]);
		}
	}
}
