package northwoods.cukeripper.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import northwoods.cukeripper.utils.parsing.CukeConsole;
import northwoods.cukeripper.utils.parsing.CukeParser;

public class LoadedCukes {

	private static List<CukeScreen> screens = new ArrayList<CukeScreen>();
	private static FeatureBuilder featureBuilder = null;

	public static List<CukeScreen> getScreens() {
		return screens;
	}

	public static void setScreens(List<CukeScreen> screens) {
		LoadedCukes.screens = screens;
	}

	public static int addScreen(String screenName) {
		int found = LoadedCukes.findScreen(screenName);
		if (found != -1) {
			return found;
		} else {
			LoadedCukes.screens.add(new CukeScreen(screenName));
			return LoadedCukes.screens.size() - 1;
		}
	}

	private static int findScreen(String screenToFind) {
		for (int i = 0; i < screens.size(); i++) {
			CukeScreen screen = screens.get(i);
			if (screen.getName().equals(screenToFind)) {
				return i;
			}
		}
		return -1;
	}

	public static void setFeatureBuilder(FeatureBuilder _featureBuilder) {
		if (LoadedCukes.featureBuilder == null)
			resetFeatureBuilder(_featureBuilder);
	}

	public static void resetFeatureBuilder(FeatureBuilder _featureBuilder) {
		featureBuilder = _featureBuilder;
	}

	public static FeatureBuilder getFeatureBuilder() {
		return featureBuilder;
	}

	public static void attachScreensToFiles(CukeFileReader reader,
			CukeParser parser) {
		File[] allScreenFiles = reader.getAllScreenFiles();
		for (CukeScreen screen : screens) {
			if (!CukeFileReader.isCanceled())
				return;
			if (screen.getScreenFile() == null) {
				boolean found = false;
				for (File screenFile : allScreenFiles) {
					if (parser.fileBelongsToScreen(reader, screen.getName(),
							screenFile)) {
						screen.setScreenFile(screenFile);
						found = true;
					}
				}
				if (!found) {
					CukeConsole.println("           "
							+ CukeConsole.MSG_COULD_NOT_FIND_FILE_FOR_SCREEN
							+ ": " + screen.getName(), false);
				}
			}
		}

	}
}
