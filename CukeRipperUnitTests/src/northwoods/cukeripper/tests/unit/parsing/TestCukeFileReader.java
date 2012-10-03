package northwoods.cukeripper.tests.unit.parsing;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;

import northwoods.cukeripper.tests.unit.helpers.FullTexts;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.parsing.CukeParser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class TestCukeFileReader {

	private File[] featureFiles = null;
	private File[] screenFiles = null;
	private File[] stepDefinitionFiles = null;

	@Mock
	private CukeFileReader reader;

	@Before
	public void Setup() {
		CukeFileReader.setJIT(false);
		CukeParser.THROW_ERRORS = true;
		initMocks(this);
		setUpAllFiles();
		setupReader();
	}

	@Test
	public void itReadsTheFeatureText() {
		assertThat(reader.readFullFileContents(featureFiles[0]),
				is(FullTexts.FEATURE_0));
	}

	@Test
	public void itReadsTheScreenText() {
		assertThat(reader.readFullFileContents(screenFiles[0]),
				is(FullTexts.SCREEN_0));
	}

	@Test
	public void itReadsTheStepDefinitionText() {
		assertThat(reader.readFullFileContents(stepDefinitionFiles[0]),
				is(FullTexts.STEP_DEF_0));
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
