package northwoods.cukeripper.utils.parsing;

import java.util.ArrayList;
import java.util.List;

//import org.eclipse.swt.SWTException;

public class CukeConsole {

	public static final String MSG_COULD_NOT_FIND_FILE_FOR_SCREEN = "Could not find file for screen";
	private static List<String> printStatements = new ArrayList<String>();
	private static List<ICukeConsoleListener> listeners = new ArrayList<ICukeConsoleListener>();

	public static void println(String text, boolean error) {
		String prefix = error ? "        ERROR: " : "";
		text = prefix + text;
		CukeConsole.printStatements.add(text);

		if (error) {
			System.err.println("{" + Thread.currentThread().getName() + "} "
					+ text);
		} else {
			System.out.println("{" + Thread.currentThread().getName() + "} "
					+ text);
		}
		for (ICukeConsoleListener listener : listeners) {
			try {
				if (!listener.isDisposed())
					listener.onPrintLn(text, error);
				// } catch (SWTException e) {
				// // e.printStackTrace();
				// System.err
				// .println("An SWTException occured while trying to print to a cuke console listener");
			} catch (Exception e) {
				System.err
						.println("An Exception occured while trying to print to a cuke console listener");
			}
		}
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

	public static void addListener(ICukeConsoleListener listener) {
		listeners.add(listener);
	}
}
