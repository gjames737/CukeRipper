package northwoods.cukeripper.tests.unit.parsing.multi_feature;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;

import northwoods.cukeripper.tests.unit.helpers.texts.FullTexts;
import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.parsing.CukeParser;
import northwoods.cukeripper.utils.parsing.FeatureFileParser;
import northwoods.cukeripper.utils.parsing.string.FeatureFileStringParser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class TestMultiFeatureFileParser {

	private static final int NUMBER_OF_FEATURES = 10;

	private FeatureFileParser featureFileParser;

	@Mock
	private CukeFileReader reader;

	@Mock
	private CukeParser parser;

	@Mock
	private FeatureFileStringParser featureFileStringParser;

	private File[] testFiles;

	private CukeFeature[] testCukeFeatures;

	private String[] orderedFeatureNames;
	private String[] featureNames;

	@Before
	public void Setup() {
		CukeFileReader.setJIT(false);
		CukeParser.THROW_ERRORS = true;
		setupFeatureNames();
		initMocks(this);

		try {
			setupMocks();
		} catch (Exception e) {

			e.printStackTrace();
			fail();
		}
		featureFileParser = new FeatureFileParser(reader);
		FullTexts.initFeatureScenarios();
	}

	@Test
	public void itHasAFileReader() {
		assertThat(featureFileParser.getReader(), is(reader));
	}

	@Test
	public void itReturnsEachFeatures() throws Exception {
		for (int i = 0; i < NUMBER_OF_FEATURES; i++) {
			CukeFeature actual_featureFromFile = featureFileParser
					.getFeatureFromFile(testFiles[i], featureFileStringParser,
							parser);
			assertThat(actual_featureFromFile, is(notNullValue()));
			assertThat(actual_featureFromFile, is(testCukeFeatures[i]));
		}
	}

	@Test
	public void itReturnsAListOfFeatures() throws Exception {

		CukeFeature[] actual_featuresFromFiles = featureFileParser
				.getSortedFeaturesInFiles(testFiles, featureFileStringParser,
						parser);
		assertThat(actual_featuresFromFiles, is(notNullValue()));
		assertThat(actual_featuresFromFiles.length, is(NUMBER_OF_FEATURES));

		for (int i = 0; i < NUMBER_OF_FEATURES; i++) {
			assertThat(actual_featuresFromFiles[i].getName(),
					is(orderedFeatureNames[i]));
		}
	}

	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	private void setupFeatureNames() {
		orderedFeatureNames = new String[NUMBER_OF_FEATURES];
		orderedFeatureNames[0] = "aaadewq";
		orderedFeatureNames[1] = "aavdewq";
		orderedFeatureNames[2] = "ay";
		orderedFeatureNames[3] = "b";
		orderedFeatureNames[4] = "fa3wa";
		orderedFeatureNames[5] = "nncv";
		orderedFeatureNames[6] = "noooo";
		orderedFeatureNames[7] = "reewrer";
		orderedFeatureNames[8] = "zzzcz";
		orderedFeatureNames[9] = "zzzdz";

		featureNames = new String[NUMBER_OF_FEATURES];
		featureNames[0] = orderedFeatureNames[6];
		featureNames[1] = orderedFeatureNames[8];
		featureNames[2] = orderedFeatureNames[3];
		featureNames[3] = orderedFeatureNames[5];
		featureNames[4] = orderedFeatureNames[1];
		featureNames[5] = orderedFeatureNames[0];
		featureNames[6] = orderedFeatureNames[9];
		featureNames[7] = orderedFeatureNames[2];
		featureNames[8] = orderedFeatureNames[4];
		featureNames[9] = orderedFeatureNames[7];

	}

	private void setupMocks() throws Exception {
		testFiles = new File[NUMBER_OF_FEATURES];
		testCukeFeatures = new CukeFeature[NUMBER_OF_FEATURES];
		for (int i = 0; i < NUMBER_OF_FEATURES; i++) {
			testFiles[i] = mock(File.class);

			testCukeFeatures[i] = new CukeFeature(featureNames[i], testFiles[i]);
			when(
					featureFileStringParser.parseOutFeature(testFiles[i],
							reader, parser)).thenReturn(testCukeFeatures[i]);
		}

	}
}
