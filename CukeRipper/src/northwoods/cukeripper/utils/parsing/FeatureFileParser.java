package northwoods.cukeripper.utils.parsing;

import northwoods.cukeripper.utils.CukeFileReader;

public class FeatureFileParser {

	private CukeFileReader reader;

	public FeatureFileParser(CukeFileReader reader) {
		this.reader = reader;
	}

	public CukeFileReader getReader() {
		return reader;
	}
}
