package northwoods.cukeripper.tests.unit.parsing;

import static northwoods.cukeripper.tests.unit.helpers.FullTexts.STEP_DEF_0_STATEMENTS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;
import java.util.List;

import northwoods.cukeripper.tests.unit.helpers.FullTexts;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.parsing.StepFileParser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class TestStepFileParser {

	private File[] featureFiles = null;
	private File[] screenFiles = null;
	private File[] stepDefinitionFiles = null;

	private StepFileParser stepParser;

	@Mock
	private CukeFileReader reader;

	@Before
	public void Setup() {

		initMocks(this);
		setUpAllFiles();
		setupReader();

		stepParser = new StepFileParser(reader);
		FullTexts.initStepDef0();
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

	}

	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	private List<GWTStatement> theGWTs() {
		return stepParser.getGWTStatementsFromFile(stepDefinitionFiles[0]);
	}

	private void setupReader() {
		when(reader.getAllFeatureFiles()).thenReturn(featureFiles);
		when(reader.getAllScreenFiles()).thenReturn(screenFiles);
		when(reader.getAllStepDefinitionFiles())
				.thenReturn(stepDefinitionFiles);

		when(reader.readFullFileContents(featureFiles[0])).thenReturn(
				FullTexts.FEATURE_0);

		when(reader.readFullFileContents(screenFiles[0])).thenReturn(
				FullTexts.SCREEN_0);

		when(reader.readFullFileContents(stepDefinitionFiles[0])).thenReturn(
				FullTexts.STEP_DEF_0);

	}

	private void setUpAllFiles() {
		featureFiles = new File[1];
		featureFiles[0] = mock(File.class);

		screenFiles = new File[1];
		screenFiles[0] = mock(File.class);

		stepDefinitionFiles = new File[1];
		stepDefinitionFiles[0] = mock(File.class);

	}
}
