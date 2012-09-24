package com.cukeripper.plugin.views;

import java.io.File;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.parsing.FeatureFileParser;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public class CukeOutlinePresenter {

	private CukeFileReader reader;
	private FeatureFileParser featureParser;
	private Action doubleClickAction;
	private CukeOutlineView view;

	public CukeOutlinePresenter(CukeOutlineView _view) {
		this.view = _view;
		refresh();
	}

	private void refresh() {
		this.reader = new CukeFileReader(this.view.getCurrentFileRoot());
		this.featureParser = new FeatureFileParser(reader);
	}

	public File[] getfeatureFiles() {
		return reader.getAllFeatureFiles();
	}

	public FeatureFileParser getFeatureParser() {
		return featureParser;
	}

	void makeActions() {
		doubleClickAction = new Action() {

			public void run() {
				ISelection selection = view.getFeatureTree().getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();
				if (obj instanceof CukeFeature) {
					// view.showMessage("It's a cuke!");
					CukeFeature feature = (CukeFeature) obj;

					File featureFile = feature.getFile();

					view.openEditorOnFile(featureFile);
				} else if (obj instanceof CukeScenario) {
					CukeScenario scenario = (CukeScenario) obj;

					File scenarioFile = scenario.getFile();

					view.openEditorOnFile(scenarioFile);
				} else {
					view.showMessage("Double-click detected on "
							+ obj.toString());
				}
			}
		};
		hookClickActions();
	}

	private void hookClickActions() {
		view.getFeatureTree().addDoubleClickListener(
				new IDoubleClickListener() {
					public void doubleClick(DoubleClickEvent event) {
						doubleClickAction.run();
					}
				});
	}

	public void handleRefreshEvent() {
		refresh();
	}
}
