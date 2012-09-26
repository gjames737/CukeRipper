package com.cukeripper.plugin.views;

import java.io.File;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.parsing.FeatureFileParser;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

public class CukeOutlinePresenter {

	private static final String NO_FILE_FOUND = "No file was found. Refresh!";
	private static final String KEY_ROOT_PATH_TO_CUKES = "cukeripper.keys.KEY_ROOT_PATH_TO_CUKES";
	private static final String MY_PLUGIN_ID = "plugin.id.cukeripper.plugin.outline";
	private CukeFileReader reader;
	private FeatureFileParser featureParser;
	private Action featureTreeDoubleClickAction;

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
		featureTreeDoubleClickAction = new Action() {
			public void run() {
				handleTreeItemDoubleClick();
			}

		};

		hookClickActions();
	}

	private void handleTreeItemClick() {
		getCurrentFeatureTreeSelection();
		Object obj = getCurrentFeatureTreeSelection();
		if (obj instanceof CukeFeature) {
			handleFeatureSingleClick((CukeFeature) obj);
		} else if (obj instanceof CukeScenario) {
			handleScenarioSingleClick((CukeScenario) obj);
		} else if (obj instanceof GWTStatement) {
			handleStatementSingleClick((GWTStatement) obj);
		}
	}

	private void handleFeatureSingleClick(CukeFeature feature) {

		// view.showMessage(msg + "  " + LoadedCukes.getScreens().size());

	}

	private void handleScenarioSingleClick(CukeScenario obj) {
		// TODO Auto-generated method stub

	}

	private void handleStatementSingleClick(GWTStatement obj) {
		// TODO Auto-generated method stub

	}

	private Object getCurrentFeatureTreeSelection() {
		ISelection selection = view.getFeatureTree().getSelection();
		return ((IStructuredSelection) selection).getFirstElement();
	}

	private void handleTreeItemDoubleClick() {

		Object obj = getCurrentFeatureTreeSelection();
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
						featureTreeDoubleClickAction.run();
					}
				});
		view.getFeatureTree().addSelectionChangedListener(
				new ISelectionChangedListener() {
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						handleTreeItemClick();
					}
				});

	}

	public void handleRefreshEvent() {
		refresh();
		savePluginSettings();
	}

	void savePluginSettings() {
		// saves plugin preferences at the workspace level
		Preferences prefs =
		// Platform.getPreferencesService().getRootNode().node(Plugin.PLUGIN_PREFEERENCES_SCOPE).node(MY_PLUGIN_ID);
		InstanceScope.INSTANCE.getNode(MY_PLUGIN_ID); // does all the above
														// behind the scenes

		prefs.put(KEY_ROOT_PATH_TO_CUKES, view.getCurrentFileRootPath());

		try {
			// prefs are automatically flushed during a plugin's "super.stop()".
			prefs.flush();
		} catch (BackingStoreException e) {
			// TODO write a real exception handler.
			e.printStackTrace();
		}
	}

	void loadPluginSettings() {
		Preferences prefs = new InstanceScope().getNode(MY_PLUGIN_ID);

		// you might want to call prefs.sync() if you're worried about others
		// changing your settings
		view.setCurrentFileRootPath(prefs.get(KEY_ROOT_PATH_TO_CUKES, "")
				.toString());

	}

	public FeatureTreeContentProvider getFeatureTreeContentProvider() {
		return new FeatureTreeContentProvider(getfeatureFiles(),
				getFeatureParser());
	}

	public SupportScreenTreeContentProvider getSupportScreensTreeContentProvider() {
		return new SupportScreenTreeContentProvider(reader);
	}

}
