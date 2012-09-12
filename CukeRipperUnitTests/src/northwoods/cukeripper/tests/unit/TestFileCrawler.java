package northwoods.cukeripper.tests.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;

import northwoods.cukeripper.utils.CukeFileReader;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class TestFileCrawler {

	private File[] featureFiles = null;
	private File[] screenFiles = null;
	private File[] stepDefinitionFiles = null;

	@Mock
	private CukeFileReader reader;

	private String absoluteRootPath = File.separator + "root" + File.separator;

	@Before
	public void Setup() {
		initMocks(this);
		setUpAllFiles();
		setupReader();
	}

	@Test
	public void itDNBU() {
		assertThat(reader.readFullFileContents(featureFiles[0]),
				is(notNullValue()));
		assertThat(reader.readFullFileContents(featureFiles[0]), is("0"));
		assertThat(reader.readFullFileContents(featureFiles[1]), is("1"));
		assertThat(reader.readFullFileContents(featureFiles[2]), is("2"));

	}

	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	private void setupReader() {
		when(reader.getAllFeatureFiles()).thenReturn(featureFiles);
		when(reader.getAllScreenFiles()).thenReturn(screenFiles);
		when(reader.getAllStepDefinitionFiles())
				.thenReturn(stepDefinitionFiles);

		when(reader.readFullFileContents(featureFiles[0])).thenReturn("0");
		when(reader.readFullFileContents(featureFiles[1])).thenReturn("1");
		when(reader.readFullFileContents(featureFiles[2])).thenReturn("2");

		when(reader.readFullFileContents(screenFiles[0])).thenReturn("");
		when(reader.readFullFileContents(screenFiles[1])).thenReturn("");
		when(reader.readFullFileContents(screenFiles[2])).thenReturn("");

		when(reader.readFullFileContents(stepDefinitionFiles[0]))
				.thenReturn("");
		when(reader.readFullFileContents(stepDefinitionFiles[1]))
				.thenReturn("");
		when(reader.readFullFileContents(stepDefinitionFiles[2]))
				.thenReturn("");

	}

	private void setUpAllFiles() {
		featureFiles = new File[3];
		featureFiles[0] = mock(File.class);
		featureFiles[1] = mock(File.class);
		featureFiles[2] = mock(File.class);

		screenFiles = new File[3];
		screenFiles[0] = mock(File.class);
		screenFiles[1] = mock(File.class);
		screenFiles[2] = mock(File.class);

		stepDefinitionFiles = new File[3];
		stepDefinitionFiles[0] = mock(File.class);
		stepDefinitionFiles[1] = mock(File.class);
		stepDefinitionFiles[2] = mock(File.class);

	}
}
