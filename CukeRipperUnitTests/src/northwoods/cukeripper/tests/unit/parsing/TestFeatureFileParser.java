package northwoods.cukeripper.tests.unit.parsing;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;
import java.util.List;

import northwoods.cukeripper.tests.unit.helpers.texts.FullTexts;
import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.parsing.CukeParser;
import northwoods.cukeripper.utils.parsing.FeatureFileParser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class TestFeatureFileParser {

	private File[] featureFiles = null;
	private File[] screenFiles = null;
	private File[] stepDefinitionFiles = null;

	private FeatureFileParser featureParser;

	@Mock
	private CukeFileReader reader;

	@Before
	public void Setup() {
		CukeFileReader.setJIT(false);
		CukeParser.THROW_ERRORS = true;
		initMocks(this);
		setUpAllFiles();
		setupReader();

		featureParser = new FeatureFileParser(reader);
		FullTexts.initFeatureScenarios();
	}

	@Test
	public void itHasAFileReader() {
		assertThat(featureParser.getReader(), is(reader));
	}

	@Test
	public void itCreatesAFeatureFromAFile() {
		assertThat(theFeatureParsed(0), is(notNullValue()));
	}

	@Test
	public void itCreatesAFeatureFromAFileWithTheCorrectName() {
		assertThat(theFeatureParsed(0).getName(), is(FullTexts.FEATURE_0_NAME));
	}

	@Test
	public void itCreatesAFeatureFromAFileWithTheCorrectNumberOfScenarios() {
		List<CukeScenario> theScenarios = theFeatureParsed(0).getScenarios();
		assertThat(theScenarios.size(),
				is(FullTexts.FEATURE_0_NUMBER_OF_SCENARIOS));
	}

	@Test
	public void itCreatesAFeatureFromAFileWithTheCorrectScenarioNames() {
		List<CukeScenario> theScenarios = theFeatureParsed(0).getScenarios();
		int size = theScenarios.size();
		for (int i = 0; i < size; i++) {
			CukeScenario cukeScenario = theScenarios.get(i);
			assertThat(cukeScenario.getName(),
					is(FullTexts.FEATURE_0_SCENARIOS[i].getName()));
		}
	}

	@Test
	public void itCreatesTheCorrectScenariosWithTheCorrectNumberOfGWTStatements() {
		List<CukeScenario> theScenarios = theFeatureParsed(0).getScenarios();
		int size = theScenarios.size();
		for (int i = 0; i < size; i++) {
			CukeScenario cukeScenario = theScenarios.get(i);
			List<GWTStatement> theStatements = cukeScenario.getStatements();
			int numOfStatements = theStatements.size();
			List<GWTStatement> expectedStatements = FullTexts.FEATURE_0_SCENARIOS[i]
					.getStatements();
			assertThat(numOfStatements, is(expectedStatements.size()));

		}
	}

	@Test
	public void itCreatesTheCorrectScenariosWithTheCorrectGWTStatementTypes() {
		List<CukeScenario> theScenarios = theFeatureParsed(0).getScenarios();
		int size = theScenarios.size();
		for (int i = 0; i < size; i++) {
			CukeScenario cukeScenario = theScenarios.get(i);
			List<GWTStatement> theStatements = cukeScenario.getStatements();
			int numOfStatements = theStatements.size();

			for (int j = 0; j < numOfStatements; j++) {
				GWTStatement actualStatement = theStatements.get(j);
				GWTStatement expectedStatement = FullTexts.FEATURE_0_SCENARIOS[i]
						.getStatement(j);
				assertThat(actualStatement.getType(),
						is(expectedStatement.getType()));
			}
		}
	}

	@Test
	public void itCreatesTheCorrectScenariosWithTheCorrectGWTStatementStatements0() {
		List<CukeScenario> theScenarios = theFeatureParsed(0).getScenarios();
		int size = theScenarios.size();
		for (int i = 0; i < size; i++) {
			CukeScenario cukeScenario = theScenarios.get(i);
			List<GWTStatement> theStatements = cukeScenario.getStatements();
			int numOfStatements = theStatements.size();

			for (int j = 0; j < numOfStatements; j++) {
				GWTStatement actualStatement = theStatements.get(j);
				GWTStatement expectedStatement = FullTexts.FEATURE_0_SCENARIOS[i]
						.getStatement(j);
				assertThat(actualStatement.getStatement(),
						is(expectedStatement.getStatement()));
			}
		}
	}

	@Test
	public void itCreatesTheCorrectScenariosWithTheCorrectGWTStatementStatements1() {
		List<CukeScenario> theScenarios = theFeatureParsed(1).getScenarios();
		int size = theScenarios.size();
		for (int i = 0; i < size; i++) {
			CukeScenario cukeScenario = theScenarios.get(i);
			List<GWTStatement> theStatements = cukeScenario.getStatements();
			int numOfStatements = theStatements.size();

			for (int j = 0; j < numOfStatements; j++) {
				GWTStatement actualStatement = theStatements.get(j);
				GWTStatement expectedStatement = FullTexts.FEATURE_1_SCENARIOS[i]
						.getStatement(j);
				assertThat(actualStatement.getStatement(),
						is(expectedStatement.getStatement()));
			}
		}
	}

	@Test
	public void itCreatesTheCorrectScenariosWithTheCorrectGWTStatementStatementsWithTheCorrectFeatureAndStepFile() {
		List<CukeScenario> theScenarios = theFeatureParsed(1).getScenarios();
		int size = theScenarios.size();
		for (int i = 0; i < size; i++) {
			CukeScenario cukeScenario = theScenarios.get(i);
			List<GWTStatement> theStatements = cukeScenario.getStatements();
			int numOfStatements = theStatements.size();

			for (int j = 0; j < numOfStatements; j++) {
				GWTStatement actualStatement = theStatements.get(j);
				GWTStatement expectedStatement = FullTexts.FEATURE_1_SCENARIOS[i]
						.getStatement(j);
				assertThat(
						actualStatement.getFeatureFile().getAbsolutePath(),
						is(expectedStatement.getFeatureFile().getAbsolutePath()));
				if (actualStatement.getStepFile() == null) {
					System.err.println("it is null for "
							+ actualStatement.slashToSlashStatement());
				}
				assertThat(actualStatement.getStepFile(), is(notNullValue()));
				assertThat(actualStatement.getStepFile().getAbsolutePath(),
						is(expectedStatement.getStepFile().getAbsolutePath()));
			}
		}
	}

	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	private CukeFeature theFeatureParsed(int index) {
		try {
			return featureParser.getFeatureFromFile(featureFiles[index]);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return null;
		}
	}

	private void setupReader() {
		when(reader.getAllFeatureFiles()).thenReturn(featureFiles);
		when(reader.getAllScreenFiles()).thenReturn(screenFiles);
		when(reader.getAllStepDefinitionFiles())
				.thenReturn(stepDefinitionFiles);

		when(reader.readFullFileContents(featureFiles[0])).thenReturn(
				FullTexts.FEATURE_0);
		when(reader.readFullFileContents(featureFiles[1])).thenReturn(
				FullTexts.FEATURE_1);

		when(reader.readFullFileContents(screenFiles[0])).thenReturn(
				FullTexts.SCREEN_0);

		when(reader.readFullFileContents(stepDefinitionFiles[0])).thenReturn(
				FullTexts.STEP_DEF_0);

	}

	private void setUpAllFiles() {
		featureFiles = new File[2];
		featureFiles[0] = mock(File.class);
		featureFiles[1] = mock(File.class);

		screenFiles = new File[1];
		screenFiles[0] = mock(File.class);

		stepDefinitionFiles = new File[1];
		stepDefinitionFiles[0] = mock(File.class);

		when(featureFiles[0].getName()).thenReturn("foo0.feature");
		when(featureFiles[1].getName()).thenReturn("foo1.feature");
		when(screenFiles[0].getName()).thenReturn("foo.rb");
		when(stepDefinitionFiles[0].getName()).thenReturn("foo.rb");

	}
}
