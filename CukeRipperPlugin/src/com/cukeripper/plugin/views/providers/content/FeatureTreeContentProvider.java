package com.cukeripper.plugin.views.providers.content;

import java.io.File;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.parsing.FeatureFileParser;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.cukeripper.plugin.views.ICukeParsingListener;

public class FeatureTreeContentProvider implements ITreeContentProvider {

	private FeatureFileParser featureFileParser;
	private File[] featureFiles;
	private ICukeParsingListener listener;

	public FeatureTreeContentProvider(ICukeParsingListener _listener,
			File[] _featureFiles, FeatureFileParser _featureParser) {
		this.listener = _listener;
		this.featureFiles = _featureFiles;
		this.featureFileParser = _featureParser;
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

		try {
			return featureFileParser.getSortedFeaturesInFiles(featureFiles);
		} catch (Exception e) {
			listener.onFeatureParseException(e);
		}

		return new CukeFeature[] {};
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof CukeFeature) {
			return ((CukeFeature) parentElement).getScenarios().toArray();
		} else if (parentElement instanceof CukeScenario) {
			CukeScenario scenario = (CukeScenario) parentElement;
			return scenario.getStatementsArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof CukeFeature) {
			return ((CukeFeature) element).getScenarios().size() > 0;
		} else if (element instanceof CukeScenario) {
			return ((CukeScenario) element).getStatements().size() > 0;
		}
		return false;
	}

}
