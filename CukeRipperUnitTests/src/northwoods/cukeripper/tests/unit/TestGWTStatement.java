package northwoods.cukeripper.tests.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import northwoods.cukeripper.tests.unit.helpers.TestHelper;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.GWTStatement.StatementType;

import org.junit.Before;
import org.junit.Test;

public class TestGWTStatement {
	private GWTStatement gwt;

	@Before
	public void Setup() {
		TestHelper.initiate();
		gwt = new GWTStatement(StatementType.GIVEN,
				TestHelper.TEST_GIVEN_STATEMENTS[0]);
	}

	@Test
	public void itCreatesTheCorrectTypePrefix_Given() {
		String expected = "Given";
		String actual = gwt.getTypePrefix();

		assertThat(actual, is(expected));
	}

	@Test
	public void itCreatesTheCorrectTypePrefix_When() {
		gwt = new GWTStatement(StatementType.WHEN,
				TestHelper.TEST_GIVEN_STATEMENTS[0]);

		String expected = "When";
		String actual = gwt.getTypePrefix();

		assertThat(actual, is(expected));
	}

	@Test
	public void itCreatesTheCorrectTypePrefix_Then() {

		gwt = new GWTStatement(StatementType.THEN,
				TestHelper.TEST_GIVEN_STATEMENTS[0]);

		String expected = "Then";
		String actual = gwt.getTypePrefix();

		assertThat(actual, is(expected));
	}

}
