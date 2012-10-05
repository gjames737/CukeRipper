package com.cukeripper.plugin.views;

import java.util.List;

import northwoods.cukeripper.utils.CommonRips;
import northwoods.cukeripper.utils.CukeFeature;
import northwoods.cukeripper.utils.CukeScenario;
import northwoods.cukeripper.utils.FeatureBuilder;
import northwoods.cukeripper.utils.GWTStatement;
import northwoods.cukeripper.utils.GWTStatement.StatementType;
import northwoods.cukeripper.utils.LoadedCukes;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;

import com.cukeripper.plugin.CommonSettings;
import com.cukeripper.plugin.views.CukeOutlinePresenter.ICukeOutlinePresenterUpdateListener;

public class FeatureWizardPresenter extends FeaturePresenter implements
		ICukeParsingListener, ICukeOutlinePresenterUpdateListener {

	private FeatureWizardView view;
	private String currentRootFilePath = "";
	private CukeFeature[] features;
	private CukeScenario[] scenarios;
	private GWTStatement[] statements;
	public static final String[] gwtItems = new String[] { "",
			CommonRips.GIVEN, CommonRips.WHEN, CommonRips.THEN };

	public FeatureWizardPresenter(FeatureWizardView featureWizard) {
		this.view = featureWizard;
		loadPluginSettings();
		CukeOutlinePresenter.addCukeOutlinePresenterUpdateListener(this);
	}

	public String[] getAllFeatureStrings() {
		features = getAllFeatures();
		String[] strs = new String[features.length];
		for (int i = 0; i < strs.length; i++) {
			strs[i] = getItemForFeature(features[i]);
		}
		return strs;
	}

	private CukeFeature[] getAllFeatures() {
		FeatureBuilder featureBuilder = LoadedCukes.getFeatureBuilder();
		if (featureBuilder == null)
			return new CukeFeature[] {};

		List<CukeFeature> featuresList = featureBuilder.getFeatures();
		CukeFeature[] features = new CukeFeature[featuresList.size()];
		for (int i = 0; i < features.length; i++) {
			features[i] = featuresList.get(i);
		}

		return features;
	}

	public String[] getAllScenarioStrings() {
		scenarios = getAllScenarios();
		String[] strs = new String[scenarios.length];
		for (int i = 0; i < strs.length; i++) {
			strs[i] = getItemForScenario(scenarios[i]);
		}
		return strs;
	}

	private CukeScenario[] getAllScenarios() {
		FeatureBuilder featureBuilder = LoadedCukes.getFeatureBuilder();
		if (featureBuilder == null)
			return new CukeScenario[] {};

		return featureBuilder.listAllScenariosAsArray();

	}

	public String[] getAllPossibleStatementStrings() {
		statements = getAllPossibleStatements();
		String[] strs = new String[statements.length];
		for (int i = 0; i < strs.length; i++) {
			strs[i] = getItemForStatement(statements[i]);
		}

		return strs;
	}

	private GWTStatement[] getAllPossibleStatements() {
		FeatureBuilder featureBuilder = LoadedCukes.getFeatureBuilder();
		if (featureBuilder == null)
			return new GWTStatement[] {};

		return featureBuilder.listStatementsAsArray();
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

	public void handleFeatureSelected() {
		String featureStr = view.getComboFeatureSelectedString();
		int featureIndex = getFeatureIndex(featureStr);
		CukeFeature currentSelectedFeature = features[featureIndex];
		List<CukeScenario> scenariosList = currentSelectedFeature
				.getScenarios();
		String[] scenarioStrings = new String[scenariosList.size()];
		for (int i = 0; i < scenarioStrings.length; i++) {
			scenarioStrings[i] = getItemForScenario(scenariosList.get(i));
		}
		view.updateScenariosDropdown(scenarioStrings);
		view.updateFeatureText(currentSelectedFeature);
		// view.clearStatementInputs();
	}

	public void handleScenarioSelected() {
		String scenarioStr = view.getComboScenarioSelectedString();
		int scenarioIndex = getScenarioIndex(scenarioStr);
		CukeScenario currentSelectedScenario = scenarios[scenarioIndex];
		view.onScenarioSelected(currentSelectedScenario);
	}

	private int getFeatureIndex(String featureStr) {
		for (int i = 0; i < features.length; i++) {
			if (getItemForFeature(features[i]).equals(featureStr)) {
				return i;
			}
		}
		return -1;
	}

	private int getScenarioIndex(String scenarioStr) {
		for (int i = 0; i < scenarios.length; i++) {
			if (getItemForScenario(scenarios[i]).equals(scenarioStr)) {
				return i;
			}
		}
		return -1;
	}

	private String getItemForScenario(CukeScenario scenario) {
		return scenario.getName();
	}

	private String getItemForFeature(CukeFeature cukeFeature) {
		return cukeFeature.toString();
	}

	private String getItemForStatement(GWTStatement statement) {
		return statement.getStatement();
	}

	public int getGWTIndexForType(StatementType type) {
		for (int i = 0; i < gwtItems.length; i++) {
			if (gwtItems[i].toLowerCase().equals(type.name().toLowerCase())) {
				return i;
			}
		}
		return -1;
	}

	public int getStatementIndexForStatement(GWTStatement gwtStatement) {
		for (int i = 0; i < statements.length; i++) {
			if (statements[i].getStatement()
					.equals(gwtStatement.getStatement())) {
				return i;
			}
		}
		return -1;
	}
}
