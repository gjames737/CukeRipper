package northwoods.cukeripper.utils.parsing;

import java.util.ArrayList;
import java.util.List;

public class CukeConsole {

	private static List<String> printStatements = new ArrayList<String>();

	public static void println(String text, boolean error) {
		String prefix = error ? "ERROR: " : "";
		text = prefix + text;
		CukeConsole.printStatements.add(text);
		if (error)
			System.err.println(text);
		else
			System.out.println(text);
	}

	public static String getAllOutput() {
		String output = "";
		for (String statement : CukeConsole.printStatements) {
			output += "\n" + statement;
		}
		return output;
	}

	public static void clear() {
		CukeConsole.printStatements.clear();
	}
}
