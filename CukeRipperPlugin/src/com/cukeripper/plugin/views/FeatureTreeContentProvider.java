package com.cukeripper.plugin.views;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.parsing.FeatureFileParser;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class FeatureTreeContentProvider implements ITreeContentProvider {

	private FeatureFileParser featureParser;
	private File[] featureFiles;

	public FeatureTreeContentProvider(File[] _featureFiles,
			FeatureFileParser _featureParser) {
		this.featureFiles = _featureFiles;
		this.featureParser = _featureParser;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(Object inputElement) {
		CukeFeature[] allFeatures = getFeatures();
		return allFeatures;
	}

	private CukeFeature[] getFeatures() {
		List<CukeFeature> featuresList = new ArrayList<CukeFeature>();
		for (int i = 0; i < featureFiles.length; i++) {
			featuresList.add(featureParser.getFeatureFromFile(featureFiles[i]));
		}
		CukeFeature[] featuresArray = new CukeFeature[featuresList.size()];
		for (int i = 0; i < featuresArray.length; i++) {
			featuresArray[i] = featuresList.get(i);
		}

		return featuresArray;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

}