package northwoods.cukeripper.utils.parsing;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.parsing.comparators.CukeFeatureComparator;
import northwoods.cukeripper.utils.parsing.string.FeatureFileStringParser;

public class FeatureFileParser {

	public static final String END_OF_CONTENTS_PADDING = "    ";
	public static final String CONSOLE_STR_PARSING_FEATURE_FROM_FILE = "Parsing feature from file";

	public static final String CONSOLE_STR_ERROR_CLEANING_CONTENTS = "Error cleaning contents.";
	private CukeFileReader reader;
	private CukeParser parser;

	public FeatureFileParser(CukeFileReader reader) {
		this.reader = reader;
		this.parser = new CukeParser();
	}

	public CukeFileReader getReader() {
		return reader;
	}

	public CukeFeature getFeatureFromFile(File file) throws Exception {
		return getFeatureFromFile(file, new FeatureFileStringParser(), parser);
	}

	public CukeFeature getFeatureFromFile(File file,
			FeatureFileStringParser featureFileStringParser, CukeParser _parser)
			throws Exception {
		CukeFeature parsedOutFeature = featureFileStringParser.parseOutFeature(
				file, reader, _parser);
		return parsedOutFeature;
	}

	public File findFileForStatement(GWTStatement gwtStatement)
			throws Exception {
		return FeatureFileParser.findFileForStatement(gwtStatement, reader,
				parser);
	}

	public static File findFileForStatement(GWTStatement gwtStatement,
			CukeFileReader reader, CukeParser parser) throws Exception {
		File stepFile = parser.findStepFileForStatement(reader, gwtStatement);
		if (stepFile == null) {
			CukeConsole.println("it is null here", true);
		}
		return stepFile;
	}

	public CukeFeature[] getSortedFeaturesInFiles(File[] featureFiles)
			throws Exception {
		return getSortedFeaturesInFiles(featureFiles,
				new FeatureFileStringParser(), parser);
	}

	public CukeFeature[] getSortedFeaturesInFiles(File[] files,
			FeatureFileStringParser featureFileStringParser, CukeParser _parser)
			throws Exception {
		List<CukeFeature> listOfFeatures = new ArrayList<CukeFeature>();
		for (int i = 0; i < files.length; i++) {
			CukeFeature featureParsed = getFeatureFromFile(files[i],
					featureFileStringParser, _parser);
			if (featureParsed != null)
				listOfFeatures.add(featureParsed);
		}
		Collections.sort(listOfFeatures, new CukeFeatureComparator());

		CukeFeature[] features = new CukeFeature[listOfFeatures.size()];
		for (int i = 0; i < features.length; i++) {
			features[i] = listOfFeatures.get(i);
		}

		return features;
	}

}
