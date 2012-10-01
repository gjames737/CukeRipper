package com.cukeripper.plugin.views;

public interface ICukeParsingListener {

	public void onCukeFileReader(Exception e);

	public void onFeatureParseException(Exception e);

	public void onStepFileParserException(Exception e);

}
