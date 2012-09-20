package northwoods.cukeripper.sparkview;

import java.io.File;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.parsing.FeatureFileParser;

public class SparkView {

	private static CukeFileReader reader;
	private static FeatureFileParser featureParser;

	public static void main(String[] args) {
		System.out.println("@#!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		reader = new CukeFileReader("C:" + File.separator + "TFSBuild"
				+ File.separator + "CoPilot" + File.separator + "Trunk"
				+ File.separator + "CoPilotCukes");

		File[] featureFiles = reader.getAllFeatureFiles();
		for (File file : featureFiles) {
			System.err.println(file.getName());
		}

		featureParser = new FeatureFileParser(reader);
		CukeFeature feature0 = featureParser
				.getFeatureFromFile(featureFiles[0]);

		System.err.println(feature0.getName());

	}
}
