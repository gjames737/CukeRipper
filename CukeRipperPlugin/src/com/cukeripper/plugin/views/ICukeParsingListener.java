package com.cukeripper.plugin.views;

public interface ICukeParsingListener {

	public void onCukeFileReaderError(Exception e);

	public void onFeatureParseException(Exception e);

	public void onStepFileParserException(Exception e);

}
