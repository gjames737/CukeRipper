package northwoods.cukeripper.tests.unit.parsing;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;

import northwoods.cukeripper.tests.unit.helpers.FullTexts;
import northwoods.cukeripper.utils.CukeFileReader;
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

		initMocks(this);
		setUpAllFiles();
		setupReader();

		featureParser = new FeatureFileParser(reader);
	}

	@Test
	public void itHasAFileReader() {
		assertThat(featureParser.getReader(), is(reader));
	}

	@Test
	public void itCreatesAFeatureFromAFile() {
		assertThat(featureParser.getFeatureFromFile(featureFiles[0]),
				is(notNullValue()));
	}

	@Test
	public void itCreatesAFeatureFromAFileWithTheCorrectName() {
		assertThat(featureParser.getFeatureFromFile(featureFiles[0]).getName(),
				is(FullTexts.FEATURE_0_NAME));
	}

	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

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
