package northwoods.cukeripper.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import northwoods.cukeripper.utils.parsing.CukeConsole;
import northwoods.cukeripper.utils.parsing.FeatureFileParser;

public class CukeFileReader {

	public static final String CONSOLE_STR_READING_FILE = "Reading";
	private File rootOfAllFiles;
	private List<File> allFiles;
	private static boolean STOP_ALL_EVENTS = false;

	public CukeFileReader(String root) throws Exception {
		this.rootOfAllFiles = new File(root);
		reloadRoot();
	}

	public void reloadRoot() throws Exception {
		allFiles = new ArrayList<File>();
		if (rootOfAllFiles.exists() && rootOfAllFiles.isDirectory()) {
			loadAllFilesFromDirectory(rootOfAllFiles);
			resetFeatureBuilder();
		}
	}

	private void resetFeatureBuilder() throws Exception {
		if (LoadedCukes.getFeatureBuilder() == null) {
			loadFeatureBuilder();
		}
	}

	public void loadFeatureBuilder() throws Exception {
		FeatureBuilder featureBuilder = new FeatureBuilder();

		File[] featureFiles = getAllFeatureFiles();
		FeatureFileParser parser = new FeatureFileParser(this);
		for (int i = 0; i < featureFiles.length; i++) {
			CukeFeature parsedfeature = parser
					.getFeatureFromFile(featureFiles[i]);
			featureBuilder.addParsedFeature(parsedfeature);
		}
		LoadedCukes.resetFeatureBuilder(featureBuilder);

	}

	public String readFullFileContents(File file) {
		CukeConsole.println(
				CONSOLE_STR_READING_FILE + " " + file.getAbsolutePath(), false);
		try {

			return readFile(file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public File[] getAllFeatureFiles() {
		return getAllFilesWithExtension(".feature");
	}

	public File[] getAllScreenFiles() {
		File[] possibleScreenFiles = getAllFilesWithExtension(".rb");
		List<File> realScreenFilesList = new ArrayList<File>();

		for (int i = 0; i < possibleScreenFiles.length; i++) {
			File file = possibleScreenFiles[i];
			if (isARealScreenFile(file))
				realScreenFilesList.add(file);
		}

		File[] realScreenFiles = new File[realScreenFilesList.size()];
		for (int i = 0; i < realScreenFilesList.size(); i++) {
			realScreenFiles[i] = realScreenFilesList.get(i);
		}

		return realScreenFiles;
	}

	private boolean isARealScreenFile(File file) {
		String contents = readFullFileContents(file);
		boolean isRealScreenFileBool = contents.contains(CommonRips.CLASS)
				&& !contents.contains(CommonRips.DOLLAR_SLASH)
				&& !contents.contains(CommonRips.SLASH_POINT);
		return isRealScreenFileBool;
	}

	public File[] getAllStepDefinitionFiles() {
		File[] possibleSetpFiles = getAllFilesWithExtension(".rb");
		List<File> realStepFilesList = new ArrayList<File>();

		for (int i = 0; i < possibleSetpFiles.length; i++) {
			File file = possibleSetpFiles[i];
			if (isARealStepFile(file))
				realStepFilesList.add(file);
		}

		File[] realStepFiles = new File[realStepFilesList.size()];
		for (int i = 0; i < realStepFilesList.size(); i++) {
			realStepFiles[i] = realStepFilesList.get(i);
		}

		return realStepFiles;
	}

	private boolean isARealStepFile(File file) {
		String contents = readFullFileContents(file);
		boolean isRealStepFileBool = !contents.contains(CommonRips.CLASS)
				&& contents.contains(CommonRips.DOLLAR_SLASH)
				&& contents.contains(CommonRips.SLASH_POINT);
		return isRealStepFileBool;
	}

	private boolean isFileWithExtension(File file, String extension) {
		boolean isFeatureFile = !file.isDirectory()
				&& file.getName().contains(extension);

		return isFeatureFile;
	}

	private File[] getAllFilesWithExtension(String extension) {
		List<File> fileList = new ArrayList<File>();
		for (File file : allFiles) {

			if (isFileWithExtension(file, extension))
				fileList.add(file);
		}
		File[] featureFiles = new File[fileList.size()];
		for (int i = 0; i < fileList.size(); i++) {
			featureFiles[i] = fileList.get(i);
		}
		return featureFiles;
	}

	private void loadAllFilesFromDirectory(final File rootFile) {

		File[] files = rootFile.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					loadAllFilesFromDirectory(files[i]);
				} else {
					allFiles.add(files[i]);
				}
			}
		}

	}

	private static String readFile(String path) throws IOException {
		FileInputStream stream = new FileInputStream(new File(path));
		try {
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
					fc.size());
			/* Instead of using default, pass in a decoder. */
			return Charset.defaultCharset().decode(bb).toString();
		} finally {
			stream.close();
		}
	}

	public static void resetStopAllEvents() {
		STOP_ALL_EVENTS = false;
	}

	public static void stopAllEvents() {
		STOP_ALL_EVENTS = true;
	}

	public static boolean isAllEventsCanceled() {
		return STOP_ALL_EVENTS;
	}

}
