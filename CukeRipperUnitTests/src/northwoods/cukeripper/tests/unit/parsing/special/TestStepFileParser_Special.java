package northwoods.cukeripper.tests.unit.parsing.special;

import static northwoods.cukeripper.tests.unit.helpers.special.FullTexts_Special.FEATURE_0;
import static northwoods.cukeripper.tests.unit.helpers.special.FullTexts_Special.SCREEN_0;
import static northwoods.cukeripper.tests.unit.helpers.special.FullTexts_Special.STEP_DEF_0;
import static northwoods.cukeripper.tests.unit.helpers.special.FullTexts_Special.STEP_DEF_0_STATEMENTS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;
import java.util.List;

import northwoods.cukeripper.tests.unit.helpers.special.FullTexts_Special;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.parsing.StepFileParser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class TestStepFileParser_Special {

	private File[] featureFiles = null;
	private File[] screenFiles = null;
	private File[] stepDefinitionFiles = null;

	private StepFileParser stepParser;

	@Mock
	private CukeFileReader reader;

	@Before
	public void Setup() {
		CukeFileReader.setJIT(false);
		initMocks(this);
		setUpAllFiles();
		setupReader();

		stepParser = new StepFileParser(reader);
		FullTexts_Special.initStepDefs();
	}

	@Test
	public void itHasAFileReader() {
		assertThat(stepParser.getReader(), is(reader));
	}

	@Test
	public void itCreatesAListOfGWTStatementsFromFile_NotNull() {
		assertThat(theGWTs(), is(notNullValue()));
	}

	@Test
	public void itCreatesAListOfGWTStatementsFromFile_CorrectQuantity() {
		List<GWTStatement> theGWTs = theGWTs();
		assertThat(theGWTs.size(), is(STEP_DEF_0_STATEMENTS.length));
	}

	@Test
	public void itCreatesAListOfGWTStatementsFromFile_CorrectTypes() {
		List<GWTStatement> theGWTs = theGWTs();

		assertThat(theGWTs.get(0).getType(),
				is(STEP_DEF_0_STATEMENTS[0].getType()));
		assertThat(theGWTs.get(1).getType(),
				is(STEP_DEF_0_STATEMENTS[1].getType()));
		assertThat(theGWTs.get(2).getType(),
				is(STEP_DEF_0_STATEMENTS[2].getType()));
		assertThat(theGWTs.get(3).getType(),
				is(STEP_DEF_0_STATEMENTS[3].getType()));

	}

	@Test
	public void itCreatesAListOfGWTStatementsFromFile_CorrectStatements() {
		List<GWTStatement> theGWTs = theGWTs();

		assertThat(theGWTs.get(0).getStatement(),
				is(STEP_DEF_0_STATEMENTS[0].getStatement()));
		assertThat(theGWTs.get(1).getStatement(),
				is(STEP_DEF_0_STATEMENTS[1].getStatement()));
		assertThat(theGWTs.get(2).getStatement(),
				is(STEP_DEF_0_STATEMENTS[2].getStatement()));
		assertThat(theGWTs.get(3).getStatement(),
				is(STEP_DEF_0_STATEMENTS[3].getStatement()));

	}

	@Test
	public void itCreatesAListOfGWTStatementsFromFile_CorrectNumberOfStepActions() {
		List<GWTStatement> theGWTs = theGWTs();

		assertThat(theGWTs.get(0).getAllActions().size(),
				is(STEP_DEF_0_STATEMENTS[0].getAllActions().size()));
		assertThat(theGWTs.get(1).getAllActions().size(),
				is(STEP_DEF_0_STATEMENTS[1].getAllActions().size()));
		assertThat(theGWTs.get(2).getAllActions().size(),
				is(STEP_DEF_0_STATEMENTS[2].getAllActions().size()));
		assertThat(theGWTs.get(3).getAllActions().size(),
				is(STEP_DEF_0_STATEMENTS[3].getAllActions().size()));

	}

	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	private List<GWTStatement> theGWTs() {
		try {
			return stepParser.getGWTStatementsFromFile(stepDefinitionFiles[0]);
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	private void setupReader() {
		when(reader.getAllFeatureFiles()).thenReturn(featureFiles);
		when(reader.getAllScreenFiles()).thenReturn(screenFiles);
		when(reader.getAllStepDefinitionFiles())
				.thenReturn(stepDefinitionFiles);

		when(reader.readFullFileContents(featureFiles[0]))
				.thenReturn(FEATURE_0);

		when(reader.readFullFileContents(screenFiles[0])).thenReturn(SCREEN_0);

		when(reader.readFullFileContents(stepDefinitionFiles[0])).thenReturn(
				STEP_DEF_0);

	}

	private void setUpAllFiles() {
		featureFiles = new File[1];
		featureFiles[0] = mock(File.class);

		screenFiles = new File[1];
		screenFiles[0] = mock(File.class);

		stepDefinitionFiles = new File[2];
		stepDefinitionFiles[0] = mock(File.class);
		stepDefinitionFiles[1] = mock(File.class);

		when(featureFiles[0].getName()).thenReturn("foo.feature");
		when(screenFiles[0].getName()).thenReturn("foo.rb");
		when(stepDefinitionFiles[0].getName()).thenReturn("foo.rb");
		when(stepDefinitionFiles[1].getName()).thenReturn("foo.rb");

	}
}
