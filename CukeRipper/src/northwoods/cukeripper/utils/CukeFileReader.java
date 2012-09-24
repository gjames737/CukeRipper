package northwoods.cukeripper.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import northwoods.cukeripper.utils.parsing.FeatureFileParser;

public class CukeFileReader {

	private File rootOfAllFiles;
	private List<File> allFiles;

	public CukeFileReader(String root) {
		this.rootOfAllFiles = new File(root);
		reloadRoot();
	}

	public void reloadRoot() {
		allFiles = new ArrayList<File>();
		loadAllFilesFromDirectory(rootOfAllFiles);
		resetFeatureBuilder();
	}

	private void resetFeatureBuilder() {
		if (LoadedCukes.getFeatureBuilder() == null) {
			loadFeatureBuilder();
		}
	}

	public void loadFeatureBuilder() {
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
		throw new RuntimeException("Not yet implemented");
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

	private void loadAllFilesFromDirectory(File rootFile) {

		File[] files = rootFile.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				loadAllFilesFromDirectory(files[i]);
			} else {
				allFiles.add(files[i]);
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

	// public File getFileWithFeature(String feature) {
	// List<CukeFeature> features = LoadedCukes.getFeatureBuilder()
	// .getFeatures();
	// for (CukeFeature cukeFeature : features) {
	// if (cukeFeature.getName().equals(features)) {
	// return allFiles.get()
	// }
	// }
	// return null;
	// }
}
