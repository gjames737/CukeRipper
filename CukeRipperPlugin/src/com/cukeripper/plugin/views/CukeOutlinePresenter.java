package com.cukeripper.plugin.views;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.CukeScreen;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.LoadedCukes;
import northwoods.cukeripper.utils.parsing.CukeConsole;
import northwoods.cukeripper.utils.parsing.FeatureFileParser;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
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

import com.cukeripper.plugin.views.providers.content.FeatureTreeContentProvider;
import com.cukeripper.plugin.views.providers.content.SupportScreenTreeContentProvider;

public class CukeOutlinePresenter implements ICukeParsingListener {
	// private static final long RESET_STOP_EVENTS_DELAY = 1000L;
	private static final String NO_FILE_FOUND = "No file was found. Refresh!";
	private static final String KEY_ROOT_PATH_TO_CUKES = "cukeripper.keys.KEY_ROOT_PATH_TO_CUKES";
	private static final String MY_PLUGIN_ID = "plugin.id.cukeripper.plugin.outline";
	// private static final String KEY_SELECTED_FEATURE =
	// "cukeripper.keys.KEY_SELECTED_FEATURE";
	// private static final String KEY_SELECTED_SCENARIO =
	// "cukeripper.keys.KEY_SELECTED_SCENARIO";
	// private static final String KEY_SELECTED_STATEMENT =
	// "cukeripper.keys.KEY_SELECTED_STATEMENT";
	private CukeFileReader reader;
	private FeatureFileParser featureParser;

	private Action featureTreeDoubleClickAction;
	private String currentFileRootPath;

	private CukeOutlineView view;

	private boolean refreshing = false;

	// private String selectedFeature = "";
	// private String selectedScenario = "";
	// private String selectedStatement = "";

	public CukeOutlinePresenter(CukeOutlineView _view) {
		this.view = _view;
		refresh(this.view.getCurrentFileRootPath());
	}

	private void runRefresh() {
		long startTime = System.currentTimeMillis();
		refreshing = true;
		setViewToStoppableState();
		refresh(currentFileRootPath);
		view.refresh();
		refreshing = false;
		long time = (System.currentTimeMillis() - startTime) / 1000L;
		CukeConsole.println("Parse time: " + time + " secs", false);
	}

