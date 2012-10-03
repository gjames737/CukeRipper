package com.cukeripper.plugin.views;

import java.io.File;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeFileReader;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.CukeScreen;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.LoadedCukes;
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
import org.eclipse.swt.widgets.Display;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

public class CukeOutlinePresenter implements ICukeParsingListener {
	private static final long RESET_STOP_EVENTS_DELAY = 3000L;
	private static final String NO_FILE_FOUND = "No file was found. Refresh!";
	private static final String KEY_ROOT_PATH_TO_CUKES = "cukeripper.keys.KEY_ROOT_PATH_TO_CUKES";
	private static final String MY_PLUGIN_ID = "plugin.id.cukeripper.plugin.outline";
	private CukeFileReader reader;
	private FeatureFileParser featureParser;
	private Action featureTreeDoubleClickAction;

	private CukeOutlineView view;

	// private Job job_handleRefreshEvent;

	public CukeOutlinePresenter(CukeOutlineView _view) {
		this.view = _view;
		refresh(this.view.getCurrentFileRootPath());
	}

	private void refresh(final String currentFileRootPath) {
		LoadedCukes.getScreens().clear();

		try {
			reader = new CukeFileReader(currentFileRootPath);
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
		setViewToStoppableState();
		final String currentFileRootPath = clear ? "" : this.view
				.getCurrentFileRootPath();

		Job job_handleRefreshEvent = new Job("cukerefreshjob23425223r212") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				setViewToStoppableState();
				// Do something long running
				refresh(currentFileRootPath);
				// If you want to update the UI
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						// Do something in the user interface
						view.refresh();
					}
				});
				return Status.OK_STATUS;
			}

		};

		// Start the Job
		job_handleRefreshEvent.schedule();

		savePluginSettings();
	}

	private void setViewToRefreshableState() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				view.setToRefreshableState();
			}
		});
	}

	private void setViewToStoppableState() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				view.setToStoppableState();
			}
		});
	}

	public void cancelJobs() {
		CukeFileReader.stopAllEvents();

		resetStopAllEventsAfter(RESET_STOP_EVENTS_DELAY);
		view.setToDisabledState();

		// job_handleRefreshEvent.cancel();
		// view.getRefreshJob().cancel();

	}

	private void resetStopAllEventsAfter(final long delay) {

		Job job_resetStopAllEvents = new Job("job_resetStopAllEvents23432324") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				// Do something long running
				handleRefreshEvent(true);
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				CukeFileReader.resetStopAllEvents();
				// If you want to update the UI
				setViewToRefreshableState();
				return Status.OK_STATUS;
			}
		};
		job_resetStopAllEvents.schedule(delay);
	}

	void savePluginSettings() {
		// saves plugin preferences at the workspace level
		final Preferences prefs = InstanceScope.INSTANCE.getNode(MY_PLUGIN_ID);
		// Platform.getPreferencesService().getRootNode().node(Plugin.PLUGIN_PREFEERENCES_SCOPE).node(MY_PLUGIN_ID);
		// does all the above
		// behind the scenes
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				prefs.put(KEY_ROOT_PATH_TO_CUKES, view.getCurrentFileRootPath());
			}
		});

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
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				printStackTraceToMessage(e);
			}
		});
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
}
