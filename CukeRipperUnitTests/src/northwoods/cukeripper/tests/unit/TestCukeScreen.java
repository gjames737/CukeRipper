package northwoods.cukeripper.tests.unit;

import static northwoods.cukeripper.utils.CommonRips.BREAKLINE;
import static northwoods.cukeripper.utils.CommonRips.CLASS;
import static northwoods.cukeripper.utils.CommonRips.DEF;
import static northwoods.cukeripper.utils.CommonRips.END;
import static northwoods.cukeripper.utils.CommonRips.TODO;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import northwoods.cukeripper.utils.CukeScreen;
import northwoods.cukeripper.utils.ScreenMethod;

import org.junit.Before;
import org.junit.Test;

public class TestCukeScreen {

	private static final String NAME = "ScreenName";
	private static final String METHOD_0_NAME = "methodName0";
	private static final String METHOD_0_BODY = "this\nthat\nthe\nother";
	CukeScreen screen;

	@Before
	public void Setup() {
		screen = new CukeScreen(NAME);
	}

	@Test
	public void itHasTheCorrectName() {
		assertThat(theName(), is(NAME));
	}

	@Test
	public void itHasNoMethods() {
		assertThat(theMethods(), is(notNullValue()));
		assertThat(theMethods().size(), is(0));
	}

	@Test
	public void itCreatesTheCorrectRubyWithNoMethods() {
		assertThat(theRuby(), is(notNullValue()));
		assertThat(theRuby(), is(noMethodsExpectedRuby()));
	}

	@Test
	public void itCreatesTheCorrectRubyWithMethods() {
		screen.addMethod(new ScreenMethod(METHOD_0_NAME, METHOD_0_BODY));
		assertThat(theRuby(), is(notNullValue()));
		assertThat(theRuby(), is(withMethodsExpectedRuby()));
	}

	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	private String noMethodsExpectedRuby() {
		String expectedRuby = "";
		expectedRuby += CLASS + " " + NAME;
		expectedRuby += BREAKLINE;
		expectedRuby += TODO;
		expectedRuby += BREAKLINE;
		expectedRuby += END;
		return expectedRuby;
	}

	private String withMethodsExpectedRuby() {
		String expectedRuby = "";
		expectedRuby += CLASS + " " + NAME;
		expectedRuby += BREAKLINE;
		expectedRuby += DEF + " " + METHOD_0_NAME;
		expectedRuby += BREAKLINE;
		expectedRuby += METHOD_0_BODY;
		expectedRuby += BREAKLINE;
		expectedRuby += END;
		System.out.println(expectedRuby);
		return expectedRuby;
	}

	private String theRuby() {
		return screen.toRuby();
	}

	private String theName() {
		return screen.getName();
	}

	private List<ScreenMethod> theMethods() {
		return screen.getMethods();
	}
}
