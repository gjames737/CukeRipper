package northwoods.cukeripper.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CukeFileReader {

	private File rootOfAllFiles;
	private List<File> allFiles;

	public CukeFileReader(String root) {
		this.rootOfAllFiles = new File(root);
		allFiles = new ArrayList<File>();
		loadAllFilesFromDirectory(rootOfAllFiles);
	}

	public String readFullFileContents(File f) {
		try {
			return readFile(f.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public File[] getAllFeatureFiles() {

		List<File> fileList = new ArrayList<File>();
		for (File file : allFiles) {
			if (isFeatureFile(file))
				fileList.add(file);
		}
		File[] featureFiles = new File[fileList.size()];
		for (int i = 0; i < fileList.size(); i++) {
			featureFiles[i] = fileList.get(i);
		}
		return featureFiles;
	}

	public File[] getAllScreenFiles() {
		throw new RuntimeException("Not yet implemented");
	}

	public File[] getAllStepDefinitionFiles() {
		throw new RuntimeException("Not yet implemented");
	}

	private boolean isFeatureFile(File file) {
		boolean isFeatureFile = !file.isDirectory()
				&& file.getName().contains(".feature");

		return isFeatureFile;
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
}