	private void refresh(final String currentFileRootPath) {
		LoadedCukes.getScreens().clear();

		try {
			List<File> excludedFiles = new ArrayList<File>();

			reader = new CukeFileReader(currentFileRootPath, excludedFiles);
			featureParser = new FeatureFileParser(reader);
		} catch (Exception e) {
			onCukeFileReaderError(e);
		}

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

	private void handleFeatureTreeItemClick() {
		Object obj = getCurrentFeatureTreeSelection();
		if (obj instanceof CukeFeature) {
			handleFeatureSingleClick((CukeFeature) obj);
		} else if (obj instanceof CukeScenario) {
			handleScenarioSingleClick((CukeScenario) obj);
		} else if (obj instanceof GWTStatement) {
			handleStatementSingleClick((GWTStatement) obj);
		}
	}

	private void handleSupportScreenTreeItemDoubleClick() {
		Object obj = getCurrentSuppotScreensTreeSelection();
		if (obj instanceof CukeScreen) {
			handleCukeScreenDoubleClick((CukeScreen) obj);
		}
	}

	private void handleCukeScreenDoubleClick(CukeScreen screen) {
		File screenFile = screen.getScreenFile();
		if (screenFile != null)
			view.openEditorOnFile(screenFile);
	}

	private void handleFeatureSingleClick(CukeFeature feature) {
		// selectedFeature = feature.toString();
	}

	private void handleScenarioSingleClick(CukeScenario scenario) {
		// selectedScenario = scenario.toString();
	}

	private void handleStatementSingleClick(GWTStatement statement) {
		// selectedStatement = statement.toString();
	}

	private Object getCurrentFeatureTreeSelection() {
		ISelection selection = view.getFeatureTree().getSelection();
		return ((IStructuredSelection) selection).getFirstElement();
	}

	private Object getCurrentSuppotScreensTreeSelection() {
		ISelection selection = view.getSupportScreensTree().getSelection();
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

	private void handleStatementDoubleClick(GWTStatement statement) {

		File statementFile = findFileForStatement(statement);
		statement.setStepFile(statementFile);

		if (statementFile != null) {
			view.openEditorOnFile(statementFile);
		} else {
			File featureFile = statement.getFeatureFile();
			if (featureFile != null) {
				view.openEditorOnFile(featureFile);
			} else {
				view.showMessage(NO_FILE_FOUND);
			}
		}
	}

	private File findFileForStatement(GWTStatement statement) {
		File statementFile = null;
		try {
			statementFile = featureParser.findFileForStatement(statement);
		} catch (Exception e) {
			CukeConsole.println(e.getMessage(), true);
			e.printStackTrace();
		}
		return statementFile;
	}

	private void hookClickActions() {
		view.getFeatureTree().addDoubleClickListener(
				new IDoubleClickListener() {
					public void doubleClick(DoubleClickEvent event) {
						featureTreeDoubleClickAction.run();
					}
				});
		view.getSupportScreensTree().addDoubleClickListener(
				new IDoubleClickListener() {
					@Override
					public void doubleClick(DoubleClickEvent event) {
						handleSupportScreenTreeItemDoubleClick();
					}
				});
		view.getFeatureTree().addSelectionChangedListener(
				new ISelectionChangedListener() {
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						handleFeatureTreeItemClick();
					}
				});

	}

	public void handleRefreshEvent(boolean clear) {
		savePluginSettings();

		currentFileRootPath = clear ? "" : this.view.getCurrentFileRootPath();
		if (clear) {
			Job job_clear = new Job("clear") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					refresh(currentFileRootPath);
					view.refresh();
					return Status.OK_STATUS;
				}
			};
			job_clear.schedule();
			return;
		} else {
			setViewToStoppableState();

			runRefresh();
		}

	}

	private void setViewToRefreshableState() {
		view.setToRefreshableState();
	}

	private void setViewToStoppableState() {
		view.setToStoppableState();
	}

	public void stopJobs() {
		CukeFileReader.stopAllEvents();
		resetStopAllEventsAfter();
		view.setToDisabledState();
	}

	private void resetStopAllEventsAfter() {
		while (refreshing) {
			// System.err.println("~~~~~~~~");
		}
		handleRefreshEvent(true);
		CukeFileReader.resetStopAllEvents();
		setViewToRefreshableState();

	}

	void savePluginSettings() {

		// saves plugin preferences at the workspace level
		final Preferences prefs = InstanceScope.INSTANCE.getNode(MY_PLUGIN_ID);
		prefs.put(KEY_ROOT_PATH_TO_CUKES, view.getCurrentFileRootPath());

		// Object obj = getCurrentFeatureTreeSelection();
		// if (obj instanceof CukeFeature) {
		// selectedFeature = obj.toString();
		// } else if (obj instanceof CukeScenario) {
		// selectedScenario = obj.toString();
		// } else if (obj instanceof GWTStatement) {
		// selectedStatement = obj.toString();
		// }
		//
		// prefs.put(KEY_SELECTED_FEATURE, selectedFeature);
		// prefs.put(KEY_SELECTED_SCENARIO, selectedScenario);
		// prefs.put(KEY_SELECTED_STATEMENT, selectedStatement);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			CukeConsole.println(e.getMessage(), true);
			e.printStackTrace();
		}
	}

	void loadPluginSettings() {
		Preferences prefs = new InstanceScope().getNode(MY_PLUGIN_ID);

		// selectedFeature = prefs.get(KEY_SELECTED_FEATURE, "");
		// selectedScenario = prefs.get(KEY_SELECTED_SCENARIO, "");
		// selectedStatement = prefs.get(KEY_SELECTED_STATEMENT, "");

		// you might want to call prefs.sync() if you're worried about others
		// changing your settings
		view.setCurrentFileRootPath(prefs.get(KEY_ROOT_PATH_TO_CUKES, "")
				.toString());

	}

	public FeatureTreeContentProvider getFeatureTreeContentProvider() {
		return new FeatureTreeContentProvider(this, getfeatureFiles(),
				getFeatureParser());
	}

	public SupportScreenTreeContentProvider getSupportScreensTreeContentProvider() {
		return new SupportScreenTreeContentProvider(this, reader);
	}

	@Override
	public void onFeatureParseException(Exception e) {
		printStackTraceToMessage(e);
	}

	@Override
	public void onStepFileParserException(Exception e) {
		printStackTraceToMessage(e);
	}

	@Override
	public void onCukeFileReaderError(final Exception e) {
		printStackTraceToMessage(e);
	}

	private void printStackTraceToMessage(Exception e) {
		StackTraceElement[] trace = e.getStackTrace();
		String msg = e.getMessage();
		for (StackTraceElement s : trace) {
			msg += "\n" + s.getClassName() + "." + s.getMethodName();
			msg += "  In file [" + s.getFileName() + "] at line "
					+ s.getLineNumber();
		}
		view.showMessage(msg);
	}

	public void handleStopBtnEvent() {
		stopJobs();
	}

	// public CukeFeature getSelectedFeature() {
	// Object[] features = ((FeatureTreeContentProvider) view.getFeatureTree()
	// .getContentProvider()).getElements(null);
	// for (Object obj : features) {
	// if (obj.toString().equals(selectedFeature)) {
	// return (CukeFeature) obj;
	// }
	// }
	// return null;
	// }
	//
	// public CukeScenario getSelectedScenario() {
	// Object[] features = ((FeatureTreeContentProvider) view.getFeatureTree()
	// .getContentProvider()).getElements(null);
	// for (Object obj : features) {
	// CukeFeature thisFeature = (CukeFeature) obj;
	// List<CukeScenario> theseScenarios = thisFeature.getScenarios();
	// for (CukeScenario cukeScenario : theseScenarios) {
	// if (cukeScenario.toString().equals(selectedScenario)) {
	// return cukeScenario;
	// }
	// }
	// }
	// return null;
	// }
	//
	// public GWTStatement getSelectedStatement() {
	// Object[] features = ((FeatureTreeContentProvider) view.getFeatureTree()
	// .getContentProvider()).getElements(null);
	// for (Object obj : features) {
	// CukeFeature thisFeature = (CukeFeature) obj;
	// List<CukeScenario> theseScenarios = thisFeature.getScenarios();
	// for (CukeScenario cukeScenario : theseScenarios) {
	// List<GWTStatement> theseStatements = cukeScenario
	// .getStatements();
	// for (GWTStatement gwtStatement : theseStatements) {
	// if (gwtStatement.toString().equals(selectedStatement)) {
	// return gwtStatement;
	// }
	// }
	// }
	// }
	// return null;
	// }

}
