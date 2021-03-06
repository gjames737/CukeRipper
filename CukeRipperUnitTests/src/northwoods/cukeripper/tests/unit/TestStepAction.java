package northwoods.cukeripper.tests.unit;

import static northwoods.cukeripper.tests.unit.helpers.TestHelper.TEST_SCREENNAME;
import static northwoods.cukeripper.utils.CommonRips.ON;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import northwoods.cukeripper.tests.unit.helpers.TestHelper;
import northwoods.cukeripper.utils.LoadedCukes;
import northwoods.cukeripper.utils.ScreenMethod;
import northwoods.cukeripper.utils.StepAction;

import org.junit.Before;
import org.junit.Test;

public class TestStepAction {

	private StepAction stepAction;

	@Before
	public void Setup() {
		TestHelper.initiate();
		stepAction = new StepAction(TestHelper.getTestScreen().getName(), 0);
		int screenInex = stepAction.getScreenIndex();
		LoadedCukes
				.getScreens()
				.get(screenInex)
				.addMethod(
						new ScreenMethod(TestHelper.getTestScreenMethodNames()
								.get(0), "#todo"));
	}

	@Test
	public void itCreatesARubyAction() {
		String expected = ON + "(" + TEST_SCREENNAME + ")" + "."
				+ TestHelper.getTestScreenMethodNames().get(0);
		String actual = stepAction.toRuby();
		assertThat(actual, is(expected));
		System.out.println(actual);
	}

}
