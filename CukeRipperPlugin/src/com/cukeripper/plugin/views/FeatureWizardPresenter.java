package com.cukeripper.plugin.views;

import java.util.ArrayList;
import java.util.List;

import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.FeatureBuilder;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.LoadedCukes;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;

import com.cukeripper.plugin.CommonSettings;
import com.cukeripper.plugin.views.CukeOutlinePresenter.ICukeOutlinePresenterUpdateListener;

public class FeatureWizardPresenter extends FeaturePresenter implements
		ICukeParsingListener, ICukeOutlinePresenterUpdateListener {

	private FeatureWizardView view;
	private String currentRootFilePath = "";

	public FeatureWizardPresenter(FeatureWizardView featureWizard) {
		this.view = featureWizard;
		loadPluginSettings();
		CukeOutlinePresenter.addCukeOutlinePresenterUpdateListener(this);
	}

	public String[] getAllPossibleStatementStrings() {
		GWTStatement[] statements = getAllPossibleStatements();
		String[] strs = new String[statements.length];
		for (int i = 0; i < strs.length; i++) {
			strs[i] = statements[i].getStatement();
		}

		return strs;
	}

	private GWTStatement[] getAllPossibleStatements() {

		FeatureBuilder featureBuilder = LoadedCukes.getFeatureBuilder();
		if (featureBuilder == null)
			return new GWTStatement[] {};

		List<CukeFeature> features = featureBuilder.getFeatures();
		List<GWTStatement> statementsList = new ArrayList<GWTStatement>();

		for (CukeFeature feature : features) {
			List<CukeScenario> scens = feature.getScenarios();
			for (CukeScenario cukeScenario : scens) {
				List<GWTStatement> states = cukeScenario.getStatements();
				for (GWTStatement gwtStatement : states) {
					statementsList.add(gwtStatement);
				}
			}
		}
		GWTStatement[] statements = new GWTStatement[statementsList.size()];
		for (int i = 0; i < statementsList.size(); i++) {
			statements[i] = statementsList.get(i);
		}

		return statements;

	}

	@Override
	public void handleRefreshEvent(boolean clear) {
		refresh(currentRootFilePath);
	}

	@SuppressWarnings("deprecation")
	void loadPluginSettings() {
		Preferences prefs = new InstanceScope()
				.getNode(CommonSettings.MY_PLUGIN_ID);

		currentRootFilePath = prefs.get(CommonSettings.KEY_ROOT_PATH_TO_CUKES,
				"").toString();
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

	protected void printStackTraceToMessage(Exception e) {
		// TODO
		// StackTraceElement[] trace = e.getStackTrace();
		// String msg = e.getMessage();
		// for (StackTraceElement s : trace) {
		// msg += "\n" + s.getClassName() + "." + s.getMethodName();
		// msg += "  In file [" + s.getFileName() + "] at line "
		// + s.getLineNumber();
		// }
		// view.showMessage(msg);
	}

	@Override
	public void onRefreshed() {
		view.refresh();
	}

}
