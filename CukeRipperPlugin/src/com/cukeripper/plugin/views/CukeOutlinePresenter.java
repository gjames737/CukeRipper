package com.cukeripper.plugin.views;

import java.io.File;

import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.parsing.FeatureFileParser;

public class CukeOutlinePresenter {

	private static final String ROOT_OF_FILES = "C:" + File.separator
			+ "TFSBuild" + File.separator + "CoPilot" + File.separator
			+ "Trunk" + File.separator + "CoPilotCukes";
	private CukeFileReader reader;
	private FeatureFileParser featureParser;

	public CukeOutlinePresenter() {
		this.reader = new CukeFileReader(ROOT_OF_FILES);
		this.featureParser = new FeatureFileParser(reader);
	}

	public File[] getfeatureFiles() {
		return reader.getAllFeatureFiles();
	}

	public FeatureFileParser getFeatureParser() {
		return featureParser;
	}

}
