package com.cukeripper.plugin.views;

import java.io.File;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.parsing.FeatureFileParser;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public class CukeOutlinePresenter {

	private static final String NO_FILE_FOUND = "No file was found. Refresh!";
	private CukeFileReader reader;
	private FeatureFileParser featureParser;
	private Action doubleClickAction;
	private CukeOutlineView view;

	public CukeOutlinePresenter(CukeOutlineView _view) {
		this.view = _view;
		refresh();
	}

	private void refresh() {
		String currentFileRootPath = this.view.getCurrentFileRootPath();
		this.reader = new CukeFileReader(currentFileRootPath);
		this.featureParser = new FeatureFileParser(reader);
		view.refresh();
	}

	public File[] getfeatureFiles() {
		File[] allFeatureFiles = reader.getAllFeatureFiles();
		return allFeatureFiles;
	}

	public FeatureFileParser getFeatureParser() {
		return featureParser;
	}

	void makeActions() {
		doubleClickAction = new Action() {
			public void run() {
				handleTreeItemDoubleClick();
			}

		};
		hookClickActions();
	}

	private void handleTreeItemDoubleClick() {
		ISelection selection = view.getFeatureTree().getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();
		if (obj instanceof CukeFeature) {
			handleFeatureDoubleClick((CukeFeature) obj);
		} else if (obj instanceof CukeScenario) {
			handleScenarioDoubleClick((CukeScenario) obj);
		} else if (obj instanceof GWTStatement) {
			handleStatementDoubleClick((GWTStatement) obj);
		} else {
			view.showMessage("Double-click detected on " + obj.toString());
		}
	}

	private void handleFeatureDoubleClick(CukeFeature feature) {
		File featureFile = feature.getFile();
		view.openEditorOnFile(featureFile);
	}

	private void handleScenarioDoubleClick(CukeScenario scenario) {
		File scenarioFile = scenario.getFile();
		view.openEditorOnFile(scenarioFile);
	}

	private void handleStatementDoubleClick(GWTStatement statment) {
		File statmentFile = statment.getStepFile();
		if (statmentFile != null) {
			view.openEditorOnFile(statmentFile);
		} else {
			File featureFile = statment.getFeatureFile();
			if (featureFile != null) {
				view.openEditorOnFile(featureFile);
			} else {
				view.showMessage(NO_FILE_FOUND);
			}
		}
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
