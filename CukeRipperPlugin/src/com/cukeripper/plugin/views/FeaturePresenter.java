package com.cukeripper.plugin.views;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.LoadedCukes;
import northwoods.cukeripper.utils.parsing.FeatureFileParser;

public class FeaturePresenter implements ICukeParsingListener {
	protected CukeFileReader reader;
	protected FeatureFileParser featureFileParser;

	protected void refresh(final String currentFileRootPath) {
		LoadedCukes.getScreens().clear();

		try {
			List<File> excludedFiles = new ArrayList<File>();

			reader = new CukeFileReader(currentFileRootPath, excludedFiles);
			featureFileParser = new FeatureFileParser(reader);
		} catch (Exception e) {
			onCukeFileReaderError(e);
		}

	}

	public void handleRefreshEvent(boolean clear) {

	}

	public File[] getfeatureFiles() {
		File[] allFeatureFiles = reader.getAllFeatureFiles();
		return allFeatureFiles;
	}

	public FeatureFileParser getFeatureParser() {
		return featureFileParser;
	}

	@Override
	public void onCukeFileReaderError(Exception e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFeatureParseException(Exception e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStepFileParserException(Exception e) {
		// TODO Auto-generated method stub

	}

}
