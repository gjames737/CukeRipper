package northwoods.cukeripper.utils.parsing;

import java.io.File;
import java.util.List;

import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.GWTStatement;

public class StepFileParser {

	public static final String CONSOLE_STR_PARSING_STATEMENTS_FROM_FILE = "Parsing statements from file";
	private CukeFileReader reader;
	private CukeParser parser;

	public StepFileParser(CukeFileReader reader) {
		this.reader = reader;
		this.parser = new CukeParser();
	}

	public CukeFileReader getReader() {
		return reader;
	}

	public List<GWTStatement> getGWTStatementsFromFile(File file) {
		CukeConsole.println(CONSOLE_STR_PARSING_STATEMENTS_FROM_FILE + " "
				+ file.getAbsolutePath(), false);
		String fullContents = getStepFileContents(file);
		List<GWTStatement> statements = parser.parseStatementsFromStepFile(
				file, fullContents);

		return statements;
	}

	private String getStepFileContents(File file) {
		return reader.readFullFileContents(file);
	}
}
